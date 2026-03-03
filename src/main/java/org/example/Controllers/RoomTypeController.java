package org.example.Controllers;

import org.example.Models.Room.RoomType;
import org.example.Services.RoomTypeService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

        import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/roomtypes")
public class RoomTypeController extends HttpServlet {

    private RoomTypeService roomTypeService;

    @Override
    public void init() throws ServletException {
        // Replace with your actual DAO implementation
        roomTypeService = new RoomTypeService(new org.example.dao.impl.RoomTypeDAOImpl());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String path = request.getPathInfo();
        if (path == null || path.equals("/")) {
            // Return all room types
            List<RoomType> roomTypes = roomTypeService.getAllRoomTypes();

            out.print("[");
            for (int i = 0; i < roomTypes.size(); i++) {
                RoomType rt = roomTypes.get(i);

                out.print("{");
                out.print("\"roomTypeID\":" + rt.getRoomTypeID() + ",");
                out.print("\"typeName\":\"" + rt.getTypeName() + "\",");
                out.print("\"chargePerNight\":" + rt.getChargePerNight() + ",");
                out.print("\"occupancyLimit\":" + rt.getOccupancyLimit());
                out.print("}");

                if (i < roomTypes.size() - 1) out.print(",");
            }
            out.print("]");
        } else {
            try {
                int roomTypeID = Integer.parseInt(path.substring(1));
                RoomType roomType = roomTypeService.getRoomTypeById(roomTypeID);
                if (roomType != null) {
                    out.println(roomType);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.println("{\"error\":\"RoomType not found\"}");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"error\":\"Invalid RoomType ID\"}");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RoomType roomType = new RoomType();
        roomType.setRoomTypeID(Integer.parseInt(request.getParameter("roomTypeID")));
        roomType.setTypeName(request.getParameter("typeName"));
        roomType.setChargePerNight(Double.parseDouble(request.getParameter("chargePerNight")));
        roomType.setOccupancyLimit(Integer.parseInt(request.getParameter("occupancyLimit")));

        roomTypeService.addRoomType(roomType);
        response.getWriter().println("{\"message\":\"RoomType added successfully\"}");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int roomTypeID = Integer.parseInt(request.getParameter("roomTypeID"));
        RoomType roomType = roomTypeService.getRoomTypeById(roomTypeID);

        if (roomType != null) {
            roomType.setTypeName(request.getParameter("typeName"));
            roomType.setChargePerNight(Double.parseDouble(request.getParameter("chargePerNight")));
            roomType.setOccupancyLimit(Integer.parseInt(request.getParameter("occupancyLimit")));
            roomTypeService.updateRoomType(roomType);
            response.getWriter().println("{\"message\":\"RoomType updated successfully\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().println("{\"error\":\"RoomType not found\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int roomTypeID = Integer.parseInt(request.getParameter("roomTypeID"));
        roomTypeService.deleteRoomType(roomTypeID);
        response.getWriter().println("{\"message\":\"RoomType deleted successfully\"}");
    }
}