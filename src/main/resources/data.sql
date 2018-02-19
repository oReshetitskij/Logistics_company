INSERT INTO person(person_id,  user_name, password, email)
VALUES (1, 'Bohdan', '12121212', 'bohdan.zsnkevich@ukr.net');
INSERT INTO person(person_id,  user_name, password, email)
VALUES (2, 'Bohdan1', '12121212', 'bohdan.zsnkevich@ukr.net2');
INSERT INTO person(person_id,  user_name, password, email)
VALUES (3, 'Bohdan2', '12121212', 'bohdan.zsnkevich@ukr.net3');
INSERT INTO role(role_id, role_name, is_employee_role) VALUES (1, 'ROLE_ADMIN', TRUE);
INSERT INTO role(role_id, role_name) VALUES (2, 'ROLE_USER', FALSE);
INSERT INTO role(role_id, role_name) VALUES (3, 'ROLE_UNCONFIRMED', FALSE);
INSERT INTO role(role_id, role_name) VALUES (4, 'ROLE_MANAGER', TRUE);
INSERT INTO person_role(person_id, role_id) VALUES (1,1);
INSERT INTO person_role(person_id, role_id) VALUES (2,3);

INSERT INTO person(person_id,  user_name, password, email)
VALUES (4, 'stanis', '$2a$10$d5LXfYNl7n7DxjL8Ci42lOBiPiSd50400IjWU0AGuUYO8TeF/14de', 'stanis.stanis@ukr.net');
INSERT INTO person(person_id,  user_name, password, email)
VALUES (5, 'stanis1', '$2a$10$x8wKe1tpGVJTE4zkHRbDj.OXXblGefWRjjdWw82e5s.m3OjHXGgM6', 'stanis1.stanis1@ukr.net');
INSERT INTO person_role(person_id, role_id) VALUES (4,2);
INSERT INTO person_role(person_id, role_id) VALUES (5,1);
INSERT INTO person_role(person_id, role_id) VALUES (5, 4);
