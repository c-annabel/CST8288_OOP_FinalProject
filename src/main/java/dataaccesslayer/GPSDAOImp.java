/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import transferobjects.GPSDTO;

/**
 *
 * @author HelloFriend
 */
public class GPSDAOImp implements GPSDAO{
    
    private Connection con;
    
    public GPSDAOImp(Connection con){
        this.con = con;
    }
    
    @Override
    public List<GPSDTO> getAllGPS() throws SQLException{
        List<GPSDTO> gpsList = new ArrayList<>();
        String sql = "SELECT * FROM GPS_Tracking";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            GPSDTO gps = new GPSDTO();
            gps.setID(rs.getInt("gps_id"));
            gps.setVehicle_number(rs.getString("vehicle_number"));
            gps.setTimeStamp(rs.getString("timestamp"));
            gps.setLatitude(rs.getString("latitude"));
            gps.setLongitude(rs.getString("longitude"));
            gpsList.add(gps);
        }

        return gpsList;
    }
    
    @Override
    public GPSDTO getGPSbyvehicle_number(Integer vehicle_number){
        GPSDTO gps = null;
        String sql = "SELECT * FROM GPS_Tracking WHERE vehicle_number = ?";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, vehicle_number);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                gps = new GPSDTO();
                gps.setID(rs.getInt("gps_id"));
                gps.setVehicle_number(rs.getString("vehicle_number"));
                gps.setTimeStamp(rs.getString("timestamp"));
                gps.setLatitude(rs.getString("latitude"));
                gps.setLongitude(rs.getString("longitude"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gps;
    }
    
    @Override
    public void addGPS(GPSDTO GPS){
        String sql = "INSERT INTO GPS_Tracking (vehicle_number, timestamp, latitude, longitude) VALUES (?, ?, ?, ?)";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, GPS.getNumber());
            ps.setString(2, GPS.getTimeStamp());
            ps.setString(3, GPS.getLatitude());
            ps.setString(4, GPS.getLongitude());
        ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void updateGPS(GPSDTO GPS){
        String sql = "UPDATE GPS_Tracking SET timestamp = ?, latitude = ?, longitude = ? WHERE vehicle_number = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, GPS.getTimeStamp());
            ps.setString(2, GPS.getLatitude());
            ps.setString(3, GPS.getLongitude());
            ps.setString(4, GPS.getNumber());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void deleteGPS(GPSDTO GPS){
        try {
            String sql = "DELETE FROM GPS_Tracking WHERE vehicle_number = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, GPS.getNumber());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
