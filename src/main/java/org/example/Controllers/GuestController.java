package org.example.Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.DTO.GuestDTO;
import org.example.Models.Guest;
import org.example.Services.GuestService;
import org.example.Util.SessionManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet("/api/guests")
public class GuestController extends HttpServlet {

    private GuestService guestService;

    @Override
    public void init() {
        guestService = new GuestService();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String token = request.getParameter("token");

        /*if (!SessionManager.isValidToken(token)) {
            out.print("{\"error\":\"Invalid or missing token\"}");
            return;
        }*/

        String nic = request.getParameter("nic");

        if (nic != null && !nic.isEmpty()) {
            Guest g = guestService.findGuestByNIC(nic);
            if (g != null) {
                out.print("{"
                        + "\"found\":true,"
                        + "\"id\":" + g.getGuestID() + ","
                        + "\"name\":\"" + g.getFullName() + "\","
                        + "\"contact\":\"" + g.getContactNumber() + "\","
                        + "\"email\":\"" + g.getEmail() + "\""
                        + "}");
            } else {
                out.print("{\"found\":false}");
            }
        } else {
            List<Guest> allGuests = guestService.getAllGuests();
            out.print("[");
            for (int i = 0; i < allGuests.size(); i++) {
                Guest g = allGuests.get(i);
                out.print("{"
                        + "\"found\":true,"
                        + "\"id\":" + g.getGuestID() + ","
                        + "\"name\":\"" + g.getFullName() + "\","
                        + "\"contact\":\"" + g.getContactNumber() + "\","
                        + "\"email\":\"" + g.getEmail() + "\","
                        + "\"nic\":\"" + g.getNIC() + "\""

                        + "}"
                        + (i < allGuests.size() - 1 ? "," : ""));
            }
            out.print("]");
        }

        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String token = request.getParameter("token");
       /* if (!SessionManager.isValidToken(token)) {
            out.print("{\"error\":\"Invalid or missing token\"}");
            return;
        }*/
        System.out.println("Came to the Guest COntroller");
        String fullName = request.getParameter("fullName");
        String nic = request.getParameter("nic");
        String contactNumber = request.getParameter("contactNumber");
        String email = request.getParameter("email");

        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setFullName(fullName);
        guestDTO.setNic(nic);
        guestDTO.setContactNumber(contactNumber);
        guestDTO.setEmail(email);

        try {
            guestService.addGuest(token, guestDTO);
            out.print("{\"success\":true}");
        } catch (Exception e) {
            out.print("{\"error\":true,\"message\":\"" + e.getMessage() + "\"}");
        }

        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String token = request.getParameter("token");
        if (!SessionManager.isValidToken(token)) {
            out.print("{\"error\":\"Invalid or missing token\"}");
            return;
        }

        int guestId = Integer.parseInt(request.getParameter("guestId"));
        String fullName = request.getParameter("fullName");
        String contactNumber = request.getParameter("contactNumber");
        String email = request.getParameter("email");

        GuestDTO guestDTO = new GuestDTO();
        guestDTO.setFullName(fullName);
        guestDTO.setContactNumber(contactNumber);
        guestDTO.setEmail(email);

        try {
          //  guestService.updateGuest(token, guestId, guestDTO);
            out.print("{\"success\":true}");
        } catch (Exception e) {
            out.print("{\"error\":true,\"message\":\"" + e.getMessage() + "\"}");
        }

        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String token = request.getParameter("token");
        if (!SessionManager.isValidToken(token)) {
            out.print("{\"error\":\"Invalid or missing token\"}");
            return;
        }

        int guestId = Integer.parseInt(request.getParameter("guestId"));

        try {
           // guestService.deleteGuest(token, guestId);
            out.print("{\"success\":true}");
        } catch (Exception e) {
            out.print("{\"error\":true,\"message\":\"" + e.getMessage() + "\"}");
        }

        out.flush();
    }
}