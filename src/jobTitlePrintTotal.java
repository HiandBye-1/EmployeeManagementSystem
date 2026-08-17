import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Connection;


//This class is used to print out the total amount paid to a specific job title in a specific 
//time period based on the job title id or job title name, start date and end date provided by the user in the previous menu
public class jobTitlePrintTotal{
    private int id = -1;
    private String name = null;
    
   
    public jobTitlePrintTotal(int id){
        this.id = id;
    }
    public jobTitlePrintTotal(String name){
        this.name = name;
    }


    public static void main(String[] args){
        //just here for fun
    }

    
    //print out report based on job title name, start date and end date
    public void jobTitlePrint(String startDate, String endDate){
        //print our report of money paid to a specific job title in a specific time period
        boolean found = false;
        //graphics
        System.out.println("\033[H\033[2J");
        System.out.flush();


        System.out.println("==============================================");
        System.out.println("          JOB TITLE PAYMENT REPORT            ");
        System.out.println("==============================================");

        if(id != -1){
            System.out.println("Filter Type  : Job Title ID");
            System.out.println("Job Title ID  : " + id);
        } else {
            System.out.println("Filter Type  : Job Title Name");
            System.out.println("Job Title Name: " + name);
        }

        System.out.println("Date Range   : " + startDate + "  ->  " + endDate);
        System.out.println("==============================================\n");

        ///////
        String sql;
        try(Connection conn = DBconnect.getConnection()){
        PreparedStatement ps;
        if(id == -1){
        sql = "SELECT jt.job_title, " +
            "SUM(p.earnings) AS total_pay " +
            "FROM payroll p " +
            "JOIN employee_job_titles ejt ON p.empid = ejt.empid " +
            "JOIN job_titles jt ON ejt.job_title_id = jt.job_title_id " +
            "WHERE LOWER(jt.job_title) = LOWER(?) " +
            "AND p.pay_date BETWEEN ? AND ? " +
            "GROUP BY jt.job_title;";

            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, startDate);
            ps.setString(3, endDate);
            }else{
                sql = "SELECT jt.job_title, " +
                        "SUM(p.earnings) AS total_pay " +
                        "FROM payroll p " +
                        "JOIN employee_job_titles ejt ON p.empid = ejt.empid " +
                        "JOIN job_titles jt ON ejt.job_title_id = jt.job_title_id " +
                        "WHERE jt.job_title_id = ? " +
                        "AND p.pay_date BETWEEN ? AND ? " +
                        "GROUP BY jt.job_title;";

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
                    System.out.println("Job Title:  " + rs.getString("job_title"));
                    System.out.println("Total Pay:  $" + rs.getDouble("total_pay"));
                }
                if(found == false){
                    if (id != -1){
                        System.out.println("No records found for Job Title ID: " + id);
                    } else {
                        System.out.println("No records found for Job Title Name: " + name);
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


