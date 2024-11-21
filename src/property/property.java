package property;

import java.util.Scanner;

public class property {
    Scanner sc = new Scanner(System.in);
    properties p = new properties();

    public void propertyTransaction() {
        int action;
        do {
            System.out.println("1. Add Property");
            System.out.println("2. View Properties");
            System.out.println("3. Update Property");
            System.out.println("4. Delete Property");
            System.out.println("5. Select Property");
            System.out.println("6. Exit");

            System.out.print("Enter action: ");
            action = sc.nextInt();
            sc.nextLine();

            switch(action) {
                case 1:
                    p.addProperty();
                    p.viewProperties();
                    break;

                case 2:
                    p.viewProperties();
                    break;

                case 3:
                    p.viewProperties();
                    p.updateProperty();
                    p.viewProperties();
                    break;

                case 4:
                    p.viewProperties();
                    p.deleteProperty();
                    p.viewProperties();
                    break;

                case 5:
                    p.selectProperty();
                    break;

                case 6:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid action. Please try again.");
            }
        } while(action != 6);
    }
}
