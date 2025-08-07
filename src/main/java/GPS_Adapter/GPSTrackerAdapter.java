/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GPS_Adapter;
/**
 *
 * @author HelloFriend
 */
public class GPSTrackerAdapter implements GPSTracker {
    private VendorGPS vendorGPS;

    public GPSTrackerAdapter(VendorGPS vendorGPS) {
        this.vendorGPS = vendorGPS;
    }

    @Override
    public double getLatitude() {
        
        return vendorGPS.fetchCoordinates()[0];
    }

    @Override
    public double getLongitude() {
        return vendorGPS.fetchCoordinates()[1];
    }

    @Override
    public String getLocationString() {
        return vendorGPS.getRawGPSString();
    }
}

