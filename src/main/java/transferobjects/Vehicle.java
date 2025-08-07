/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transferobjects;

/**
 * abstract class for Vehicle simple factory
 * @author Chen Wang
 */
public abstract class Vehicle {
    private int vehicleId;
    private String vehicleNumber;
    private String vehicleType;
    private String fuelType;
    private double consumptionRate;
    private int maxPassengers;
    private int routeId;
    
    //getter 
    public int getVehicleId() { return vehicleId; }
    public String getVehicleNumber() { return vehicleNumber; }
    public String getVehicleType() { return vehicleType; }
    public String getFuelType() { return fuelType; }
    public double getConsumptionRate() { return consumptionRate; }
    public int getMaxPassengers() { return maxPassengers; }
    public int getRouteId() { return routeId; }
    
    //setter
    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public void setConsumptionRate(double consumptionRate) {
        this.consumptionRate = consumptionRate;
    }

    public void setMaxPassengers(int maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }
}

