CREATE SCHEMA IF NOT EXISTS sports;

CREATE TABLE sports.SPORT (
    sports_id INT AUTO_INCREMENT PRIMARY KEY,
    SPORTS_NAME VARCHAR(25) NOT NULL,
    modified_date datetime
);

CREATE TABLE sports.PLAYER (
    player_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(25) NOT NULL,
    level INT NOT NULL CHECK (level >= 1 AND level <= 10),
    age INT NOT NULL,
    gender ENUM('MALE', 'FEMALE') NOT NULL,
    modified_date datetime
);

CREATE TABLE sports.PLAYER_SPORT (
    player_id INT,
    sports_id INT,
    PRIMARY KEY (player_id, sports_id),
    FOREIGN KEY (player_id) REFERENCES sports.PLAYER(player_id),
    FOREIGN KEY (sports_id) REFERENCES sports.SPORT(sports_id)
);
