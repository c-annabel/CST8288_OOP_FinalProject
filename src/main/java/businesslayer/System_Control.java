/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package businesslayer;
import dataaccesslayer.FuelConsumptionDAO;
import dataaccesslayer.GPSDaoImpl;
import dataaccesslayer.Maintence_ReportDAOImp;
import dataaccesslayer.UserDAOImpl;
import dataaccesslayer.VehicleDAOImpl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import transferobjects.CredentialsDTO;
import transferobjects.User;
import transferobjects.Vehicle;
import dataaccesslayer.RouteDAOImpl;
import transferobjects.FuelConsumptionDTO;
import transferobjects.GPSDTO;
import transferobjects.Maintence_ReportDTO;
import transferobjects.RouteDTO;
import transferobjects.StationDTO;
/**
 *
 * @author Chen Wang
 */
public class System_Control {
    private final UserDAOImpl userDao;
    private final VehicleDAOImpl VehicleDAO;
    private final RouteDAOImpl RouteDAO;
    private final Maintence_ReportDAOImp ReportDAO;
    private final FuelConsumptionDAO FuelConsumptionDAO;
    private final GPSDaoImpl GPSDAO;
    
    public System_Control(CredentialsDTO creds){
        userDao = new UserDAOImpl(creds);
        VehicleDAO = new VehicleDAOImpl(creds);
        RouteDAO = new RouteDAOImpl(creds);
        ReportDAO = new Maintence_ReportDAOImp(creds);
        FuelConsumptionDAO = new FuelConsumptionDAO(creds);
        GPSDAO = new GPSDaoImpl(creds);
    }
    
    //User 
    public boolean register(User u){
        return userDao.registerUser(u);
    }
    
    public User login(String email, String password){
        return userDao.loginUser(email, password);
    }  
    
    public void UserTakeBreak(String log, String name){
        userDao.takeABreak(log, name);
    }
    
    public List<String> getUserBreakLog(String name){
        return userDao.getBreakLog(name);
    }
    
    //routes and stations
    public void addRoute(RouteDTO route) throws SQLException {
        RouteDAO.addRoute(route);
    }

    public RouteDTO getRouteById(int routeId) throws SQLException {
        return RouteDAO.getRouteById(routeId);
    }

    public List<RouteDTO> getAllRoutes() throws SQLException {
        return RouteDAO.getAllRoutes();
    }

    public void updateRoute(RouteDTO route) throws SQLException {
        RouteDAO.updateRoute(route);
    }

    public void deleteRoute(int routeId) throws SQLException {
        RouteDAO.deleteRoute(routeId);
    }

    public List<RouteDTO> getRouteStationsView() throws SQLException {
        return RouteDAO.getRouteStationsView();
    }

    public void addStation(StationDTO station) throws SQLException {
        RouteDAO.addStation(station);
    }

    public StationDTO getStationById(int stationId) throws SQLException {
        return RouteDAO.getStationById(stationId);
    }

    public List<StationDTO> getAllStations() throws SQLException {
        return RouteDAO.getAllStations();
    }

    public void updateStation(StationDTO station) throws SQLException {
        RouteDAO.updateStation(station);
    }

    public void deleteStation(int stationId) throws SQLException {
        RouteDAO.deleteStation(stationId);
    }

    public void assignStationToRoute(int routeId, int stationId, int stopOrder) throws SQLException {
        RouteDAO.assignStationToRoute(routeId, stationId, stopOrder);
    }
    
    //Vehicle Management
    public List<Vehicle> getAllVehicles(){
        List<Vehicle> vehicles = new ArrayList<>();
        try{
            vehicles = VehicleDAO.getAllVehicles();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return vehicles;
    }
    
    public Vehicle getVehicleByNumber(String vehicleNumber){
        Vehicle v = null;
        try{
            v = VehicleDAO.getVehicleByNumber(vehicleNumber);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return v;
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
    
    // Add to System_Control.java
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
    
    public void addGps(GPSDTO gps){
        this.GPSDAO.addGPS(gps);
    }
}
