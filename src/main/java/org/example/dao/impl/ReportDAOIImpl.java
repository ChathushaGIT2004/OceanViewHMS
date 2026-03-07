package org.example.dao.impl;

import org.example.DTO.ReportType;
import org.example.Models.Reports.ReportData;
import org.example.Models.User.UserActivityLog;
import org.example.dao.*;

import java.util.List;
import java.util.Map;

public class ReportDAOImpl {

    private final UserDAO userDAO = new UserDAOImpl();
    private final UserActivityLogDAO userActivityDAO = new UserActivityLogDAOImpl();
    private final ReservationDAO reservationDAO = new ReservationDAOImpl();
    private final BillingDAO billingDAO = new BillingDAOImpl();
    private final GuestDAO guestDAO = new GuestDAOImpl();

    public List<ReportData> fetchReport(ReportType type, Map<String, Object> filters) {
        switch (type) {
            case USERS:
                return userDAO.fetchReportData(filters);
            case USER_ACTIVITY:
                return userActivityDAO.fetchReportData(filters);
            case RESERVATIONS:
                return reservationDAO.fetchReportData(filters);
            case BILLINGS:
                return billingDAO.fetchReportData(filters);
            case GUESTS:
                return guestDAO.fetchReportData(filters);
            default:
                throw new IllegalArgumentException("Unsupported report type: " + type);
        }
    }
}