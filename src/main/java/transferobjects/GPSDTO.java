/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transferobjects;

/**
 *
 * @author HelloFriend
 */
public class GPSDTO {
    private int id;
    private String vehicle_number;
    private String timestamp;
    private String latitude;
    private String longitude;
    
    public void setID(int id){
        this.id = id;
    }
    
    public void setVehicle_number(String number){
        this.vehicle_number = number;
    }
    
    public void setTimeStamp(String time){
        this.timestamp = time;
    }
    
    public void setLatitude(String latitude){
        this.latitude = latitude;
    }
    
    public void setLongitude(String longitude){
        this.longitude = longitude;
    }
    
    public int getID(){
        return this.id;
    }
    
    public String getNumber(){
        return this.vehicle_number;
    }
    
    public String getTimeStamp(){
        return this.timestamp;
    }
    
    public String getLatitude(){
        return this.latitude;
    }
    
    public String getLongitude(){
        return this.longitude;
    }
}
