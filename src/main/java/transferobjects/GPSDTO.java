/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transferobjects;

/**
 *
 * @author Chen Wang
 */
public class GPSDTO {
    private int GPS_id;
    private String vehicle_number;
    private String timestamp;
    private String latitude;
    private String longitude;
    private String type; // "vehicle" or "station"

    // Getters and setters
    public int getGPS_id(){return GPS_id;}
    public void setGps_id(int GPS_id){this.GPS_id = GPS_id;}
    public String getVehicle_number() { return vehicle_number; }
    public void setIdentifier(String vehicle_number) { this.vehicle_number = vehicle_number; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public String getLatitude() { return latitude; }
    public void setLatitude(String latitude) { this.latitude = latitude; }
    public String getLongitude() { return longitude; }
    public void setLongitude(String longitude) { this.longitude = longitude; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
