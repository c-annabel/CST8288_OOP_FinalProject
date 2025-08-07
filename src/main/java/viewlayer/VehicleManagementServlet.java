/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewlayer;
import businesslayer.System_Control;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import transferobjects.CredentialsDTO;
import transferobjects.Vehicle;

/**
 *
 * @author Chen Wang
 */
public class VehicleManagementServlet extends HttpServlet {
    private System_Control logic;
    private CredentialsDTO cred = new CredentialsDTO();
    private static final String DATABASE_USERNAME = "cst8288";
    private static final String DATABASE_PASSWORD = "cst8288";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        cred.setPassword(DATABASE_PASSWORD);
        cred.setUsername(DATABASE_USERNAME);
        
        logic = new System_Control(cred);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        if(request.getParameter("AddVehiclePage") != null){
            PageCreation.AddVehicle(request, out);
        } else if(request.getParameter("ViewAll") != null){
            List<Vehicle> vehicles = logic.getAllVehicles();
            PageCreation.ShowAllVehicle(vehicles, out);
        } else if(request.getParameter("EditVehicle") != null){
            List<Vehicle> vehicles = logic.getAllVehicles();
            PageCreation.EditVehicle(vehicles, out);
        } else {
            PageCreation.VehicleManageHome(request, out);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        cred.setPassword(DATABASE_PASSWORD);
        cred.setUsername(DATABASE_USERNAME);
        
        logic = new System_Control(cred);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
    }
}
