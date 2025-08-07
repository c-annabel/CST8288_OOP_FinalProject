/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccesslayer;

/**
 *
 * @author Chen Wang
 */
import businesslayer.VehicleFactory;
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

public class VehicleDAOImpl implements VehicleDAO {
    private CredentialsDTO cred;
    
    public VehicleDAOImpl(CredentialsDTO cred){
        this.cred = cred;
    }
    
    @Override
    public void insertVehicle(Vehicle vehicle) throws SQLException {
        String sql = "INSERT INTO Vehicles (vehicle_number, vehicle_type, fuel_type, consumption_rate, max_passengers, route_id) VALUES (?, ?, ?, ?, ?, ?)";
        DataSource source = new DataSource(cred);
        try (Connection con = source.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, vehicle.getVehicleNumber());
            ps.setString(2, vehicle.getClass().getSimpleName().replaceAll("([A-Z])", " $1").trim());
            ps.setString(3, vehicle.getFuelType());
            ps.setDouble(4, vehicle.getConsumptionRate());
            ps.setInt(5, vehicle.getMaxPassengers());
            ps.setInt(6, vehicle.getRouteId());
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    @Override
    public List<Vehicle> getAllVehicles() throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM Vehicles";
        DataSource source = new DataSource(cred);
        try (Connection con = source.createConnection();
             Statement stmt = con.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String type = rs.getString("vehicle_type");
                Vehicle vehicle = VehicleFactory.createVehicle(type);
                vehicle.setVehicleNumber(rs.getString("vehicle_number"));
                vehicle.setFuelType(rs.getString("fuel_type"));
                vehicle.setConsumptionRate(rs.getDouble("consumption_rate"));
                vehicle.setMaxPassengers(rs.getInt("max_passengers"));
                vehicle.setRouteId(rs.getInt("route_id"));
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
        DataSource source = new DataSource(cred);
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
                    return vehicle;
                }
            }
        }
        return null;
    }
    
    @Override
    public void updateVehicle(Vehicle vehicle) throws SQLException {
        String sql = "UPDATE Vehicles SET fuel_type = ?, consumption_rate = ?, max_passengers = ?, route_id = ? WHERE vehicle_number = ?";
        DataSource source = new DataSource(cred);
        try (Connection con = source.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, vehicle.getFuelType());
            ps.setDouble(2, vehicle.getConsumptionRate());
            ps.setInt(3, vehicle.getMaxPassengers());
            ps.setInt(4, vehicle.getRouteId());
            ps.setString(5, vehicle.getVehicleNumber());
            ps.executeUpdate();
        }
    }
    
    @Override
    public void deleteVehicle(String vehicleNumber) throws SQLException {
        String sql = "DELETE FROM Vehicles WHERE vehicle_number = ?";
        DataSource source = new DataSource(cred);
        try (Connection con = source.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, vehicleNumber);
            ps.executeUpdate();
        }
    }
    
    private String getVehicleType(Vehicle vehicle) {
        if (vehicle instanceof DieselBus) return "Diesel Bus";
        if (vehicle instanceof ElectricLightRail) return "Electric Light Rail";
        if (vehicle instanceof DieselElectricTrain) return "Diesel-Electric Train";
        return "Unknown";
    }
    
}