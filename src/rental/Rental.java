
package rental;


import java.util.Scanner;

public class Rental {
    config conf = new config();

        public static void main(String[]  args) {
            
            Scanner sc = new Scanner(System.in);
            Rental rent = new Rental();
            
            int action;
            do{
                System.out.println("1. Add Property");
                System.out.println("2. Display Properties");
                System.out.println("3. Update Property");
                System.out.println("4. Delete Property");
                System.out.println("5. Exit");

                System.out.println("Enter action");
                action = sc.nextInt();   

                     switch(action){      
                    case 1:
                        rent.addRental();
                        break;

                    case 2:
                        
                        rent.viewRental();
                        break;

                    case 3:  
                        rent.updateRental();
                        
                        break;
                        
                    case 4:
                       
                        rent.deleteRental();
                        break;

                    case 5:
                        System.out.println("Exiting.....");
                        break;
                    default:
                        System.out.println("Invalid action.Please try again");
                }
            }while(action != 5);
                    
    }
        public void addRental(){
            Scanner sc = new Scanner(System.in);

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
        
        public void viewRental() {
            String qry = "SELECT * FROM tbl_Tenant";
            String[] hrds = {"ID","Name", "Date", "Address", "Duration", "Status"};
            String[] clmns = {"r_id", "r_name", "r_date", "r_address", "r_duration", "r_status"};

            config conf = new config();
            conf.viewRecords(qry, hrds, clmns);
    }   
        
        private void updateRental() {
            Scanner sc = new Scanner (System.in);
            System.out.println("Enter ID to update: ");
            int id = sc.nextInt();
            
            System.out.println("New Tenant name: ");
            String rname = sc.next();
            
            System.out.println("New Date: ");
            String rdate = sc.next();
            
            System.out.println("New Address: ");
            String raddress = sc.next();
            
            System.out.println("New Duration: ");
            String rduration = sc.next();
            
            System.out.println("New Status: ");
            String rstatus = sc.next();
            
            String qry = "UPDATE tbl_Tenant SET r_name = ?, r_date = ?, r_address = ?, r_duration = ?, r_status = ? WHERE r_id = " + id;
       
            conf.updateRecord(qry, rname, rdate, raddress, rduration, rstatus);
        }

        private void deleteRental(){
            
            Scanner sc = new Scanner (System.in);
            System.out.println("Enter the ID to Delete: ");
            int id = sc.nextInt();
            
            String qry = "DELETE FROM tbl_Tenant WHERE r_id = ?";
            
            config conf = new config();
            conf.deleteRecord(qry, id);
        }
        
   }
        

        
        


