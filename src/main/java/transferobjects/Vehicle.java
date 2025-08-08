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
    private String latitude;
    private String longitude;
    private int hoursOfcomponents;
    private int maintenance_threshold;
    private int fuel_alert_threshold;
    private String diagnostics;
    
    //getter 
    public int getVehicleId() { return vehicleId; }
    public String getVehicleNumber() { return vehicleNumber; }
    public String getVehicleType() { return vehicleType; }
    public String getFuelType() { return fuelType; }
    public double getConsumptionRate() { return consumptionRate; }
    public int getMaxPassengers() { return maxPassengers; }
    public int getRouteId() { return routeId; }
    public String getLatitude() { return latitude; }
    public String getLongitude() { return longitude; }
    public int getHoursOfcomponents() {return hoursOfcomponents;}
    public int getMaintenance_threshold() {return maintenance_threshold;}
    public int getFuel_alert_threshold() {return fuel_alert_threshold;}
    public String getDiagnostics() {return diagnostics;}
    
    
    //setter
    public void setVehicleType(String type){
        this.vehicleType = type;
    }
    public void setVehicleID(int id){
        this.vehicleId = id;
    }
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
    
    public void setLatitude(String latitude) { 
        this.latitude = latitude; 
    }
    
    public void setLongitude(String longitude) { 
        this.longitude = longitude; 
    }
    
    public void setHoursOfcomponents(int hoursOfcomponents){
        this.hoursOfcomponents = hoursOfcomponents;
    }
    
    public void setMaintenance_threshold(int maintenance_threshold) {
        this.maintenance_threshold = maintenance_threshold;
    }
    
    public void setFuel_alert_threshold(int fuel_alert_threshold) {
        this.fuel_alert_threshold = fuel_alert_threshold;
    }
    
    public void setDiagnostics(String diagnostics) {
        this.diagnostics = diagnostics;
    }
}

