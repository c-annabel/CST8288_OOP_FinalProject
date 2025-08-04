/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

public class Vehicle {
    private int vehicleId;
    private String vehicleNumber;
    private String vehicleType;
    private String fuelType;
    private double consumptionRate;
    private int maxPassengers;
    private int routeId;

    private Vehicle(VehicleBuilder builder) {
        this.vehicleId = builder.vehicleId;
        this.vehicleNumber = builder.vehicleNumber;
        this.vehicleType = builder.vehicleType;
        this.fuelType = builder.fuelType;
        this.consumptionRate = builder.consumptionRate;
        this.maxPassengers = builder.maxPassengers;
        this.routeId = builder.routeId;
    }

    public int getVehicleId() { return vehicleId; }
    public String getVehicleNumber() { return vehicleNumber; }
    public String getVehicleType() { return vehicleType; }
    public String getFuelType() { return fuelType; }
    public double getConsumptionRate() { return consumptionRate; }
    public int getMaxPassengers() { return maxPassengers; }
    public int getRouteId() { return routeId; }

    public static class VehicleBuilder {
        private int vehicleId;
        private String vehicleNumber;
        private String vehicleType;
        private String fuelType;
        private double consumptionRate;
        private int maxPassengers;
        private int routeId;

        public VehicleBuilder setVehicleId(int vehicleId) {
            this.vehicleId = vehicleId; return this;
        }
        public VehicleBuilder setVehicleNumber(String vehicleNumber) {
            this.vehicleNumber = vehicleNumber; return this;
        }
        public VehicleBuilder setVehicleType(String vehicleType) {
            this.vehicleType = vehicleType; return this;
        }
        public VehicleBuilder setFuelType(String fuelType) {
            this.fuelType = fuelType; return this;
        }
        public VehicleBuilder setConsumptionRate(double rate) {
            this.consumptionRate = rate; return this;
        }
        public VehicleBuilder setMaxPassengers(int max) {
            this.maxPassengers = max; return this;
        }
        public VehicleBuilder setRouteId(int routeId) {
            this.routeId = routeId; return this;
        }

        public Vehicle build() {
            return new Vehicle(this);
        }
    }
}
