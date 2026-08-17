    //an input menu to search for divisions by divison_id or division_name
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.SQLException;
    import java.sql.ResultSet;
    import java.util.Scanner;

    public class divisionSearch {
        public static void main(String[] args) {
            //empty main method, the actual menu will be called from adminReportMenu
        }


        public static void divisionMenu(Scanner in){
            
            String input = "";
            
            while(!input.equals("0")) {
                //clear previous images
                System.out.print("\033[H\033[2J");
                System.out.flush();
                //graphics
                try (Connection conn = DBconnect.getConnection()) {
                    displayDivisions(conn);
                } catch (SQLException e) {
                    System.out.println("SQL Error: " + e.getMessage());
                } catch (ClassNotFoundException e) {
                    System.out.println("Class Not Found Error: " + e.getMessage());
                }

                System.out.println("Enter Division ID or Name (0 to go back): ");
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

                    //if the input is an integer, search for division by ID
                    try{
                        id = Integer.parseInt(input);
                        sqlcmd = "SELECT * FROM division WHERE ID = ?";
                        ps = conn.prepareStatement(sqlcmd);
                        ps.setInt(1, id);
    
                    //if the input is a string, search for division by Name   
                    }catch (NumberFormatException e){
                        sqlcmd = "SELECT * FROM division WHERE Name = ?";
                        ps = conn.prepareStatement(sqlcmd);
                        ps.setString(1, input);
                        }
                        
                    
                    try(ResultSet rs = ps.executeQuery()){
                        if(rs.next()){
                            //division found by given id
                            if (id != -1){
                                divisionWithinRange report = new divisionWithinRange(id);
                                report.run(in);

                            }else{
                                divisionWithinRange report = new divisionWithinRange(input);
                                report.run(in);

                            }

                
                    } else { // if no division is found with the given id or name, print out a message
                        if(id != -1){
                            System.out.println("!!! No division found with the given id: " + id+ " !!!");
                            System.out.println("Please press ENTER to continue...");
                            in.nextLine();
                        } else {

                            System.out.println("!!! No division found with the given name: " + input+" !!!");
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

        public static void displayDivisions(Connection conn) throws SQLException {
            String sql = "SELECT ID, Name FROM division ORDER BY ID";

            
            try(PreparedStatement ps= conn.prepareStatement(sql); 
                ResultSet rs = ps.executeQuery()){

                System.out.println("===== Division List =====");
                System.out.printf("%-5s | %-20s\n", "ID", "Name");
                System.out.println("-----------------------------");

            while (rs.next()) {
                System.out.printf("%-5d | %-20s\n",
                        rs.getInt("ID"),
                        rs.getString("Name"));
            }

            System.out.println("-----------------------------");
            }
       }
    }

