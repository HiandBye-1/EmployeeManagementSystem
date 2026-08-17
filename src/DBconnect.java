import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnect {

    private static final String URL =
        "jdbc:mysql://localhost:3306/employeeData?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "HiandBye";   // your MySQL username
    private static final String PASSWORD = "Thanhly2410?";     // your MySQL password

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // Explicitly load the driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Return a connection
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}