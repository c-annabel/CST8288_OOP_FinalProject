/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccesslayer;

import dataaccesslayer.DAOinterface.Maintence_ReportDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import transferobjects.CredentialsDTO;
import transferobjects.Maintence_ReportDTO;

/**
 *
 * @author HelloFriend
 */
public class Maintence_ReportDAOImp implements Maintence_ReportDAO{
    private CredentialsDTO cred;
    
    public Maintence_ReportDAOImp(CredentialsDTO cred){
        this.cred = cred;
    }
    
    @Override
    public List<Maintence_ReportDTO> getAllReports() throws SQLException{
        List<Maintence_ReportDTO> reports = new ArrayList<>();
        String sql = "SELECT * FROM Maintenance_Report";
        Maintence_ReportDTO report = new Maintence_ReportDTO();
        DataSource source = new DataSource(cred);
        
        try (Connection con = source.createConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);) 
        {
            
            while (rs.next()) {
                report.setID(rs.getInt("report_id"));
                report.setVehicle_number(rs.getString("vehicle_number"));
                report.setstatus(rs.getString("report_status"));
                report.setDescription(rs.getString("report_description"));  
                reports.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }
    
    @Override
    public List<Maintence_ReportDTO> getReportbyvehicle_number(Integer vehicle_number){
        String sql = "SELECT * FROM Maintenance_Report WHERE vehicle_number=?";
        Maintence_ReportDTO report = new Maintence_ReportDTO();
        List<Maintence_ReportDTO> reports = new ArrayList<>();
        DataSource source = new DataSource(cred);
        
        try (Connection con = source.createConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, vehicle_number);
            
            try(ResultSet rs = stmt.executeQuery();){
                while (rs.next()) {
                    report.setID(rs.getInt("report_id"));
                    report.setVehicle_number(rs.getString("vehicle_number"));
                    report.setstatus(rs.getString("report_status"));
                    report.setDescription(rs.getString("report_description"));  
                    reports.add(report);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }
    
    @Override
    public boolean addReport(Maintence_ReportDTO report){
        String sql = "INSERT INTO Maintenance_Report (vehicle_number, report_status, report_description) VALUES (?, ?, ?)";
        DataSource source = new DataSource(cred);
        
        try (Connection con = source.createConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setString(1, report.getNumber());
            stmt.setString(2, report.getStatus());
            stmt.setString(3, report.getDescription());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean updateReport(Maintence_ReportDTO report){
        String sql = "UPDATE Maintenance_Report SET report_status=?, report_description=? WHERE report_id=?";
        DataSource source = new DataSource(cred);
        
        try (Connection con = source.createConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, report.getStatus());
            stmt.setString(2, report.getDescription());
            stmt.setInt(3, report.getID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean deleteReport(Maintence_ReportDTO report){
        String sql = "DELETE FROM Maintenance_Report WHERE report_id=?";
        int reportId = report.getID();
        DataSource source = new DataSource(cred);
        
        try (Connection con = source.createConnection();
            PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setInt(1, reportId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
