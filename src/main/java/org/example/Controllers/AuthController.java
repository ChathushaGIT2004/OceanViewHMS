package org.example.Controllers;


import org.example.Services.AuthService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
        import jakarta.servlet.ServletException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/api/login")
public class AuthController extends HttpServlet {

    private AuthService authService;

    @Override
    public void init() {
        authService = new AuthService();
    }

    // 🔥 HANDLE LOGIN
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {



        String username = request.getParameter("username");
        System.out.println(username);
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        Map<String, Object> result =
                authService.login(username, password, role);

        boolean success = (boolean) result.get("success");
        System.out.println("AC:"+result);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (success) {

            String token = (String) result.get("token");

            // ✅ Save token in session
            HttpSession session = request.getSession();
            session.setAttribute("token", token);

            response.setStatus(HttpServletResponse.SC_OK);

            out.write("{\"success\": true, \"message\": \"Login success\"}");

        } else {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            out.write("{\"success\": false, \"message\": \"Login failed\"}");
        }
    }
}