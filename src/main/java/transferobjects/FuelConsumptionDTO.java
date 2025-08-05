package transferobjects;

import java.sql.Timestamp;

public class FuelConsumptionDTO {

    private int logId;
    private String vehicleNumber;
    private double fuelConsumed;
    private Timestamp timestamp;

    // Getters and setters
    public int getLogId() { return logId; }
    public void setLogId(int logId) { this.logId = logId; }

    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }

    public double getFuelConsumed() { return fuelConsumed; }
    public void setFuelConsumed(double fuelConsumed) { this.fuelConsumed = fuelConsumed; }

    public Timestamp getTimestamp() { return timestamp; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
}
