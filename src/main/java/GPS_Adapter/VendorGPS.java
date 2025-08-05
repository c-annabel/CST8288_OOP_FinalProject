/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GPS_Adapter;

/**
 *
 * @author HelloFriend
 */
public class VendorGPS {
    public double[] fetchCoordinates() {
        // Example: returns {lat, lon}
        return new double[]{43.6532, -79.3832};
    }

    public String getRawGPSString() {
        return "LAT=43.6532;LON=-79.3832";
    }
}
