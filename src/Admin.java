import java.util.Scanner;



public class Admin {

    private static AdminService service = new AdminService();
    private static Scanner scanner = new Scanner(System.in);

    public static void menu() {
        while (true) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. Search Employee");
            System.out.println("2. Delete Employee");
            System.out.println("3. Update Salary");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            // ✅ prevent crash if non-number is entered
            while (!scanner.hasNextInt()) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println("Please enter a number (1-4).");
                System.out.println("\n--- ADMIN MENU ---");
                System.out.println("1. Search Employee");
                System.out.println("2. Delete Employee");
                System.out.println("3. Update Salary");
                System.out.println("4. Exit");
                System.out.print("Enter choice: ");
                scanner.next();
            }

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 : 
                    searchEmployee();
                    break;
                case 2 : 
                    deleteEmployee();
                    break;

                case 3 : 
                    updateSalary();
                    break;
                case 4 : {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("Exiting Employee Services Menu...");
                    return;
                }
                default:
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void searchEmployee() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("--- SEARCH EMPLOYEE ---");
        System.out.print("Enter empID: ");
        //check if the input value is an integer
        while (!scanner.hasNextInt()) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("--- SEARCH EMPLOYEE ---");
            System.out.print("Enter a valid numeric empID: ");
            scanner.next();
        }  


        int id = scanner.nextInt();

        Employee emp = service.findEmployee(id);
        System.out.println("--- SEARCH EMPLOYEE RESULT---");
        if (emp != null) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("--- SEARCH EMPLOYEE RESULT---");
            System.out.println("Found: " + emp.getName() + " | $" + emp.getSalary());
            System.out.println("-----------------------------");
            
        } else {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("--- SEARCH EMPLOYEE RESULT---");
            System.out.println("Employee with ID " + id + " not found.");
            System.out.println("-----------------------------");
        }

    }

    private static void deleteEmployee() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("--- DELETE EMPLOYEE ---");
        System.out.print("Enter empID to delete: ");

        while (!scanner.hasNextInt()) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("--- DELETE EMPLOYEE ---");
            System.out.print("Enter a valid numeric empID: ");
            scanner.next();
        }  

        int id = scanner.nextInt();

        Employee emp = service.findEmployee(id);

        if (emp == null) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("--- DELETE EMPLOYEE RESULT---");
            System.out.println("Employee with ID " + id + " not found.");
            System.out.println("-----------------------------");
            return;
        }

        System.out.print("Confirm delete employee " + emp.getName() + " with ID " + id + " (yes/no): ");
        String confirm = scanner.next();

        if (confirm.equalsIgnoreCase("yes")) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("--- DELETE EMPLOYEE RESULT---");
            if (service.deleteEmployee(id)) {
                System.out.println("Deleted successfully.");
            } else {
                System.out.println("Delete failed.");
            }
            System.out.println("-----------------------------");


        } else {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("--- DELETE EMPLOYEE RESULT---");
            System.out.println("Delete cancelled");
            System.out.println("-----------------------------");
        }

    }

    private static void updateSalary() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("--- UPDATE SALARY ---");
        System.out.println("NOTE: - for decrease ex: -10 for -10%");
        System.out.print("Enter % increase(float or whole number): ");
        //check if the input value is a double
        while (!scanner.hasNextDouble()) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Enter a valid percentage.");
            System.out.println("--- UPDATE SALARY ---");
            System.out.println("NOTE: - for decrease ex: -10 for -10%");
            System.out.print("Enter % increase(float or whole number): ");
            //check if the input is non-negative
            scanner.next();
        }

        
        double percent = scanner.nextDouble();

        System.out.print("Max salary and including: ");

        while (!scanner.hasNextDouble()) {
            System.out.println("Enter a valid salary limit.");
            scanner.next();
        }

        double max = scanner.nextDouble();

        service.updateSalary(percent, max);
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("--- UPDATE SALARY RESULT---");
        System.out.println("Salaries updated.");
        System.out.println("-----------------------------");
    }
}
