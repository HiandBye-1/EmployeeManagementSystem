//this class will take in the start date and end date of the report
//and check if the input is valid before moving on to the next step of generating the report
import java.util.*;
import java.text.SimpleDateFormat; 


public class divisionWithinRange {

    private int id = -1;
    private String name ="";

    public divisionWithinRange(int id){
        this.id = id;
    }

    public divisionWithinRange(String name){
        this.name = name;
    }


    public static void main(String[] args){//menu to ask for time period(division id or name already taken care of in divisionSearch.java)

    }
    //menu to ask for time period(division id or name already taken care of in divisionSearch.java)
public void run(Scanner in){

    while (true) {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("===== Division Report Menu =====");

        if(id != -1){
            System.out.println("Selected division ID: " + id);
        } else {
            System.out.println("Selected division Name: " + name);
        }

        System.out.println("\nFormat: MM-yyyy (ex: 01-2023)");

        System.out.println("Start Date (or 0 to go back): ");
        String startDate = in.nextLine();
        if(startDate.equals("0")) return;
        System.out.println();
        
        System.out.println("End Date (or 0 to go back): ");
        System.out.println("(Leave blank to generate report for the month of the start date only)");
        String endDate = in.nextLine();
        if(endDate.equals("0")) return;

        boolean success = checkValidDates(startDate, endDate, in);

        if(success) return; // only exit if report was generated
    }
}

    //function to check if the input dates are valid and in the correct format, if not, return to the menu to retype the dates
    public boolean checkValidDates(String startDate, String endDate, Scanner in) {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        try {
            //replace all non-numeric characters with a dash to ensure the date is in the correct format
            startDate = startDate.replaceAll("[^0-9]", "-");
            endDate = endDate.replaceAll("[^0-9]", "-");
            
            //if end date is left blank, set it to the same as the start date to generate report for that month only
            if(endDate.isEmpty()) endDate = startDate;
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
                System.out.println("Start date must be before end date.");
                System.out.println("Press ENTER to retype the dates.");
                in.nextLine(); // Wait for user input before returning to the menu
                return false; // Return to the menu
            
            } else {//if the dates are valid, call the method to generate the report based on the division id or name and the start and end date

                if (id != -1){
                    divisionPrintTotal report = new divisionPrintTotal(id);
                    report.divisionPrint(sqlStart, sqlEnd);

                }
                else{
                    divisionPrintTotal report = new divisionPrintTotal(name);
                    report.divisionPrint(sqlStart, sqlEnd);
                }
                System.out.println("Press ENTER to go back to the menu...");
                in.nextLine();
                return true;

            }
        } catch (Exception e) {
            System.out.println(" Invalid date format.");
            System.out.println(" Please use MM-YYYY (Example: 03-2025)");
            System.out.println("Press ENTER to retype the dates.");
            in.nextLine(); // Wait for user input before returning to the menu
            return false; // Return to the menu
        }

    }



}

