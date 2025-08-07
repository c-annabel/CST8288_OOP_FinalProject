/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package businesslayer;
import transferobjects.Vehicle;
import transferobjects.DieselBus;
import transferobjects.DieselElectricTrain;
import transferobjects.ElectricLightRail;

/**
 *
 * @author HelloFriend
 */
public class VehicleFactory {
    public static Vehicle createVehicle(String type) {
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
