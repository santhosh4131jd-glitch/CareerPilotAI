
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
                   + "progress_percentage FROM learning_topics "
                   + "ORDER BY id";

        try (
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)
        ) {

            System.out.println("\n=== CareerPilotAI Learning Topics ===");

            boolean found = false;

            while (resultSet.next()) {
                found = true;

                System.out.println(
                    resultSet.getInt("id") + " | "
                    + resultSet.getString("topic_name") + " | "
                    + resultSet.getString("category") + " | "
                    + resultSet.getString("status") + " | "
                    + resultSet.getInt("progress_percentage") + "%"
                );
            }

            if (!found) {
                System.out.println("No learning topics found.");
            }

        } catch (SQLException e) {
            System.out.println("Unable to retrieve learning topics.");
            e.printStackTrace();
        }
    }

    // Update progress for a topic
    public void updateTopicProgress(
            int topicId, String status, int progress) {

        // Validate topic ID
        if (topicId <= 0) {
            System.out.println("Topic ID must be a positive number.");
            return;
        }

        // Validate progress range
        if (progress < 0 || progress > 100) {
            System.out.println("Progress must be between 0 and 100.");
            return;
        }

        // Normalize status
        if (status == null) {
            System.out.println(
                "Invalid status. Use Not Started, In Progress, or Completed."
            );
            return;
        }

        String normalizedStatus = status.trim().toLowerCase();

        switch (normalizedStatus) {
            case "not started":
            case "notstarted":
                normalizedStatus = "Not Started";
                break;

            case "in progress":
            case "inprogress":
                normalizedStatus = "In Progress";
                break;

            case "completed":
                normalizedStatus = "Completed";
                break;

            default:
                System.out.println(
                    "Invalid status. Use Not Started, In Progress, or Completed."
                );
                return;
        }

        // Validate status and progress combination
        if (normalizedStatus.equals("Completed") && progress != 100) {
            System.out.println("Completed topics must have 100% progress.");
            return;
        }

        if (normalizedStatus.equals("Not Started") && progress != 0) {
            System.out.println("Not Started topics must have 0% progress.");
            return;
        }

        if (normalizedStatus.equals("In Progress")
                && (progress <= 0 || progress >= 100)) {
            System.out.println(
                "In Progress topics must have progress between 1% and 99%."
            );
            return;
        }

        String sql = "UPDATE learning_topics "
                   + "SET status = ?, progress_percentage = ? "
                   + "WHERE id = ?";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setString(1, normalizedStatus);
            statement.setInt(2, progress);
            statement.setInt(3, topicId);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Topic progress updated successfully!");
            } else {
                System.out.println("No topic found with ID " + topicId);
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
          + "COALESCE(SUM(CASE WHEN status = 'Completed' "
          + "THEN 1 ELSE 0 END), 0) AS completed_topics, "
          + "COALESCE(AVG(progress_percentage), 0) "
          + "AS overall_progress "
          + "FROM learning_topics";

        try (
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)
        ) {

            if (resultSet.next()) {
                System.out.println("\n===== Overall Learning Progress =====");
                System.out.println(
                    "Total topics: " + resultSet.getInt("total_topics")
                );
                System.out.println(
                    "Completed topics: " + resultSet.getInt("completed_topics")
                );
                System.out.printf(
                    "Overall progress: %.2f%%%n",
                    resultSet.getDouble("overall_progress")
                );
            }

        } catch (SQLException e) {
            System.out.println("Unable to calculate overall progress.");
            e.printStackTrace();
        }
    }

    // Display learning status summary
    public void displayLearningSummary() {

        String sql =
            "SELECT COUNT(*) AS total_topics, "
          + "COALESCE(SUM(CASE WHEN status = 'Completed' "
          + "THEN 1 ELSE 0 END), 0) AS completed, "
          + "COALESCE(SUM(CASE WHEN status = 'In Progress' "
          + "THEN 1 ELSE 0 END), 0) AS in_progress, "
          + "COALESCE(SUM(CASE WHEN status = 'Not Started' "
          + "THEN 1 ELSE 0 END), 0) AS not_started "
          + "FROM learning_topics";

        try (
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)
        ) {

            if (resultSet.next()) {
                System.out.println("\n===== Learning Dashboard =====");
                System.out.println(
                    "Total topics: " + resultSet.getInt("total_topics")
                );
                System.out.println(
                    "Completed: " + resultSet.getInt("completed")
                );
                System.out.println(
                    "In Progress: " + resultSet.getInt("in_progress")
                );
                System.out.println(
                    "Not Started: " + resultSet.getInt("not_started")
                );
            }

        } catch (SQLException e) {
            System.out.println("Unable to display learning summary.");
            e.printStackTrace();
        }
    }

    // Add a new learning topic
    public void addLearningTopic(String topicName, String category) {

        if (topicName == null || topicName.trim().isEmpty()) {
            System.out.println("Topic name cannot be empty.");
            return;
        }

        if (category == null || category.trim().isEmpty()) {
            System.out.println("Category cannot be empty.");
            return;
        }

        String sql = "INSERT INTO learning_topics "
                   + "(topic_name, category, status, progress_percentage) "
                   + "VALUES (?, ?, 'Not Started', 0)";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, topicName.trim());
            statement.setString(2, category.trim());

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Learning topic added successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Unable to add learning topic.");
            e.printStackTrace();
        }
    }
}
