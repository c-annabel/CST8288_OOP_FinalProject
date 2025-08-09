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
import transferobjects.VehicleDTO;

public interface VehicleDAO {
    void insertVehicle(VehicleDTO vehicle) throws SQLException;
    List<VehicleDTO> getAllVehicles() throws SQLException;
    VehicleDTO getVehicleByNumber(String vehicleNumber) throws SQLException;
    void updateVehicle(VehicleDTO vehicle) throws SQLException;
    void deleteVehicle(String vehicleNumber) throws SQLException;
}
