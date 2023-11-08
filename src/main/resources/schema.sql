DROP TABLE IF EXISTS Series;
DROP TABLE IF EXISTS Events;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Tags;
DROP TABLE IF EXISTS Associate;
DROP TABLE IF EXISTS Has;

CREATE TABLE Series(
                           id INT AUTO_INCREMENT,
                           title VARCHAR(50),
                           description VARCHAR(50),
                           PRIMARY KEY(id)
);

CREATE TABLE Events(
                       id INT AUTO_INCREMENT,
                       event_date DATE,
                       value_event INT,
                       comment VARCHAR(50),
                       id_series INT NOT NULL,
                       PRIMARY KEY(id),
                       FOREIGN KEY(id_series) REFERENCES Series(id)
);

CREATE TABLE Users(
                      id INT AUTO_INCREMENT,
                      Login VARCHAR(50),
                      first_name VARCHAR(50),
                      last_name VARCHAR(50),
                      PRIMARY KEY(id)
);

CREATE TABLE Tags(
                     id INT AUTO_INCREMENT,
                     label VARCHAR(50),
                     PRIMARY KEY(id)
);

CREATE TABLE Associate(
                          id_event INT,
                          id_tag INT,
                          PRIMARY KEY(id_event, id_tag),
                          FOREIGN KEY(id_event) REFERENCES Events(id),
                          FOREIGN KEY(id_tag) REFERENCES Tags(id)
);

CREATE TABLE Has(
                    id_series INT,
                    id_user INT,
                    PRIMARY KEY(id_series, id_user),
                    FOREIGN KEY(id_series) REFERENCES Series(id),
                    FOREIGN KEY(id_user) REFERENCES Users(id)
);

INSERT INTO Series (title, description)
VALUES ('TimeSeries 1', 'Description of the TimeSeries 1'),
       ('TimeSeries 2', 'Description of the TimeSeries 2');

INSERT INTO Events (event_date, value_event, comment, id_series)
VALUES ('2023-10-30', 20, 'Event for the timeSeries 1', 1),
       ('2023-10-31', 10, 'Event for the timeSeries 1', 1),
       ('2023-11-1', 100, 'Event for the timeSeries2', 2);

INSERT INTO Users (Login, first_name, last_name)
VALUES  ('nochevassu', 'noe', 'chevassus'),
        ('ledodeman', 'lena', 'dodeman');

INSERT INTO Tags (label)
VALUES ('Label1'),
       ('Label2');

INSERT INTO Associate (id_event, id_tag)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (3, 2);


INSERT INTO Has (id_series, id_user)
VALUES (1, 1),
       (1, 2),
       (2, 2);



