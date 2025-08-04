/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datalayer;

import domain.Vehicle;
import util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class VehicleDAOImpl implements VehicleDAO {

    @Override
    public boolean addVehicle(Vehicle v) {
        String sql = "INSERT INTO Vehicles (vehicle_number, vehicle_type, fuel_type, consumption_rate, max_passengers, route_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, v.getVehicleNumber());
            stmt.setString(2, v.getVehicleType());
            stmt.setString(3, v.getFuelType());
            stmt.setDouble(4, v.getConsumptionRate());
            stmt.setInt(5, v.getMaxPassengers());
            stmt.setInt(6, v.getRouteId());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
