
//serve as a menu to show the report result to admin
//display division, jobTitle, newHire salary report
//admin will then choose the report and either divisionSearch 
//or jobTitleSearch will be called to display the report result
import java.util.Scanner;
public class adminReportMenu{
    private static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        //empty main method, the actual menu will be called from another class
    }

    public static void adminReport(){
        String input = "";

        boolean notReturn = true;
        while(notReturn){
            //clear previous images
            System.out.print("\033[H\033[2J");
            System.out.flush();

            //graphics
            System.out.println("===============================================================");
            System.out.println("                      ADMIN REPORT MENU");
            System.out.println("===============================================================");
            System.out.println("1) Division Report        (Salary)");
            System.out.println("2) Job Title Report       (Salary)");
            System.out.println("3) New Hire Report        (Hire Date)");
            System.out.println("0) Exit");
            System.out.println("---------------------------------------------------------------");
            System.out.print("Enter choice (1-3 or 0): ");
            input = in.nextLine();



            //if input is an integer:
            try{
                int choice = Integer.parseInt(input);
                switch(choice){
                    case 1:
                        divisionSearch.divisionMenu(in);
                        break;

                    case 2:
                        jobTitleSearch.jobTitleMenu(in);
                        break;

                    case 3:
                        newHireSearch.run(in);
                        break;

                    case 0:
                        notReturn = false;
                        break;

                    default:
                        //clear image
                        System.out.print("\033[H\033[2J");
                        System.out.flush();

                        System.out.println("---------------------------------------------------------------");
                        System.out.println("Invalid input. Please select a valid option.");
                        System.out.println("---------------------------------------------------------------");
                        System.out.print("Press ENTER to continue...");
                        in.nextLine();
                        break;
                }

            //if input is a string:
            }catch(NumberFormatException e){
                
                if(input.toLowerCase().equals("division report")){
                    divisionSearch.divisionMenu(in);

                } else if(input.toLowerCase().equals("job title report")){
                    jobTitleSearch.jobTitleMenu(in);
                    
                }
                else if(input.toLowerCase().equals("new hire report")){
                    newHireSearch.run(in);

                }
                else if(input.toLowerCase().equals("exit")){
                    notReturn = false;

                } else {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();

                    System.out.println("---------------------------------------------------------------");
                    System.out.println("Invalid input. Please select a valid option.");
                    System.out.println("---------------------------------------------------------------");
                    System.out.print("Press ENTER to continue...");
                    in.nextLine();
                }
            }


        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Exiting admin report menu...");

    }

    
    
}