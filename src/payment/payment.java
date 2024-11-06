package payment;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import rental.config;

public class payment {
    Scanner sc = new Scanner(System.in);
     config conf = new config();

    public void paymentTransaction() {
        int action;
        do {
            System.out.println("1. Add Payment");
            System.out.println("2. View Payments");
            System.out.println("3. Update Payment");
            System.out.println("4. Delete Payment");
            System.out.println("5. Select Payment");
            System.out.println("6. Exit");

            System.out.print("Enter action: ");
            action = sc.nextInt();
            sc.nextLine();

            switch(action) {
                case 1:
                    addPayment();
                    viewPayments();
                    break;

                case 2:
                    viewPayments();
                    break;

                case 3:
                    viewPayments();
                    updatePayment();
                    viewPayments();
                    break;

                case 4:
                    viewPayments();
                    deletePayment();
                    viewPayments();
                    break;

                case 5:
                    selectPayment();
                    break;

                case 6:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.print("Invalid action. Please try again.");
            }
        } while(action != 6);
    }

    public void addPayment() {
        String paymentId = promptValidPaymentId();
        String tenantId = promptValidTenantId();
        String paymentDate = promptValidDate();
        String paymentAmount = promptValidAmount();
        String paymentStatus = promptValidStatus();

        String sql = "INSERT INTO tbl_payment (payment_id, tenant_id, payment_date, payment_amount, payment_status) VALUES (?, ?, ?, ?, ?)";
        conf.addRecord(sql, paymentId, tenantId, paymentDate, paymentAmount, paymentStatus);
        System.out.println("Payment added successfully.\n");
    }

    public void viewPayments() {
        String qry = "SELECT * FROM tbl_payment";
        String[] hrds = {"Payment ID", "Tenant ID", "Payment Date", "Amount", "Status"};
        String[] clmns = {"payment_id", "tenant_id", "payment_date", "payment_amount", "payment_status"};

        conf.viewRecords(qry, hrds, clmns);
    }

    public void updatePayment() {
        System.out.print("Enter Payment ID to Update: ");
        String id = sc.nextLine();

        while (conf.getSingleValue("SELECT payment_id FROM tbl_payment WHERE payment_id = ?", id) == 0) {
            System.out.println("Selected Payment ID doesn't exist!");
            System.out.print("Select payment ID again: ");
            id = sc.nextLine();
        }

        String tenantId = promptValidTenantId();
        String paymentDate = promptValidDate();
        String paymentAmount = promptValidAmount();
        String paymentStatus = promptValidStatus();

        String qry = "UPDATE tbl_payment SET tenant_id = ?, payment_date = ?, payment_amount = ?, payment_status = ? WHERE payment_id = ?";
        conf.updateRecord(qry, tenantId, paymentDate, paymentAmount, paymentStatus, id);
        System.out.println("Payment updated successfully.\n");
    }

    public void deletePayment() {
        System.out.print("Enter Payment ID to Delete: ");
        String id = sc.nextLine();

        while (conf.getSingleValue("SELECT payment_id FROM tbl_Payment WHERE payment_id = ?", id) == 0) {
            System.out.println("Selected Payment ID doesn't exist!");
            System.out.print("Select payment ID again: ");
            id = sc.nextLine();
        }

        String qry = "DELETE FROM tbl_Payment WHERE payment_id = ?";
        conf.deleteRecord(qry, id);
        System.out.println("Payment deleted successfully.\n");
    }

    public void selectPayment() {
        System.out.print("Enter Payment ID: ");
        String id = sc.nextLine();

        while (conf.getSingleValue("SELECT payment_id FROM tbl_Payment WHERE payment_id = ?", id) == 0) {
            System.out.println("Selected Payment ID doesn't exist!");
            System.out.print("Select payment ID again: ");
            id = sc.nextLine();
        }

        String qry = "SELECT * FROM tbl_Payment WHERE payment_id = ?";

        try {
            PreparedStatement findRow = conf.connectDB().prepareStatement(qry);
            findRow.setString(1, id);

            try (ResultSet rs = findRow.executeQuery()) {
                if (rs.next()) {
                    String paymentId = rs.getString("payment_id");
                    String tenantId = rs.getString("tenant_id");
                    String paymentDate = rs.getString("payment_date");
                    String paymentAmount = rs.getString("payment_amount");
                    String paymentStatus = rs.getString("payment_status");

                    System.out.println("\nPayment Information: ");
                    System.out.println("-------------------------------------");
                    System.out.println("Payment ID: " + paymentId);
                    System.out.println("Tenant ID: " + tenantId);
                    System.out.println("Payment Date: " + paymentDate);
                    System.out.println("Amount: " + paymentAmount);
                    System.out.println("Status: " + paymentStatus);
                    System.out.println("-------------------------------------");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

   
    private String promptValidPaymentId() {
        String paymentId;
        do {
            System.out.print("Payment ID (alphanumeric): ");
            paymentId = sc.nextLine();
        } while (!isValidPaymentId(paymentId));
        return paymentId;
    }

    private String promptValidTenantId() {
        String tenantId;
        do {
            System.out.print("Tenant ID (numeric): ");
            tenantId = sc.nextLine();
        } while (!isValidTenantId(tenantId));
        return tenantId;
    }

    private String promptValidDate() {
        String date;
        do {
            System.out.print("Enter Date (YYYY-MM-DD): ");
            date = sc.nextLine();
        } while (!isValidDate(date));
        return date;
    }

    private String promptValidAmount() {
        String amount;
        do {
            System.out.print("Enter Amount (positive number): ");
            amount = sc.nextLine();
        } while (!isValidAmount(amount));
        return amount;
    }

    private String promptValidStatus() {
        String status;
        do {
            System.out.print("Enter Status (Paid/Unpaid): ");
            status = sc.nextLine();
        } while (!isValidStatus(status));
        return status;
    }

   
    private boolean isValidPaymentId(String paymentId) {
        return Pattern.matches("^[A-Za-z0-9]+$", paymentId);
    }

    private boolean isValidTenantId(String tenantId) {
        return tenantId.matches("\\d+"); 
    }

    private boolean isValidDate(String date) {
        return Pattern.matches("^\\d{4}-\\d{2}-\\d{2}$", date);
    }

    private boolean isValidAmount(String amount) {
        try {
            double value = Double.parseDouble(amount);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidStatus(String status) {
        return status.equalsIgnoreCase("Paid") || status.equalsIgnoreCase("Unpaid");
    }
}

            