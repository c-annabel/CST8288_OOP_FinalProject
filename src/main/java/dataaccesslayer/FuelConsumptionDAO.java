package dataaccesslayer;

import transferobjects.FuelConsumptionDTO;
import java.sql.SQLException;
import java.util.List;

public interface FuelConsumptionDAO {
    void addFuelConsumption(FuelConsumptionDTO fuelData) throws SQLException;
    List<FuelConsumptionDTO> getFuelConsumptionLogs(String vehicleNumber) throws SQLException;
}
