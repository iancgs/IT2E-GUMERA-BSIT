
package rental;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import java.util.Scanner;

public class rentals {
    config conf = new config();
    Scanner sc = new Scanner(System.in);
    
    
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
    
    public void selectRental(){
        System.out.print("Enter ID: ");
        int id = sc.nextInt();


        while (conf.getSingleValue("SELECT r_id FROM tbl_Tenant WHERE r_id = ?", id) == 0) {
            System.out.println("Selected ID doesn't exist!");
            System.out.print("Select tenant ID again: ");
            id = sc.nextInt();
            sc.nextLine(); 
        }
        
        String qry = "SELECT * FROM tbl_Tenant WHERE r_id = ?";
        
        try{
            PreparedStatement findRow = conf.connectDB().prepareStatement(qry);
            findRow.setInt(1, id);
            
            try(ResultSet rs = findRow.executeQuery()){
                int rid = rs.getInt("r_id");
                String rname = rs.getString("r_name");
                String rdate = rs.getString("r_date");
                String raddress = rs.getString("r_address");
                String rduration = rs.getString("r_duration");
                String rstatus = rs.getString("r_status");
                
                System.out.println("\nTenant Information: ");
                System.out.println("-------------------------------------");
                System.out.println("ID: "+rid);
                System.out.println("Name: "+rname);
                System.out.println("Date: "+rdate);
                System.out.println("Address: "+raddress);
                System.out.println("Duration: "+rduration);
                System.out.println("-------------------------------------");
                System.out.println("Status: "+rstatus);

            }
            
            
          
        } catch(SQLException e){
            System.out.println("Error: "+e.getMessage());
        }
    }
    

    public String promptValidName() {
        String name;
        do {
            System.out.print("Tenant Name (only alphabets): ");
            name = sc.nextLine();
        } while (!isValidName(name));
        return name;
    }

    public String promptValidDate() {
        String date;
        do {
            System.out.print("Enter Date (YYYY-MM-DD): ");
            date = sc.nextLine();
        } while (!isValidDate(date));
        return date;
    }

    public String promptValidAddress() {
        String address;
        do {
            System.out.print("Enter Address (cannot be empty): ");
            address = sc.nextLine();
        } while (address.trim().isEmpty());
        return address;
    }

    public String promptValidDuration() {
        String duration;
        do {
            System.out.print("Enter Duration (positive integer): ");
            duration = sc.nextLine();
        } while (!isValidDuration(duration));
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
        return Pattern.matches("^[A-Za-z]+$", name);
    }

    public boolean isValidDate(String date) {
        return Pattern.matches("^\\d{4}-\\d{2}-\\d{2}$", date);
    }

    public boolean isValidDuration(String duration) {
        try {
            int value = Integer.parseInt(duration);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isValidStatus(String status) {
        return status.equalsIgnoreCase("Active") || status.equalsIgnoreCase("Inactive");
    }
}
