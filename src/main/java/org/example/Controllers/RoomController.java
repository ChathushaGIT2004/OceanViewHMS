package org.example.Controllers;

import org.example.Services.RoomService;


import org.example.Models.Room.Room;
import org.example.Services.RoomService;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
        import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/api/rooms")
public class RoomController extends HttpServlet {

    private RoomService roomService;

    @Override
    public void init() throws ServletException {
        System.out.println("Came to Room COntroller");
        try {
            roomService = new RoomService();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("RoomService init failed", e);
        }
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String path = request.getPathInfo();

        // 🔹 Query params
        String status = request.getParameter("status");
        String typeParam = request.getParameter("type");
        String availableParam = request.getParameter("available");

        try {

            // ===============================
            // 1️⃣ GET ROOM BY ID
            // ===============================
            if (path != null && !path.equals("/")) {
                int roomID = Integer.parseInt(path.substring(1));
                Room room = roomService.getRoomById(roomID);

                if (room != null) {
                    out.print("{");
                    out.print("\"roomID\":" + room.getRoomID() + ",");
                    out.print("\"roomType\":" + room.getRoomType() + ",");
                    out.print("\"roomStatus\":\"" + room.getRoomStatus() + "\"");
                    out.print("}");
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\":\"Room not found\"}");
                }
                return;
            }

            // ===============================
            // 2️⃣ FILTER: AVAILABLE ROOMS
            // ===============================
            List<Room> rooms;

            if ("true".equalsIgnoreCase(availableParam)) {
                rooms = roomService.getAvailableRooms();
            }

            // ===============================
            // 3️⃣ FILTER: BY STATUS
            // ===============================
            else if (status != null) {
                rooms = roomService.getRoomsByStatus(status);
            }

            // ===============================
            // 4️⃣ FILTER: BY TYPE
            // ===============================
            else if (typeParam != null) {
                int typeID = Integer.parseInt(typeParam);
                rooms = roomService.getRoomsByType(typeID);
            }

            // ===============================
            // 5️⃣ GET ALL ROOMS
            // ===============================
            else {
                rooms = roomService.getAllRooms();
            }

            // 🔹 Convert list to JSON
            out.print("[");
            for (int i = 0; i < rooms.size(); i++) {
                Room r = rooms.get(i);

                out.print("{");
                out.print("\"roomID\":" + r.getRoomID() + ",");
                out.print("\"roomType\":" + r.getRoomType() + ",");
                out.print("\"roomStatus\":\"" + r.getRoomStatus() + "\"");
                out.print("}");

                if (i < rooms.size() - 1) out.print(",");
            }
            out.print("]");

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Example: adding a new room
        int roomID = Integer.parseInt(request.getParameter("roomID"));
        int roomType = Integer.parseInt(request.getParameter("roomType"));
        String roomStatus = request.getParameter("roomStatus");

        Room room = new Room();
        room.setRoomID(roomID);
        room.setRoomType(roomType);
        room.setRoomStatus(roomStatus);

        roomService.addRoom(room);
        response.getWriter().println("{\"message\":\"Room added successfully\"}");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Update room status or type
        int roomID = Integer.parseInt(request.getParameter("roomID"));
        String roomStatus = request.getParameter("roomStatus");
        int roomType = Integer.parseInt(request.getParameter("roomType"));

        Room room = roomService.getRoomById(roomID);
        if (room != null) {
            room.setRoomStatus(roomStatus);
            room.setRoomType(roomType);
            roomService.updateRoom(room);
            response.getWriter().println("{\"message\":\"Room updated successfully\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().println("{\"error\":\"Room not found\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int roomID = Integer.parseInt(request.getParameter("roomID"));
        roomService.deleteRoom(roomID);
        response.getWriter().println("{\"message\":\"Room deleted successfully\"}");
    }
}
