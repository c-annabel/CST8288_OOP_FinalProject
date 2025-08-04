/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewlayer;

import datalayer.VehicleDAO;
import datalayer.VehicleDAOImpl;
import domain.Vehicle;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/addVehicle")
public class AddVehicleServlet extends HttpServlet {

    private final VehicleDAO vehicleDAO = new VehicleDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String vehicleNumber = request.getParameter("vehicleNumber");
        String vehicleType = request.getParameter("vehicleType");
        String fuelType = request.getParameter("fuelType");
        double consumptionRate = Double.parseDouble(request.getParameter("consumptionRate"));
        int maxPassengers = Integer.parseInt(request.getParameter("maxPassengers"));
        int routeId = Integer.parseInt(request.getParameter("routeId"));

        Vehicle newVehicle = new Vehicle.VehicleBuilder()
                .setVehicleNumber(vehicleNumber)
                .setVehicleType(vehicleType)
                .setFuelType(fuelType)
                .setConsumptionRate(consumptionRate)
                .setMaxPassengers(maxPassengers)
                .setRouteId(routeId)
                .build();

        boolean success = vehicleDAO.addVehicle(newVehicle);

        if (success) {
            response.sendRedirect("vehicle_success.html");
        } else {
            response.sendRedirect("vehicle_fail.html");
        }
    }
}
