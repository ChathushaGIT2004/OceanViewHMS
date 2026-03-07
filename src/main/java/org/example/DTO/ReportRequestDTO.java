package org.example.DTO;

import java.util.Map;

public class ReportRequestDTO {
    private ReportType reportType;
    private String format;
    private Map<String, Object> filters;

    public ReportType getReportType() { return reportType; }
    public void setReportType(ReportType reportType) { this.reportType = reportType; }

    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }

    public Map<String, Object> getFilters() { return filters; }
    public void setFilters(Map<String, Object> filters) { this.filters = filters; }
}