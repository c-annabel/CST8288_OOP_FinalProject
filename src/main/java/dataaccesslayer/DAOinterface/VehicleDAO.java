/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccesslayer.DAOinterface;

/**
 *
 * @author Chen Wang
 */
import java.sql.SQLException;
import java.util.List;
import transferobjects.Vehicle;

public interface VehicleDAO {
    void insertVehicle(Vehicle vehicle) throws SQLException;
    List<Vehicle> getAllVehicles() throws SQLException;
    Vehicle getVehicleByNumber(String vehicleNumber) throws SQLException;
    void updateVehicle(Vehicle vehicle) throws SQLException;
    void deleteVehicle(String vehicleNumber) throws SQLException;
}
