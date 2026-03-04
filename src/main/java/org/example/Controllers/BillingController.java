package org.example.Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import com.google.gson.Gson;
import org.example.DTO.*;
import org.example.Models.Billings.*;
import org.example.Services.BillingService;

import java.io.*;

@WebServlet("/api/billing/*")
public class BillingController extends HttpServlet {

    private final Gson gson = new Gson();
    private final BillingService billingService = new BillingService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String path = request.getPathInfo();

        try {
            if ("/generate".equals(path)) {
                // Generate a new billing
                GenerateBillingRequest req = gson.fromJson(request.getReader(), GenerateBillingRequest.class);
                var result = billingService.generateBilling(req);
                out.print(gson.toJson(result));

            } else if ("/pay".equals(path)) {

                PayBillRequestDTO req = gson.fromJson(request.getReader(), PayBillRequestDTO.class);


                var result = billingService.payBill(req.getToken(), req.getBillID(), req.getAmount(), req.getPaymentMethod());

                out.print(gson.toJson(result));
            } else if ("/update".equals(path)) {
                // Update a billing
                Billing billing = gson.fromJson(request.getReader(), Billing.class);
                var result = billingService.updateBilling(billing);
                out.print(gson.toJson(result));

            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"message\":\"Invalid endpoint\"}");
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"message\":\"Invalid number format: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"message\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println(" Billing COntroller Accessed ");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String path = request.getPathInfo();

        try {
            if (path == null || path.equals("/") || path.equals("/all")) {
                // Return all billings
                System.out.println("Printing all method accessed");
                out.print(gson.toJson(billingService.getAllBillings()));
            } else {
                // Return billing by ID
                try {
                    int billID = Integer.parseInt(path.replace("/", "").trim());
                    System.out.println("Printing get methos accessed using BillID");
                    BillingDTO bill = billingService.getBillById(billID);
                    if (bill != null) out.print(gson.toJson(bill));
                    else {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        out.print("{\"message\":\"Billing not found\"}");
                    }
                } catch (NumberFormatException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"message\":\"Invalid billing ID\"}");
                }
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"message\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response); // allow PUT for update
    }
}