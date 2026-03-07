package org.example.dao;


import org.example.Models.Reports.ReportData;
import java.util.List;
import java.util.Map;

public interface ReportDAO {
    List<ReportData> fetchReportData(Map<String, Object> filters);
}