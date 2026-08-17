import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    private String currentEmail;
    private Role currentRole;
    private int currentEmployeeId;
    private String currentFirstName;
    private String currentLastName;
    private boolean isLoggedIn;

    public Login() {
        this.isLoggedIn = false;
    }
    
    public boolean authenticate(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Email is required.");
            return false;
        }

        if (password == null || password.trim().isEmpty()) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Password is required.");
            return false;
        }
        
        String sql = "SELECT empid, Fname, Lname, email, Salary FROM employees WHERE email = ?";
        
        try (Connection conn = DBconnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            System.out.print("\033[H\033[2J");
            System.out.flush(); 
            if (rs.next()) {
                int empID = rs.getInt("empid");
                String firstName = rs.getString("Fname");
                String lastName = rs.getString("Lname");
                String dbEmail = rs.getString("email");
                boolean passwordValid = verifyPassword(password, empID);  
                
                if (passwordValid) {
                    Role role = determineRole(empID);

                    currentEmail = dbEmail;
                    currentRole = role;
                    currentEmployeeId = empID;
                    currentFirstName = firstName;
                    currentLastName = lastName;
                    isLoggedIn = true;
                    System.out.println("\n==================================================");
                    System.out.println("Login successful! Welcome, " + firstName + " " + lastName + " (" + role + ")");
                    System.out.println("==================================================\n");
                    System.out.println("Employee ID: " + empID);
                    System.out.println("Email: " + dbEmail);
                    System.out.println("Role: " + role);
                    System.out.println("==================================================\n");
                    return true;
                }else{
                    System.out.println("Invalid password. Please try again.");
                    return false;
                }
            }else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println("No user found with the provided email." + email);
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Database error: " + e.getMessage());
            return false;
        }
    }

    private boolean verifyPassword(String password, int empID) {
        String expectedPassword;
        if(empID <= 3) {
            expectedPassword = "admin" + empID;
        } else {
            expectedPassword = "emp" + empID;
        }
        return password.equals(expectedPassword);
    }
    private Role determineRole(int empID) {
        if(empID <= 3) {
            return Role.HR_ADMIN;
        } else {
            return Role.EMPLOYEE;
        }
    }

    public void logout() {
        if (isLoggedIn) {
            System.out.println("\nGoodbye, " + currentFirstName + " " + currentLastName + "!\n");
            currentEmail = null;
            currentRole = null;
            currentEmployeeId = 0;
            currentFirstName = null;
            currentLastName = null;
            isLoggedIn = false;
        }
    }
    public String getCurrentEmail() {return currentEmail; }
    public Role getCurrentRole() { return currentRole; }
    public int getCurrentEmployeeId() { return currentEmployeeId; }
    public String getCurrentFirstName() { return currentFirstName; }
    public String getCurrentLastName() { return currentLastName; }
    public String getCurrentFullName() { return currentFirstName + " " + currentLastName; }
    public boolean isLoggedIn() { return isLoggedIn; }
    public boolean isHRAdmin() { return isLoggedIn && currentRole == Role.HR_ADMIN; }
    public boolean isEmployee() { return isLoggedIn && currentRole == Role.EMPLOYEE; }
    public void showLoginScreen() {

        System.out.println("\n==================================================");
        System.out.println("     EMPLOYEE MANAGEMENT SYSTEM");
        System.out.println("==================================================");
        System.out.println("             LOGIN SCREEN");
        System.out.println("--------------------------------------------------");
        System.out.println("   Enter your company email to login");
        System.out.println("--------------------------------------------------\n");
    }
}