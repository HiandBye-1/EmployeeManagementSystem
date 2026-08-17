//an input menu to search for new hires using a date range then 
//and print out the list of new hires in that time period
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Date;


public class newHireSearch {
    public static void main(String[] args) {
        //empty main method, the actual menu will be called from adminReportMenu
    }

public static void run(Scanner in){
    
    while (true) {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("===============================================================");
        System.out.println("                    NEW HIRE REPORT MENU");
        System.out.println("===============================================================");

        try(Connection conn = DBconnect.getConnection()){
            String rangeSql = "SELECT MIN(HireDate) AS oldest, MAX(HireDate) AS latest FROM employees";
            PreparedStatement psRange = conn.prepareStatement(rangeSql);
            ResultSet rangeRs = psRange.executeQuery();

            if(rangeRs.next()){
                SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");

                System.out.println("Available Data Range:");
                System.out.println("Oldest Hire : " + sdf.format(rangeRs.getDate("oldest")));
                System.out.println("Latest Hire : " + sdf.format(rangeRs.getDate("latest")));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching data range: " + e.getMessage());
        } catch(ClassNotFoundException e){
            System.out.println("Error loading database driver: " + e.getMessage());
        }

        System.out.println("---------------------------------------------------------------");
        System.out.println("Enter date range using format: MM-yyyy (Example: 01-2023)");
        System.out.println("Type 0 at any time to return to menu");
        System.out.println("---------------------------------------------------------------");

        System.out.print("Start Date : ");
        String startDate = in.nextLine().trim();
        if(startDate.equals("0")) return;

        System.out.print("End Date   : ");
        String endDate = in.nextLine().trim();
        System.out.println("(Leave blank for single month)");
        
        if(endDate.equals("0")) return;

        boolean success = checkValidDates(startDate, endDate, in);

        if(success) return;
    }
}

    //function to check if the input dates are valid and in the correct format, if not, return to the menu to retype the dates
    public static boolean checkValidDates(String startDate, String endDate, Scanner in) {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        try {
            //replace all non-numeric characters with a dash to ensure the date is in the correct format
            startDate = startDate.replaceAll("[^0-9]", "-");
            if(endDate.isEmpty()) endDate = startDate; // if end date is left blank, set it to the same as the start date to generate report for that month only
            else endDate = endDate.replaceAll("[^0-9]", "-");
            
            SimpleDateFormat inputFormat = new SimpleDateFormat("MM-yyyy");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date start = inputFormat.parse(startDate);
            Date end = inputFormat.parse(endDate);

            Calendar cal = Calendar.getInstance();
    
            //set the start date to the first day of the month to ensure the report includes all payments made in that month
            cal.setTime(start);
            cal.set(Calendar.DAY_OF_MONTH,1);
            String sqlStart = outputFormat.format(cal.getTime());

            //set the end date to the last day of the month to ensure the report includes all payments made in that month
            cal.setTime(end);
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            String sqlEnd = outputFormat.format(cal.getTime());

            if (start.after(end)) {

                System.out.println("\n===============================================================");
                System.out.println("                        DATE RANGE ERROR");
                System.out.println("===============================================================");

                System.out.println("  Start date must be earlier than end date.");
                System.out.println();
                System.out.println("---------------------------------------------------------------");
                System.out.print("Press ENTER to re-enter dates...");

                in.nextLine();
                return false;

            } else {//if the dates are valid, call the method to generate the report based on the job title id or name and the start and end date

                newHirePrintAll report = new newHirePrintAll();
                report.newHirePrint(sqlStart, sqlEnd, in);
                return false;

            }
        } catch (Exception e) {
            System.out.println("\n===============================================================");
            System.out.println("                       INVALID DATE INPUT");
            System.out.println("===============================================================");

            System.out.println("   The date format you entered is not valid.");
            System.out.println();
            System.out.println("   Correct format : MM-YYYY");
            System.out.println("   Example        : 03-2025");
            System.out.println();
            System.out.println("---------------------------------------------------------------");
            System.out.print("Press ENTER to re-enter dates...");
            in.nextLine(); // Wait for user input before returning to the menu
            return false; // Return to the menu
        }

    }
}

