package viewlayer;

import businesslayer.System_Control;
import dataaccesslayer.FuelConsumptionDAO;
import transferobjects.FuelConsumption;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.List;
import transferobjects.CredentialsDTO;

/**
 * 
 * @author chinmoy
 */
public class FuelMonitorServlet extends HttpServlet {
    private System_Control logic;
    private CredentialsDTO cred = new CredentialsDTO();
    private static final String DATABASE_USERNAME = "cst8288";
    private static final String DATABASE_PASSWORD = "cst8288";
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        cred.setPassword(DATABASE_PASSWORD);
        cred.setUsername(DATABASE_USERNAME);
        FuelConsumptionDAO fuelDAO = new FuelConsumptionDAO(cred);
        
        // Fetch fuel consumption data
        List<FuelConsumption> fuelData = fuelDAO.getFuelConsumptionReport();

        out.println("<h3>Fuel/Energy Consumption Reports</h3>");
        for (FuelConsumption data : fuelData) {
            out.println("<p>" + data.getVehicleNumber() + " (" + data.getVehicleType() + ") - "
                    + "Fuel Consumed: " + data.getTotalFuelConsumed() + " liters, "
                    + "Average Distance Covered: " + data.getAverageDistanceCovered() + " km</p>");
            // Check if consumption exceeds the threshold and alert
            if (data.getTotalFuelConsumed() > 50) {  // Example: alert if fuel consumption exceeds 50 liters
                out.println("<p><strong>Alert: High Consumption for " + data.getVehicleNumber() + "!</strong></p>");
            }
        }
    }
}
