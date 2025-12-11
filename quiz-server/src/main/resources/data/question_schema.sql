DROP TABLE IF EXISTS questions;

CREATE TABLE questions (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           questionText VARCHAR(255) NOT NULL,
                           answer1Text VARCHAR(255) NOT NULL,
                           answer1Correct BOOLEAN NOT NULL DEFAULT FALSE,
                           answer2Text VARCHAR(255) NOT NULL,
                           answer2Correct BOOLEAN NOT NULL DEFAULT FALSE,
                           answer3Text VARCHAR(255) NOT NULL,
                           answer3Correct BOOLEAN NOT NULL DEFAULT FALSE,
                           answer4Text VARCHAR(255) NOT NULL,
                           answer4Correct BOOLEAN NOT NULL DEFAULT FALSE,
                           isDelete	tinyint  null,
                           createTime   datetime default CURRENT_TIMESTAMP null,
                           updateTime   datetime default CURRENT_TIMESTAMP null
);