/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package businesslayer;
import dataaccesslayer.FuelConsumptionDAO;
import dataaccesslayer.GPSDaoImpl;
import dataaccesslayer.Maintence_ReportDAOImp;
import dataaccesslayer.OperatorDAOImpl;
import dataaccesslayer.VehicleDAOImpl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import transferobjects.CredentialsDTO;
import transferobjects.OperatorsDTO;
import transferobjects.VehicleDTO;
import dataaccesslayer.RouteDAOImpl;
import transferobjects.FuelConsumptionDTO;
import transferobjects.GPSDTO;
import transferobjects.Maintence_ReportDTO;
import transferobjects.RouteDTO;
import transferobjects.StationDTO;
/**
 * connect with DAO and Presentation layer
 * @author Chen Wang
 */
public class System_Control {
    private final OperatorDAOImpl userDao;
    private final VehicleDAOImpl VehicleDAO;
    private final RouteDAOImpl RouteDAO;
    private final Maintence_ReportDAOImp ReportDAO;
    private final FuelConsumptionDAO FuelConsumptionDAO;
    private final GPSDaoImpl GPSDAO;
    
    /**
     * Initialize DAO
     * @param creds 
     */
    public System_Control(CredentialsDTO creds){
        userDao = new OperatorDAOImpl(creds);
        VehicleDAO = new VehicleDAOImpl(creds);
        RouteDAO = new RouteDAOImpl(creds);
        ReportDAO = new Maintence_ReportDAOImp(creds);
        FuelConsumptionDAO = new FuelConsumptionDAO(creds);
        GPSDAO = new GPSDaoImpl(creds);
    }
    
    //User
    /**
     * operator register function from userDAO
     * @param u
     * @return boolean
     */
    public boolean register(OperatorsDTO u){
        return userDao.registerUser(u);
    }
    
    /**
     * operator login function from userDAO
     * @param email
     * @param password
     * @return 
     */
    public OperatorsDTO login(String email, String password){
        return userDao.loginUser(email, password);
    }  
    
    /**
     * take a break for operator in home page
     * @param log
     * @param name 
     */
    public void UserTakeBreak(String log, String name){
        userDao.takeABreak(log, name);
    }
    
    /**
     * get log for selective name of user
     * @param name
     * @return List<String> list of log
     */
    public List<String> getUserBreakLog(String name){
        return userDao.getBreakLog(name);
    }
    
    //routes and stations
    /**
     * add new route without any station
     * @param route
     * @throws SQLException 
     */
    public void addRoute(RouteDTO route) throws SQLException {
        RouteDAO.addRoute(route);
    }

    /**
     * found route by route id
     * @param routeId
     * @return RouteDTO target route
     * @throws SQLException 
     */
    public RouteDTO getRouteById(int routeId) throws SQLException {
        return RouteDAO.getRouteById(routeId);
    }
    
    /**
     * get all route in the database
     * @return List<RouteDTO> all routes
     * @throws SQLException  
     */
    public List<RouteDTO> getAllRoutes() throws SQLException {
        return RouteDAO.getAllRoutes();
    }
    
    /**
     * update target route, changing naming
     * @param route
     * @throws SQLException 
     */
    public void updateRoute(RouteDTO route) throws SQLException {
        RouteDAO.updateRoute(route);
    }
    
    /**
     * delete route by id
     * @param routeId
     * @throws SQLException 
     */
    public void deleteRoute(int routeId) throws SQLException {
        RouteDAO.deleteRoute(routeId);
    }
    
    /**
     * get route and station view from database 
     * @return
     * @throws SQLException 
     */
    public List<RouteDTO> getRouteStationsView() throws SQLException {
        return RouteDAO.getRouteStationsView();
    }
    
    /**
     * add station into database
     * @param station
     * @throws SQLException 
     */
    public void addStation(StationDTO station) throws SQLException {
        RouteDAO.addStation(station);
    }
    
    /**
     * get station by id
     * @param stationId
     * @return StationDTO target
     * @throws SQLException 
     */
    public StationDTO getStationById(int stationId) throws SQLException {
        return RouteDAO.getStationById(stationId);
    }
    
    /**
     * get all stations
     * @return List<StationDTO>
     * @throws SQLException 
     */
    public List<StationDTO> getAllStations() throws SQLException {
        return RouteDAO.getAllStations();
    }
    
    /**
     * update target station
     * @param station
     * @throws SQLException 
     */
    public void updateStation(StationDTO station) throws SQLException {
        RouteDAO.updateStation(station);
    }
    
    /**
     * delete station by id
     * @param stationId
     * @throws SQLException 
     */
    public void deleteStation(int stationId) throws SQLException {
        RouteDAO.deleteStation(stationId);
    }
    
    /**
     * sign station to route
     * @param routeId
     * @param stationId
     * @param stopOrder
     * @throws SQLException 
     */
    public void assignStationToRoute(int routeId, int stationId, int stopOrder) throws SQLException {
        RouteDAO.assignStationToRoute(routeId, stationId, stopOrder);
    }
    
    
    //Vehicle Management
    /**
     * get all vehicle
     * @return List<VehicleDTO>
     */
    public List<VehicleDTO> getAllVehicles(){
        List<VehicleDTO> vehicles = new ArrayList<>();
        try{
            vehicles = VehicleDAO.getAllVehicles();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return vehicles;
    }
    
    /**
     * get vehicle by unique vehicle number 
     * @param vehicleNumber
     * @return 
     */
    public VehicleDTO getVehicleByNumber(String vehicleNumber){
        VehicleDTO v = null;
        try{
            v = VehicleDAO.getVehicleByNumber(vehicleNumber);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return v;
    }
    
    /**
     * add new vehicle
     * @param v 
     */
    public void addVehicle(VehicleDTO v){
        try{
            VehicleDAO.insertVehicle(v);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    /**
     * update target vehicle
     * @param v target
     */
    public void updateVehicle(VehicleDTO v){
        try{
            VehicleDAO.updateVehicle(v);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    /**
     * delete vehicle by vehicle number
     * @param vehicleNumber 
     */
    public void deleteVehicle(String vehicleNumber){
        try{
            VehicleDAO.deleteVehicle(vehicleNumber);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    // Add to System_Control.java
    /**
     * get all operators in the database
     * @return 
     */
    public List<OperatorsDTO> getAllUsers() {
        return userDao.getAllUsers();
    }
    
    /**
     * add GPS adapter for location
     * @param gps 
     */
    public void addGps(GPSDTO gps){
        this.GPSDAO.addGPS(gps);
    }
}
