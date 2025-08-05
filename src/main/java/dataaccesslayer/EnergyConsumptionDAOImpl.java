package dataaccesslayer;

import transferobjects.EnergyConsumptionDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnergyConsumptionDAOImpl implements EnergyConsumptionDAO {

    private Connection connection;

    public EnergyConsumptionDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addEnergyConsumption(EnergyConsumptionDTO energyData) throws SQLException {
        String query = "INSERT INTO EnergyConsumptionLog (vehicle_number, energy_consumed) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, energyData.getVehicleNumber());
            stmt.setDouble(2, energyData.getEnergyConsumed());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<EnergyConsumptionDTO> getEnergyConsumptionLogs(String vehicleNumber) throws SQLException {
        String query = "SELECT * FROM EnergyConsumptionLog WHERE vehicle_number = ?";
        List<EnergyConsumptionDTO> energyLogs = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, vehicleNumber);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                EnergyConsumptionDTO log = new EnergyConsumptionDTO();
                log.setLogId(rs.getInt("log_id"));
                log.setVehicleNumber(rs.getString("vehicle_number"));
                log.setEnergyConsumed(rs.getDouble("energy_consumed"));
                log.setTimestamp(rs.getTimestamp("timestamp"));
                energyLogs.add(log);
            }
        }
        return energyLogs;
    }
}
