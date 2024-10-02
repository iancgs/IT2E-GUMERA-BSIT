
package rental;


import java.util.Scanner;

public class Rental {

   
    public void addRental(){
       Scanner sc = new Scanner(System.in);
        config conf = new config();
        System.out.print("tenant Name: ");
        String rname = sc.next();
        System.out.print("Enter Date: ");
        String date = sc.next();
        System.out.print("Enter Address: ");
        String address = sc.next();
        System.out.print("Enter Duration: ");
        String duration = sc.next();
        System.out.print    ("Enter Status: ");
        String status = sc.next();

        String sql = "INSERT INTO tbl_Tenant (r_name, r_date, r_address, r_duration, r_status) VALUES (?, ?, ?, ?, ?)";
        conf.addRecord(sql , rname, date, address, duration, status);

    }

        public static void main(String[]  args) {
            
        Scanner sc = new Scanner(System.in);
    
            System.out.println("1. Add Property");
            System.out.println("2. Display Properties");
            System.out.println("3. Update Property");
            System.out.println("4. Delete Property");
            System.out.println("5. Exit");
    
            System.out.println("Enter action");
            int action = sc.nextInt();
            
            switch(action){
                
                case 1:
                    Rental test = new Rental();
                    test.addRental();
                               
    
    
}
    
        }
}