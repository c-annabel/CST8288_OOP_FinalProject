/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewlayer;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
/**
 *
 * @author chinmoy
 */
public class ReportServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h3>Maintenance Dashboard</h3>");
        out.println("<p>Brake replacement for Bus #101</p>");

        out.println("<h3>Operator Performance</h3>");
        out.println("<p>Operator #01: On-time 95%</p>");

        out.println("<h3>Fuel Cost Report</h3>");
        double totalCost = 1500.00;
        out.println("<p>Total Fuel Costs: $" + totalCost + "</p>");
    }
}
