/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccesslayer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import transferobjects.CredentialsDTO;
import transferobjects.RouteDTO;
import transferobjects.StationDTO;
import dataaccesslayer.DAOinterface.RouteDAO;
  
/**
 *  RouteDAO implementation
 * @author Chen Wang
 */
public class RouteDAOImpl implements RouteDAO  {
    private CredentialsDTO cred;
    
    public RouteDAOImpl(CredentialsDTO cred){
        this.cred = cred;
    }
    
    @Override
    public void addRoute(RouteDTO route) throws SQLException {
        String sql = "INSERT INTO Routes(route_name) VALUES(?)";
        DataSource source = new DataSource(cred);
        
        try (Connection con = source.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, route.getRouteName());
            ps.executeUpdate();
        }
    }
    
    
    @Override
    public RouteDTO getRouteById(int routeId) throws SQLException {
        String sql = "SELECT route_id, route_name FROM Routes WHERE route_id = ?";
        DataSource source = new DataSource(cred);
        try (Connection con = source.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, routeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    RouteDTO r = new RouteDTO();
                    r.setRouteId(rs.getInt("route_id"));
                    r.setRouteName(rs.getString("route_name"));
                    return r;
                }
            }
        }
        return null;
    }
    
    @Override
    public List<RouteDTO> getAllRoutes() throws SQLException {
        String sql = "SELECT route_id, route_name FROM Routes ORDER BY route_id";
        List<RouteDTO> list = new ArrayList<>();
        DataSource source = new DataSource(cred);
        
        try (Connection con = source.createConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RouteDTO r = new RouteDTO();
                r.setRouteId(rs.getInt("route_id"));
                r.setRouteName(rs.getString("route_name"));
                list.add(r);
            }
        }
        return list;
    }
    
        @Override
    public void updateRoute(RouteDTO route) throws SQLException {
        String sql = "UPDATE Routes SET route_name = ? WHERE route_id = ?";
        DataSource source = new DataSource(cred);
        try (Connection con = source.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, route.getRouteName());
            ps.setInt(2, route.getRouteId());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteRoute(int routeId) throws SQLException {
        String sql = "DELETE FROM Routes WHERE route_id = ?";
        DataSource source = new DataSource(cred);
        try (Connection con = source.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, routeId);
            ps.executeUpdate();
        }
    }
    
        @Override
    public List<RouteDTO> getRouteStationsView() throws SQLException {
        String sql = "SELECT route_id, route_name, stop_order, station_id, station_name, latitude, longitude FROM vw_route_stations";
        List<RouteDTO> list = new ArrayList<>();
        DataSource source = new DataSource(cred);
        try (Connection con = source.createConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RouteDTO v = new RouteDTO();
                v.setRouteId(rs.getInt("route_id"));
                v.setRouteName(rs.getString("route_name"));
                v.setStopOrder(rs.getInt("stop_order"));
                v.setStationId(rs.getInt("station_id"));
                v.setStationName(rs.getString("station_name"));
                v.setLatitude(rs.getString("latitude"));
                v.setLongitude(rs.getString("longitude"));
                list.add(v);
            }
        }
        return list;
    }
    
        @Override
    public List<RouteDTO> getRouteStationsByRouteId(int routeId) throws SQLException {
        String sql = "SELECT route_id, route_name, stop_order, station_id, station_name, latitude, longitude " +
                     "FROM vw_route_stations WHERE route_id = ? ORDER BY stop_order";
        List<RouteDTO> list = new ArrayList<>();
        DataSource source = new DataSource(cred);
        try (Connection con = source.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, routeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RouteDTO v = new RouteDTO();
                    v.setRouteId(rs.getInt("route_id"));
                    v.setRouteName(rs.getString("route_name"));
                    v.setStopOrder(rs.getInt("stop_order"));
                    v.setStationId(rs.getInt("station_id"));
                    v.setStationName(rs.getString("station_name"));
                    v.setLatitude(rs.getString("latitude"));
                    v.setLongitude(rs.getString("longitude"));
                    list.add(v);
                }
            }
        }
        return list;
    }
    
    @Override
    public void addStation(StationDTO s) throws SQLException {
        String sql = "INSERT INTO Stations(station_name, latitude, longitude) VALUES(?,?,?)";
        DataSource source = new DataSource(cred);
        try (Connection con = source.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getStationName());
            ps.setString(2, s.getLatitude());
            ps.setString(3, s.getLongitude());
            ps.executeUpdate();
        }
    }
    
    @Override
    public StationDTO getStationById(int stationId) throws SQLException {
        String sql = "SELECT station_id, station_name, latitude, longitude FROM Stations WHERE station_id = ?";
        DataSource source = new DataSource(cred);
        try (Connection con = source.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, stationId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    StationDTO s = new StationDTO();
                    s.setStationId(rs.getInt("station_id"));
                    s.setStationName(rs.getString("station_name"));
                    s.setLatitude(rs.getString("latitude"));
                    s.setLongitude(rs.getString("longitude"));
                    return s;
                }
            }
        }
        return null;
    }

    @Override
    public List<StationDTO> getAllStations() throws SQLException {
        String sql = "SELECT station_id, station_name, latitude, longitude FROM Stations ORDER BY station_id";
        List<StationDTO> list = new ArrayList<>();
        DataSource source = new DataSource(cred);
        try (Connection con = source.createConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                StationDTO s = new StationDTO();
                s.setStationId(rs.getInt("station_id"));
                s.setStationName(rs.getString("station_name"));
                s.setLatitude(rs.getString("latitude"));
                s.setLongitude(rs.getString("longitude"));
                list.add(s);
            }
        }
        return list;
    }

    @Override
    public void updateStation(StationDTO s) throws SQLException {
        String sql = "UPDATE Stations SET station_name=?, latitude=?, longitude=? WHERE station_id=?";
        DataSource source = new DataSource(cred);
        try (Connection con = source.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getStationName());
            ps.setString(2, s.getLatitude());
            ps.setString(3, s.getLongitude());
            ps.setInt(4, s.getStationId());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteStation(int stationId) throws SQLException {
        String sql = "DELETE FROM Stations WHERE station_id = ?";
        DataSource source = new DataSource(cred);
        try (Connection con = source.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, stationId);
            ps.executeUpdate();
        }
    }
    
    @Override
    public void assignStationToRoute(int routeId, int stationId, int stopOrder) throws SQLException{
        String sql = "INSERT INTO RouteStations (route_id, station_id, stop_order) VALUES (?, ?, ?)";
        DataSource source = new DataSource(cred);
        try (Connection con = source.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, routeId);
            ps.setInt(2, stationId);
            ps.setInt(3, stopOrder);
            ps.executeUpdate();
        }
    }
    
}
