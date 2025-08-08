/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transferobjects;

/**
 *
 * @author Chen Wang
 */
public class RouteDTO {
    private int routeId;
    private String routeName;
    private int stopOrder;
    private int stationId;
    private String stationName;
    private String latitude;
    private String longitude;
    
    public int getRouteId() { return routeId; }
    public String getRouteName() { return routeName; }
    public int getStopOrder() { return stopOrder; }
    public int getStationId() { return stationId; }
    public String getStationName() { return stationName; }
    public String getLatitude() { return latitude; }
    public String getLongitude() { return longitude; }
    
    public void setRouteId(int routeId) { this.routeId = routeId; }
    public void setRouteName(String routeName) { this.routeName = routeName; }
    public void setStopOrder(int stopOrder) { this.stopOrder = stopOrder; }
    public void setStationId(int stationId) { this.stationId = stationId; }
    public void setStationName(String stationName) { this.stationName = stationName; }
    public void setLatitude(String latitude) { this.latitude = latitude; }
    public void setLongitude(String longitude) { this.longitude = longitude; }
    
}
