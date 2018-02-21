INSERT INTO contact(contact_id, first_name, last_name, phone_number) VALUES (1, 'Bohdan', 'Zinkevich', '111-11-11');
INSERT INTO contact(contact_id, first_name, last_name, phone_number) VALUES (2, 'Bohdan', 'Zinkevich', '222-22-22');
INSERT INTO contact(contact_id, first_name, last_name, phone_number) VALUES (3, 'Bohdan', 'Zinkevich', '333-33-33');
INSERT INTO contact(contact_id, first_name, last_name, phone_number) VALUES (4, 'stanis', 'stanis', '444-44-44');
INSERT INTO contact(contact_id, first_name, last_name, phone_number) VALUES (5, 'stanis', 'stanis', '444-44-44');

INSERT INTO person(person_id, user_name, password, email, contact_id, manager_id)
VALUES (1, 'Bohdan', '12121212', 'bohdan.zsnkevich@ukr.net', 1, 1);
INSERT INTO person(person_id,  user_name, password, email, contact_id , manager_id )
VALUES (2, 'Bohdan1', '12121212', 'bohdan.zsnkevich@ukr.net2', 2, 2);
INSERT INTO person(person_id,  user_name, password, email, contact_id , manager_id )
VALUES (3, 'Bohdan2', '12121212', 'bohdan.zsnkevich@ukr.net3', 3, 3);

INSERT INTO role(role_id, role_name, is_employee_role) VALUES (1, 'ROLE_ADMIN', TRUE);
INSERT INTO role(role_id, role_name, is_employee_role) VALUES (2, 'ROLE_USER', FALSE);
INSERT INTO role(role_id, role_name, is_employee_role) VALUES (3, 'ROLE_UNCONFIRMED', FALSE);
INSERT INTO role(role_id, role_name, is_employee_role) VALUES (4, 'ROLE_MANAGER', TRUE);

INSERT INTO person_role(person_id, role_id) VALUES (1,1);
INSERT INTO person_role(person_id, role_id) VALUES (2,3);

INSERT INTO person(person_id,  user_name, password, email, contact_id, manager_id)
VALUES (4, 'stanis', '$2a$10$d5LXfYNl7n7DxjL8Ci42lOBiPiSd50400IjWU0AGuUYO8TeF/14de', 'stanis.stanis@ukr.net', 4,3);
INSERT INTO person(person_id,  user_name, password, email, contact_id, manager_id)
VALUES (5, 'stanis1', '$2a$10$x8wKe1tpGVJTE4zkHRbDj.OXXblGefWRjjdWw82e5s.m3OjHXGgM6', 'stanis1.stanis1@ukr.net', 5,4);

INSERT INTO person_role(person_id, role_id) VALUES (4,2);
INSERT INTO person_role(person_id, role_id) VALUES (5,1);

INSERT INTO person_role(person_id, role_id) VALUES (5, 4);

INSERT INTO advertisement_type(type_advertisement_id, advertisement_name) VALUES (1, 'Advertisement');
INSERT INTO advertisement_type(type_advertisement_id, advertisement_name) VALUES (2, 'Announcement');
INSERT INTO advertisement_type(type_advertisement_id, advertisement_name) VALUES (3, 'Other');