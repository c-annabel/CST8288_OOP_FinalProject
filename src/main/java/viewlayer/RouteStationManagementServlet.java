package viewlayer;

import businesslayer.System_Control;
import dataaccesslayer.RouteDAOImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transferobjects.CredentialsDTO;
import transferobjects.RouteDTO;
import transferobjects.StationDTO;

/**
 * 
 * @author Chen Wang
 */
public class RouteStationManagementServlet extends HttpServlet {
    private System_Control logic;
    private static final String DATABASE_USERNAME = "cst8288";
    private static final String DATABASE_PASSWORD = "cst8288";
    private CredentialsDTO cred = new CredentialsDTO();

    @Override
    public void init() {
        cred.setPassword(DATABASE_PASSWORD);
        cred.setUsername(DATABASE_USERNAME);
        logic = new System_Control(cred);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try {
            if (request.getParameter("AddRoutePage") != null) {
                showAddRouteForm(out);
            } else if (request.getParameter("AddStationPage") != null) {
                showAddStationForm(out);
            } else if (request.getParameter("ViewAllRoutes") != null) {
                showAllRoutesWithStations(out);
            } else if (request.getParameter("ViewAllStations") != null) {
                showAllStations(out);
            } else if (request.getParameter("AssignStationPage") != null) {
                showAssignStationForm(out);
            } else if (request.getParameter("EditRoute") != null) {
                showEditRouteForm(request, out);
            } else if (request.getParameter("EditStation") != null) {
                showEditStationForm(request, out);
            } else {
                showHomePage(out);
            }
        } catch (SQLException ex) {
            out.println("<p>Error: " + ex.getMessage() + "</p>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        
        try {
            switch (action) {
                case "AddRoute":
                    addRoute(request);
                    showHomePage(out);
                    break;
                case "AddStation":
                    addStation(request);
                    showHomePage(out);
                    break;
                case "AssignStation":
                    assignStation(request);
                    showHomePage(out);
                    break;
                case "UpdateRoute":
                    updateRoute(request);
                    showHomePage(out);
                    break;
                case "UpdateStation":
                    updateStation(request);
                    showHomePage(out);
                    break;
                case "DeleteRoute":
                    deleteRoute(request);
                    showHomePage(out);
                    break;
                case "DeleteStation":
                    deleteStation(request);
                    showHomePage(out);
                    break;
                default:
                    showHomePage(out);
            }
        } catch (SQLException | NumberFormatException ex) {
            out.println("<p>Error: " + ex.getMessage() + "</p>");
        }
    }

    // Helper methods for page rendering
    private void showHomePage(PrintWriter out) throws SQLException {
        out.println("<html><head><title>Route & Station Management</title></head><body>");
        out.println("<h1>Route & Station Management</h1>");
        out.println("<ul>");
        out.println("<li><a href='RouteStationManagementServlet?AddRoutePage=true'>Add New Route</a></li>");
        out.println("<li><a href='RouteStationManagementServlet?AddStationPage=true'>Add New Station</a></li>");
        out.println("<li><a href='RouteStationManagementServlet?ViewAllRoutes=true'>View All Routes</a></li>");
        out.println("<li><a href='RouteStationManagementServlet?ViewAllStations=true'>View All Stations</a></li>");
        out.println("<li><a href='RouteStationManagementServlet?AssignStationPage=true'>Assign Station to Route</a></li>");
        out.println("</ul>");
        out.println("<a href='HomeServlet'>Back to System Home</a>");
        out.println("</body></html>");
    }

    private void showAddRouteForm(PrintWriter out) {
        out.println("<html><head><title>Add Route</title></head><body>");
        out.println("<h2>Add New Route</h2>");
        out.println("<form action='RouteStationManagementServlet' method='post'>");
        out.println("Route Name: <input type='text' name='routeName' required><br>");
        out.println("<input type='submit' name='action' value='AddRoute'>");
        out.println("</form>");
        out.println("<a href='RouteStationManagementServlet'>Back to Home</a>");
        out.println("</body></html>");
    }

    private void showAddStationForm(PrintWriter out) {
        out.println("<html><head><title>Add Station</title></head><body>");
        out.println("<h2>Add New Station</h2>");
        out.println("<form action='RouteStationManagementServlet' method='post'>");
        out.println("Station Name: <input type='text' name='stationName' required><br>");
        out.println("Latitude: <input type='text' name='latitude' required><br>");
        out.println("Longitude: <input type='text' name='longitude' required><br>");
        out.println("<input type='submit' name='action' value='AddStation'>");
        out.println("</form>");
        out.println("<a href='RouteStationManagementServlet'>Back to Home</a>");
        out.println("</body></html>");
    }

    private void showAssignStationForm(PrintWriter out) throws SQLException {
        List<RouteDTO> routes = logic.getAllRoutes();
        List<StationDTO> stations = logic.getAllStations();
        
        out.println("<html><head><title>Assign Station to Route</title></head><body>");
        out.println("<h2>Assign Station to Route</h2>");
        out.println("<form action='RouteStationManagementServlet' method='post'>");
        
        out.println("Select Route: <select name='routeId' required>");
        for (RouteDTO route : routes) {
            out.println("<option value='" + route.getRouteId() + "'>" + route.getRouteName() + "</option>");
        }
        out.println("</select><br>");
        
        out.println("Select Station: <select name='stationId' required>");
        for (StationDTO station : stations) {
            out.println("<option value='" + station.getStationId() + "'>" + station.getStationName() + "</option>");
        }
        out.println("</select><br>");
        
        out.println("Stop Order: <input type='number' name='stopOrder' min='1' required><br>");
        out.println("<input type='submit' name='action' value='AssignStation'>");
        out.println("</form>");
        out.println("<a href='RouteStationManagementServlet'>Back to Home</a>");
        out.println("</body></html>");
    }

    private void showAllRoutesWithStations(PrintWriter out) throws SQLException {
        List<RouteDTO> routes = logic.getRouteStationsView();
        
        out.println("<html><head><title>All Routes</title>");
        out.println("<style>table, th, td { border: 1px solid black; border-collapse: collapse; }</style>");
        out.println("</head><body>");
        out.println("<h2>All Routes with Stations</h2>");
        out.println("<table>");
        out.println("<tr><th>Route ID</th><th>Route Name</th><th>Stop Order</th><th>Station ID</th><th>Station Name</th><th>Latitude</th><th>Longitude</th><th>Actions</th></tr>");
        
        int currentRouteId = -1;
        for (RouteDTO route : routes) {
            if (route.getRouteId() != currentRouteId) {
                currentRouteId = route.getRouteId();
                out.println("<tr>");
                out.println("<td rowspan='" + getRowSpan(routes, currentRouteId) + "'>" + currentRouteId + "</td>");
                out.println("<td rowspan='" + getRowSpan(routes, currentRouteId) + "'>" + route.getRouteName() + "</td>");
            } else {
                out.println("<tr>");
            }
            
            out.println("<td>" + route.getStopOrder() + "</td>");
            out.println("<td>" + route.getStationId() + "</td>");
            out.println("<td>" + route.getStationName() + "</td>");
            out.println("<td>" + route.getLatitude() + "</td>");
            out.println("<td>" + route.getLongitude() + "</td>");
            
            if (route.getRouteId() == currentRouteId && route.getStopOrder() == 1) {
                out.println("<td rowspan='" + getRowSpan(routes, currentRouteId) + "'>");
                out.println("<a href='RouteStationManagementServlet?EditRoute=true&routeId=" + currentRouteId + "'>Edit</a> | ");
                out.println("<a href='RouteStationManagementServlet?action=DeleteRoute&routeId=" + currentRouteId + "'>Delete</a>");
                out.println("</td>");
            }
            
            out.println("</tr>");
        }
        
        out.println("</table>");
        out.println("<a href='RouteStationManagementServlet'>Back to Home</a>");
        out.println("</body></html>");
    }

    private int getRowSpan(List<RouteDTO> routes, int routeId) {
        int count = 0;
        for (RouteDTO r : routes) {
            if (r.getRouteId() == routeId) count++;
        }
        return count;
    }

    private void showAllStations(PrintWriter out) throws SQLException {
        List<StationDTO> stations = logic.getAllStations();
        
        out.println("<html><head><title>All Stations</title>");
        out.println("<style>table, th, td { border: 1px solid black; border-collapse: collapse; }</style>");
        out.println("</head><body>");
        out.println("<h2>All Stations</h2>");
        out.println("<table>");
        out.println("<tr><th>Station ID</th><th>Station Name</th><th>Latitude</th><th>Longitude</th><th>Actions</th></tr>");
        
        for (StationDTO station : stations) {
            out.println("<tr>");
            out.println("<td>" + station.getStationId() + "</td>");
            out.println("<td>" + station.getStationName() + "</td>");
            out.println("<td>" + station.getLatitude() + "</td>");
            out.println("<td>" + station.getLongitude() + "</td>");
            out.println("<td>");
            out.println("<a href='RouteStationManagementServlet?EditStation=true&stationId=" + station.getStationId() + "'>Edit</a> | ");
            out.println("<a href='RouteStationManagementServlet?action=DeleteStation&stationId=" + station.getStationId() + "'>Delete</a>");
            out.println("</td>");
            out.println("</tr>");
        }
        
        out.println("</table>");
        out.println("<a href='RouteStationManagementServlet'>Back to Home</a>");
        out.println("</body></html>");
    }

    private void showEditRouteForm(HttpServletRequest request, PrintWriter out) throws SQLException {
        int routeId = Integer.parseInt(request.getParameter("routeId"));
        RouteDTO route = logic.getRouteById(routeId);
        
        out.println("<html><head><title>Edit Route</title></head><body>");
        out.println("<h2>Edit Route</h2>");
        out.println("<form action='RouteStationManagementServlet' method='post'>");
        out.println("<input type='hidden' name='routeId' value='" + route.getRouteId() + "'>");
        out.println("Route Name: <input type='text' name='routeName' value='" + route.getRouteName() + "' required><br>");
        out.println("<input type='submit' name='action' value='UpdateRoute'>");
        out.println("</form>");
        out.println("<a href='RouteStationManagementServlet'>Back to Home</a>");
        out.println("</body></html>");
    }

    private void showEditStationForm(HttpServletRequest request, PrintWriter out) throws SQLException {
        int stationId = Integer.parseInt(request.getParameter("stationId"));
        StationDTO station = logic.getStationById(stationId);
        
        out.println("<html><head><title>Edit Station</title></head><body>");
        out.println("<h2>Edit Station</h2>");
        out.println("<form action='RouteStationManagementServlet' method='post'>");
        out.println("<input type='hidden' name='stationId' value='" + station.getStationId() + "'>");
        out.println("Station Name: <input type='text' name='stationName' value='" + station.getStationName() + "' required><br>");
        out.println("Latitude: <input type='text' name='latitude' value='" + station.getLatitude() + "' required><br>");
        out.println("Longitude: <input type='text' name='longitude' value='" + station.getLongitude() + "' required><br>");
        out.println("<input type='submit' name='action' value='UpdateStation'>");
        out.println("</form>");
        out.println("<a href='RouteStationManagementServlet'>Back to Home</a>");
        out.println("</body></html>");
    }

    // Helper methods for database operations
    private void addRoute(HttpServletRequest request) throws SQLException {
        RouteDTO route = new RouteDTO();
        route.setRouteName(request.getParameter("routeName"));
        logic.addRoute(route);
    }

    private void addStation(HttpServletRequest request) throws SQLException {
        StationDTO station = new StationDTO();
        station.setStationName(request.getParameter("stationName"));
        station.setLatitude(request.getParameter("latitude"));
        station.setLongitude(request.getParameter("longitude"));
        logic.addStation(station);
    }

    private void assignStation(HttpServletRequest request) throws SQLException {
        int routeId = Integer.parseInt(request.getParameter("routeId"));
        int stationId = Integer.parseInt(request.getParameter("stationId"));
        int stopOrder = Integer.parseInt(request.getParameter("stopOrder"));
        logic.assignStationToRoute(routeId, stationId, stopOrder);
    }

    private void updateRoute(HttpServletRequest request) throws SQLException {
        RouteDTO route = new RouteDTO();
        route.setRouteId(Integer.parseInt(request.getParameter("routeId")));
        route.setRouteName(request.getParameter("routeName"));
        logic.updateRoute(route);
    }

    private void updateStation(HttpServletRequest request) throws SQLException {
        StationDTO station = new StationDTO();
        station.setStationId(Integer.parseInt(request.getParameter("stationId")));
        station.setStationName(request.getParameter("stationName"));
        station.setLatitude(request.getParameter("latitude"));
        station.setLongitude(request.getParameter("longitude"));
        logic.updateStation(station);
    }

    private void deleteRoute(HttpServletRequest request) throws SQLException {
        int routeId = Integer.parseInt(request.getParameter("routeId"));
        logic.deleteRoute(routeId);
    }

    private void deleteStation(HttpServletRequest request) throws SQLException {
        int stationId = Integer.parseInt(request.getParameter("stationId"));
        logic.deleteStation(stationId);
    }
}