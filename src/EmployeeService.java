import java.sql.*;
import java.util.Scanner;

public class EmployeeService{

    Scanner scanner = new Scanner(System.in);

    // =========================================
    // SECURE ACCESS (LOGIN BEFORE VIEWING DATA)
    // =========================================
    public void secureEmployeeAccess() {

        System.out.print("Enter Employee ID: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid ID.");
            scanner.nextLine();
            return;
        }
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
        String dob = scanner.nextLine();

        System.out.print("Enter SSN: ");
        String ssn = scanner.nextLine();

        try (Connection conn = DBconnect.getConnection()) {

            String query =
                "SELECT e.Fname, e.Lname, e.email, e.Salary, " +
                "a.street, c.city_name, s.state_code " +
                "FROM employees e " +
                "JOIN addresses a ON e.empid = a.empid " +  
                "JOIN cities c ON a.cityID = c.cityID " +
                "JOIN states s ON a.stateID = s.stateID " +
                "WHERE e.empid = ? AND a.DOB = ? AND e.SSN = ?";

            try (PreparedStatement ps = conn.prepareStatement(query)) {

                ps.setInt(1, id);
                Date sqlDate;

                try {
                    sqlDate = Date.valueOf(dob);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid date format. Use YYYY-MM-DD.");
                    return;
                }

                ps.setDate(2, sqlDate);
                
                ps.setString(3, ssn);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    System.out.println("\n===== ACCESS GRANTED =====");
                    System.out.println("Name: " + rs.getString("Fname") + " " + rs.getString("Lname"));
                    System.out.println("Email: " + rs.getString("email"));
                    System.out.printf("Salary: $%.2f\n", rs.getDouble("Salary"));
                    System.out.println("Address: " + rs.getString("street"));
                    System.out.println("City: " + rs.getString("city_name"));
                    System.out.println("State: " + rs.getString("state_code"));
                    System.out.println("=========================");
                    System.out.println("Press enter to continue.");
                    scanner.nextLine();
                    System.out.print("\033[H\033[2J");
                    System.out.flush();

                } else {
                    System.out.println("\nACCESS DENIED: Incorrect credentials.");
                    System.out.println("Press enter to continue.");
                    scanner.nextLine();
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                }

                rs.close();
            }

        } catch (Exception e) {
            System.out.println("Database error occurred.");
        }
    }

    // =========================================
    // PAY HISTORY
    // =========================================
    public void viewPayHistory() {

        System.out.print("Enter Employee ID: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid ID.");
            scanner.nextLine();
            return;
        }

        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
        String dob = scanner.nextLine();

        System.out.print("Enter SSN: ");
        String ssn = scanner.nextLine();



        try (Connection conn = DBconnect.getConnection()) {

        String query =
            "SELECT p.pay_date, p.earnings, p.fed_tax, p.state_tax " +
            "FROM payroll p " +
            "JOIN employees e ON p.empid = e.empid " +
            "JOIN addresses a ON e.empid = a.empid " +
            "WHERE e.empid = ? AND a.DOB = ? AND e.SSN = ?";

            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setInt(1, id);

                Date sqlDate;
                //make sure date enter right
                try {
                    sqlDate = Date.valueOf(dob);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid date format. Use YYYY-MM-DD.");
                    return;
                }

                ps.setDate(2, sqlDate);
                
                ps.setString(3, ssn);

                ResultSet rs = ps.executeQuery();

                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println("\n===== PAY HISTORY =====");
                //check credential correct
                boolean found = false;
    
                if (rs.next()) {
                    do{
                        found = true;
                        System.out.println("Date: " + rs.getDate("pay_date"));
                        System.out.printf("Earnings: $%.2f\n", rs.getDouble("earnings"));
                        System.out.printf("Federal Tax: $%.2f\n", rs.getDouble("fed_tax"));
                        System.out.printf("State Tax: $%.2f\n", rs.getDouble("state_tax"));
                        System.out.println("----------------------");
                    }while (rs.next());

                } else {
                    System.out.println("\nIncorrect credentials/No pay history found.");
                }
                System.out.println("=======================");
                System.out.println("Press enter to continue.");
                scanner.nextLine();

                System.out.print("\033[H\033[2J");
                System.out.flush();
                rs.close();
            }

        } catch (Exception e) {
            System.out.println("Database error occurred.");
        }
    }
}