# CareerPilotAI 🎯

A Java and MySQL-based placement preparation tracker designed to help students organize and monitor their technical learning progress.

## Features

- View learning topics
- Update topic progress
- Track overall learning progress
- View a learning dashboard
- Store learning topics in a MySQL database

## Technologies Used

- Java (JDK 21)
- MySQL
- JDBC
- Eclipse IDE
- Git and GitHub

## Project Structure

```text
CareerPilotAI/
├── src/
│   ├── Main.java
│   ├── DatabaseTest.java
│   ├── connection/
│   │   └── DBConnection.java
│   └── dao/
│       └── LearningTopicDAO.java
├── .gitignore
├── .classpath
├── .project
└── README.md
```

## Database Setup

1. Install MySQL.
2. Create a database named `careerpilotai`.
3. Create and populate the required `learning_topics` table.
4. Configure the `CAREERPILOT_DB_PASSWORD` environment variable with your MySQL password.
5. Run the Java application from Eclipse.

## How to Run

1. Clone or download this repository.
2. Open the project in Eclipse.
3. Configure the JDK and MySQL connection.
4. Ensure the database and table are configured.
5. Run `Main.java`.

## Purpose

This project is being developed as a learning and portfolio project to improve Java programming, database connectivity, and software development skills.

## Author

Santhosh