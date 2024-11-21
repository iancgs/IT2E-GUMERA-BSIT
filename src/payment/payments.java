package payment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;
import rental.config;

public class payments {
    config conf = new config();
    Scanner sc = new Scanner(System.in);

    public void addPayment() {
        int rentalId = promptValidRentalId();
        String paymentDate = promptValidDate();
        String paymentStatus = promptValidStatus();
        double paymentAmount = promptValidAmount();

        String sql = "INSERT INTO tbl_payment (r_id, payment_date, payment_status, payment_amount) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, rentalId, paymentDate, paymentStatus, paymentAmount);
        System.out.println("Payment added successfully.\n");
    }

    public void viewPayments() {
        String qry = "SELECT * FROM tbl_payment";
        String[] headers = {"Payment ID", "Rental ID", "Payment Date", "Payment Status", "Payment Amount"};
        String[] columns = {"payment_id", "r_id", "payment_date", "payment_status", "payment_amount"};

        conf.viewRecords(qry, headers, columns);
    }

    public void updatePayment() {
        System.out.print("Enter Payment ID to Update: ");
        int id = sc.nextInt();
        sc.nextLine();

        while (conf.getSingleValue("SELECT payment_id FROM tbl_payment WHERE payment_id = ?", id) == 0) {
            System.out.println("Selected Payment ID doesn't exist!");
            System.out.print("Select Payment ID again: ");
            id = sc.nextInt();
            sc.nextLine();
        }

        int rentalId = promptValidRentalId();
        String paymentDate = promptValidDate();
        String paymentStatus = promptValidStatus();
        double paymentAmount = promptValidAmount();

        String qry = "UPDATE tbl_payment SET r_id = ?, payment_date = ?, payment_status = ?, payment_amount = ? WHERE payment_id = ?";
        conf.updateRecord(qry, rentalId, paymentDate, paymentStatus, paymentAmount, id);
        System.out.println("Payment updated successfully.\n");
    }

    public void deletePayment() {
        System.out.print("Enter Payment ID to Delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        while (conf.getSingleValue("SELECT payment_id FROM tbl_payment WHERE payment_id = ?", id) == 0) {
            System.out.println("Selected Payment ID doesn't exist!");
            System.out.print("Select Payment ID again: ");
            id = sc.nextInt();
            sc.nextLine();
        }

        String qry = "DELETE FROM tbl_payment WHERE payment_id = ?";
        conf.deleteRecord(qry, id);
        System.out.println("Payment deleted successfully.\n");
    }

    public void selectPayment() {
        System.out.print("Enter Payment ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        while (conf.getSingleValue("SELECT payment_id FROM tbl_payment WHERE payment_id = ?", id) == 0) {
            System.out.println("Selected Payment ID doesn't exist!");
            System.out.print("Select Payment ID again: ");
            id = sc.nextInt();
            sc.nextLine();
        }

        String qry = "SELECT * FROM tbl_payment WHERE payment_id = ?";

        try {
            PreparedStatement findRow = conf.connectDB().prepareStatement(qry);
            findRow.setInt(1, id);

            try (ResultSet rs = findRow.executeQuery()) {
                System.out.println("\nPayment Information:");
                System.out.println("-------------------------------------");
                System.out.println("Payment ID: " + rs.getInt("payment_id"));
                System.out.println("Rental ID: " + rs.getInt("r_id"));
                System.out.println("Payment Date: " + rs.getString("payment_date"));
                System.out.println("Payment Status: " + rs.getString("payment_status"));
                System.out.println("Payment Amount: " + rs.getDouble("payment_amount"));
                System.out.println("-------------------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Validation Methods
    public int promptValidRentalId() {
        int rentalId;
        do {
            System.out.print("Enter Rental ID (must exist): ");
            rentalId = sc.nextInt();
            sc.nextLine();
        } while (conf.getSingleValue("SELECT r_id FROM tbl_rental WHERE r_id = ?", rentalId) == 0);
        return rentalId;
    }

    public String promptValidDate() {
        String date;
        do {
            System.out.print("Enter Payment Date (YYYY-MM-DD): ");
            date = sc.nextLine();
        } while (!isValidDate(date));
        return date;
    }

    public String promptValidStatus() {
        String status;
        do {
            System.out.print("Enter Payment Status (Paid/Unpaid): ");
            status = sc.nextLine();
        } while (!isValidStatus(status));
        return status;
    }

    public double promptValidAmount() {
        double amount;
        do {
            System.out.print("Enter Payment Amount (positive number): ");
            while (!sc.hasNextDouble()) {
                System.out.print("Invalid input. Enter Payment Amount (positive number): ");
                sc.next();
            }
            amount = sc.nextDouble();
            sc.nextLine();
        } while (amount <= 0);
        return amount;
    }

    public boolean isValidDate(String date) {
        return Pattern.matches("^\\d{4}-\\d{2}-\\d{2}$", date);
    }

    public boolean isValidStatus(String status) {
        return status.equalsIgnoreCase("Paid") || status.equalsIgnoreCase("Unpaid");
    }
}
