package org.example.Controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import org.example.DTO.AddReservationRequestDTO;
import org.example.DTO.ReservationDTO;
import org.example.DTO.ResponseMessageDTO;
import org.example.DTO.UpdateStatusDTO;
import org.example.Models.Billings.BillableItems.Reservation;
import org.example.Services.ReservationService;
import org.example.Services.GuestService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/reservations/*")
public class ReservationController extends HttpServlet {

    private ReservationService reservationService;
    private GuestService guestService;
    private Gson gson;



    @Override
    public void init() {
        reservationService = new ReservationService();
        guestService = new GuestService();
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm") // match frontend ISO format
                .create();
    }

    // =====================================================
    // 🔹 GET — FETCH ALL RESERVATIONS
    // =====================================================
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String pathInfo = request.getPathInfo(); // gives /3

        if (pathInfo != null && pathInfo.length() > 1) {

            try {
                int reservationId = Integer.parseInt(pathInfo.substring(1));

                Reservation reservation = reservationService.getReservationById(reservationId);

                if (reservation == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("Reservation not found");
                    return;
                }

                response.setContentType("application/json");
                new Gson().toJson(reservation, response.getWriter());

            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Invalid reservation ID");
            }

        } else {
            // if no ID → return all reservations
            List<ReservationDTO> reservations = reservationService.getAllReservations();
            response.setContentType("application/json");
            new Gson().toJson(reservations, response.getWriter());
        }
    }
    // =====================================================
    // 🔥 POST — CREATE RESERVATION
    // =====================================================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            AddReservationRequestDTO dto = gson.fromJson(request.getReader(), AddReservationRequestDTO.class);
            ResponseMessageDTO result = reservationService.addReservation(dto);
            out.print(gson.toJson(result));

        } catch (Exception e) {
            out.print(gson.toJson(new ResponseMessageDTO(false, e.getMessage())));
        }
    }

    // =====================================================
    // 🔥 PUT — UPDATE RESERVATION OR STATUS
    // =====================================================
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            String path = request.getPathInfo(); // e.g., /update/status

            if ("/update/status".equalsIgnoreCase(path)) {
                // JSON body for UpdateStatusDTO
                UpdateStatusDTO dto = gson.fromJson(request.getReader(), UpdateStatusDTO.class);
                if (dto == null) {
                    out.print(gson.toJson(new ResponseMessageDTO(false, "Invalid JSON payload")));
                    return;
                }

                // Call service to update status
                ResponseMessageDTO result = reservationService.updateReservationStatus(dto);
                out.print(gson.toJson(result));

            } else {
                // Full reservation update via query params
                int reservationID = Integer.parseInt(request.getParameter("reservationID"));
                String status = request.getParameter("status");

                Reservation reservation = new Reservation();
                reservation.setReservationID(reservationID);
                reservation.setStatus(status);

                ResponseMessageDTO result = reservationService.updateReservationDetails("", reservation);

                if (result.isSuccess())
                    out.print("{\"success\":true,\"message\":\"Reservation updated\"}");
                else
                    out.print("{\"success\":false,\"message\":\"" + result.getMessage() + "\"}");
            }

        } catch (Exception e) {
            out.print(gson.toJson(new ResponseMessageDTO(false, e.getMessage())));
        }
    }

    // =====================================================
    // 🔥 DELETE RESERVATION
    // =====================================================
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            int reservationID = Integer.parseInt(request.getParameter("reservationID"));
            boolean success = reservationService.deleteReservation("", reservationID);

            if (success)
                out.print("{\"success\":true,\"message\":\"Reservation deleted\"}");
            else
                out.print("{\"success\":false,\"message\":\"Delete failed\"}");

        } catch (Exception e) {
            out.print(gson.toJson(new ResponseMessageDTO(false, e.getMessage())));
        }
    }
}