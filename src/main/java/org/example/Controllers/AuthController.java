package org.example.Controllers;

import org.example.Services.AuthService;
import org.example.Util.SessionManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/api/auth/*")
public class AuthController extends HttpServlet {

    private AuthService authService;

    @Override
    public void init() {
        authService = new AuthService();
    }

    // 🔥 HANDLE LOGIN
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        Map<String, Object> result = authService.login(username, password, role);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        boolean success = (boolean) result.get("success");

        if (success) {
            String token = (String) result.get("token");
            out.write("{\"success\": true, \"message\": \"Login successful\", \"token\": \"" + token + "\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.write("{\"success\": false, \"message\": \"" + result.get("message") + "\"}");
        }
    }

    // 🔥 HANDLE LOGOUT
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization"); // expect token in header
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (token == null || token.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"success\": false, \"message\": \"Missing token\"}");
            return;
        }

        authService.logout(token);
        out.write("{\"success\": true, \"message\": \"Logout successful\"}");
    }
}