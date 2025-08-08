/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package businesslayer;

import transferobjects.GPSDTO;
import transferobjects.Vehicle;

/**
 *
 * @author HelloFriend
 */
public class VehicleAdapter implements LocationAdapter {
    private final Vehicle vehicle;
    private final String timestamp;

    public VehicleAdapter(Vehicle vehicle, String timestamp) {
        this.vehicle = vehicle;
        this.timestamp = timestamp;
    }

    @Override
    public GPSDTO adaptToGPSDTO() {
        GPSDTO dto = new GPSDTO();
        dto.setIdentifier(vehicle.getVehicleNumber());
        dto.setLatitude(""); // Will be updated from GPS tracking
        dto.setLongitude(""); // Will be updated from GPS tracking
        dto.setTimestamp(timestamp);
        dto.setType("vehicle");
        return dto;
    }
}
