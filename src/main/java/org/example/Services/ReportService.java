package org.example.Services;


import org.example.DTO.ReportRequestDTO;
import org.example.DTO.ReportResponseDTO;
import org.example.DTO.ReportType;
import org.example.dao.impl.ReportDAOImpl;
import org.example.Models.Reports.ReportData ;
import org.example.Util.CSVUtil;
import org.example.Util.ExcelUtil;
import org.example.Util.PDFUtil;

import java.util.List;

public class ReportService   {
    private final ReportDAOImpl reportDAO = new ReportDAOImpl();


    public ReportResponseDTO generateReport(ReportRequestDTO request) {
        List<ReportData> data = reportDAO.fetchReport(request.getReportType(), request.getFilters());

        ReportResponseDTO response = new ReportResponseDTO();
        response.setData(data);
        response.setReportName(request.getReportType() + "_Report_" + System.currentTimeMillis());

        // Generate output file
        switch (request.getFormat().toUpperCase()) {
            case "CSV":
                CSVUtil.createCSV(data, response.getReportName() + ".csv");
                break;
            case "EXCEL":
                ExcelUtil.createExcel(data, response.getReportName() + ".xlsx");
                break;
            case "PDF":
                PDFUtil.createPDF(data, response.getReportName() + ".pdf");
                break;
        }

        return response;
    }
}