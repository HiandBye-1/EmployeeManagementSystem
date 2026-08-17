import java.util.Scanner;
//A menu to add a new employee
//if user enter -1 at any time, it will return to main menu
public class addEmployeeMenu {

    public static void main(String[] args) {

    }
    

    // these methods ensure the user input is valid, and keeps prompting until they do
    //read an integer with error handling
    private int readInt(Scanner sc, String prompt) {
        if (prompt.equals("-1")) return -1;
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    // This method ensures the user inputs a valid double value, and keeps prompting until they do
    private double readDouble(Scanner sc, String prompt) {
        if(prompt.equals("-1")) return -1;
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    // This method ensures the user inputs a date in the correct format (YYYY-MM-DD), and keeps prompting until they do
    private String readDate(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();
            if(input.equals("-1")) {
                return "-1";
            }
            else if (input.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return input;
            }

            System.out.println("Invalid format. Use YYYY-MM-DD.");
        }
    }

    //this method ensurer the user enter seomthign in instead of leaving it blank, and keeps prompting until they do
    private String readNonEmpty(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();
            if (input.equals("-1")) {
                return "-1";
            }
            else if (!input.trim().isEmpty()) {
                return input;
            }

            System.out.println("Input cannot be empty. Try again.");
        }
    }


    // this method ensures the user input digits only for numbers, and keeps prompting until they do or -1 entered
    private String readNum(Scanner sc, String prompt) {
    while (true) {
        System.out.print(prompt);
        String input = sc.nextLine();
        // must be digits only
        if (input.equals("-1")) {
            return "-1";
        }
        else if (input.matches("\\d+")) {
            return input;
        }


        System.out.println("Invalid number. Use digits only.");
    }
}


    //end of input validation methods
    
    // ================= MAIN MENU =================

    public void addEmployee(Scanner sc, lookUpService lookup) {

        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("\n==================================================");
        System.out.println("            EMPLOYEE MANAGEMENT SYSTEM");
        System.out.println("                 ADD NEW EMPLOYEE");
        System.out.println("==================================================\n");

        System.out.println("--------------- BASIC INFORMATION ---------------");
        System.out.println("NOTE: All fields are required.");
        System.out.println("Type -1 at any time to cancel and return.\n");


        String fname = readNonEmpty(sc, "First Name: ");
        if (fname.equals("-1")) {
            return;
        }

        String lname = readNonEmpty(sc, "Last Name: ");
        if (lname.equals("-1")) {
            return;
        }

        String email = readNonEmpty(sc, "Email: ");
        if (email.equals("-1")) {
            return;
        }

        String hireDate = readDate(sc, "Hire Date (YYYY-MM-DD): ");
        if(hireDate.equals("-1")) {
            return;
        }

        double salary = readDouble(sc, "Salary: ");
        if(salary == -1) {
            return;
        }


        String ssn = readNum(sc, "SSN: ");
        if(ssn.equals("-1")) {
            return;
        }

        System.out.println("\n--------------- ADDRESS INFORMATION ---------------");


        String street = readNonEmpty(sc, "Street: ");

        System.out.println("\nAvailable Cities:");
        lookup.showCities();
        int cityID = readInt(sc, "City ID: ");
        if(cityID == -1) {
            return;
        }

        //states
        System.out.println("\nAvailable States:");
        lookup.showStates();
        int stateID = readInt(sc, "State ID: ");
        if(stateID == -1) {
            return;
        }

        //zip code
        String zip = readNum(sc, "ZIP Code: ");
        if(zip.equals("-1")) {
            return;
        }

        //DOB
        String dob = readDate(sc, "Date of Birth (YYYY-MM-DD): ");
        if(dob.equals("-1")) {
            return;
        }

        //phone number
        String phone = readNum(sc, "Phone Number: ");
        if(phone.equals("-1")) {
            return;
        }

        //emergency contact name
        String emergencyName = readNonEmpty(sc, "Emergency Contact Name: ");
        if(emergencyName.equals("-1")) {
            return;
        }

        //emergency contact phone
        String emergencyPhone = readNum(sc, "Emergency Contact Phone: ");
        if(emergencyPhone.equals("-1")) {
            return;
        }

        
        System.out.println("\n--------------- WORK ASSIGNMENT ---------------");

        
        System.out.println("\nDivisions: (enter id#)");
        lookup.showDivisions();
        int divID = readInt(sc, "Division ID: ");
        if(divID == -1) {
            return;
        }

        System.out.println("\nJob Titles: (enter id#)");
        lookup.showJobTitles();
        int jobID = readInt(sc, "Job Title ID: ");
        if(jobID == -1) {
            return;
        }

        //show confirmation screen before submitting to database
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("\n==================================================");
        System.out.println("            CONFIRMING EMPLOYEE CREATION");
        System.out.println("==================================================");

        System.out.println("Name  : " + fname + " " + lname);
        System.out.println("Email : " + email);
        System.out.println("Salary: " + salary);

        System.out.println("==================================================");

        // IMPORTANT: check class name consistency here
        addingEmployee service = new addingEmployee();

        service.addEmployee(
            fname, lname, email, hireDate, salary, ssn,
            street, cityID, stateID, zip, dob,
            phone, emergencyName, emergencyPhone,
            divID, jobID
        );
        System.out.println("==================================================\n");
        
        
    }
    
}