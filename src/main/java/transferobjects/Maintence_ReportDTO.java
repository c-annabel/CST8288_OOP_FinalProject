/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transferobjects;

/**
 *
 * @author HelloFriend
 */
public class Maintence_ReportDTO {
    private int id;
    private String vehicle_number;
    private String report_status;
    private String report_description;
    
    public void setID(int id){
        this.id = id;
    }
    
    public void setVehicle_number(String number){
        this.vehicle_number = number;
    }
    
    public void setstatus(String status){
        this.report_status = status;
    }
    
    public void setDescription(String description){
        this.report_description = description;
    }
    
    public int getID(){
        return this.id;
    }
    
    public String getNumber(){
        return this.vehicle_number;
    }
    
    public String getStatus(){
        return this.report_status;
    }
    
    public String getDescription(){
        return this.report_description;
    }
}
