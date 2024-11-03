package rental;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Rental {
    Scanner sc = new Scanner(System.in);
    config conf = new config();

    public void rentaltransac() {
        int action;
        do {
            System.out.println("1. Add Property");
            System.out.println("2. Display Properties");
            System.out.println("3. Update Property");
            System.out.println("4. Delete Property");
            System.out.println("5. Exit");

            System.out.print("Enter action: ");
            action = sc.nextInt();
            sc.nextLine();

            switch(action) {      
                case 1:
                    addRental();
                    break;

                case 2:
                    viewRental();
                    break;

                case 3:  
                    updateRental();
                    break;
                    
                case 4:
                    deleteRental();
                    break;

                case 5:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.print("Invalid action. Please try again.");
            }
        } while(action != 5);
    }

    public void addRental() {
        String rname = promptValidName();
        String date = promptValidDate();
        String address = promptValidAddress();
        String duration = promptValidDuration();
        String status = promptValidStatus();

        String sql = "INSERT INTO tbl_Tenant (r_name, r_date, r_address, r_duration, r_status) VALUES (?, ?, ?, ?, ?)";
        conf.addRecord(sql, rname, date, address, duration, status);
        System.out.println("Property added successfully.\n");
    }
    
    public void viewRental() {
        String qry = "SELECT * FROM tbl_Tenant";
        String[] hrds = {"ID", "Name", "Date", "Address", "Duration", "Status"};
        String[] clmns = {"r_id", "r_name", "r_date", "r_address", "r_duration", "r_status"};

        conf.viewRecords(qry, hrds, clmns);
    }   
        
    public void updateRental() {
        System.out.print("Enter ID to Update: ");
        int id = sc.nextInt();
        sc.nextLine();

        while (conf.getSingleValue("SELECT r_id FROM tbl_Tenant WHERE r_id = ?", id) == 0) {
            System.out.println("Selected ID doesn't exist!");
            System.out.print("Select tenant ID again: ");
            id = sc.nextInt();
            sc.nextLine(); 
        }
        
        String rname = promptValidName();
        String date = promptValidDate();
        String address = promptValidAddress();
        String duration = promptValidDuration();
        String status = promptValidStatus();

        String qry = "UPDATE tbl_Tenant SET r_name = ?, r_date = ?, r_address = ?, r_duration = ?, r_status = ? WHERE r_id = " + id;
        conf.updateRecord(qry, rname, date, address, duration, status);
        System.out.println("Property updated successfully.\n");
    }

    public void deleteRental() {
        System.out.print("Enter ID to Delete: ");
        int id = sc.nextInt();


        while (conf.getSingleValue("SELECT r_id FROM tbl_Tenant WHERE r_id = ?", id) == 0) {
            System.out.println("Selected ID doesn't exist!");
            System.out.print("Select tenant ID again: ");
            id = sc.nextInt();
            sc.nextLine(); 
        }

        String qry = "DELETE FROM tbl_Tenant WHERE r_id = ?";
        conf.deleteRecord(qry, id);
        System.out.println("Property deleted successfully.\n");
    }
    

    private String promptValidName() {
        String name;
        do {
            System.out.print("Tenant Name (only alphabets): ");
            name = sc.nextLine();
        } while (!isValidName(name));
        return name;
    }

    private String promptValidDate() {
        String date;
        do {
            System.out.print("Enter Date (YYYY-MM-DD): ");
            date = sc.nextLine();
        } while (!isValidDate(date));
        return date;
    }

    private String promptValidAddress() {
        String address;
        do {
            System.out.print("Enter Address (cannot be empty): ");
            address = sc.nextLine();
        } while (address.trim().isEmpty());
        return address;
    }

    private String promptValidDuration() {
        String duration;
        do {
            System.out.print("Enter Duration (positive integer): ");
            duration = sc.nextLine();
        } while (!isValidDuration(duration));
        return duration;
    }

    private String promptValidStatus() {
        String status;
        do {
            System.out.print("Enter Status (Active/Inactive): ");
            status = sc.nextLine();
        } while (!isValidStatus(status));
        return status;
    }

    private boolean isValidName(String name) {
        return Pattern.matches("^[A-Za-z]+$", name);
    }

    private boolean isValidDate(String date) {
        return Pattern.matches("^\\d{4}-\\d{2}-\\d{2}$", date);
    }

    private boolean isValidDuration(String duration) {
        try {
            int value = Integer.parseInt(duration);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidStatus(String status) {
        return status.equalsIgnoreCase("Active") || status.equalsIgnoreCase("Inactive");
    }
}
