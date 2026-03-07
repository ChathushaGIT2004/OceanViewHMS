package org.example.Models.Reports;





import java.util.Map;

public class ReportData {
    private Map<String, Object> row;

    public ReportData(Map<String, Object> row) { this.row = row; }
    public Map<String, Object> getRow() { return row; }
    public void setRow(Map<String, Object> row) { this.row = row; }
}