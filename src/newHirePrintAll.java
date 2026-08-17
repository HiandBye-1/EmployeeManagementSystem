import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.Connection;
import java.util.Date;

//This class is used to print out the total amount paid to a specific division in a specific 
//time period based on the division id or division name, start date and end date provided by the user in the previous menu
public class newHirePrintAll{


    public static void main(String[] args){
        //just here for fun
    }

    
    //print out list of new hires in a specific time period based on the start and end date provided by the user in the previous menu
    public void newHirePrint(String startDate, String endDate, Scanner in){

        //graphics
        System.out.println("\033[H\033[2J");
        System.out.flush();

        System.out.println("===============================================================");
        System.out.println("                        NEW HIRES RESULTS            ");
        System.out.println("===============================================================\n");


        ///////

        try(Connection conn = DBconnect.getConnection()){

        String sql = "Select Fname, Lname, email, HireDate "+
                    "FROM Employees "+
                    "WHERE HireDate BETWEEN ? AND ? "+
                    "ORDER BY HireDate";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, startDate);
        ps.setString(2, endDate);
        ResultSet rs = ps.executeQuery();



        
        //if there are results, print out the report in a readable format, otherwise print 
        //out a message saying no hires were found for that time period
        //print report in ascending order based on the hire date, and include the first name, 
        // last name, email and hire date of each new hire in the report
        
        int count = 0;
        
        while(rs.next()){
            if (count == 0){
                System.out.printf("%-12s %-12s %-25s %-12s%n", "First Name", "Last Name", "Email", "Hire Date");
                System.out.println("---------------------------------------------------------------");
                count++;
            }
            //print out the report in a readable format
            System.out.printf("%-12s %-12s %-25s %-12s%n", 
                  rs.getString("Fname"), 
                  rs.getString("Lname"), 
                  rs.getString("email"), 
                  rs.getDate("HireDate"));
            }
            if(count == 0){

                SimpleDateFormat displayFormat = new SimpleDateFormat("MM-yyyy");

                try {
                    Date s = java.sql.Date.valueOf(startDate);
                    Date e = java.sql.Date.valueOf(endDate);


                    System.out.println("  No employees were hired in the selected period.");
                    System.out.println();
                    System.out.println("  From : " + displayFormat.format(s));
                    System.out.println("  To   : " + displayFormat.format(e));

                } catch (Exception ex) {
                    System.out.println("No employees found in the selected date range.");
                }
            }


        System.out.println("\n===============================================================\n");

        System.out.println("Press ENTER to go back to the menu...");
        in.nextLine();
        return;


        }catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        }
}



