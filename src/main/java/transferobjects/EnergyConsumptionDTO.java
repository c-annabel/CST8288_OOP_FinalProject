package transferobjects;

import java.sql.Timestamp;

public class EnergyConsumptionDTO {

    private int logId;
    private String vehicleNumber;
    private double energyConsumed;
    private Timestamp timestamp;

    // Getters and setters
    public int getLogId() { return logId; }
    public void setLogId(int logId) { this.logId = logId; }

    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }

    public double getEnergyConsumed() { return energyConsumed; }
    public void setEnergyConsumed(double energyConsumed) { this.energyConsumed = energyConsumed; }

    public Timestamp getTimestamp() { return timestamp; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
}
