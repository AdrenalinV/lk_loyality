--CREATE Table demo (id INT,name Varchar(40));
--INSERT INTO demo VALUES(1,'testName');
DROP TABLE IF EXISTS cities CASCADE;
CREATE TABLE cities (
                    id bigserial PRIMARY KEY,
                    title VARCHAR(255) NOT NULL
);
INSERT INTO cities (id, title) VALUES
   (1, 'Москва'),
   (2, 'Санкт-Петербург');

DROP TABLE IF EXISTS statuses CASCADE;
CREATE TABLE statuses (
                      id bigserial PRIMARY KEY,
                      title VARCHAR(255) NOT NULL
);
INSERT INTO statuses (id, title) VALUES
     (1, 'Золото'),
     (2, 'Серебро'),
     (3, 'Бронза');

DROP TABLE IF EXISTS cards CASCADE;
CREATE TABLE cards (
                   id bigserial PRIMARY KEY,
                   cardnumber numeric(7) NOT NULL,
                   qrcode VARCHAR(13) NOT NULL,
                   used BOOLEAN
);
INSERT INTO cards (id, cardnumber, qrcode, used) VALUES
     (1, 1000001, '9999100000014', TRUE),
     (2, 1000002, '9999100000021', TRUE),
     (3, 1000003, '9999100000038', TRUE),
     (4, 1000004, '9999100000045', FALSE),
     (5, 1000005, '9999100000042', FALSE),
     (6, 1000006, '9999100000069', FALSE),
     (7, 1000007, '9999100000076', FALSE),
     (8, 1000008, '9999100000083', FALSE),
     (9, 1000009, '9999100000090', FALSE),
     (10, 1000010, '9999100000106', FALSE);

DROP TABLE IF EXISTS bonuses CASCADE;
CREATE TABLE bonuses (
                     id bigserial PRIMARY KEY,
                     card_id bigint REFERENCES cards(id),
                     activebonus FLOAT
);
INSERT INTO bonuses (card_id, activebonus) VALUES
   (1, 25.5),
   (2, 5.8),
   (3, 172.7);

DROP TABLE IF EXISTS counters CASCADE;
CREATE TABLE counters (
                      id bigserial PRIMARY KEY,
                      card_id bigint REFERENCES cards(id),
                      delta FLOAT NOT NULL,
                      delta_date_time timestamp NOT NULL,
                      active_date DATE
);
INSERT INTO counters (card_id, delta, delta_date_time, active_date) VALUES
    (1, 10, '2021-11-01 10:05:06', '2021-11-11'),
    (2, 15, '2021-11-02 11:05:06', '2021-11-12'),
    (3, 20, '2021-11-03 12:05:06', '2021-11-13'),
    (1, 1, '2021-11-12 13:05:06', '2021-11-22'),
    (2, 3, '2021-11-12 14:05:06', '2021-11-22'),
    (2, -15, '2021-11-12 14:05:06', '2021-11-22'),
    (3, 40, '2021-11-06 15:05:06', '2021-11-16'),
    (1, 5.5, '2021-11-07 16:05:06', '2021-11-17'),
    (2, 10, '2021-12-09 10:00:33', '2021-12-19'),
    (3, 20, '2021-12-09 12:15:06', '2021-12-19'),
    (1, 4.5, '2021-12-15 11:05:06', '2021-12-25'),
    (3, 20.9, '2021-12-15 15:11:30', '2021-12-25'),
    (3, 40.1, '2021-12-16 19:15:06', '2021-12-26');
INSERT INTO counters (card_id, delta, delta_date_time) VALUES
    (1, -5, '2021-12-16 20:05:00');
INSERT INTO counters (card_id, delta, delta_date_time, active_date) VALUES
    (1, 0.5, '2021-12-16 20:05:00', '2021-12-26'),
    (3, 15.5, '2021-12-17 10:10:00', '2021-12-27');
INSERT INTO counters (card_id, delta, delta_date_time) VALUES
    (3, -50, '2021-12-17 10:10:00'),
    (2, -10, '2021-12-19 20:05:06');
INSERT INTO counters (card_id, delta, delta_date_time, active_date) VALUES
    (2, 2.8, '2021-12-19 20:05:06', '2021-12-29'),
    (3, 30, '2021-12-25 10:10:00', '2021-12-04'),
    (2, 5.9, '2022-01-02 19:22:00', '2022-01-12'),
    (1, 8, '2022-01-02 11:10:00', '2022-01-12'),
    (1, 11, '2022-01-05 12:30:00', '2022-01-15'),
    (3, 15, '2022-01-04 19:25:06', '2022-03-14'),
    (3, 21.2, '2022-01-05 10:44:00', '2022-03-15'),
    (2, 0.9, '2022-01-12 18:05:06', '2022-03-22');
INSERT INTO counters (card_id, delta, delta_date_time) VALUES
    (2, -5.9, '2022-01-12 18:05:06');
INSERT INTO counters (card_id, delta, delta_date_time, active_date) VALUES
    (2, 2, '2022-01-13 13:01:03', '2022-03-23'),
    (2, 9, '2022-01-14 16:07:16', '2022-03-24'),
    (1, 18, '2022-01-15 11:05:06', '2022-03-25'),
    (3, 25, '2022-01-20 19:55:06', '2022-03-30'),
    (3, 14.3, '2022-01-21 09:09:09', '2022-03-31');

DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users (
                   id bigserial PRIMARY KEY,
                   email VARCHAR(255) UNIQUE NOT NULL,
                   password VARCHAR(255),
                   card_id bigint REFERENCES cards(id),
                   name VARCHAR(255),
                   phone VARCHAR(12),
                   sex VARCHAR(7),
                   birthday DATE,
                   city_id bigint REFERENCES cities(id),
                   status_id bigint REFERENCES statuses(id)
);
INSERT INTO users (email, password, card_id, name, phone, sex, birthday, city_id, status_id) VALUES
    ('1@1.ru', '{bcrypt}$2y$12$mFUdPh8.ESnhu.eyDjxrYuSigUIOboDP94mt7vuNhf604Yw0iuKQa', 1, 'Иванов Иван', '+79600001122', 'мужской', '1985-05-09', 2, 2),
    ('3@3.ru', '{bcrypt}$2y$12$mFUdPh8.ESnhu.eyDjxrYuSigUIOboDP94mt7vuNhf604Yw0iuKQa', 3, 'Катина Екатерина', '+79990004455', 'женский', '1995-03-08', 1, 1);
INSERT INTO users (email, password, card_id, status_id) VALUES
    ('2@2.ru', '{bcrypt}$2y$12$mFUdPh8.ESnhu.eyDjxrYuSigUIOboDP94mt7vuNhf604Yw0iuKQa', 2, 3);