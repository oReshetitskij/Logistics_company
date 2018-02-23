DROP TABLE IF EXISTS logistic_company.registration_link;
DROP TABLE IF EXISTS "logistic_company"."work_day";
DROP TABLE IF EXISTS "logistic_company".person_role;
DROP TABLE IF EXISTS "logistic_company"."advertisement";
DROP TABLE IF EXISTS "logistic_company"."advertisement_type";
DROP TABLE IF EXISTS "logistic_company"."order";
DROP TABLE IF EXISTS "logistic_company"."address_contact";
DROP TABLE IF EXISTS "logistic_company"."office";
DROP TABLE IF EXISTS "logistic_company"."person";
DROP TABLE IF EXISTS "logistic_company"."contact";
DROP TABLE IF EXISTS "logistic_company"."address";
DROP TABLE IF EXISTS "logistic_company"."role";
DROP TABLE IF EXISTS "logistic_company"."order_status";


DROP FUNCTION IF EXISTS logistic_company.delete_old_rows();


DROP SEQUENCE IF EXISTS "logistic_company"."main_seq_id";
DROP TYPE IF EXISTS logistic_company.WEEK_DAY;

DROP SCHEMA IF EXISTS "logistic_company";


CREATE SCHEMA "logistic_company";

CREATE TYPE logistic_company.WEEK_DAY AS ENUM ('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday');

CREATE SEQUENCE "logistic_company"."main_seq_id"
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 10;

SELECT setval('"logistic_company"."main_seq_id"', 100, TRUE);

SET SEARCH_PATH TO logistic_company;

CREATE TABLE "logistic_company"."contact" (
  "contact_id"   INT4 DEFAULT nextval('main_seq_id' :: REGCLASS) NOT NULL,
  "first_name"   VARCHAR(45) COLLATE "default"                   NOT NULL,
  "last_name"    VARCHAR(45) COLLATE "default"                   NOT NULL,
  "phone_number" VARCHAR(45) COLLATE "default"                   NOT NULL

);

CREATE TABLE "logistic_company"."person" (
  "person_id"         INT4 DEFAULT nextval('main_seq_id' :: REGCLASS) NOT NULL,
  "user_name"         VARCHAR(45) COLLATE "default",
  "password"          VARCHAR(200) COLLATE "default"                  NOT NULL,
  "registration_date" TIMESTAMP                                       NOT NULL DEFAULT NOW(),
  "manager_id"        INT,
  "email"             VARCHAR(45) COLLATE "default"                        NOT NULL,
  "contact_id"        INT4                                            NOT NULL
);

CREATE TABLE "logistic_company"."role" (
  "role_id"          INT4 DEFAULT nextval('main_seq_id' :: REGCLASS) NOT NULL,
  "role_name"        VARCHAR(45) COLLATE "default"                   NOT NULL,
  "is_employee_role" BOOLEAN                                         NOT NULL,
  "priority"         VARCHAR(45) COLLATE "default"
);

CREATE TABLE "logistic_company"."person_role"
(
  "person_id" INT4,
  "role_id"   INT4
);


CREATE TABLE "logistic_company"."advertisement"
(
  "advertisement_id"      INT4 DEFAULT nextval('main_seq_id' :: REGCLASS)     NOT NULL,
  "description"           VARCHAR(1000) COLLATE "default"                     NOT NULL,
  "publication_date"      TIMESTAMP                                           NOT NULL DEFAULT NOW(),
  "publication_date_end"  Date                                                NOT NULL,
  "type_advertisement_id" INT4                                                NOT NULL
);

CREATE TABLE "logistic_company"."advertisement_type"
(
  "type_advertisement_id" INT4 DEFAULT nextval('main_seq_id' :: REGCLASS)    NOT NULL,
  "advertisement_name"    VARCHAR(60) COLLATE "default"                      NOT NULL
);


CREATE TABLE "logistic_company"."work_day"
(
  "employee_id" INT4     NOT NULL,
  "week_day"    WEEK_DAY NOT NULL,
  "begin_time"  TIME     NOT NULL,
  "end_time"    TIME     NOT NULL
);

CREATE TABLE "logistic_company"."order"
(
  "order_id"          INT4 DEFAULT nextval('main_seq_id' :: REGCLASS)    NOT NULL,
  "creation_date"     TIMESTAMP                                          NOT NULL DEFAULT NOW(),
  "delivery_time"     TIME                                               NOT NULL,
  "order_status_time" TIMESTAMP                                          NOT NULL DEFAULT NOW(),
  "courier_id"        INT4                                               NOT NULL,
  "reseiver_id"       INT4                                               NOT NULL,
  "sender_id"         INT4                                               NOT NULL,
  "office_id"         INT4                                               NOT NULL,
  "order_status_id"   INT4
);

CREATE TABLE "logistic_company"."office"
(
  "office_id"    INT4 DEFAULT nextval('main_seq_id' :: REGCLASS)    NOT NULL,
  "name"         VARCHAR(60) COLLATE "default"                      NOT NULL,
  "address_id"   INT4
);

CREATE TABLE "logistic_company"."order_status"
(
  "order_status_id" INT4 DEFAULT nextval('main_seq_id' :: REGCLASS)   NOT NULL,
  "status_name"     VARCHAR(60) COLLATE "default"                     NOT NULL
);


CREATE TABLE "logistic_company"."address_contact"
( "address_contact_id"    INT4 DEFAULT nextval('main_seq_id' :: REGCLASS) NOT NULL,
  "contact_id"            INT4,
  "address_id"            INT4
);

CREATE TABLE "logistic_company"."address"
(
  "address_id"   INT4 DEFAULT nextval('main_seq_id' :: REGCLASS) NOT NULL,
  "address_name"  VARCHAR(150) COLLATE "default"                  NOT NULL
);

CREATE TABLE logistic_company.registration_link
(
  registration_link_id UUID NOT NULL,
  person_id            INT4 NOT NULL
);


ALTER TABLE "logistic_company"."person"
  ADD UNIQUE ("email");
ALTER TABLE "logistic_company"."person"
  ADD UNIQUE ("contact_id");
ALTER TABLE logistic_company.registration_link
  ADD UNIQUE (person_id);


ALTER TABLE "logistic_company"."person"
  ADD PRIMARY KEY ("person_id");
ALTER TABLE "logistic_company"."role"
  ADD PRIMARY KEY ("role_id");
ALTER TABLE "logistic_company"."advertisement"
  ADD PRIMARY KEY ("advertisement_id");
ALTER TABLE "logistic_company"."advertisement_type"
  ADD PRIMARY KEY ("type_advertisement_id");
ALTER TABLE "logistic_company"."office"
  ADD PRIMARY KEY ("office_id");
ALTER TABLE "logistic_company"."order_status"
  ADD PRIMARY KEY ("order_status_id");
ALTER TABLE "logistic_company"."contact"
  ADD PRIMARY KEY ("contact_id");
ALTER TABLE "logistic_company"."address"
  ADD PRIMARY KEY (address_id);
ALTER TABLE logistic_company.registration_link
  ADD PRIMARY KEY (registration_link_id);
ALTER TABLE logistic_company.person_role
  ADD PRIMARY KEY (person_id, role_id);
ALTER TABLE "logistic_company"."address_contact"
  ADD PRIMARY KEY ("address_contact_id");


ALTER TABLE "logistic_company"."person_role"
  ADD FOREIGN KEY ("role_id") REFERENCES "logistic_company"."role" ("role_id");

ALTER TABLE "logistic_company"."person_role"
  ADD FOREIGN KEY ("person_id") REFERENCES "logistic_company"."person" ("person_id") ON DELETE CASCADE;

ALTER TABLE "logistic_company"."advertisement"
  ADD FOREIGN KEY ("type_advertisement_id") REFERENCES "logistic_company".advertisement_type ("type_advertisement_id");

ALTER TABLE "logistic_company"."work_day"
  ADD FOREIGN KEY ("employee_id") REFERENCES "logistic_company"."person" (person_id);

ALTER TABLE "logistic_company"."person"
  ADD FOREIGN KEY("contact_id") REFERENCES "logistic_company"."contact"(contact_id);

ALTER TABLE "logistic_company"."order"
  ADD FOREIGN KEY ("reseiver_id")REFERENCES "logistic_company"."address_contact"("address_contact_id");

ALTER TABLE "logistic_company"."order"
  ADD FOREIGN KEY ("sender_id") REFERENCES "logistic_company"."address_contact"("address_contact_id");

ALTER TABLE "logistic_company"."order"
  ADD FOREIGN KEY ("office_id") REFERENCES "logistic_company"."office" ("office_id");

ALTER TABLE "logistic_company"."order"
  ADD FOREIGN KEY ("order_status_id") REFERENCES "logistic_company"."order_status" ("order_status_id");

ALTER TABLE "logistic_company"."address_contact"
  ADD FOREIGN KEY ("contact_id") REFERENCES "logistic_company"."contact" ("contact_id");

ALTER TABLE "logistic_company"."address_contact"
  ADD FOREIGN KEY ("address_id") REFERENCES "logistic_company"."address" ("address_id");



ALTER  TABLE "logistic_company"."office"
  ADD  FOREIGN KEY ("address_id") REFERENCES  "logistic_company"."address"(address_id);

ALTER TABLE "logistic_company"."order"
  ADD FOREIGN KEY ("courier_id") REFERENCES "logistic_company"."person" ("person_id");

ALTER TABLE logistic_company.registration_link
  ADD FOREIGN KEY (person_id) REFERENCES logistic_company.person (person_id);


CREATE FUNCTION delete_old_rows()
  RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
DECLARE
  row_count INT;
BEGIN
  DELETE FROM person
  WHERE person.registration_date < NOW() - INTERVAL '20 second' AND person_id IN (SELECT person_id
                                                                                  FROM person_role
                                                                                  WHERE role_id IN (SELECT
                                                                                                      logistic_company.role.role_id
                                                                                                    FROM
                                                                                                      logistic_company.role
                                                                                                    WHERE role_name =
                                                                                                          'ROLE_UNCONFIRMED'));
  IF found
  THEN
    GET DIAGNOSTICS row_count = ROW_COUNT;
  END IF;
  RAISE NOTICE 'DELETED % row(s) FROM person', row_count;
  RETURN NULL;
END;
$$;


CREATE TRIGGER trigger_delete_old_rows
  AFTER INSERT
  ON person
EXECUTE PROCEDURE delete_old_rows();