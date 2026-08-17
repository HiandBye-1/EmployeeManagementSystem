import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminService {
    ///////////////change
    
    public Employee findEmployee(int empId) {
        String sql = "SELECT * FROM employees WHERE empid = ?";

        try (Connection con = DBconnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    String fname = rs.getString("fname");
                    String lname = rs.getString("lname");
                    double salary = rs.getDouble("salary");

                    return new Employee(empId, fname, lname, salary);
                }
            }

        } catch (SQLException|ClassNotFoundException e) {
            e.printStackTrace();
        } 

        return null;
    }

    public boolean deleteEmployee(int empId) {

    String deletePayroll = "DELETE FROM payroll WHERE empid = ?";
    String deleteAddress = "DELETE FROM addresses WHERE empid = ?";
    String deleteJob = "DELETE FROM employee_job_titles WHERE empid = ?";
    String deleteDivision = "DELETE FROM employee_division WHERE empid = ?";
    String deleteEmployee = "DELETE FROM employees WHERE empid = ?";

    try (Connection con = DBconnect.getConnection()) {

        con.setAutoCommit(false); 

        try (
            PreparedStatement ps1 = con.prepareStatement(deletePayroll);
            PreparedStatement ps2 = con.prepareStatement(deleteAddress);
            PreparedStatement ps3 = con.prepareStatement(deleteJob);
            PreparedStatement ps4 = con.prepareStatement(deleteDivision);
            PreparedStatement ps5 = con.prepareStatement(deleteEmployee)
        ) {

            ps1.setInt(1, empId);
            ps1.executeUpdate();

            ps2.setInt(1, empId);
            ps2.executeUpdate();

            ps3.setInt(1, empId);
            ps3.executeUpdate();

            ps4.setInt(1, empId);
            ps4.executeUpdate();

            ps5.setInt(1, empId);
            ps5.executeUpdate();

            con.commit();
            return true;

        } catch (SQLException e) {
            con.rollback(); 
            e.printStackTrace();
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
}


    public void updateSalary(double percent, double maxSalary) {
        String sql = "UPDATE employees SET salary = salary + (salary * ? / 100) WHERE salary <= ?";

        try (Connection con = DBconnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, percent);
            ps.setDouble(2, maxSalary);

            ps.executeUpdate();

        } catch (SQLException|ClassNotFoundException e) {
            e.printStackTrace();
        } 
    } 
}