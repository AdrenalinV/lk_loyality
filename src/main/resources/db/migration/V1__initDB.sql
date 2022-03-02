
DROP TABLE IF EXISTS cities CASCADE;
CREATE TABLE cities (
                    id bigserial NOT NULL,
                    title VARCHAR(255) UNIQUE NOT NULL,
                    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS cards CASCADE;
CREATE TABLE cards (
                   id bigserial NOT NULL,
                   cardnumber numeric(7) NOT NULL,
                   qrcode VARCHAR(13) NOT NULL,
                   activebonus FLOAT DEFAULT(0.0),
                   used BOOLEAN,
                   PRIMARY KEY (id)
);

DROP TABLE IF EXISTS counters CASCADE;
CREATE TABLE counters (
                      id bigserial NOT NULL,
                      card_id bigint NOT Null,
                      delta FLOAT NOT NULL,
                      delta_date_time timestamp NOT NULL,
                      active_date DATE,
                      PRIMARY KEY (id),
                      FOREIGN KEY (card_id) REFERENCES cards(id)
);

DROP TABLE IF EXISTS users CASCADE;
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
                   status bigint DEFAULT 1,
                   PRIMARY KEY (id),
                   FOREIGN KEY (city_id) REFERENCES cities(id),
                   FOREIGN KEY (card_id) REFERENCES cards(id)
                   );