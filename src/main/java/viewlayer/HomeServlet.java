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
import transferobjects.CredentialsDTO;

/**
 *
 * @author Chen Wang
 */
public class HomeServlet extends HttpServlet {
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
        HttpSession session = request.getSession(false);

        if (!(boolean)session.getAttribute("login")) {
            response.sendRedirect("ViewServlet");
            return;
        }
        PageCreation.SystemHomePage(request, out, logic);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
        cred.setPassword(DATABASE_PASSWORD);
        cred.setUsername(DATABASE_USERNAME);
        logic = new System_Control(cred);

        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);

        if (session != null) {
            String name = (String) session.getAttribute("name");

            if ("break".equals(action)) {
                // Handle break logging
                String breakTime = request.getParameter("breakTime");
                if (breakTime != null && !breakTime.isEmpty()) {
                    String logMessage = "BREAK: " + name + " took a " + breakTime + " minute break";
                    logic.UserTakeBreak(logMessage, name);
                    session.setAttribute("breakMessage", "Break logged successfully!");
                }
            } else if ("logout".equals(action)) {
                // Handle logout
                session.invalidate();
                response.sendRedirect("ViewServlet");
                return;
            }
        }

        response.sendRedirect("HomeServlet");
    }
}
