package dataaccesslayer;

import transferobjects.EnergyConsumptionDTO;
import java.sql.SQLException;
import java.util.List;

public interface EnergyConsumptionDAO {
    void addEnergyConsumption(EnergyConsumptionDTO energyData) throws SQLException;
    List<EnergyConsumptionDTO> getEnergyConsumptionLogs(String vehicleNumber) throws SQLException;
}
