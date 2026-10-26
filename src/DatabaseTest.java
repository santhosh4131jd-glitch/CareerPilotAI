
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseTest {

    public static void main(String[] args) {

        String url =
            "jdbc:mysql://localhost:3306/careerpilotai";

        String username = "root";

        // Replace this with your actual MySQL password.
        String password = System.getenv("CAREERPILOT_DB_PASSWORD");

        String sql = "SELECT id, topic_name, category, status, "
                   + "progress_percentage FROM learning_topics";

        try (
            Connection connection =
                DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql)
        ) {
            System.out.println("Connected to CareerPilotAI database!");
            System.out.println("--------------------------------------");

            while (resultSet.next()) {
                System.out.println(
                    resultSet.getInt("id") + " | "
                    + resultSet.getString("topic_name") + " | "
                    + resultSet.getString("category") + " | "
                    + resultSet.getString("status") + " | "
                    + resultSet.getInt("progress_percentage") + "%"
                );
            }

        } catch (Exception e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
    }
}
