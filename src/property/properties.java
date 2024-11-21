package property;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import rental.config;
import java.util.Scanner;

public class properties {
    config conf = new config();
    Scanner sc = new Scanner(System.in);

    public void addProperty() {
        String propertyName = promptValidPropertyName();
        String propertyAddress = promptValidPropertyAddress();
        String propertyStatus = promptValidPropertyStatus();

        String sql = "INSERT INTO tbl_property (p_name, p_address, p_status) VALUES (?, ?, ?)";
        conf.addRecord(sql, propertyName, propertyAddress, propertyStatus);
        System.out.println("Property added successfully.\n");
    }

    public void viewProperties() {
        String qry = "SELECT * FROM tbl_property";
        String[] hrds = {"Property ID", "Property Name", "Property Address", "Property Status"};
        String[] clmns = {"p_id", "p_name", "p_address", "p_status"};

        conf.viewRecords(qry, hrds, clmns);
    }

    public void updateProperty() {
        System.out.print("Enter Property ID to Update: ");
        String id = sc.nextLine();

        while (conf.getSingleValue("SELECT p_id FROM tbl_property WHERE p_id = ?", id) == 0) {
            System.out.println("Selected Property ID doesn't exist!");
            System.out.print("Select property ID again: ");
            id = sc.nextLine();
        }

        String propertyName = promptValidPropertyName();
        String propertyAddress = promptValidPropertyAddress();
        String propertyStatus = promptValidPropertyStatus();

        String qry = "UPDATE tbl_property SET p_name = ?, p_address = ?, p_status = ? WHERE p_id = ?";
        conf.updateRecord(qry, propertyName, propertyAddress, propertyStatus, id);
        System.out.println("Property updated successfully.\n");
    }

    public void deleteProperty() {
        System.out.print("Enter Property ID to Delete: ");
        String id = sc.nextLine();

        while (conf.getSingleValue("SELECT p_id FROM tbl_property WHERE p_id = ?", id) == 0) {
            System.out.println("Selected Property ID doesn't exist!");
            System.out.print("Select property ID again: ");
            id = sc.nextLine();
        }

        String qry = "DELETE FROM tbl_property WHERE p_id = ?";
        conf.deleteRecord(qry, id);
        System.out.println("Property deleted successfully.\n");
    }

    public void selectProperty() {
        System.out.print("Enter Property ID: ");
        String id = sc.nextLine();

        while (conf.getSingleValue("SELECT p_id FROM tbl_property WHERE p_id = ?", id) == 0) {
            System.out.println("Selected Property ID doesn't exist!");
            System.out.print("Select property ID again: ");
            id = sc.nextLine();
        }

        String qry = "SELECT * FROM tbl_property WHERE p_id = ?";

        try {
            PreparedStatement findRow = conf.connectDB().prepareStatement(qry);
            findRow.setString(1, id);

            try (ResultSet rs = findRow.executeQuery()) {
                if (rs.next()) {
                    String propertyId = rs.getString("p_id");
                    String propertyName = rs.getString("p_name");
                    String propertyAddress = rs.getString("p_address");
                    String propertyStatus = rs.getString("p_status");

                    System.out.println("\nProperty Information: ");
                    System.out.println("-------------------------------------");
                    System.out.println("Property ID: " + propertyId);
                    System.out.println("Property Name: " + propertyName);
                    System.out.println("Property Address: " + propertyAddress);
                    System.out.println("Status: " + propertyStatus);
                    System.out.println("-------------------------------------");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public String promptValidPropertyName() {
        String propertyName;
        do {
            System.out.print("Property Name (Alphanumeric): ");
            propertyName = sc.nextLine();
        } while (!isValidPropertyName(propertyName));
        return propertyName;
    }

    public String promptValidPropertyAddress() {
        String propertyAddress;
        do {
            System.out.print("Property Address (Alphanumeric): ");
            propertyAddress = sc.nextLine();
        } while (!isValidPropertyAddress(propertyAddress));
        return propertyAddress;
    }

    public String promptValidPropertyStatus() {
        String status;
        do {
            System.out.print("Property Status (Available/Occupied): ");
            status = sc.nextLine();
        } while (!isValidPropertyStatus(status));
        return status;
    }

    public boolean isValidPropertyName(String propertyName) {
        return propertyName.length() > 0 && propertyName.length() <= 50;
    }

    public boolean isValidPropertyAddress(String propertyAddress) {
        return propertyAddress.length() > 0 && propertyAddress.length() <= 100;
    }

    public boolean isValidPropertyStatus(String status) {
        return status.equalsIgnoreCase("Available") || status.equalsIgnoreCase("Occupied");
    }
}
