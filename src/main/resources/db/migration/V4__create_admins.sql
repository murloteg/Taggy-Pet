INSERT INTO users (user_id, email, phone_number, first_name, active, date_of_creation,
                   permission_to_show_email, permission_to_show_phone_number, password)
VALUES ('1', 'admin1@taggypet.help', 'Hidden', 'Admin_1', true, '2023-08-01',
        false, false, '$2a$10$eGZVyydEnvL/GZALoDqNTe6fAUivPbR6XsnR95R7o.zszoeEgFccq');

INSERT INTO user_roles (user_id, roles) VALUES ('1', 'ROLE_ADMIN');

INSERT INTO users (user_id, email, phone_number, first_name, active, date_of_creation,
                   permission_to_show_email, permission_to_show_phone_number, password)
VALUES ('2', 'admin2@taggypet.help', 'Hidden', 'Admin_2', true, '2023-08-01',
        false, false, '$2a$10$.hKAKBeczQpateCOeHjimOKPwK1xR9xH3RjxJEw7DFtR/.qE2IPQa');

INSERT INTO user_roles (user_id, roles) VALUES ('2', 'ROLE_ADMIN');