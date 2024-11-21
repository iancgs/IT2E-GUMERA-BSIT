package rental;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;
import property.properties;

public class rentals {
    config conf = new config();
    Scanner sc = new Scanner(System.in);
    properties prop = new properties();

    public void addRental() {
        String tenantName = promptValidTenantName();
        String tenantContactNum = promptValidTenantContact();
        prop.viewProperties();
        int propertyId = promptValidPropertyId();
        String rentalDate = promptValidDate();
        int duration = promptValidDuration();
        String status = promptValidStatus();

        String sql = "INSERT INTO tbl_rental (tenant_name, tenant_contact_num, p_id, r_date, r_duration, r_status) VALUES (?, ?, ?, ?, ?, ?)";
        conf.addRecord(sql, tenantName, tenantContactNum, propertyId, rentalDate, duration, status);
        System.out.println("Rental added successfully.\n");
    }

    public void viewRental() {
        String query = "SELECT * FROM tbl_rental";
        String[] headers = {"ID", "Tenant Name", "Contact", "Property ID", "Date", "Duration", "Status"};
        String[] columns = {"r_id", "tenant_name", "tenant_contact_num", "p_id", "r_date", "r_duration", "r_status"};
        conf.viewRecords(query, headers, columns);
    }

    public void updateRental() {
        System.out.print("Enter Rental ID to Update: ");
        int rentalId = sc.nextInt();
        sc.nextLine();

        while (conf.getSingleValue("SELECT r_id FROM tbl_rental WHERE r_id = ?", rentalId) == 0) {
            System.out.println("Selected Rental ID doesn't exist!");
            System.out.print("Enter Rental ID again: ");
            rentalId = sc.nextInt();
            sc.nextLine();
        }

        String tenantName = promptValidTenantName();
        String tenantContactNum = promptValidTenantContact();
        prop.viewProperties();
        int propertyId = promptValidPropertyId();
        String rentalDate = promptValidDate();
        int duration = promptValidDuration();
        String status = promptValidStatus();

        String query = "UPDATE tbl_rental SET tenant_name = ?, tenant_contact_num = ?, p_id = ?, r_date = ?, r_duration = ?, r_status = ? WHERE r_id = ?";
        conf.updateRecord(query, tenantName, tenantContactNum, propertyId, rentalDate, duration, status, rentalId);
        System.out.println("Rental updated successfully.\n");
    }

    public void deleteRental() {
        System.out.print("Enter Rental ID to Delete: ");
        int rentalId = sc.nextInt();

        while (conf.getSingleValue("SELECT r_id FROM tbl_rental WHERE r_id = ?", rentalId) == 0) {
            System.out.println("Selected Rental ID doesn't exist!");
            System.out.print("Enter Rental ID again: ");
            rentalId = sc.nextInt();
        }

        String query = "DELETE FROM tbl_rental WHERE r_id = ?";
        conf.deleteRecord(query, rentalId);
        System.out.println("Rental deleted successfully.\n");
    }

    public void selectRental() {
        System.out.print("Enter Rental ID: ");
        int rentalId = sc.nextInt();

        while (conf.getSingleValue("SELECT r_id FROM tbl_rental WHERE r_id = ?", rentalId) == 0) {
            System.out.println("Selected Rental ID doesn't exist!");
            System.out.print("Enter Rental ID again: ");
            rentalId = sc.nextInt();
        }

        String query = "SELECT * FROM tbl_rental WHERE r_id = ?";
        try (PreparedStatement ps = conf.connectDB().prepareStatement(query)) {
            ps.setInt(1, rentalId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("\nRental Information:");
                    System.out.println("----------------------------");
                    System.out.println("ID: " + rs.getInt("r_id"));
                    System.out.println("Tenant Name: " + rs.getString("tenant_name"));
                    System.out.println("Contact: " + rs.getString("tenant_contact_num"));
                    System.out.println("Property ID: " + rs.getInt("p_id"));
                    System.out.println("Date: " + rs.getString("r_date"));
                    System.out.println("Duration: " + rs.getInt("r_duration"));
                    System.out.println("Status: " + rs.getString("r_status"));
                    System.out.println("----------------------------");
                } else {
                    System.out.println("No rental found with the given ID.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public String promptValidTenantName() {
        String name;
        do {
            System.out.print("Enter Tenant Name (alphabets only): ");
            name = sc.nextLine();
        } while (!isValidName(name));
        return name;
    }

    public String promptValidTenantContact() {
        String contact;
        do {
            System.out.print("Enter Contact Number (11 digits): ");
            contact = sc.nextLine();
        } while (!isValidContact(contact));
        return contact;
    }

    public int promptValidPropertyId() {
        int propertyId;
        do {
            System.out.print("Enter Property ID: ");
            propertyId = sc.nextInt();
            sc.nextLine();
        } while (conf.getSingleValue("SELECT p_id FROM tbl_property WHERE p_id = ?", propertyId) == 0);
        return propertyId;
    }

    public String promptValidDate() {
        String date;
        do {
            System.out.print("Enter Date (YYYY-MM-DD): ");
            date = sc.nextLine();
        } while (!isValidDate(date));
        return date;
    }

    public int promptValidDuration() {
        int duration;
        do {
            System.out.print("Enter Duration (positive number of days): ");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input! Please enter a positive integer.");
                sc.next();
            }
            duration = sc.nextInt();
            sc.nextLine();
        } while (duration <= 0);
        return duration;
    }

    public String promptValidStatus() {
        String status;
        do {
            System.out.print("Enter Status (Active/Inactive): ");
            status = sc.nextLine();
        } while (!isValidStatus(status));
        return status;
    }

    public boolean isValidName(String name) {
        return Pattern.matches("^[A-Za-z ]+$", name);
    }

    public boolean isValidContact(String contact) {
        return Pattern.matches("^\\d{11}$", contact);
    }

    public boolean isValidDate(String date) {
        return Pattern.matches("^\\d{4}-\\d{2}-\\d{2}$", date);
    }

    public boolean isValidStatus(String status) {
        return status.equalsIgnoreCase("Active") || status.equalsIgnoreCase("Inactive");
    }
}
