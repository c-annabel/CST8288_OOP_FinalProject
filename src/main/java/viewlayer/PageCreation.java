/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewlayer;

import businesslayer.System_Control;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import transferobjects.Vehicle;

/**
 *
 * @author Chen Wang
 */
public class PageCreation {
    public static void loginPage(HttpServletRequest request, PrintWriter out){
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("    <meta charset=\"UTF-8\">");
        out.println("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("    <title>Public Transit FLeet Management</title>");
        out.println("    <style>");
        out.println("        body { font-family: Arial, sans-serif; background-color: #f4f4f4; display: flex; justify-content: center; align-items: center; min-height: 100vh; margin: 0; }");
        out.println("        .login-container { background-color: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); text-align: center; width: 350px; }");
        out.println("        h2 { color: #333; margin-bottom: 20px; }");
        out.println("        .form-group { margin-bottom: 15px; text-align: left; }");
        out.println("        label { display: block; margin-bottom: 5px; color: #555; font-weight: bold; }");
        out.println("        input[type=\"text\"], input[type=\"password\"] { width: calc(100% - 22px); padding: 10px; border: 1px solid #ddd; border-radius: 4px; font-size: 16px; }");
        out.println("        button { background-color: #007bff; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; font-size: 16px; transition: background-color 0.3s ease; width: 100%; }");
        out.println("        button:hover { background-color: #0056b3; }");
        out.println("        .error-message { color: red; margin-top: 10px; }");
        out.println("    </style>");
        out.println("</head>");
        out.println("<body>");
        out.println("    <div class=\"login-container\">");
        out.println("        <h2>Problic Transit FLeet Management</h2>");
        out.println("        <form action=\"ViewServlet\" method=\"post\">");
        out.println("            <div class=\"form-group\">");
        out.println("                <label for=\"Email\">Email:</label>");
        out.println("                <input type=\"text\" id=\"Email\" name=\"Email\" value=\"\" required>");
        out.println("            </div>");
        out.println("            <div class=\"form-group\">");
        out.println("                <label for=\"password\">Password:</label>");
        out.println("                <input type=\"password\" id=\"password\" name=\"password\" value=\"\" required>");
        out.println("            </div>");
        out.println("            <button type=\"submit\" name=\"login\" value=\"Login\">Login</button>");
        // Display error message if present in request attributes
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
            out.println("<p class=\"error-message\">" + errorMessage + "</p>");
        }
        out.println("        </form>");
        out.println("    </div>");
        out.println("</body>");
        out.println("</html>");
    }
    
    public static void SystemHomePage(HttpServletRequest request, PrintWriter out, System_Control logic){
        HttpSession session = request.getSession(false);
        String name = (String) session.getAttribute("name");
        String role = (String) session.getAttribute("role");

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Manager Dashboard</title></head><body>");
        out.println("<h1>Welcome, " + name + " ("+ role +")</h1>");

        // Display break confirmation message if exists
        String breakMessage = (String) session.getAttribute("breakMessage");
        if (breakMessage != null) {
            out.println("<p style='color:green'>" + breakMessage + "</p>");
            session.removeAttribute("breakMessage"); // Clear after display
        }

        if(role.equals("Manager")){
            out.println("<ul>");
            out.println("<li><a href='VehicleManagementServlet'>Manage Vehicles</a></li>");
            out.println("<li><a href='ReportServlet'>Manage Reports</a></li>");
            out.println("<li><a href='GPSOverviewServlet'>View GPS Tracker</a></li>");
            out.println("<li><a href='RouteStationManagementServlet'>Route and Station Management</a></li>");
            out.println("</ul>");
        }else{
            out.println("<ul>");
            out.println("<li><a href='ReportServlet'>Manage Reports</a></li>");
            out.println("<li><a href='GPSOverviewServlet'>View GPS Tracker</a></li>");
            out.println("</ul>");
        }

        out.println("<form action='HomeServlet' method='post'>");
        out.println("<label for='breakTime'>Break Time (minutes): </label>");
        out.println("<input type='number' id='breakTime' name='breakTime' min='1' placeholder='Enter minutes'>");
        out.println("<br><br>");

        out.println("<button type='submit' name='action' value='logout'>Logout</button>");
        out.println("<button type='submit' name='action' value='break'>Take a Break</button>");
        out.println("</form>");

        // Display break logs
        out.println("<h2>Your Break Logs</h2>");
        out.println("<ul>");
        List<String> breakLogs = logic.getUserBreakLog(name);
        for (String log : breakLogs) {
            out.println("<li>" + log + "</li>");
        }
        out.println("</ul>");

        out.println("</body></html>");
    }
    
    public static void VehicleManageHome(HttpServletRequest request, PrintWriter out){
        
        out.println("<html><head><title>Vehicle Management</title></head><body>");
        out.println("<h1>Vehicle Management Home</h1>");
        out.println("<form action=\"VehicleManagementServlet\" method=\"get\" >");
        out.println("<ul>");
        out.println("<li><input type=\"submit\" name=\"AddVehiclePage\" value=\"AddVehiclePage\">Add New Vehicle</input></li>");
        out.println("<li><input type=\"submit\" name=\"ViewAll\" value=\"ViewAll\">View All Vehicles</input></li>");
        out.println("<li><input type=\"submit\" name=\"EditVehicle\" value=\"EditVehicle\">Edit or Delete Vehicles</input></li>");
        out.println("</ul>");
        out.println("</form>");
        out.println("<a href='HomeServlet'>Back to System Home</a>");
        out.println("</body></html>");
    }
    public static void ShowAllVehicle(List<Vehicle> vehicles, PrintWriter out) {
        out.println("<html><head><title>Vehicle Management</title>");
        out.println("<style>");
        out.println("table {border-collapse: collapse; width: 100%;}");
        out.println("th, td {border: 1px solid #ddd; padding: 8px; text-align: left;}");
        out.println("th {background-color: #f2f2f2;}");
        out.println("tr:nth-child(even) {background-color: #f9f9f9;}");
        out.println("</style>");
        out.println("</head><body>");
        out.println("<h1>Vehicle Management</h1>");
        out.println("<table>");
        out.println("<tr>");
        out.println("<th>ID</th>");
        out.println("<th>Number</th>");
        out.println("<th>Type</th>");
        out.println("<th>Fuel</th>");
        out.println("<th>Consumption Rate</th>");
        out.println("<th>Capacity</th>");
        out.println("<th>Route ID</th>");
        out.println("<th>Hours Used</th>");
        out.println("<th>Maint Threshold</th>");
        out.println("<th>Fuel Alert</th>");
        out.println("<th>Diagnostics</th>");
        out.println("</tr>");

        for (Vehicle v : vehicles) {
            out.println("<tr>");
            out.println("<td>" + v.getVehicleId() + "</td>");
            out.println("<td>" + v.getVehicleNumber() + "</td>");
            out.println("<td>" + v.getVehicleType() + "</td>");
            out.println("<td>" + v.getFuelType() + "</td>");
            out.println("<td>" + v.getConsumptionRate() + "</td>");
            out.println("<td>" + v.getMaxPassengers() + "</td>");
            out.println("<td>" + v.getRouteId() + "</td>");
            out.println("<td>" + v.getHoursOfcomponents() + "</td>");
            out.println("<td>" + v.getMaintenance_threshold() + "</td>");
            out.println("<td>" + v.getFuel_alert_threshold() + "</td>");

            // Add diagnostic status with color coding
            String diagnostics = v.getDiagnostics();
            String color = "black";
            if ("Need service".equalsIgnoreCase(diagnostics)) {
                color = "red";
            } else if ("No Need service".equalsIgnoreCase(diagnostics)) {
                color = "green";
            }
            out.println("<td style='color:" + color + ";'>" + diagnostics + "</td>");

            out.println("</tr>");
        }

        out.println("</table>");
        out.println("<a href='VehicleManagementServlet'>Back to System Home</a>");
        out.println("</body></html>");
    }
    
    public static void AddVehicle(HttpServletRequest request, PrintWriter out){
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\"><head><title>Vehicle Management</title></head><body>");
        out.println("<h1>Vehicle Management</h1>");
        out.println("<form action='VehicleManagementServlet' method='post'>");
        out.println("Vehicle Type: <select name='vehicleType'>");
        out.println("<option value='Diesel Bus'>Diesel Bus</option>");
        out.println("<option value='Electric Light Rail'>Electric Light Rail</option>");
        out.println("<option value='Diesel-Electric Train'>Diesel-Electric Train</option>");
        out.println("</select><br>");
        out.println("Fuel Type: <input type='text' name='fuelType'><br>");
        out.println("Consumption Rate: <input type='number' name='consumptionRate' step='any'><br>");
        out.println("Max Passengers: <input type='number' name='maxPassengers'><br>");
        out.println("<input type='submit' name=\"action\" value='AddVehicle'>");
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
            out.println("<p class=\"error-message\">" + errorMessage + "</p>");
        }
        out.println("</form>");
        out.println("<a href='VehicleManagementServlet'>Back to System Home</a>");
        out.println("</body></html>");
    }
    
    public static void EditVehicle(List<Vehicle> vehicles, HttpServletRequest request, PrintWriter out) {
        out.println("<html><head><title>Edit/Delete Vehicle</title>");
        out.println("<style>");
        out.println("form {border: 1px solid #ccc; padding: 20px; margin-bottom: 20px; border-radius: 5px;}");
        out.println("input[type='text'], input[type='number'] {width: 100%; padding: 8px; margin: 5px 0;}");
        out.println(".error-message {color: red; font-weight: bold;}");
        out.println("</style>");
        out.println("</head><body>");
        out.println("<h2>Edit or Delete Vehicle</h2>");

        for (Vehicle v : vehicles) {
            out.println("<form action='VehicleManagementServlet' method='post'>");
            out.println("<input type='hidden' name='vehicleId' value='" + v.getVehicleId() + "'>");

            out.println("<label>Vehicle Number:</label>");
            out.println("<input type='text' name='vehicleNumber' value='" + v.getVehicleNumber() + "' readonly><br>");

            out.println("<label>Vehicle Type:</label>");
            out.println("<input type='text' name='VehicleType' value='" + v.getVehicleType() + "' readonly><br>");

            out.println("<label>Fuel Type:</label>");
            out.println("<input type='text' name='fuelType' value='" + v.getFuelType() + "'><br>");

            out.println("<label>Consumption Rate:</label>");
            out.println("<input type='number' step='any' name='consumptionRate' value='" + v.getConsumptionRate() + "'><br>");

            out.println("<label>Max Passengers:</label>");
            out.println("<input type='number' name='maxPassengers' value='" + v.getMaxPassengers() + "'><br>");

            out.println("<label>Route ID:</label>");
            out.println("<input type='number' name='routeId' value='" + v.getRouteId() + "'><br>");

            out.println("<label>Hours Used:</label>");
            out.println("<input type='number' name='hoursUsed' value='" + v.getHoursOfcomponents() + "'><br>");

            out.println("<label>Maintenance Threshold:</label>");
            out.println("<input type='number' name='maintenanceThreshold' value='" + v.getMaintenance_threshold() + "'><br>");

            out.println("<label>Fuel Alert Threshold:</label>");
            out.println("<input type='number' name='fuelAlertThreshold' value='" + v.getFuel_alert_threshold() + "'><br>");

            out.println("<label>Diagnostics:</label>");
            out.println("<select name='diagnostics'>");
            out.println("<option value='Need service'" + ("Need service".equals(v.getDiagnostics()) ? " selected" : "") + ">Need service</option>");
            out.println("<option value='No Need service'" + ("No Need service".equals(v.getDiagnostics()) ? " selected" : "") + ">No Need service</option>");
            out.println("</select><br>");

            out.println("<input type='submit' name='action' value='Update' style='margin-right:10px;'>");
            out.println("<input type='submit' name='action' value='Delete' style='background-color:#ff6666;'>");

            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null) {
                out.println("<p class='error-message'>" + errorMessage + "</p>");
            }
            out.println("</form><hr>");
        }
        out.println("<a href='VehicleManagementServlet'>Back to System Home</a>");
        out.println("</body></html>");
    }  
}


