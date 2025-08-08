/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package businesslayer;

import transferobjects.GPSDTO;
import transferobjects.StationDTO;

/**
 *
 * @author HelloFriend
 */
public class StationAdapter implements LocationAdapter {
    private final StationDTO station;

    public StationAdapter(StationDTO station) {
        this.station = station;
    }

    @Override
    public GPSDTO adaptToGPSDTO() {
        GPSDTO dto = new GPSDTO();
        dto.setIdentifier(station.getStationName());
        dto.setLatitude(station.getLatitude());
        dto.setLongitude(station.getLongitude());
        dto.setType("station");
        return dto;
    }
}