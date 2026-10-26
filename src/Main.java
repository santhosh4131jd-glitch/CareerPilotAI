
import java.util.Scanner;
import dao.LearningTopicDAO;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        LearningTopicDAO dao = new LearningTopicDAO();

        int choice = 0;

        do {
            System.out.println("\n===== CareerPilotAI =====");
            System.out.println("1. View all learning topics");
            System.out.println("2. Update topic progress");
            System.out.println("3. View overall progress");
            System.out.println("4. View learning dashboard");
            System.out.println("5. Add a learning topic");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a number.");
                scanner.next();
                System.out.print("Enter your choice: ");
            }

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {

                case 1:
                    dao.displayAllTopics();
                    break;

                case 2:
                    System.out.print("Enter topic ID: ");

                    while (!scanner.hasNextInt()) {
                        System.out.println("Please enter a valid topic ID.");
                        scanner.next();
                        System.out.print("Enter topic ID: ");
                    }

                    int topicId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print(
                        "Enter status (Not Started, In Progress, Completed): "
                    );
                    String status = scanner.nextLine().trim();

                    System.out.print("Enter progress percentage (0-100): ");

                    while (!scanner.hasNextInt()) {
                        System.out.println("Please enter a number from 0 to 100.");
                        scanner.next();
                        System.out.print("Enter progress percentage (0-100): ");
                    }

                    int progress = scanner.nextInt();
                    scanner.nextLine();

                    dao.updateTopicProgress(topicId, status, progress);
                    break;

                case 3:
                    dao.displayOverallProgress();
                    break;

                case 4:
                    dao.displayLearningSummary();
                    break;

                case 5:
                    System.out.print("Enter topic name: ");
                    String topicName = scanner.nextLine().trim();

                    System.out.print("Enter category: ");
                    String category = scanner.nextLine().trim();

                    dao.addLearningTopic(topicName, category);
                    break;

                case 6:
                    System.out.println("Thank you for using CareerPilotAI!");
                    break;

                default:
                    System.out.println(
                        "Invalid choice. Please select 1 to 6."
                    );
            }

        } while (choice != 6);

        scanner.close();
    }
}
