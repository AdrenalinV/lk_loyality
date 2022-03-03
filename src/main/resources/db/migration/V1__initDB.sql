CREATE TABLE cities (
    id bigserial NOT NULL,
    title VARCHAR(255) UNIQUE NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE cards (
    id bigserial NOT NULL,
    cardnumber numeric(7) NOT NULL,
    qrcode VARCHAR(13) NOT NULL,
    activebonus FLOAT DEFAULT(0.0),
    used BOOLEAN,
    PRIMARY KEY (id)
    );

CREATE TABLE users (
    id bigserial NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255),
    card_id bigint NOT NULL,
    name VARCHAR(255),
    phone VARCHAR(12),
    sex VARCHAR(7),
    birthday DATE,
    city_id bigint not null,
    status bigint DEFAULT 0,
    PRIMARY KEY (id),
    FOREIGN KEY (city_id) REFERENCES cities(id),
    FOREIGN KEY (card_id) REFERENCES cards(id)
    );

create TABLE roles (
    id bigserial PRIMARY KEY NOT NULL,
    name varchar(50) NOT NULL
    );

create TABLE users_roles(
    user_id bigserial NOT NULL,
    role_id bigserial NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
    );

CREATE TABLE documents (
    id bigserial PRIMARY KEY NOT NULL,
    number NUMERIC(9) NOT NULL,
    date_time TIMESTAMP NOT NULL,
    sum FLOAT NOT NULL,
    card_id bigserial NOT NULL,
    FOREIGN KEY (card_id) REFERENCES cards (id)
    );

CREATE TABLE counters (
    id bigserial PRIMARY KEY,
    card_id bigint REFERENCES cards(id),
    delta FLOAT NOT NULL,
    delta_date_time timestamp NOT NULL,
    active_date DATE,
    document_id bigserial NOT NULL,
    FOREIGN KEY (document_id) REFERENCES documents (id)
    );