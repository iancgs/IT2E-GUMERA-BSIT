
package rental;


import java.util.InputMismatchException;
import java.util.Scanner;
import payment.payment;

public class Main {
    config conf = new config();

        public static void main(String[]  args) {
            Scanner sc = new Scanner(System.in);
     
        Rental rent = new Rental();
        payment pay = new payment();
        
        int op = -1; 
        
        do {
            try {
                System.out.println("\n------------------------------------------------");
                System.out.println("           WELCOME TO RENTAL SYSTEM      ");
                System.out.println("------------------------------------------------");
   

                System.out.println("1. RENTAL ");
                System.out.println("2. PAYMENT");
                System.out.println("3. EXIT ");

                System.out.print("\nSelect an Option: ");
                op = sc.nextInt();
                sc.nextLine(); 

                switch (op) {
                    case 1:
                        rent.rentaltransac();
                        break;
                        
                    case 2:
                        pay.paymentTransaction();                                          
                        
                    case 3:
                        System.out.println("Exiting....");
                        break;

                    default:
                        System.out.println("Invalid Option.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine();  
                op = -1;  
            }
        } while (op != 2);

        sc.close(); 
    }

        }
        

        
        


