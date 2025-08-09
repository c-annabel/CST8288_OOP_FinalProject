/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package businesslayer;

import transferobjects.GPSDTO;
import transferobjects.StationDTO;
import transferobjects.VehicleDTO;

/**
 * interface for GPS Adapter for translate location to system
 * @author Chen Wang
 */
public interface LocationAdapter {
    GPSDTO adaptToGPSDTO();
}

