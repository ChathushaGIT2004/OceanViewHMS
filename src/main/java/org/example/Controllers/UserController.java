package org.example.Controllers;


import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

        import org.example.DTO.UserDTO;
import org.example.Services.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/users/*")
public class UserController extends HttpServlet {

    private final UserService userService = new UserService();
    private final Gson gson = new Gson();

    // =============================
    // GET (All or By ID or BY TOKEN)
    // =============================


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");

        String pathInfo = req.getPathInfo();

        // If path is /me, fetch user info by token
        if ("/me".equals(pathInfo)) {
            String authHeader = req.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().write("{\"error\":\"Token required\"}");
                return;
            }

            String token = authHeader.substring(7);

            try {
                UserDTO user = userService.getUserByToken(token);

                if (user == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("{\"error\":\"User not found\"}");
                    return;
                }

                resp.getWriter().write(new Gson().toJson(user));
            } catch (RuntimeException e) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
            }
            return;
        }

        // Existing GET logic (all users / by ID)
        if (pathInfo == null || pathInfo.equals("/")) {
            List<UserDTO> users = userService.getAllUsers();
            resp.getWriter().write(gson.toJson(users));
        } else {
            int id = Integer.parseInt(pathInfo.substring(1));
            UserDTO user = userService.getUserById(id);

            if (user == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("User not found");
                return;
            }

            resp.getWriter().write(gson.toJson(user));
        }
    }
    // =============================
    // CREATE USER
    // =============================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");

        UserDTO dto = gson.fromJson(req.getReader(), UserDTO.class);
        String password = req.getParameter("password");

        if (password == null || password.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Password required");
            return;
        }

        userService.createUser(dto, password);

        resp.getWriter().write("{\"message\":\"User Created\"}");
    }

    // =============================
    // UPDATE USER
    // =============================
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");

        UserDTO dto = gson.fromJson(req.getReader(), UserDTO.class);

        try {
            userService.updateUser(dto);
            resp.getWriter().write("{\"message\":\"User Updated\"}");
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write(e.getMessage());
        }
    }

    // =============================
    // DELETE USER
    // =============================
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");

        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("User ID required");
            return;
        }

        int id = Integer.parseInt(pathInfo.substring(1));

        userService.deleteUser(id);

        resp.getWriter().write("{\"message\":\"User Deleted\"}");
    }

    // =============================
    // UPDATE PASSWORD (PATCH)
    // =============================

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");

        if (!"/password".equals(req.getPathInfo())) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String authHeader = req.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("Token required");
            return;
        }

        String token = authHeader.substring(7);

        // Expecting JSON: { "newPassword": "123456" }
        var jsonObject = gson.fromJson(req.getReader(), com.google.gson.JsonObject.class);

        String newPassword = jsonObject.get("newPassword").getAsString();

        try {
            userService.updatePassword(token, newPassword);
            resp.getWriter().write("{\"message\":\"Password Updated\"}");
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write(e.getMessage());
        }
    }
}