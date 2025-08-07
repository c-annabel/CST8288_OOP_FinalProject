/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transferobjects;

/**
 * 
 * @author chinmoy
 */
public class FuelConsumption {
    private String vehicleNumber;
    private String vehicleType;
    private double totalFuelConsumed;
    private double averageDistanceCovered;

    // Constructor
    public FuelConsumption(String vehicleNumber, String vehicleType, double totalFuelConsumed, double averageDistanceCovered) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.totalFuelConsumed = totalFuelConsumed;
        this.averageDistanceCovered = averageDistanceCovered;
    }

    // Getters and Setters
    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public double getTotalFuelConsumed() {
        return totalFuelConsumed;
    }

    public void setTotalFuelConsumed(double totalFuelConsumed) {
        this.totalFuelConsumed = totalFuelConsumed;
    }

    public double getAverageDistanceCovered() {
        return averageDistanceCovered;
    }

    public void setAverageDistanceCovered(double averageDistanceCovered) {
        this.averageDistanceCovered = averageDistanceCovered;
    }
}
