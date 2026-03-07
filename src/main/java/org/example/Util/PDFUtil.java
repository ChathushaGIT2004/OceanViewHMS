package org.example.Util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import org.example.Models.Reports.ReportData;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class PDFUtil {

    public static void createPDF(List<ReportData> data, String filename) {
        try {
            PdfWriter writer = new PdfWriter(filename);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            if (data.isEmpty()) {
                document.add(new Paragraph("No data available for this report."));
            } else {
                // Create table headers
                Map<String, Object> firstRow = data.get(0).getRow();
                Table table = new Table(firstRow.size());

                // Add headers
                for (String key : firstRow.keySet()) {
                    table.addHeaderCell(new Cell().add(new Paragraph(key)));
                }

                // Add data rows
                for (ReportData rowData : data) {
                    for (Object value : rowData.getRow().values()) {
                        table.addCell(new Cell().add(new Paragraph(value == null ? "" : value.toString())));
                    }
                }
                document.add(table);
            }

            document.close();
            System.out.println("PDF report generated: " + filename);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}