import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class lookUpService {
    //display a list of cities, states, divisions, and job titles for the user to choose from when adding an employee
    public void showCities() {
        //list of cites with their IDs for the user to choose from when adding an employee
        String sql = "SELECT cityID, city_name FROM cities";

        try (Connection conn = DBconnect.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getInt("cityID") + " - " + rs.getString("city_name"));
            }

        } catch (Exception e) {
            System.out.println("Error loading cities: " + e.getMessage());
        }
    }

    public void showStates() {
        //list of states with their IDs for the user to choose from when adding an employee
        String sql = "SELECT stateID, state_code FROM states";

        try (Connection conn = DBconnect.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getInt("stateID") + " - " + rs.getString("state_code"));
            }

        } catch (Exception e) {
            System.out.println("Error loading states: " + e.getMessage());
        }
    }

    public void showDivisions() {
        //list of divisions with their IDs for the user to choose from when adding an employee
        String sql = "SELECT ID, Name FROM division";

        try (Connection conn = DBconnect.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getInt("ID") + " - " + rs.getString("Name"));
            }

        } catch (Exception e) {
            System.out.println("Error loading divisions: " + e.getMessage());
        }
    }

    public void showJobTitles() {
        //list of job titles with their IDs for the user to choose from when adding an employee
        String sql = "SELECT job_title_id, job_title FROM job_titles";

        try (Connection conn = DBconnect.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println(rs.getInt("job_title_id") + " - " + rs.getString("job_title"));
            }

        } catch (Exception e) {
            System.out.println("Error loading job titles: " + e.getMessage());
        }
    }
}