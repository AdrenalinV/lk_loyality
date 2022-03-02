DROP TABLE IF EXISTS documents CASCADE;
CREATE TABLE documents (
    id bigserial PRIMARY KEY NOT NULL,
    number NUMERIC(9) NOT NULL,
    date_time TIMESTAMP NOT NULL,
    sum FLOAT NOT NULL,
    card_id bigserial NOT NULL,
    FOREIGN KEY (card_id) REFERENCES cards (id)
);


DROP TABLE counters CASCADE;
CREATE TABLE counters (
    id bigserial PRIMARY KEY,
    card_id bigint REFERENCES cards(id),
    delta FLOAT NOT NULL,
    delta_date_time timestamp NOT NULL,
    active_date DATE,
    document_id bigserial NOT NULL,
    FOREIGN KEY (document_id) REFERENCES documents (id)
);
