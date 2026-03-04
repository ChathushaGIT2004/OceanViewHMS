package org.example.Controllers;

import com.google.gson.Gson;
import org.example.Models.Room.RoomAllocation;
import org.example.Services.RoomAllocationService;
import org.example.DTO.ResponseMessageDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

        import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

// Base URL: /api/room-allocation/*
@WebServlet("/api/room-allocation/*")
public class RoomAllocationController extends HttpServlet {

    private final RoomAllocationService allocationService = new RoomAllocationService();
    private final Gson gson = new Gson();

    // GET allocations
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String path = request.getPathInfo(); // e.g., /123 or /all
        if (path == null || path.equals("/all")) {
            List<RoomAllocation> allocations = allocationService.getAllAllocations();
            out.print(gson.toJson(allocations));
            return;
        }

        try {
            int allocationId = Integer.parseInt(path.substring(1));
            RoomAllocation allocation = allocationService.getAllocationById(allocationId);
            if (allocation != null) {
                out.print(gson.toJson(allocation));
            } else {
                out.print(gson.toJson(new ResponseMessageDTO(false, "Allocation not found")));
            }
        } catch (NumberFormatException e) {
            out.print(gson.toJson(new ResponseMessageDTO(false, "Invalid allocation ID")));
        }
    }

    // POST: add room to reservation
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        RoomAllocationRequest req = gson.fromJson(request.getReader(), RoomAllocationRequest.class);

        try {
            ResponseMessageDTO allocation = allocationService.addRoomToReservation(req.getReservationId(), req.getRoomId());
            out.print(gson.toJson(allocation));
        } catch (IllegalArgumentException e) {
            out.print(gson.toJson(new ResponseMessageDTO(false, e.getMessage())));
        }
    }

    // DELETE: remove room allocation
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String path = request.getPathInfo(); // e.g., /123
        try {
            int allocationId = Integer.parseInt(path.substring(1));
            System.out.println("Allocation ID On COntroller"+ allocationId);
            boolean success = allocationService.removeRoomFromReservation(allocationId,true);
            if (success) {
                out.print(gson.toJson(new ResponseMessageDTO(true, "Room allocation removed successfully")));
            } else {
                out.print(gson.toJson(new ResponseMessageDTO(false, "Allocation not found")));
            }
        } catch (NumberFormatException e) {
            out.print(gson.toJson(new ResponseMessageDTO(false, "Invalid allocation ID")));
        }
    }

    // PUT: update allocation status (optional)
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        UpdateAllocationStatusRequest req = gson.fromJson(request.getReader(), UpdateAllocationStatusRequest.class);
        RoomAllocation allocation = allocationService.getAllocationById(req.getAllocationId());

        if (allocation == null) {
            out.print(gson.toJson(new ResponseMessageDTO(false, "Allocation not found")));
            return;
        }

        allocation.setAllocationStatus(req.getStatus());
        allocationService.updateAllocation(allocation);
        out.print(gson.toJson(new ResponseMessageDTO(true, "Allocation status updated")));
    }

    // DTOs for JSON requests
    private static class RoomAllocationRequest {
        private int reservationId;
        private int roomId;

        public int getReservationId() { return reservationId; }
        public int getRoomId() { return roomId; }
    }

    private static class UpdateAllocationStatusRequest {
        private int allocationId;
        private String status;

        public int getAllocationId() { return allocationId; }
        public String getStatus() { return status; }
    }
}