/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewlayer;

import businesslayer.LocationAdapter;
import businesslayer.StationAdapter;
import businesslayer.System_Control;
import dataaccesslayer.DataSource;
import transferobjects.CredentialsDTO;
import transferobjects.GPSDTO;
import transferobjects.StationDTO;
import transferobjects.Vehicle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

public class GPSOverviewServlet extends HttpServlet {
    private static final String DATABASE_USERNAME = "cst8288";
    private static final String DATABASE_PASSWORD = "cst8288";
    private CredentialsDTO cred = new CredentialsDTO();
    private System_Control logic;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        cred.setPassword(DATABASE_PASSWORD);
        cred.setUsername(DATABASE_USERNAME);
        logic = new System_Control(cred);
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try {
            // Get all stations and vehicles
            List<StationDTO> stations = logic.getAllStations();
            List<Vehicle> vehicles = logic.getAllVehicles();
            
            // Get latest GPS tracking data for vehicles
            List<GPSDTO> locationData = getLocationData(stations, vehicles);
            
            // Display the GPS overview page
            displayGPSOverview(request, out, locationData);
            
        } catch (SQLException e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }
    }
    
    private List<GPSDTO> getLocationData(List<StationDTO> stations, List<Vehicle> vehicles) 
            throws SQLException {
        List<GPSDTO> locationData = new ArrayList<>();
        String sql = "SELECT gps_id, vehicle_number, timestamp, latitude, longitude FROM GPS_Tracking";

        DataSource source = new DataSource(cred);
        try (Connection con = source.createConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                GPSDTO gps = new GPSDTO();
                gps.setGps_id(rs.getInt("gps_id"));
                gps.setIdentifier(rs.getString("vehicle_number"));
                gps.setTimestamp(rs.getString("timestamp"));
                gps.setLatitude(rs.getString("latitude"));
                gps.setLongitude(rs.getString("longitude"));

                locationData.add(gps);
            }
        }
        return locationData;
    }
    
    private void displayGPSOverview(HttpServletRequest request, PrintWriter out, 
                                    List<GPSDTO> locationData) {
        out.println("<html><head><title>GPS Overview</title>");
        out.println("<style>");
        out.println("  table { border-collapse: collapse; width: 100%; }");
        out.println("  th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
        out.println("  tr:nth-child(even) { background-color: #f2f2f2; }");
        out.println("  .station { background-color: #d4edda; }");
        out.println("  .vehicle { background-color: #cce5ff; }");
        out.println("</style>");
        
        out.println("</head><body>");
        out.println("<h1>GPS Overview</h1>");
        
        // Real-time locations table
        out.println("<h2>Real-time Locations</h2>");
        out.println("<table>");
        out.println("<tr><th>Type</th><th>Identifier</th><th>Timestamp</th><th>Latitude</th><th>Longitude</th></tr>");
        
        for (GPSDTO dto : locationData) {
            String rowClass = "station".equals(dto.getType()) ? "station" : "vehicle";
            out.println("<tr class='" + rowClass + "'>");
            out.println("<td>" + ("station".equals(dto.getType()) ? "Station" : "Vehicle") + "</td>");
            out.println("<td>" + dto.getVehicle_number() + "</td>");
            out.println("<td>" + (dto.getTimestamp() != null ? dto.getTimestamp() : "N/A") + "</td>");
            out.println("<td>" + dto.getLatitude() + "</td>");
            out.println("<td>" + dto.getLongitude() + "</td>");
            out.println("</tr>");
        }
        
        out.println("</table>");
        
        // Break log section
        HttpSession session = request.getSession(false);
        if (session != null) {
            String name = (String) session.getAttribute("name");
            out.println("<h2>Break Log for " + name + "</h2>");
            out.println("<form action='HomeServlet' method='post'>");
            out.println("Break Time (minutes): ");
            out.println("<input type='number' name='breakTime' min='1' required>");
            out.println("<input type='submit' name='action' value='LogBreak'>");
            out.println("</form>");
        }
        
        out.println("<br><a href='HomeServlet'>Back to Home</a>");
        out.println("</body></html>");
    }
}
