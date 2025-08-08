/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccesslayer;

import transferobjects.FuelConsumptionDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import transferobjects.CredentialsDTO;

/**
 *
 * @author chinmoy
 */
public class FuelConsumptionDAO {
    private CredentialsDTO cred;
    
    public FuelConsumptionDAO(CredentialsDTO cred){
        this.cred = cred;
    }
    
    public List<FuelConsumptionDTO> getFuelConsumptionReport() {
        List<FuelConsumptionDTO> fuelData = new ArrayList<>();
        DataSource source = new DataSource(cred);
        String query = "SELECT v.vehicle_number, v.vehicle_type, SUM(fcl.fuel_consumed) AS total_fuel_consumed, "
                     + "AVG(fcl.distance_covered) AS average_distance_covered "
                     + "FROM Vehicles v "
                     + "JOIN FuelConsumptionLogs fcl ON v.vehicle_id = fcl.vehicle_id "
                     + "GROUP BY v.vehicle_id";

        try (Connection conn = source.createConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                FuelConsumptionDTO fuel = new FuelConsumptionDTO(
                        rs.getString("vehicle_number"),
                        rs.getString("vehicle_type"),
                        rs.getDouble("total_fuel_consumed"),
                        rs.getDouble("average_distance_covered")
                );
                fuelData.add(fuel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fuelData;
    }
}
