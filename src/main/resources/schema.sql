DROP TABLE IF EXISTS Series;
DROP TABLE IF EXISTS Events;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Tags;


CREATE TABLE Series(
                           id_serie INT AUTO_INCREMENT,
                           title VARCHAR(50),
                           description VARCHAR(50),
                           PRIMARY KEY(id_serie)
);

CREATE TABLE Events(
                       id_event INT AUTO_INCREMENT,
                       event_date DATE,
                       value_event INT,
                       comment VARCHAR(50),
                       id_serie INT NOT NULL,
                       PRIMARY KEY(id_event),
                       FOREIGN KEY(id_serie) REFERENCES Series(id_serie)
);

CREATE TABLE Users(
                      id_user INT AUTO_INCREMENT,
                      username VARCHAR(250),
                      password VARCHAR(250),
                      role VARCHAR(50),
                      PRIMARY KEY(id_user)
);

CREATE TABLE Tags(
                     id_tag INT AUTO_INCREMENT,
                     label VARCHAR(50),
                     PRIMARY KEY(id_tag)
);

CREATE TABLE tags_events(
                          id_event INT,
                          id_tag INT,
                          PRIMARY KEY(id_event, id_tag),
                          FOREIGN KEY(id_event) REFERENCES Events(id_event),
                          FOREIGN KEY(id_tag) REFERENCES Tags(id_tag)
);


INSERT INTO Series (title, description)
VALUES ('TimeSeries 1', 'Description of the TimeSeries 1'),
       ('TimeSeries 2', 'Description of the TimeSeries 2');

INSERT INTO Events (event_date, value_event, comment, id_serie)
VALUES ('2023-10-30', 20, 'Event 1 for the timeSeries 1', 1),
       ('2023-10-31', 10, 'Event 2 for the timeSeries 1', 1),
       ('2023-11-1', 100, 'Event 3 for the timeSeries2', 2);

INSERT INTO Users (username, password, role)
VALUES ('user', '$2y$10$.qkbukzzX21D.bqbI.B2R.tvWP90o/Y16QRWVLodw51BHft7ZWbc.', 'USER'),
       ('admin', '$2y$10$kp1V7UYDEWn17WSK16UcmOnFd1mPFVF6UkLrOOCGtf24HOYt8p1iC', 'ADMIN');

INSERT INTO Tags (label)
VALUES ('Tag 1'),
       ('Tag 2');









