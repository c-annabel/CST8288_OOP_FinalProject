/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dataaccesslayer.DAOinterface;

import java.sql.SQLException;
import java.util.List;
import transferobjects.Maintence_ReportDTO;

/**
 *
 * @author HelloFriend
 */
public interface Maintence_ReportDAO {
        List<Maintence_ReportDTO> getAllReports()  throws SQLException;
	List<Maintence_ReportDTO> getReportbyvehicle_number(Integer vehicle_number);
	boolean addReport(Maintence_ReportDTO report);
	boolean updateReport(Maintence_ReportDTO report);
	boolean deleteReport(Maintence_ReportDTO report);
}
