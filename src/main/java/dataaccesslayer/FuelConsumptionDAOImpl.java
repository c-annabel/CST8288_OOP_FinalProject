package dataaccesslayer;

import transferobjects.FuelConsumptionDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuelConsumptionDAOImpl implements FuelConsumptionDAO {

    private Connection connection;

    public FuelConsumptionDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addFuelConsumption(FuelConsumptionDTO fuelData) throws SQLException {
        String query = "INSERT INTO FuelConsumptionLog (vehicle_number, fuel_consumed) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, fuelData.getVehicleNumber());
            stmt.setDouble(2, fuelData.getFuelConsumed());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<FuelConsumptionDTO> getFuelConsumptionLogs(String vehicleNumber) throws SQLException {
        String query = "SELECT * FROM FuelConsumptionLog WHERE vehicle_number = ?";
        List<FuelConsumptionDTO> fuelLogs = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, vehicleNumber);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                FuelConsumptionDTO log = new FuelConsumptionDTO();
                log.setLogId(rs.getInt("log_id"));
                log.setVehicleNumber(rs.getString("vehicle_number"));
                log.setFuelConsumed(rs.getDouble("fuel_consumed"));
                log.setTimestamp(rs.getTimestamp("timestamp"));
                fuelLogs.add(log);
            }
        }
        return fuelLogs;
    }
}
