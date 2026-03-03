package org.example.Controllers;

import org.example.Services.AuthService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
        import jakarta.servlet.ServletException;

import java.io.IOException;

@WebServlet("/api/logout")
public class LogoutController extends HttpServlet {

    private AuthService authService;

    @Override
    public void init() {
        authService = new AuthService();
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session != null) {
            String token = (String) session.getAttribute("token");

            if (token != null) {
                authService.logout(token);
            }

            session.invalidate();
        }

        response.sendRedirect(request.getContextPath() + "/login.html");
    }
}