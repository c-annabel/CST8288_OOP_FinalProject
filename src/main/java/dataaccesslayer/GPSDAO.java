/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dataaccesslayer;

import java.sql.SQLException;
import java.util.List;
import transferobjects.GPSDTO;
/**
 *
 * @author HelloFriend
 */
public interface GPSDAO {
        List<GPSDTO> getAllGPS()  throws SQLException;
	GPSDTO getGPSbyvehicle_number(Integer vehicle_number);
	void addGPS(GPSDTO GPS);
	void updateGPS(GPSDTO GPS);
	void deleteGPS(GPSDTO GPS);
}
