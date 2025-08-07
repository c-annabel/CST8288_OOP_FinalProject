DROP DATABASE IF EXISTS fleetManagement;

CREATE DATABASE fleetManagement;

use fleetManagement;

-- User table
CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_type ENUM('Manager', 'Operator') NOT NULL,
    FOREIGN KEY (User_Log_ID) REFERENCES UsersLog(User_Log_ID),
);

CREATE TABLE UsersLog(
    User_Log_ID INT AUTO_INCREMENT PRIMARY KEY,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (log_Id) REFERENCES log(log_Id)
);

CREATE TABLE log(
    log_Id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(100)
)

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
    maintenance_threshold DECIMAL(10, 2) DEFAULT 100.00,
    fuel_alert_threshold DECIMAL(10, 2) DEFAULT 50.00,  -- Fuel alert threshold for FR-04
    FOREIGN KEY (route_id) REFERENCES Routes(route_id)
);

-- Fuel Consumption Logs table (for FR-04)
CREATE TABLE FuelConsumptionLogs (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    vehicle_id INT NOT NULL,
    consumption_date DATE NOT NULL,
    fuel_consumed DECIMAL(10, 2) NOT NULL,  -- Fuel consumed in liters or kWh
    distance_covered DECIMAL(10, 2),  -- Distance covered (optional, in kilometers)
    FOREIGN KEY (vehicle_id) REFERENCES Vehicles(vehicle_id)
);

CREATE TABLE Maintenance_Report(
    report_id INT AUTO_INCREMENT PRIMARY KEY,
    vehicle_number VARCHAR(50) UNIQUE NOT NULL,
    report_status VARCHAR(50),
    report_description VARCHAR(100),
    FOREIGN KEY (vehicle_number) REFERENCES Vehicles(vehicle_number)
);

CREATE TABLE GPS_Tracking(
    gps_id INT AUTO_INCREMENT PRIMARY KEY,
    vehicle_number VARCHAR(50) UNIQUE NOT NULL,
    timestamp VARCHAR(50),
    latitude VARCHAR(50),
    longitude VARCHAR(50),
    FOREIGN KEY (vehicle_number) REFERENCES Vehicles(vehicle_number)
);


-- View for fuel consumption report (for FR-06)
CREATE VIEW FuelConsumptionReport AS
SELECT v.vehicle_number, v.vehicle_type, SUM(fcl.fuel_consumed) AS total_fuel_consumed, 
       AVG(fcl.distance_covered) AS average_distance_covered
FROM Vehicles v
JOIN FuelConsumptionLogs fcl ON v.vehicle_id = fcl.vehicle_id
GROUP BY v.vehicle_id;