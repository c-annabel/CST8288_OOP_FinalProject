DROP DATABASE IF EXISTS fleetManagement;

CREATE DATABASE fleetManagement;

use fleetManagement;

-- User table
CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_type ENUM('Manager', 'Operator') NOT NULL
);

CREATE TABLE logs(
    logs_Id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(100)
);

CREATE TABLE UsersLog(
    User_Log_ID INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    logs_ID INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (logs_Id) REFERENCES logs(logs_Id)
);

-- Routes table
CREATE TABLE Routes (
    route_id INT AUTO_INCREMENT PRIMARY KEY,
    route_name VARCHAR(100) UNIQUE NOT NULL
);

-- Stations table
CREATE TABLE Stations (
    station_id INT AUTO_INCREMENT PRIMARY KEY,
    station_name VARCHAR(100) NOT NULL,
    latitude VARCHAR(50),
    longitude VARCHAR(50)
);

-- Mapping table between routes and their stations
CREATE TABLE RouteStations (
    route_station_id INT AUTO_INCREMENT PRIMARY KEY,
    route_id INT NOT NULL,
    station_id INT NOT NULL,
    stop_order INT NOT NULL,  -- order of the station in the route
    FOREIGN KEY (route_id) REFERENCES Routes(route_id),
    FOREIGN KEY (station_id) REFERENCES Stations(station_id)
);

-- Updated Vehicles table with foreign key to Routes
CREATE TABLE Vehicles (
    vehicle_id INT AUTO_INCREMENT PRIMARY KEY,
    vehicle_number VARCHAR(50) UNIQUE NOT NULL,
    vehicle_type ENUM('Diesel Bus', 'Electric Light Rail', 'Diesel-Electric Train') NOT NULL,
    fuel_type VARCHAR(50),
    consumption_rate DECIMAL(10,2),
    max_passengers INT,
    route_id INT,  -- FK to assigned route
    diagnostics ENUM('Need service', 'No Need service') DEFAULT('No Need service'),
    maintenance_threshold DECIMAL(10, 2) DEFAULT 500.00,
    hoursOfcomponents INT DEFAULT 0,
    fuel_alert_threshold DECIMAL(10, 2) DEFAULT 50.00,  -- Fuel alert threshold for FR-04
    FOREIGN KEY (route_id) REFERENCES Routes(route_id),
    INDEX (vehicle_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Per-prefix sequence table
CREATE TABLE VehicleNumberSeq (
    prefix VARCHAR(10) PRIMARY KEY,
    seq INT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Fuel Consumption Logs table (for FR-04)
CREATE TABLE FuelConsumptionLogs (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    vehicle_id INT NOT NULL,
    consumption_date DATE NOT NULL,
    fuel_consumed DECIMAL(10, 2) NOT NULL,  -- Fuel consumed in liters or kWh
    distance_covered DECIMAL(10, 2),  -- Distance covered (optional, in kilometers)
    FOREIGN KEY (vehicle_id) REFERENCES Vehicles(vehicle_id) ON DELETE CASCADE
);

CREATE TABLE Maintenance_Report(
    report_id INT AUTO_INCREMENT PRIMARY KEY,
    vehicle_number VARCHAR(50) UNIQUE NOT NULL,
    report_status VARCHAR(50),
    report_description VARCHAR(100),
    FOREIGN KEY (vehicle_number) REFERENCES Vehicles(vehicle_number) ON DELETE CASCADE
);

CREATE TABLE GPS_Tracking(
    gps_id INT AUTO_INCREMENT PRIMARY KEY,
    vehicle_number VARCHAR(50) UNIQUE NOT NULL,
    timestamp VARCHAR(50),
    latitude VARCHAR(50),
    longitude VARCHAR(50),
    FOREIGN KEY (vehicle_number) REFERENCES Vehicles(vehicle_number) ON DELETE CASCADE
);


-- View for fuel consumption report (for FR-06)
CREATE VIEW FuelConsumptionReport AS
SELECT v.vehicle_number, v.vehicle_type, SUM(fcl.fuel_consumed) AS total_fuel_consumed, 
       AVG(fcl.distance_covered) AS average_distance_covered
FROM Vehicles v
JOIN FuelConsumptionLogs fcl ON v.vehicle_id = fcl.vehicle_id
GROUP BY v.vehicle_id;


-- VIEW: route -> ordered stations
CREATE OR REPLACE VIEW vw_route_stations AS
SELECT 
    rs.route_id,
    r.route_name,
    rs.stop_order,
    s.station_id,
    s.station_name,
    s.latitude,
    s.longitude
FROM RouteStations rs
JOIN Routes   r ON r.route_id = rs.route_id
JOIN Stations s ON s.station_id = rs.station_id
ORDER BY rs.route_id, rs.stop_order;

DELIMITER //

CREATE TRIGGER bi_vehicles_number
BEFORE INSERT ON Vehicles
FOR EACH ROW
BEGIN
    DECLARE pfx VARCHAR(10);
    DECLARE n INT;

    -- Map vehicle_type to your desired prefix
    SET pfx = CASE NEW.vehicle_type
        WHEN 'Diesel Bus'           THEN 'DB'   
        WHEN 'Electric Light Rail'  THEN 'ELR'
        WHEN 'Diesel-Electric Train'THEN 'DET'
        ELSE 'GEN'
    END;

    -- Atomically get next sequence for this prefix
    INSERT INTO VehicleNumberSeq(prefix, seq) VALUES (pfx, 1)
    ON DUPLICATE KEY UPDATE seq = LAST_INSERT_ID(seq + 1);

    SET n = LAST_INSERT_ID();

    -- Build the final number, e.g., BN-1, BN-2, ELR-1 ...
    SET NEW.vehicle_number = CONCAT(pfx, '-', n);
END;
//

DELIMITER ;


-- sample data for test:
-- Insert sample routes
INSERT INTO Routes (route_name) VALUES
('Downtown Express'),
('University Line'),
('Lakeshore Loop');

-- Insert sample stations
INSERT INTO Stations (station_name, latitude, longitude) VALUES
('Central Station', '43.6532', '-79.3832'),
('Market Square', '43.6506', '-79.3848'),
('University Campus', '43.6629', '-79.3957'),
('Research Park', '43.6731', '-79.3876'),
('Lakefront Park', '43.6331', '-79.4186'),
('Marina Bay', '43.6275', '-79.4223'),
('City Hall', '43.6530', '-79.3830'),
('Tech District', '43.6582', '-79.3801'),
('Sports Complex', '43.6400', '-79.3900'),
('Medical Center', '43.6700', '-79.4000');

-- Assign stations to routes with stop orders

-- Downtown Express route
INSERT INTO RouteStations (route_id, station_id, stop_order) VALUES
(1, 1, 1),   -- Central Station (first stop)
(1, 2, 2),   -- Market Square
(1, 7, 3),   -- City Hall
(1, 8, 4);   -- Tech District

-- University Line route
INSERT INTO RouteStations (route_id, station_id, stop_order) VALUES
(2, 1, 1),   -- Central Station (first stop)
(2, 3, 2),   -- University Campus
(2, 4, 3),   -- Research Park
(2, 10, 4);  -- Medical Center

-- Lakeshore Loop route
INSERT INTO RouteStations (route_id, station_id, stop_order) VALUES
(3, 1, 1),   -- Central Station (first stop)
(3, 5, 2),   -- Lakefront Park
(3, 6, 3),   -- Marina Bay
(3, 9, 4),   -- Sports Complex
(3, 1, 5);   -- Back to Central Station (loop)

-- Insert sample vehicles assigned to routes
INSERT INTO Vehicles (vehicle_type, fuel_type, consumption_rate, max_passengers, route_id) VALUES
('Diesel Bus', 'Diesel', 8.5, 50, 1),
('Electric Light Rail', 'Electricity', 15.2, 120, 2),
('Diesel-Electric Train', 'Diesel', 25.7, 200, 3),
('Diesel Bus', 'Diesel', 8.2, 50, 1),
('Electric Light Rail', 'Electricity', 14.8, 120, 2);

-- Insert sample maintenance data for components
UPDATE Vehicles SET 
    hoursOfcomponents = 600
WHERE vehicle_id = 1;  -- First diesel bus

UPDATE Vehicles SET 
    hoursOfcomponents = 950
WHERE vehicle_id = 2;  -- First electric rail

UPDATE Vehicles SET 
    hoursOfcomponents = 500
WHERE vehicle_id = 4;  -- Second diesel bus

-- Insert sample fuel consumption logs
INSERT INTO FuelConsumptionLogs (vehicle_id, consumption_date, fuel_consumed, distance_covered) VALUES
(1, '2023-10-01', 42.5, 300),
(1, '2023-10-02', 38.7, 280),
(2, '2023-10-01', 120.5, 450),
(2, '2023-10-02', 118.2, 440),
(3, '2023-10-01', 185.3, 600),
(4, '2023-10-01', 41.8, 290),
(5, '2023-10-01', 115.7, 430);

-- Insert sample operators
INSERT INTO Users (name, email, password, user_type) VALUES
('John Transit', 'john@transit.com', SHA2('pass123', 256), 'Operator'),
('Sarah Conductor', 'sarah@transit.com', SHA2('pass123', 256), 'Operator'),
('Mike Controller', 'mike@transit.com', SHA2('pass123', 256), 'Manager');

-- Insert break logs
INSERT INTO logs (description) VALUES 
('BREAK: Lunch break - 30 mins'),
('BREAK: Rest break - 15 mins'),
('BREAK: Lunch break - 45 mins'),
('BREAK: Equipment check - 20 mins');

INSERT INTO UsersLog (user_id, logs_ID) VALUES
(1, 1),  -- John: Lunch break
(1, 2),  -- John: Rest break
(2, 3),  -- Sarah: Lunch break
(2, 4);  -- Sarah: Equipment check