INSERT INTO person(person_id,  user_name, password, email)
VALUES (1, 'Bohdan', '12121212', 'bohdan.zsnkevich@ukr.net');
INSERT INTO person(person_id,  user_name, password, email)
VALUES (2, 'Bohdan1', '12121212', 'bohdan.zsnkevich@ukr.net2');
INSERT INTO person(person_id,  user_name, password, email)
VALUES (3, 'Bohdan2', '12121212', 'bohdan.zsnkevich@ukr.net3');
INSERT INTO role(role_id, role_name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO role(role_id, role_name) VALUES (2, 'ROLE_USER');
INSERT INTO role(role_id, role_name) VALUES (3, 'ROLE_UNCONFIRMED');
INSERT INTO person_role(person_id, role_id) VALUES (1,1);
INSERT INTO person_role(person_id, role_id) VALUES (2,3);