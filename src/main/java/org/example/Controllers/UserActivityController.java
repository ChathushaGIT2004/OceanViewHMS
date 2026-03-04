package org.example.Controllers;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

        import org.example.Models.User.UserActivityLog;
import org.example.dao.UserActivityLogDAO;
import org.example.dao.impl.UserActivityLogDAOImpl;
import org.example.Security.Feature;
import org.example.Security.RoleFeatureManager;

import org.example.Util.SessionManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/api/admin/activity")
public class UserActivityController extends HttpServlet {

    private final UserActivityLogDAO logDAO = new UserActivityLogDAOImpl();
    private final Gson gson = new GsonBuilder()

            .setPrettyPrinting()
            .create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");

        String token = request.getHeader("Authorization");

        // 🔐 Validate session
        if (!SessionManager.isValidToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"Invalid session\"}");
            return;
        }

        String role = SessionManager.getRole(token);


        // 🔐 Check if role has VIEW_USERS feature
        if (!RoleFeatureManager.hasFeature(role, Feature.VIEW_USERS)) {
            System.out.println("USer ROle"+role+"\ntoken :"+token);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);

            response.getWriter().write("{\"error\":\"Access denied. Feature not allowed for your role\"}");
            return;
        }

        // 📌 Get userId parameter
        String userIdParam = request.getParameter("userId");

        if (userIdParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"userId parameter required\"}");
            return;
        }

        try {
            int userId = Integer.parseInt(userIdParam);

            List<UserActivityLog> logs = logDAO.findByUserId(userId);

            if (logs == null || logs.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("[]"); // empty JSON array
                return;
            }

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(logs));

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid userId format\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"Server error\"}");
            e.printStackTrace();
        }
    }
}