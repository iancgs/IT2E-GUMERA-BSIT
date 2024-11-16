package rental;

import java.util.Scanner;

public class Rental {
    Scanner sc = new Scanner(System.in);
    rentals r = new rentals();

    public void rentaltransac() {
        int action;
        do {
            System.out.println("1. Add Property");
            System.out.println("2. Display Properties");
            System.out.println("3. Update Property");
            System.out.println("4. Delete Property");
            System.out.println("5. Select Property");
            System.out.println("6. Exit");

            System.out.print("Enter action: ");
            action = sc.nextInt();
            sc.nextLine();

            switch(action) {      
                case 1:
                    r.addRental();
                    r.viewRental();
                    break;

                case 2:
                    r.viewRental();
                    break;

                case 3:  
                    r.viewRental();
                    r.updateRental();
                    r.viewRental();
                    break;
                    
                case 4:
                    r.viewRental();
                    r.deleteRental();
                    r.viewRental();
                    break;

                case 5:
                    r.selectRental();
                    break;
                    
                case 6:                            
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.print("Invalid action. Please try again.");
            }
        } while(action != 6);
    }
}
