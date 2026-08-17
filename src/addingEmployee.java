import java.sql.CallableStatement;
import java.sql.Connection;

public class addingEmployee {
    //a call procedure to add an employee to the database, with all the necessary information, including address and emergency contact information
    public void addEmployee(
            String fname,
            String lname,
            String email,
            String hireDate,
            double salary,
            String ssn,

            String street,
            int cityID,
            int stateID,
            String zip,
            String dob,
            String phone,
            String emergencyName,
            String emergencyPhone,

            int divID,
            int jobTitleID
    ) {

        String sql = "{CALL add_employee(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

        try (Connection conn = DBconnect.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, fname);
            stmt.setString(2, lname);
            stmt.setString(3, email);
            stmt.setString(4, hireDate);
            stmt.setDouble(5, salary);
            stmt.setString(6, ssn);

            stmt.setString(7, street);
            stmt.setInt(8, cityID);
            stmt.setInt(9, stateID);
            stmt.setString(10, zip);
            stmt.setString(11, dob);
            stmt.setString(12, phone);
            stmt.setString(13, emergencyName);
            stmt.setString(14, emergencyPhone);

            stmt.setInt(15, divID);
            stmt.setInt(16, jobTitleID);

            stmt.execute();

            System.out.println("Employee successfully submitted to database.");

        } catch (Exception e) {
            System.out.println("Error inserting employee: " + e.getMessage());
        }
    }
}