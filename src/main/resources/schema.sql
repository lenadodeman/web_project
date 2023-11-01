DROP TABLE IF EXISTS TimeSeries;
DROP TABLE IF EXISTS Events;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Tags;
DROP TABLE IF EXISTS Associate;
DROP TABLE IF EXISTS Has;

CREATE TABLE TimeSeries(
                           id_timeSeries INT AUTO_INCREMENT,
                           title VARCHAR(50),
                           description VARCHAR(50),
                           PRIMARY KEY(id_timeSeries)
);

CREATE TABLE Events(
                       id_event INT AUTO_INCREMENT,
                       eventDate DATE,
                       valueEvent INT,
                       comment VARCHAR(50),
                       id_timeSeries INT NOT NULL,
                       PRIMARY KEY(id_event),
                       FOREIGN KEY(id_timeSeries) REFERENCES TimeSeries(id_timeSeries)
);

CREATE TABLE Users(
                      id_user INT AUTO_INCREMENT,
                      Login VARCHAR(50),
                      first_name VARCHAR(50),
                      last_name VARCHAR(50),
                      PRIMARY KEY(id_user)
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
                    id_timeSeries INT,
                    id_user INT,
                    PRIMARY KEY(id_timeSeries, id_user),
                    FOREIGN KEY(id_timeSeries) REFERENCES TimeSeries(id_timeSeries),
                    FOREIGN KEY(id_user) REFERENCES Users(id_user)
);



