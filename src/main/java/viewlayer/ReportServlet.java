/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// Updated ReportServlet.java
package viewlayer;

import businesslayer.System_Control;
import dataaccesslayer.FuelConsumptionDAO;
import dataaccesslayer.UserDAOImpl;
import dataaccesslayer.VehicleDAOImpl;
import transferobjects.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

/**
 * 
 * @author Chinmoy
 * @since modify by Chen Wang
 */
public class ReportServlet extends HttpServlet {
    private static final String DATABASE_USERNAME = "cst8288";
    private static final String DATABASE_PASSWORD = "cst8288";
    private CredentialsDTO cred;
    private System_Control logic;

    @Override
    public void init() {
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        cred = new CredentialsDTO();
        cred.setPassword(DATABASE_PASSWORD);
        cred.setUsername(DATABASE_USERNAME);
        logic = new System_Control(cred);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try {
            // 1. Predictive Maintenance Alerts
            out.println("<h2>Predictive Maintenance Alerts</h2>");
            displayMaintenanceAlerts(request, out);

            // 2. Fuel/Energy Consumption Reports
            out.println("<h2>Fuel/Energy Consumption Reports</h2>");
            displayFuelConsumption(out);

            // 3. Operator Performance Dashboard
            out.println("<h2>Operator Performance Dashboard</h2>");
            displayOperatorPerformance(out);

        } catch (SQLException e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
            e.printStackTrace();
        }
    }

    private void displayMaintenanceAlerts(HttpServletRequest request, PrintWriter out) throws SQLException {
        List<Vehicle> vehicles = logic.getAllVehicles();
        out.println("<table border='1' style='border-collapse: collapse;'>");
        out.println("<tr><th>Vehicle #</th><th>Type</th><th>Component</th><th>Hours Used</th><th>Threshold</th><th>Status</th></tr>");
        HttpSession session = request.getSession(false);
        for (Vehicle vehicle : vehicles) {
            String vehicleType = vehicle.getVehicleType();
            String component = "";
            int hoursUsed = 0;
            double threshold = vehicle.getMaintenance_threshold();
            
            // Get component-specific data
            if (vehicle instanceof DieselBus || vehicle instanceof DieselElectricTrain) {
                component = "Brake";
                hoursUsed = vehicle.getHoursOfcomponents();
            } else if (vehicle instanceof ElectricLightRail) {
                component = "Catenary";
                hoursUsed = ((ElectricLightRail) vehicle).getHoursOfcomponents();
            }
            
            String status = hoursUsed > threshold ? 
                "<span style='color:red;'>MAINTENANCE REQUIRED</span>" : 
                "<span style='color:green;'>OK</span>";
            
            out.println("<tr>");
            out.println("<td>" + vehicle.getVehicleNumber() + "</td>");
            out.println("<td>" + vehicleType + "</td>");
            out.println("<td>" + component + "</td>");
            out.println("<td>" + hoursUsed + "</td>");
            out.println("<td>" + threshold + "</td>");
            out.println("<td>" + status + "</td>");
            out.println("</tr>");
        }
        out.println("</table>");
        
    }

    private void displayFuelConsumption(PrintWriter out) {
        FuelConsumptionDAO fuelDAO = new FuelConsumptionDAO(cred);
        List<FuelConsumptionDTO> fuelData = fuelDAO.getFuelConsumptionReport();
        
        out.println("<table border='1' style='border-collapse: collapse;'>");
        out.println("<tr><th>Vehicle #</th><th>Type</th><th>Total Fuel Consumed</th><th>Avg Distance</th><th>Status</th></tr>");
        
        for (FuelConsumptionDTO data : fuelData) {
            String alert = data.getTotalFuelConsumed() > 50 ? 
                "<span style='color:red;'>HIGH CONSUMPTION</span>" : 
                "<span style='color:green;'>NORMAL</span>";
            
            out.println("<tr>");
            out.println("<td>" + data.getVehicleNumber() + "</td>");
            out.println("<td>" + data.getVehicleType() + "</td>");
            out.println("<td>" + data.getTotalFuelConsumed() + " liters</td>");
            out.println("<td>" + data.getAverageDistanceCovered() + " km</td>");
            out.println("<td>" + alert + "</td>");
            out.println("</tr>");
        }
        out.println("</table>");
    }

    private void displayOperatorPerformance(PrintWriter out) throws SQLException {
        UserDAOImpl userDao = new UserDAOImpl(cred);
        List<User> operators = new ArrayList<>();
        
        // Get only operators (assuming type 'Operator')
        for (User user : userDao.getAllUsers()) {
            if ("Operator".equals(user.getUserType())) {
                operators.add(user);
            }
        }
        
        out.println("<table border='1' style='border-collapse: collapse;'>");
        out.println("<tr><th>Operator</th><th>On-time Rate</th><th>Efficiency</th><th>Break Logs</th></tr>");
        
        for (User operator : operators) {
            // Simulated performance data
            double onTimeRate = 80 + (Math.random() * 20); // 80-100%
            double efficiency = 75 + (Math.random() * 25); // 75-100%
            
            List<String> breakLogs = logic.getUserBreakLog(operator.getName());
            String breaks = breakLogs.isEmpty() ? "No breaks recorded" : 
                "<details><summary>" + breakLogs.size() + " breaks</summary>" + 
                String.join("<br>", breakLogs) + "</details>";
            
            out.println("<tr>");
            out.println("<td>" + operator.getName() + "</td>");
            out.println("<td>" + String.format("%.1f%%", onTimeRate) + "</td>");
            out.println("<td>" + String.format("%.1f%%", efficiency) + "</td>");
            out.println("<td>" + breaks + "</td>");
            out.println("</tr>");
        }
        out.println("</table>");
    }
}
