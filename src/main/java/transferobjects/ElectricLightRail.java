/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transferobjects;

/**
 *
 * @author Chen Wang
 */
public class ElectricLightRail extends VehicleDTO {
    private int hoursOfUseCatenary;
    private int hoursOfUsePantograph;
    private int hoursOfUseBreakers;
    
    public int getUseCatenary(){
        return this.hoursOfUseCatenary;
    }
    
    public int getUsePantograph(){
        return this.hoursOfUsePantograph;
    }
    
    public int getUseBreakers(){
        return this.hoursOfUseBreakers;
    }
    
    public void setUseCatenary(int hours){
        this.hoursOfUseCatenary = hours;
    }
    
    public void setUsePantograph(int hours){
        this.hoursOfUsePantograph = hours;
    }
    
    public void sethoursOfUseBreakers(int hours){
        this.hoursOfUseCatenary = hours;
    }
    
    
}
