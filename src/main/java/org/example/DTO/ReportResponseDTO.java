package org.example.DTO;

import java.util.List;
import org.example.Models.Reports.ReportData;

public class ReportResponseDTO {
    private List<ReportData> data;
    private String reportName;

    public List<ReportData> getData() { return data; }
    public void setData(List<ReportData> data) { this.data = data; }

    public String getReportName() { return reportName; }
    public void setReportName(String reportName) { this.reportName = reportName; }
}