DROP TABLE IF EXISTS Series;
DROP TABLE IF EXISTS Events;
DROP TABLE IF EXISTS dbUsers;
DROP TABLE IF EXISTS Tags;
DROP TABLE IF EXISTS Associate;
DROP TABLE IF EXISTS Has;

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

CREATE TABLE dbUsers(
                      id_user INT AUTO_INCREMENT PRIMARY KEY,
                      username VARCHAR(50) NOT NULL,
                      password VARCHAR(250) NOT NULL,
                      role VARCHAR(50) NOT NULL
);

CREATE TABLE Tags(
                     id_tag INT AUTO_INCREMENT,
                     label VARCHAR(50),
                     PRIMARY KEY(id_tag)
);

CREATE TABLE Associate(
                          id_event INT,
                          id_tag INT,
                          PRIMARY KEY(id_event, id_tag),
                          FOREIGN KEY(id_event) REFERENCES Events(id_event),
                          FOREIGN KEY(id_tag) REFERENCES Tags(id_tag)
);

CREATE TABLE Has(
                    id_serie INT,
                    id_user INT,
                    PRIMARY KEY(id_serie, id_user),
                    FOREIGN KEY(id_serie) REFERENCES Series(id_serie),
                    FOREIGN KEY(id_user) REFERENCES dbUsers(id_user)
);

INSERT INTO Series (title, description)
VALUES ('TimeSeries 1', 'Description of the TimeSeries 1'),
       ('TimeSeries 2', 'Description of the TimeSeries 2');

INSERT INTO Events (event_date, value_event, comment, id_serie)
VALUES ('2023-10-30', 20, 'Event 1 for the timeSeries 1', 1),
       ('2023-10-31', 10, 'Event 2 for the timeSeries 1', 1),
       ('2023-11-1', 100, 'Event 3 for the timeSeries2', 2);

INSERT INTO dbUsers (username, password, role)
VALUES  ('dbuser','$2y$10$.qkbukzzX21D.bqbI.B2R.tvWP90o/Y16QRWVLodw51BHft7ZWbc.','USER'),
        ('dbadmin','$2y$10$kp1V7UYDEWn17WSK16UcmOnFd1mPFVF6UkLrOOCGtf24HOYt8p1iC','ADMIN');

INSERT INTO Tags (label)
VALUES ('Tag 1'),
       ('Tag 2');

INSERT INTO Associate (id_event, id_tag)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (3, 2);


INSERT INTO Has (id_serie, id_user)
VALUES (1, 1),
       (1, 2),
       (2, 2);



