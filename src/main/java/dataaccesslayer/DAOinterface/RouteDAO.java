/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dataaccesslayer.DAOinterface;

import java.sql.SQLException;
import java.util.List;
import transferobjects.RouteDTO;
import transferobjects.StationDTO;
/**
 *
 * @author Chen Wang
 */

public interface RouteDAO {
    // Routes
    void addRoute(RouteDTO route) throws SQLException;
    RouteDTO getRouteById(int routeId) throws SQLException;
    List<RouteDTO> getAllRoutes() throws SQLException;
    void updateRoute(RouteDTO route) throws SQLException;
    void deleteRoute(int routeId) throws SQLException;

    // Stations for a Route (from the VIEW)
    List<RouteDTO> getRouteStationsView() throws SQLException;
    List<RouteDTO> getRouteStationsByRouteId(int routeId) throws SQLException;

    // Station CRUD (optional basic ops)
    void addStation(StationDTO s) throws SQLException;
    StationDTO getStationById(int stationId) throws SQLException;
    List<StationDTO> getAllStations() throws SQLException;
    void updateStation(StationDTO s) throws SQLException;
    void deleteStation(int stationId) throws SQLException;
    
    //sign station to route
    void assignStationToRoute(int routeId, int stationId, int stopOrder) throws SQLException;
}