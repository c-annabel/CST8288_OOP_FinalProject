/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccesslayer;

/**
 *
 * @author Chen Wang
 */
import businesslayer.System_Control;
import businesslayer.VehicleFactory;
import dataaccesslayer.DAOinterface.RouteDAO;
import transferobjects.Vehicle;
import dataaccesslayer.DAOinterface.VehicleDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import transferobjects.CredentialsDTO;
import transferobjects.DieselBus;
import transferobjects.ElectricLightRail;
import transferobjects.DieselElectricTrain;
import transferobjects.GPSDTO;
import transferobjects.RouteDTO;

public class VehicleDAOImpl implements VehicleDAO {
    private CredentialsDTO cred;
    private DataSource source;
    
    public VehicleDAOImpl(CredentialsDTO cred){
        this.cred = cred;
        this.source = new DataSource(cred);
    }
    
    @Override
    public void insertVehicle(Vehicle vehicle) throws SQLException {
        String sql = "INSERT INTO Vehicles (vehicle_type, fuel_type, consumption_rate, max_passengers, ) VALUES (?, ?, ?, ?)";
        try (Connection con = source.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, this.getVehicleType(vehicle));
            ps.setString(2, vehicle.getFuelType());
            ps.setDouble(3, vehicle.getConsumptionRate());
            ps.setInt(4, vehicle.getMaxPassengers());
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    @Override
    public List<Vehicle> getAllVehicles() throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM Vehicles";
        try (Connection con = source.createConnection();
             Statement stmt = con.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String type = rs.getString("vehicle_type");
                Vehicle vehicle = VehicleFactory.createVehicle(type);
                vehicle.setVehicleType(type);
                vehicle.setVehicleID(rs.getInt("vehicle_id"));
                vehicle.setVehicleNumber(rs.getString("vehicle_number"));
                vehicle.setFuelType(rs.getString("fuel_type"));
                vehicle.setConsumptionRate(rs.getDouble("consumption_rate"));
                vehicle.setMaxPassengers(rs.getInt("max_passengers"));
                vehicle.setRouteId(rs.getInt("route_id"));
                vehicle.setHoursOfcomponents(rs.getInt("hoursOfcomponents"));
                vehicle.setDiagnostics(rs.getString("diagnostics"));
                vehicle.setMaintenance_threshold(rs.getInt("maintenance_threshold"));
                vehicle.setFuel_alert_threshold(rs.getInt("fuel_alert_threshold"));
                vehicles.add(vehicle);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return vehicles;
    }
    
    @Override
    public Vehicle getVehicleByNumber(String vehicleNumber) throws SQLException {
        String sql = "SELECT * FROM Vehicles WHERE vehicle_number = ?";
        try (Connection con = source.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             ps.setString(1, vehicleNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Vehicle vehicle = VehicleFactory.createVehicle(rs.getString("vehicle_type"));
                    vehicle.setVehicleNumber(rs.getString("vehicle_number"));
                    vehicle.setFuelType(rs.getString("fuel_type"));
                    vehicle.setConsumptionRate(rs.getDouble("consumption_rate"));
                    vehicle.setMaxPassengers(rs.getInt("max_passengers"));
                    vehicle.setRouteId(rs.getInt("route_id"));
                    vehicle.setHoursOfcomponents(rs.getInt("hoursOfcomponents"));
                    vehicle.setDiagnostics(rs.getString("diagnostics"));
                    vehicle.setMaintenance_threshold(rs.getInt("diagnostics"));
                    vehicle.setFuel_alert_threshold(rs.getInt("fuel_alert_threshold"));
                    return vehicle;
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public void updateVehicle(Vehicle vehicle) throws SQLException {
        // Get the new route ID from the updated vehicle
        int newRouteId = vehicle.getRouteId();
        System_Control logic = new System_Control(cred);
        // Initialize variables for station coordinates
        String latitude = null;
        String longitude = null;

        // Only fetch station if a valid route is assigned
        if (newRouteId > 0) {
            // Create RouteDAO to access station information
            RouteDAO routeDAO = new RouteDAOImpl(cred);

            // Get all stations for the new route
            List<RouteDTO> routeStations = routeDAO.getRouteStationsByRouteId(newRouteId);

            // If route has stations, get coordinates of first station
            if (!routeStations.isEmpty()) {
                RouteDTO firstStation = routeStations.get(0);
                latitude = firstStation.getLatitude();
                longitude = firstStation.getLongitude();
            }
        }

        // Build SQL query with new latitude/longitude fields
        String sql = "UPDATE Vehicles "
                + "SET fuel_type = ?, "
                + "consumption_rate = ?, "
                + "max_passengers = ?, "
                + "route_id = ?, "
                + "hoursOfcomponents = ?, "
                + "diagnostics = ? "
                + "WHERE vehicle_number = ?";
        
        GPSDTO gps = new GPSDTO();
        gps.setIdentifier(vehicle.getVehicleNumber());
        gps.setType("Vehicle");
        gps.setTimestamp("0");
        gps.setLatitude(latitude);
        gps.setLongitude(longitude);
        
        logic.addGps(gps);
        try (Connection con = source.createConnection();
            PreparedStatement ps = con.prepareStatement(sql);) {
            
            // Set parameters for the update
            ps.setString(1, vehicle.getFuelType());
            ps.setDouble(2, vehicle.getConsumptionRate());
            ps.setInt(3, vehicle.getMaxPassengers());
            ps.setInt(4, vehicle.getRouteId());
            ps.setInt(5, vehicle.getHoursOfcomponents());
            ps.setString(6, vehicle.getDiagnostics());
            ps.setString(7, vehicle.getVehicleNumber());

            // Execute the update
            ps.executeUpdate();
        } 
    }
    
    @Override
    public void deleteVehicle(String vehicleNumber) throws SQLException {
        try (Connection con = source.createConnection()) {
            // First delete from child tables
            deleteFromTable(con, "DELETE FROM FuelConsumptionLogs WHERE vehicle_id IN (SELECT vehicle_id FROM Vehicles WHERE vehicle_number = ?)", vehicleNumber);
            deleteFromTable(con, "DELETE FROM Maintenance_Report WHERE vehicle_number = ?", vehicleNumber);
            deleteFromTable(con, "DELETE FROM GPS_Tracking WHERE vehicle_number = ?", vehicleNumber);

            // Then delete the vehicle
            deleteFromTable(con, "DELETE FROM Vehicles WHERE vehicle_number = ?", vehicleNumber);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    private String getVehicleType(Vehicle vehicle) {
        if (vehicle instanceof DieselBus) return "Diesel Bus";
        if (vehicle instanceof ElectricLightRail) return "Electric Light Rail";
        if (vehicle instanceof DieselElectricTrain) return "Diesel-Electric Train";
        return "Unknown";
    }
    
    private void deleteFromTable(Connection con, String sql, String vehicleNumber){
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, vehicleNumber);
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}