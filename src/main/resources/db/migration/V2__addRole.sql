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

insert into roles (id, name)
values
(1, 'ROLE_USER'), (2 ,'ROLE_ADMIN');