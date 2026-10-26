
package dao;

import connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LearningTopicDAO {

    // Display all learning topics
    public void displayAllTopics() {

        String sql = "SELECT id, topic_name, category, status, "
                   + "progress_percentage FROM learning_topics";

        try (
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)
        ) {

            System.out.println("\n=== CareerPilotAI Learning Topics ===");

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String topic = resultSet.getString("topic_name");
                String category = resultSet.getString("category");
                String status = resultSet.getString("status");
                int progress = resultSet.getInt("progress_percentage");

                System.out.println(
                    id + " | " + topic + " | " + category
                    + " | " + status + " | " + progress + "%"
                );
            }

        } catch (SQLException e) {
            System.out.println("Unable to retrieve learning topics.");
            e.printStackTrace();
        }
    }

    // Update progress for a topic
    public void updateTopicProgress(
            int topicId, String status, int progress) {

        // Validate progress range
        if (progress < 0 || progress > 100) {
            System.out.println(
                "Progress must be between 0 and 100."
            );
            return;
        }

        // Validate status
        if (status == null ||
            !(status.equalsIgnoreCase("NotStarted") ||
              status.equalsIgnoreCase("InProgress") ||
              status.equalsIgnoreCase("Completed"))) {

            System.out.println(
                "Invalid status! Use NotStarted, InProgress, or Completed."
            );
            return;
        }

        // Validate status and progress combination
        if (status.equalsIgnoreCase("Completed") && progress != 100) {
            System.out.println(
                "Completed topics must have 100% progress."
            );
            return;
        }

        if (status.equalsIgnoreCase("NotStarted") && progress != 0) {
            System.out.println(
                "NotStarted topics must have 0% progress."
            );
            return;
        }

        if (status.equalsIgnoreCase("InProgress") &&
            (progress <= 0 || progress >= 100)) {

            System.out.println(
                "InProgress topics must have 1-99% progress."
            );
            return;
        }

        // SQL query to update topic progress
        String sql = "UPDATE learning_topics "
                   + "SET status = ?, progress_percentage = ? "
                   + "WHERE id = ?";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setString(1, status);
            statement.setInt(2, progress);
            statement.setInt(3, topicId);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println(
                    "Topic progress updated successfully!"
                );
            } else {
                System.out.println(
                    "No topic found with ID " + topicId
                );
            }

        } catch (SQLException e) {
            System.out.println("Unable to update topic progress.");
            e.printStackTrace();
        }
    }

    // Display overall learning progress
    public void displayOverallProgress() {

        String sql =
            "SELECT COUNT(*) AS total_topics, "
          + "SUM(CASE WHEN status = 'Completed' THEN 1 ELSE 0 END) "
          + "AS completed_topics, "
          + "COALESCE(AVG(progress_percentage), 0) "
          + "AS overall_progress "
          + "FROM learning_topics";

        try (
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)
        ) {

            if (resultSet.next()) {

                int totalTopics =
                    resultSet.getInt("total_topics");

                int completedTopics =
                    resultSet.getInt("completed_topics");

                double overallProgress =
                    resultSet.getDouble("overall_progress");

                System.out.println(
                    "\n===== Overall Learning Progress ====="
                );

                System.out.println(
                    "Total topics: " + totalTopics
                );

                System.out.println(
                    "Completed topics: " + completedTopics
                );

                System.out.printf(
                    "Overall progress: %.2f%%%n",
                    overallProgress
                );
            }

        } catch (SQLException e) {
            System.out.println(
                "Unable to calculate overall progress."
            );
            e.printStackTrace();
        }
    }

    // Display learning status summary
    public void displayLearningSummary() {

        String sql =
            "SELECT " +
            "COUNT(*) AS total_topics, " +
            "SUM(CASE WHEN status = 'Completed' THEN 1 ELSE 0 END) AS completed, " +
            "SUM(CASE WHEN status = 'InProgress' THEN 1 ELSE 0 END) AS in_progress, " +
            "SUM(CASE WHEN status = 'NotStarted' THEN 1 ELSE 0 END) AS not_started " +
            "FROM learning_topics";

        try (
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)
        ) {

            if (resultSet.next()) {

                System.out.println("\n===== Learning Dashboard =====");
                System.out.println("Total topics: " +
                    resultSet.getInt("total_topics"));
                System.out.println("Completed: " +
                    resultSet.getInt("completed"));
                System.out.println("In Progress: " +
                    resultSet.getInt("in_progress"));
                System.out.println("Not Started: " +
                    resultSet.getInt("not_started"));
            }

        } catch (SQLException e) {
            System.out.println("Unable to display learning summary.");
            e.printStackTrace();
        }
    }

}
