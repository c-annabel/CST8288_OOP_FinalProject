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

-- Routes table
CREATE TABLE Routes (
    route_id INT AUTO_INCREMENT PRIMARY KEY,
    route_name VARCHAR(100) UNIQUE NOT NULL
);

-- Stations table
CREATE TABLE Stations (
    station_id INT AUTO_INCREMENT PRIMARY KEY,
    station_name VARCHAR(100) NOT NULL,
    location VARCHAR(150)
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
    FOREIGN KEY (route_id) REFERENCES Routes(route_id)
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