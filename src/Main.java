import java.util.Scanner;

public class Main {
    private static Login loginService;
    private static Scanner scanner;

    public static void main(String[] args) {
        loginService = new Login();
        scanner = new Scanner(System.in);

        while (true) { 
    
            loginService.showLoginScreen();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (loginService.authenticate(email, password)) {
                //clearScreen();
                if (loginService.isHRAdmin()) {
 
                    showHRAdminMenu();

                } else {

                    connectToEmployee();
                }
                break;

            } else {
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                clearScreen();
            }
        }
        scanner.close();
    }

     public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    private static void showHRAdminMenu(){

        System.out.println("\nConnecting to HR Admin Portal...\n");
        while (true) { 
            System.out.println("\n==================================================");
            System.out.println("                HR ADMIN MAIN MENU");
            System.out.println(" ==================================================");
            System.out.println("1. Employee Services");
            System.out.println("2. Reports Menu");
            System.out.println("3. Add Employee");
            System.out.println("4. Logout");
            System.out.print("\nEnter choice (1-4): ");

            String choice = scanner.nextLine();

            try{
                int choiceInt = Integer.parseInt(choice);
                switch (choiceInt) {
                case 1:
                    ///////////////changes
                    clearScreen();
                    Admin.menu();
                    break;
                case 2:
                    ///report menus
                    clearScreen();
                    adminReportMenu.adminReport();
                    break;
                case 3:
                    clearScreen();
                    ///////////////add employee
                    addEmployeeMenu.main(null);

                    addEmployeeMenu menu = new addEmployeeMenu();
                    lookUpService lookup = new lookUpService();

                    Scanner sc = new Scanner(System.in);
                    menu.addEmployee(sc, lookup);

                    break;
                case 4:
                    clearScreen();
                    loginService.logout();
                    return;
                default:
                    clearScreen();
                    System.out.println("Invalid choice.");
                    System.out.print("Press Enter to continue...");
                    scanner.nextLine();
                    clearScreen();
                    break;
            }
            }catch(Exception e){
                clearScreen();
                System.out.println("Please enter a number between 1 and 3.");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                clearScreen();
                continue;
            }
        
        }
    }
    
    private static void connectToEmployee(){
        System.out.println("\n Connecting to Employee Portal...\n");

        EmployeeService serviceForEmployee = new EmployeeService();
        while (true) {
            System.out.println("==================================================");
            System.out.println("          EMPLOYEE PORTAL");
            System.out.println("==================================================");
            System.out.println("1. View My Information");
            System.out.println("2. View My Pay History");
            System.out.println("3. Logout");
            System.out.print("\nEnter choice (1-3): ");

            String choice = scanner.nextLine();
            int choiceInt = 0;

            //check if choice is a word
            try{
                choiceInt = Integer.parseInt(choice);    
                switch (choiceInt) {
                case 1:
                    clearScreen();
                    serviceForEmployee.secureEmployeeAccess();
                    break;
                case 2:
                    clearScreen();
                    serviceForEmployee.viewPayHistory();
                    break;

                case 3:
                    clearScreen();
                    loginService.logout();
                    return;
                default:
                    clearScreen();
                    System.out.println(" Invalid choice.");
                    System.out.print("Press Enter to continue...");
                    scanner.nextLine();
                    clearScreen();
                    break;
            }     
            }catch(NumberFormatException e){
                clearScreen();
                System.out.println("Please enter a number between 1 and 3.");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                clearScreen();
                continue;
            }
            

        }
    }

}