/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transferobjects;

/**
 *
 * @author Chen Wang
 */
public class DieselBus extends VehicleDTO {
    private int hoursOfUseBrake;
    private int hoursOfUseTires;
    private int hoursOfUseAxle;
    private String EngineDiagnostics;
    
    public int getUseBrake(){
        return this.hoursOfUseBrake;
    }
    
    public int getUseTires(){
        return this.hoursOfUseTires;
    }
    
    public int getUseAxle(){
        return this.hoursOfUseAxle;
    }
    
    public String EngineDiagnostics(){
        return EngineDiagnostics;
    }
    
    public void setUseBrake(int hours){
        this.hoursOfUseBrake = hours;
    }
    
    public void setUseTires(int hours){
        this.hoursOfUseTires = hours;
    }
    
    public void sethoursOfUseAxle(int hours){
        this.hoursOfUseAxle = hours;
    }
    
    public void setEngineDiagnostics(String Diagnostics){
        this.EngineDiagnostics = Diagnostics;
    }
}
