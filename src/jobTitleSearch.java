    //an input menu to search for job title or using job title id, then pass the job title id to the next menu to ask for time period 
    //and print out the report of total amount paid to that job title in that time period
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.SQLException;
    import java.sql.ResultSet;
    import java.util.Scanner;

    public class jobTitleSearch {
        public static void main(String[] args) {
            //empty main method, the actual menu will be called from adminReportMenu
        }

        public static void jobTitleMenu(Scanner in){
            
            String input = "";
            
            while(!input.equals("0")) {
                //clear previous images
                System.out.print("\033[H\033[2J");
                System.out.flush();
                //graphics
                try (Connection conn = DBconnect.getConnection()) {
                    displayJobTitles(conn);
                } catch (SQLException e) {
                    System.out.println("SQL Error: " + e.getMessage());
                } catch (ClassNotFoundException e) {
                    System.out.println("Class Not Found Error: " + e.getMessage());
                }

                System.out.println("Enter the Job Title ID or it Name (0 to go back): ");
                input = in.nextLine();

                //return to the previous menu
                if (input.equals("0")){
                    break;
                }

                else{
                    //clear previous images
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                }


                try(Connection conn = DBconnect.getConnection()){

                    String sqlcmd;
                    PreparedStatement ps;
                    int id = -1;

                    //input in the job_title_ID
                    try{
                        id = Integer.parseInt(input);
                        sqlcmd = "SELECT * FROM job_titles WHERE job_title_id = ?";
                        ps = conn.prepareStatement(sqlcmd);
                        ps.setInt(1, id);
    
                    //input in the job_title name
                    }catch (NumberFormatException e){
                        sqlcmd = "SELECT * FROM job_titles WHERE job_title = ?";
                        ps = conn.prepareStatement(sqlcmd);
                        ps.setString(1, input);
                        }
                        
                    
                    try(ResultSet rs = ps.executeQuery()){
                        if(rs.next()){
                            //division found by given id
                            if (id != -1){
                                jobTitleWithinRange report = new jobTitleWithinRange(id);
                                report.run(in);

                            }else{
                                jobTitleWithinRange report = new jobTitleWithinRange(input);
                                report.run(in);

                            }

                
                    } else { // if no division is found with the given id or name, print out a message
                        if(id != -1){
                            System.out.println("!!! No job title found with the given id: " + id+ " !!!");
                            System.out.println("Please press ENTER to continue...");
                            in.nextLine();
                        } else {

                            System.out.println("!!! No job title found with the given name: " + input+" !!!");
                            System.out.println("Please press ENTER to continue...");
                            in.nextLine();
                        }
                    }
                }
                
                } catch (SQLException e) { // handle SQL exceptions
                    System.out.println("SQL Error: " + e.getMessage());
                } catch (ClassNotFoundException e) { // handle ClassNotFound exceptions
                    System.out.println("Class Not Found Error: " + e.getMessage());
                }
            
            }
        }

        public static void displayJobTitles(Connection conn) throws SQLException {
            String sql = "SELECT job_title_id, job_title FROM job_titles ORDER BY job_title_id";

            
            try(PreparedStatement ps= conn.prepareStatement(sql); 
                ResultSet rs = ps.executeQuery()){

                System.out.println("===== Job Title List =====");
                System.out.printf("%-5s | %-20s\n", "ID", "Name");
                System.out.println("-----------------------------");

            while (rs.next()) {
                System.out.printf("%-5d | %-20s\n",
                        rs.getInt("job_title_id"),
                        rs.getString("job_title"));
            }

            System.out.println("-----------------------------");
            }
       }
    }

