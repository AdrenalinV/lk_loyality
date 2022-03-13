-- добавляем город
INSERT INTO cities (title)
VALUES
('Нижний Новгород'),
('Москва');

-- добавляем роли
INSERT INTO roles (name)
VALUES
('ROLE_USER'),
('ROLE_ADMIN');

-- добавляем карту
INSERT INTO cards (cardnumber, qrcode, activebonus, used)
VALUES
(1000001, 'qwerty', 0.0, true),
(1000002, 'qwerty', 0.0, true),
(1000003, 'qwerty', 0.0, true);

-- добаляем пользователей
INSERT INTO users (email, password, card_id, name, phone, sex, city_id, status)
VALUES
('user@mail.ru','$2a$10$ycn39AY7DyIWkoqvGsVGIuDIVTlvrijaJB7jbBUrxFEj3gCgL8cby', 1, 'user', '9999999999', 'men',1, 0),
('admin@mail.ru','$2a$10$ycn39AY7DyIWkoqvGsVGIuDIVTlvrijaJB7jbBUrxFEj3gCgL8cby', 2, 'admin', '9999999999', 'men',2, 0);

-- добавить роли пользователям
INSERT INTO users_roles (user_id, role_id)
VALUES
(1,1),
(2,2);

-- добавить документы
INSERT INTO documents (number, date_time, sum, card_id)
VALUES
(100000001,'03-11-2022 12:02:22', 1234.5, 1),
(100000002,'03-01-2022 12:02:32', 12.5, 2),
(100000003,'03-17-2022 12:02:42', 2347.5, 1),
(100000004,'03-01-2022 12:02:52', 834.5, 2),
(100000005,'03-21-2022 12:02:55', 5634.5, 1);

-- добавить изменения баланса
INSERT INTO counters (card_id, delta, delta_date_time, active_date, document_id)
VALUES
(1, 123, '03-11-2022 5:32:12', '03-15-2022', 1),
(1, 123.4, '03-17-2022 5:32:12', '03-20-2022', 2),
(1, -50.3, '03-17-2022 5:32:12', '03-17-2022', 3),
(1, 50.3, '03-21-2022 5:32:12', '03-21-2022', 4),
(1, 34.3, '03-21-2022 5:32:12', '04-22-2022', 5);