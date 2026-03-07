package org.example.Services;

import org.example.DTO.*;
import org.example.Models.Billings.*;
import org.example.Models.Guest;
import org.example.Services.BillableItemHandler.BillableItemRegistry;
import org.example.Util.EmailUtil;
import org.example.Util.SessionManager;
import org.example.dao.BillingDAO;
import org.example.dao.impl.BillingDAOImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BillingService {

    private final BillingDAO billingDAO;
    private final BillingItemService billingItemService;
    private final BillableItemRegistry registry;
    private final GuestService guestService;
    private final EmailUtil emailUtil=new EmailUtil();
    private final  ReservationService reservationService=new ReservationService();

    public BillingService() {
        this.billingDAO = new BillingDAOImpl();
        this.billingItemService = new BillingItemService();
        this.registry = new BillableItemRegistry();
        this.guestService = new GuestService();
    }

    // ================= GENERATE BILL =================
    public ResponseMessageDTO generateBilling(GenerateBillingRequest req) {
        try {
            String token =req.getToken();

            if (!SessionManager.isValidToken(token)) {
                return ResponseMessageDTO.invalidToken();
            }

            BillableItemDTO dto = req.getBillableItem();
            System.out.println("Bill generate Request Accepted \nBill ID"+dto.getId()+"\nItemType="+dto.getItemType());
            if (dto == null) return failure("Billable item is missing");
            System.out.println(dto.getItemType()+"\n"+dto.getPrice());

            // Fetch real BillableItem via registry
            BillableItem item = registry.fetchItem(dto.getItemType(), dto.getId());
            if (item == null) return failure("Unsupported item type: " + dto.getItemType());

            // Fetch guest
            Guest guest = guestService.findGuestByID(req.getGuestId());
            if (guest == null) return failure("Guest not found");

            // CREATE BILL
            Billing billing = new Billing();
            billing.setGuestID(guest.getGuestID());
            billing.setItems(new ArrayList<>());

            BillingItem billingItem = new BillingItem();
            billingItem.setItem(item);
            billingItem.setItemID(item.getId());
            billingItem.setItemType(item.getItemType());
            billingItem.setQuantity(1);
            billingItem.setPrice(item.getPrice());

            billing.getItems().add(billingItem);

            billing.setTaxes(req.getTaxes());
            billing.setDiscount(req.getDiscount());
            billing.setNetPrice(item.getPrice());
            billing.setGrossPrice(item.getPrice() + req.getTaxes() - req.getDiscount());

            billing.setAmountPaid(0);
            billing.setBalanceDue(billing.getGrossPrice());
            billing.setPaymentStatus("PENDING");

            // Save BILL using DAO
            ResponseMessageDTO billingResponse = billingDAO.save(billing);



            int billID = (int) billingResponse.getData();
            billing.setBillID(billID);

            // Save BILL ITEM
            billingItemService.addItemToBill(billID, billingItem);
            registry.markItemAsBilled(item);



            UserActivityLogService.getInstance().log(
                    token,
                    "CREATE",
                    "BILLING",
                    billID,
                    "Generated new bill with tax applied"
            );

            ResponseMessageDTO response = new ResponseMessageDTO();
            response.setSuccess(true);
            response.setMessage("Billing created successfully");
            response.setData(billID);
            return response;

        } catch (Exception e) {
            return failure("Error creating billing: " + e.getMessage());
        }
    }

    // ================= PAY BILL =================
    public ResponseMessageDTO payBill(String token, int billID, double amount, String method) {

        if (!SessionManager.isValidToken(token)) {
            return ResponseMessageDTO.invalidToken();
        }

        try {

            ResponseMessageDTO paymentResponse = billingDAO.pay(billID, amount, method);
            if (!paymentResponse.isSuccess()) return paymentResponse;

            Billing billing = billingDAO.findById(billID);
            billing.setItems(billingItemService.getItemsByBill(billID));
            Guest guest= guestService.findGuestByID(billing.getGuestID());

            String subject = "Payment Confirmation - Ocean View Hotel";
            String body = String.format(
                    "Dear %s,\n\n" +
                            "We are pleased to confirm that your payment has been successfully received for your booking at Ocean View Hotel.\n\n" +
                            "Booking Details:\n" +

                            "Booking ID: "+billing.getBillID() +"\n" +

                            "Amount Paid: "+billing.getAmountPaid()+"\n\n" +
                            "Thank you for choosing Ocean View Hotel. We look forward to welcoming you and ensuring a memorable stay.\n\n" +
                            "Best regards,\n" +
                            "Ocean View Hotel Team" );

            emailUtil.sendPlainTextEmail(guest.getEmail(), subject, body);


            UserActivityLogService.getInstance().log(
                    token,
                    "CREATE",
                    "BILLING",
                    billID,
                    "Generated new bill with tax applied"
            );

            ResponseMessageDTO response = new ResponseMessageDTO();
            response.setSuccess(true);
            response.setMessage("Payment successful");
            response.setData(toDTO(billing));
            return response;

        } catch (Exception e) {
            return failure("Payment error: " + e.getMessage());
        }
    }

    // ================= GET BILL(S) =================
    public BillingDTO getBillById(int billID) {
        Billing billing = billingDAO.findById(billID);
        if (billing != null) {
            billing.setItems(billingItemService.getItemsByBill(billID));
            return toDTO(billing);
        }
        return null;
    }

    public List<BillingDTO> getAllBillings() {
        List<Billing> list = billingDAO.findAll();
        return list.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // ================= UPDATE BILL =================
    public ResponseMessageDTO updateBilling(Billing dto) {
        try {
            Billing billing = billingDAO.findById(dto.getBillID());
            if (billing == null) return failure("Billing not found");

            billing.setTaxes(dto.getTaxes());
            billing.setDiscount(dto.getDiscount());
            billing.setNetPrice(dto.getNetPrice());
            billing.setGrossPrice(dto.getGrossPrice());
            billing.setAmountPaid(dto.getAmountPaid());
            billing.setBalanceDue(dto.getBalanceDue());
            billing.setPaymentMethod(dto.getPaymentMethod());
            billing.setPaymentStatus(dto.getPaymentStatus());

            billingDAO.update(billing);



            ResponseMessageDTO response = new ResponseMessageDTO();
            response.setSuccess(true);
            response.setMessage("Billing updated successfully");
            response.setData(toDTO(billing));
            return response;

        } catch (Exception e) {
            return failure("Error updating billing: " + e.getMessage());
        }
    }

    // ================= SEARCH BILLINGS =================
    public List<BillingDTO> searchByGuest(int guestID) {
        List<Billing> list = billingDAO.findByGuestId(guestID);
        return list.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<BillingDTO> searchByGuestAndItemType(int guestID, String itemType) {
        List<Billing> list = billingDAO.findByGuestId(guestID);
        List<Billing> filtered = list.stream()
                .filter(b -> b.getItems().stream().anyMatch(i -> i.getItemType().equalsIgnoreCase(itemType)))
                .collect(Collectors.toList());
        return filtered.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // ================= HELPERS =================
    private BillingDTO toDTO(Billing billing) {
        BillingDTO dto = new BillingDTO();

        dto.setBillID(billing.getBillID());

        // Guest info
        Guest guest = guestService.findGuestByID(billing.getGuestID());
        if (guest != null) {
            GuestInfoDTO guestInfo = new GuestInfoDTO();
            guestInfo.setGuestID(guest.getGuestID());
            guestInfo.setNic(guest.getNIC());
            guestInfo.setFullName(guest.getFullName());
            dto.setGuest(guestInfo);
        }

        // Billing amounts & status
        dto.setNetPrice(billing.getNetPrice());
        dto.setGrossPrice(billing.getGrossPrice());
        dto.setTaxes(billing.getTaxes());
        dto.setDiscount(billing.getDiscount());
        dto.setAmountPaid(billing.getAmountPaid());
        dto.setBalanceDue(billing.getBalanceDue());
        dto.setPaymentMethod(billing.getPaymentMethod());
        dto.setPaymentStatus(billing.getPaymentStatus());

        // Billing items
        dto.setItems(billing.getItems());

        // Date/Time fields (assume Billing object now has them)
        dto.setCreatedAt(billing.getCreatedAt());       // When bill was created
        dto.setUpdatedAt(billing.getUpdatedAt());       // Last updated
        dto.setPaymentDate(billing.getPaymentDate());   // Last payment date
        System.out.println(dto.getPaymentDate());

        return dto;
    }
    private ResponseMessageDTO failure(String msg) {
        ResponseMessageDTO dto = new ResponseMessageDTO();
        dto.setSuccess(false);
        dto.setMessage(msg);
        return dto;
    }
}