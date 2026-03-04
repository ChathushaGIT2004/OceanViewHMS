package org.example.Controllers;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import org.example.DTO.HelpItemDTO;
import org.example.Services.Impl.HelpService;
import org.example.Services.IHelpService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/help")
public class HelpController extends HttpServlet {

    private final IHelpService helpService = new HelpService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String category = request.getParameter("category");
        List<HelpItemDTO> result;

        if (category != null && !category.isEmpty()) {
            result = helpService.getHelpByCategory(category);
        } else {
            result = helpService.getAllHelpItems();
        }

        out.print(new Gson().toJson(result));
        out.flush();
    }
}
