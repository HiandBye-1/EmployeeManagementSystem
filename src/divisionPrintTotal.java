import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;


//This class is used to print out the total amount paid to a specific division in a specific 
//time period based on the division id or division name, start date and end date provided by the user in the previous menu
public class divisionPrintTotal{
    private int id = -1;
    private String name = null;
    
   
    public divisionPrintTotal(int id){
        this.id = id;
    }
    public divisionPrintTotal(String name){
        this.name = name;
    }


    public static void main(String[] args){
        //just here for fun
    }

    
    //print out report based on division name, start date and end date
    public void divisionPrint(String startDate, String endDate){
        //print our report of money paid to a specific division in a specific time period
        boolean found = false;
        //graphics
        System.out.println("\033[H\033[2J");
        System.out.flush();


        System.out.println("==============================================");
        System.out.println("          DIVISION PAYMENT REPORT            ");
        System.out.println("==============================================");

        if(id != -1){
            System.out.println("Filter Type  : Division ID");
            System.out.println("Division ID  : " + id);
        } else {
            System.out.println("Filter Type  : Division Name");
            System.out.println("Division Name: " + name);
        }

        System.out.println("Date Range   : " + startDate + "  ->  " + endDate);
        System.out.println("==============================================\n");

        ///////
        String sql;
        try(Connection conn = DBconnect.getConnection()){
        PreparedStatement ps;
        if(id == -1){
            sql = "SELECT d.Name AS division_name,\r\n" + // 
                        "SUM(p.earnings) AS total_pay \r\n" + //
                        "FROM payroll p\r\n" + //
                        "JOIN employees e ON p.empid = e.empid\r\n" + //
                        "JOIN employee_division ed ON e.empid = ed.empid\r\n" + //
                        "JOIN division d ON ed.div_ID = d.ID\r\n" + //
                        "WHERE LOWER(d.Name) = LOWER(?)"
                        + " AND p.pay_date >= ?"
                        + " AND p.pay_date <= ?"                          
                        + " GROUP BY d.Name;";

            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, startDate);
            ps.setString(3, endDate);
            }else{
                sql = "SELECT d.Name AS division_name,\r\n" + // 
                        "SUM(p.earnings) AS total_pay \r\n" + //
                        "FROM payroll p\r\n" + //
                        "JOIN employees e ON p.empid = e.empid\r\n" + //
                        "JOIN employee_division ed ON e.empid = ed.empid\r\n" + //
                        "JOIN division d ON ed.div_ID = d.ID\r\n" + //
                        "WHERE d.ID = ?"
                        + " AND p.pay_date >= ?"
                        + " AND p.pay_date <= ?"
                        + " GROUP BY d.Name;";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ps.setString(2, startDate);
                ps.setString(3, endDate);
            }

            try(ResultSet rs = ps.executeQuery()){
                System.out.println("------------------ RESULTS --------------------");

                while(rs.next()){
                    //print out the report in a readable format
                    found = true;
                    System.out.println("Division Name: " + rs.getString("division_name"));
                    System.out.println("Total Pay    : $" + rs.getDouble("total_pay"));
                }
                if(found == false){
                    if (id != -1){
                        System.out.println("No records found for Division ID: " + id);
                    } else {
                        System.out.println("No records found for Division Name: " + name);
                    }
                }
                System.out.println("-----------------------------------------------\n");
            }

        
    
            }catch (SQLException e) {
                System.out.println("Error connecting to the database: " + e.getMessage());
            }catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }


