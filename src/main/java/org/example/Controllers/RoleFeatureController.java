package org.example.Controllers;

import org.example.Security.Feature;
import org.example.Security.RoleFeatureManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
        import jakarta.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import com.google.gson.Gson;
import org.example.Util.SessionManager;

@WebServlet("/api/role/features")
public class RoleFeatureController extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // 🔐 Get token directly (no Bearer)
        String token = request.getHeader("Authorization");

        if (token == null || token.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.write("{\"success\": false, \"message\": \"Missing token\"}");
            return;
        }


        if (!SessionManager.isValidToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.write("{\"success\": false, \"message\": \"Invalid or expired token\"}");
            return;
        }

        // 🎯 Get role from session
        String role = SessionManager.getRole(token);

        if (role == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.write("{\"success\": false, \"message\": \"Role not found\"}");
            return;
        }

        Set<Feature> features = RoleFeatureManager.getFeaturesByRole(role);

        out.write(gson.toJson(features));
    }
}