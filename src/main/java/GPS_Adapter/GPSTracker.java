/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GPS_Adapter;

/**
 *
 * @author HelloFriend
 */
public interface GPSTracker {
    double getLatitude();
    double getLongitude();
    String getLocationString();
}
