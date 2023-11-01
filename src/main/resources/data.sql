INSERT INTO TimeSeries (title, description)
VALUES ('TimeSeries 1', 'Description of the TimeSeries 1'),
       ('TimeSeries 2', 'Description of the TimeSeries 2');

INSERT INTO Events (eventDate, valueEvent, comment, id_timeSeries)
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


INSERT INTO Has (id_timeSeries, id_user)
VALUES (1, 1),
       (1, 2),
       (2, 2);