/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewlayer;

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
    
    public static void SystemHomePage(HttpServletRequest request, PrintWriter out){
        HttpSession session = request.getSession(false);
        String name = (String) session.getAttribute("name");
        String role = (String) session.getAttribute("role");
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Manager Dashboard</title></head><body>");
        out.println("<h1>Welcome, " + name + " ("+ role +")</h1>");
        if(role.equals("Manager")){
            out.println("<ul>");
            out.println("<li><a href='VehicleManagementServlet'>Manage Vehicles</a></li>");
            out.println("<li><a href='MaintenanceServlet'>Manage Maintenance Reports</a></li>");
            out.println("<li><a href='GPSOverviewServlet'>View GPS Tracker</a></li>");
            out.println("</ul>");
        }else{
            out.println("<ul>");
            out.println("<li><a href='MaintenanceServlet'>Manage Maintenance Reports</a></li>");
            out.println("<li><a href='GPSOverviewServlet'>View GPS Tracker</a></li>");
            out.println("</ul>");
        }
        out.println("<a href='LogoutServlet'>Logout</a>");
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
    public static void ShowAllVehicle(List<Vehicle> vehicles, PrintWriter out){
        out.println("<html><head><title>Vehicle Management</title></head><body>");
        out.println("<h1>Vehicle Management</h1>");
        out.println("<table border='1'>");
        out.println("<tr><th>ID</th><th>Number</th><th>Type</th><th>Fuel</th><th>Rate</th><th>Capacity</th><th>Route ID</th><th>Action</th></tr>");

        for (Vehicle v : vehicles) {
            out.println("<tr>");
            out.println("<td>" + v.getVehicleId() + "</td>");
            out.println("<td>" + v.getVehicleNumber() + "</td>");
            out.println("<td>" + v.getVehicleType() + "</td>");
            out.println("<td>" + v.getFuelType() + "</td>");
            out.println("<td>" + v.getConsumptionRate() + "</td>");
            out.println("<td>" + v.getMaxPassengers() + "</td>");
            out.println("<td>" + v.getRouteId() + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("</body></html>");
    }
    
    public static void AddVehicle(HttpServletRequest request, PrintWriter out){
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\"><head><title>Vehicle Management</title></head><body>");
        out.println("<h1>Vehicle Management</h1>");
        out.println("<form action='VehicleManagementServlet' method='post'>");
        out.println("Vehicle Number: <input type='text' name='vehicleNumber'><br>");
        out.println("Vehicle Type: <select name='vehicleType'>");
        out.println("<option value='Diesel Bus'>Diesel Bus</option>");
        out.println("<option value='Electric Light Rail'>Electric Light Rail</option>");
        out.println("<option value='Diesel-Electric Train'>Diesel-Electric Train</option>");
        out.println("</select><br>");
        out.println("Fuel Type: <input type='text' name='fuelType'><br>");
        out.println("Consumption Rate: <input type='text' name='consumptionRate'><br>");
        out.println("Max Passengers: <input type='number' name='maxPassengers'><br>");
        out.println("Route ID: <input type='number' name='routeId'><br>");
        out.println("<input type='submit' name=\"AddVehicle\" value='AddVehicle'>");
        out.println("</form>");
        out.println("</body></html>");
    }
    
    public static void EditVehicle(List<Vehicle> vehicles, PrintWriter out){
        out.println("<html><head><title>Edit/Delete Vehicle</title></head><body>");
        out.println("<h2>Edit or Delete Vehicle</h2>");
        for (Vehicle v : vehicles) {
            out.println("<form action='EditVehicleServlet' method='post'>");
            out.println("<input type='hidden' name='vehicleId' value='" + v.getVehicleId() + "'>");
            out.println("Vehicle Number: <input type='text' name='vehicleNumber' value='" + v.getVehicleNumber() + "'><br>");
            out.println("Fuel Type: <input type='text' name='fuelType' value='" + v.getFuelType() + "'><br>");
            out.println("Consumption Rate: <input type='text' name='consumptionRate' value='" + v.getFuelType() + "'><br>");
            out.println("Max Passengers: <input type='number' name='maxPassengers' value='" + v.getMaxPassengers() + "'><br>");
            out.println("Route ID: <input type='number' name='routeId' value='" + v.getRouteId() + "'><br>");
            out.println("<input type='submit' name='action' value='Update'>");
            out.println("<input type='submit' name='action' value='Delete'>");
            out.println("</form><hr>");
        }
        out.println("</body></html>");
    }
}
