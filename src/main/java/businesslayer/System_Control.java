/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package businesslayer;
import dataaccesslayer.UserDAOImpl;
import dataaccesslayer.DataSource;
import dataaccesslayer.VehicleDAOImpl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import transferobjects.CredentialsDTO;
import transferobjects.User;
import transferobjects.Vehicle;

/**
 *
 * @author Chen Wang
 */
public class System_Control {
    private DataSource source;
    private CredentialsDTO creds;
    private UserDAOImpl userDao;
    private VehicleDAOImpl VehicleDAO;
            
    public System_Control(CredentialsDTO creds){
        userDao = new UserDAOImpl(creds);
        VehicleDAO = new VehicleDAOImpl(creds);
        this.creds = creds;
    }
    
    public boolean register(User u){
        return userDao.registerUser(u);
    }
    
    public User login(String email, String password){
        return userDao.loginUser(email, password);
    }  
    
    public List<Vehicle> getAllVehicles(){
        List<Vehicle> vehicles = new ArrayList<>();
        try{
            vehicles = VehicleDAO.getAllVehicles();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return vehicles;
    }
    
    public void addVehicle(Vehicle v){
        try{
            VehicleDAO.insertVehicle(v);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    public void updateVehicle(Vehicle v){
        try{
            VehicleDAO.updateVehicle(v);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    public void deleteVehicle(String vehicleNumber){
        try{
            VehicleDAO.deleteVehicle(vehicleNumber);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
