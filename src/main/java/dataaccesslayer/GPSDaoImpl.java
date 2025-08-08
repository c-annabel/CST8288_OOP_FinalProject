/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccesslayer;

import dataaccesslayer.DAOinterface.GPSDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import transferobjects.CredentialsDTO;
import transferobjects.GPSDTO;
/**
 *
 * @author HelloFriend
 */
public class GPSDaoImpl implements GPSDAO{
    private CredentialsDTO cred;

    public GPSDaoImpl(CredentialsDTO cred) {
        this.cred = cred;
    }
    
    @Override
    public List<GPSDTO> getAllGPS() throws SQLException {
        String sql = "SELECT gps_id, vehicle_number, `timestamp`, latitude, longitude " +
                     "FROM GPS_Tracking ORDER BY gps_id DESC";
        List<GPSDTO> list = new ArrayList<>();
        DataSource source = new DataSource(cred);
        try (Connection con = source.createConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                GPSDTO g = new GPSDTO();
                g.setGps_id(rs.getInt("gps_id"));
                g.setIdentifier(rs.getString("vehicle_number"));
                g.setTimestamp(rs.getString("`timestamp`".replace("`",""))); // or rs.getString("timestamp")
                g.setLatitude(rs.getString("latitude"));
                g.setLongitude(rs.getString("longitude"));
                list.add(g);
            }
        }
        return list;
    }
    @Override
    public GPSDTO getGPSbyvehicle_number(String vehicleNumber){
        String sql = "SELECT gps_id, vehicle_number, `timestamp`, latitude, longitude " +
                     "FROM GPS_Tracking WHERE vehicle_number = ? ORDER BY gps_id DESC LIMIT 1";
        DataSource source = new DataSource(cred);
        try (Connection con = source.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, vehicleNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    GPSDTO g = new GPSDTO();
                    g.setGps_id(rs.getInt("gps_id"));
                    g.setIdentifier(rs.getString("vehicle_number"));
                    g.setTimestamp(rs.getString("`timestamp`".replace("`","")));
                    g.setLatitude(rs.getString("latitude"));
                    g.setLongitude(rs.getString("longitude"));
                    return g;
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        } catch (SQLException e){
                e.printStackTrace();
        }
        return null;
    }

    /**
     * Insert a GPS record. Latitude/Longitude are **taken from Vehicles** matching vehicle_number.
     * If the vehicle_number does not exist, no row will be inserted (rows=0).
     */
    @Override
    public void addGPS(GPSDTO gps) {
        // One-shot insert selecting lat/lng from Vehicles
        String sql = "INSERT INTO GPS_Tracking (vehicle_number, timestamp, latitude, longitude) "
                   + "VALUES (?, ?, ?, ?)";
                    
        DataSource source = new DataSource(cred);
        
        try (Connection con = source.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, gps.getVehicle_number());
            ps.setString(2, gps.getTimestamp());
            ps.setString(3, gps.getLatitude());
            ps.setString(4, gps.getLongitude());
            ps.executeUpdate(); // 1 = inserted, 0 = no matching vehicle
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateGPS(GPSDTO gps) {
        // Basic update by gps_id (keeps whatever lat/lng are in the record)
        String sql = "UPDATE GPS_Tracking " +
                     "SET `timestamp` = ?, latitude = ?, longitude = ? " +
                     "WHERE gps_id = ?";
        DataSource source = new DataSource(cred);
        
        try (Connection con = source.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, gps.getTimestamp());
            ps.setString(2, gps.getLatitude());
            ps.setString(3, gps.getLongitude());
            ps.setInt(4, gps.getGPS_id());
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public int refreshLatLngFromVehicle(String vehicleNumber) throws SQLException {
        // Optional helper: refresh last GPS record’s lat/lng from Vehicles for that vehicle
        String sql =
            "UPDATE GPS_Tracking g " +
            "JOIN Vehicles v ON v.vehicle_number = g.vehicle_number " +
            "SET g.latitude = v.latitude, g.longitude = v.longitude " +
            "WHERE g.vehicle_number = ? " +
            "ORDER BY g.gps_id DESC LIMIT 1";
        DataSource source = new DataSource(cred);
        try (Connection con = source.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, vehicleNumber);
            return ps.executeUpdate();
        }
    }

    @Override
    public void deleteGPSById(int gpsId){
        String sql = "DELETE FROM GPS_Tracking WHERE gps_id = ?";
        DataSource source = new DataSource(cred);
        try (Connection con = source.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, gpsId);
            ps.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteGPSByVehicle(String vehicleNumber){
        String sql = "DELETE FROM GPS_Tracking WHERE vehicle_number = ?";
        DataSource source = new DataSource(cred);
        try (Connection con = source.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, vehicleNumber);
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    
}
