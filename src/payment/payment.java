package payment;

import java.util.Scanner;
import rental.config;

public class payment {
    Scanner sc = new Scanner(System.in);
    payments p = new payments();

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
                    p.addPayment();
                    p.viewPayments();
                    break;

                case 2:
                    p.viewPayments();
                    break;

                case 3:
                    p.viewPayments();
                    p.updatePayment();
                    p.viewPayments();
                    break;

                case 4:
                    p.viewPayments();
                    p.deletePayment();
                    p.viewPayments();
                    break;

                case 5:
                    p.selectPayment();
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

            