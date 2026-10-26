
CREATE DATABASE IF NOT EXISTS careerpilotai;

USE careerpilotai;

CREATE TABLE IF NOT EXISTS learning_topics (
    id INT NOT NULL AUTO_INCREMENT,
    topic_name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    status VARCHAR(20) DEFAULT 'Not Started',
    progress_percentage INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
