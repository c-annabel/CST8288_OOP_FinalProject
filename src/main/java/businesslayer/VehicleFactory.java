/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package businesslayer;
import transferobjects.VehicleDTO;
import transferobjects.DieselBus;
import transferobjects.DieselElectricTrain;
import transferobjects.ElectricLightRail;

/**
 * according the Vehicle type, building different type Vehicle class
 * including Diesel Bus, Electric Light Rail and Diesel-Electric Train
 * implement by simple factor design pattern
 * @author Chen wang
 */
public class VehicleFactory {
    public static VehicleDTO createVehicle(String type) {
        switch (type) {
            case "Diesel Bus":
                return new DieselBus();
            case "Electric Light Rail":
                return new ElectricLightRail();
            case "Diesel-Electric Train":
                return new DieselElectricTrain();
            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + type);
        }
    }
}
