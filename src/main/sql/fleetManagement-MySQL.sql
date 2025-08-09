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


-- Insert sample users
INSERT INTO Users (name, email, password, user_type) VALUES
('John Manager', 'john.manager@transit.com', SHA2('manager123', 256), 'Manager'),
('Sarah Operator', 'sarah.operator@transit.com', SHA2('operator456', 256), 'Operator'),
('Mike Supervisor', 'mike.supervisor@transit.com', SHA2('super789', 256), 'Manager'),
('Emily Technician', 'emily.tech@transit.com', SHA2('techpass', 256), 'Operator');

-- Insert sample logs
INSERT INTO logs (description) VALUES
('Break started - 15 minutes'),
('Break ended'),
('Vehicle maintenance scheduled'),
('Fuel alert triggered'),
('Route change notification'),
('System login'),
('GPS tracking initiated');

-- Insert user logs
INSERT INTO UsersLog (user_id, logs_ID) VALUES
(1, 1), (1, 2), (2, 1), (2, 2), (3, 3), (4, 4), (1, 6), (2, 7);

-- Insert sample routes
INSERT INTO Routes (route_name) VALUES
('Downtown Express'),
('University Loop'),
('River Cross'),
('Westside Connector');

-- Insert sample stations
INSERT INTO Stations (station_name, latitude, longitude) VALUES
('Central Station', '43.651070', '-79.347015'),
('University Terminal', '43.660780', '-79.395919'),
('Riverfront Plaza', '43.642560', '-79.374987'),
('Westside Hub', '43.678340', '-79.412345'),
('Commerce Square', '43.655432', '-79.381234'),
('Parkview Station', '43.663210', '-79.402345');

-- Insert route-station mappings
INSERT INTO RouteStations (route_id, station_id, stop_order) VALUES
(1, 1, 1), (1, 5, 2), (1, 3, 3),
(2, 2, 1), (2, 6, 2), (2, 1, 3),
(3, 3, 1), (3, 4, 2), (3, 6, 3),
(4, 4, 1), (4, 5, 2), (4, 1, 3);

-- Insert sample vehicles (trigger will auto-generate vehicle numbers)
INSERT INTO Vehicles (vehicle_type, fuel_type, consumption_rate, max_passengers, route_id, diagnostics, maintenance_threshold, hoursOfcomponents, fuel_alert_threshold) VALUES
('Diesel Bus', 'Diesel', 25.5, 50, 1, 'No Need service', 500.00, 320, 40.0),
('Electric Light Rail', 'Electricity', 18.2, 120, 2, 'Need service', 600.00, 620, 30.0),
('Diesel-Electric Train', 'Diesel', 42.3, 200, 3, 'No Need service', 750.00, 450, 50.0),
('Diesel Bus', 'Diesel', 26.1, 50, 4, 'No Need service', 500.00, 280, 40.0),
('Electric Light Rail', 'Electricity', 17.8, 120, 1, 'No Need service', 600.00, 520, 30.0);

-- Insert fuel consumption logs
INSERT INTO FuelConsumptionLogs (vehicle_id, consumption_date, fuel_consumed, distance_covered) VALUES
(1, '2023-08-01', 120.5, 280.2),
(1, '2023-08-02', 115.3, 275.8),
(2, '2023-08-01', 85.2, 320.5),
(2, '2023-08-02', 82.7, 315.3),
(3, '2023-08-01', 210.4, 450.8),
(3, '2023-08-02', 205.7, 445.2),
(4, '2023-08-01', 118.7, 285.4),
(5, '2023-08-01', 80.3, 310.2);

-- Insert maintenance reports
INSERT INTO Maintenance_Report (vehicle_number, report_status, report_description) VALUES
('DB-1', 'Completed', 'Routine oil change and filter replacement'),
('ELR-1', 'Pending', 'Catenary system inspection needed'),
('DET-1', 'In Progress', 'Brake system maintenance'),
('DB-2', 'Completed', 'Tire rotation and pressure check'),
('ELR-2', 'Scheduled', 'Monthly electrical system diagnostic');

-- Insert GPS tracking data
INSERT INTO GPS_Tracking (vehicle_number, timestamp, latitude, longitude) VALUES
('DB-1', '2023-08-09 08:15:32', '43.650120', '-79.348765'),
('ELR-1', '2023-08-09 08:20:15', '43.661230', '-79.396543'),
('DET-1', '2023-08-09 08:25:47', '43.643210', '-79.376543'),
('DB-2', '2023-08-09 08:30:22', '43.679120', '-79.413456'),
('ELR-2', '2023-08-09 08:35:19', '43.654320', '-79.382109');
