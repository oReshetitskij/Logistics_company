DROP TABLE IF EXISTS "logistic_company"."work_day";
DROP TABLE IF EXISTS "logistic_company".employee_role;
DROP TABLE IF EXISTS "logistic_company"."advertisement";
DROP TABLE IF EXISTS "logistic_company"."advertisement_type";
DROP TABLE IF EXISTS "logistic_company"."order";
DROP TABLE IF EXISTS "logistic_company"."person";
DROP TABLE IF EXISTS "logistic_company"."role";
DROP TABLE IF EXISTS "logistic_company"."bonus";
DROP TABLE IF EXISTS "logistic_company"."office";
DROP TABLE IF EXISTS "logistic_company"."order_status";



DROP FUNCTION IF EXISTS logistic_company.delete_old_rows();


DROP SEQUENCE IF EXISTS "logistic_company"."main_seq_id";
DROP TYPE IF EXISTS logistic_company.week_day;

DROP SCHEMA IF EXISTS "logistic_company";


CREATE SCHEMA "logistic_company";

CREATE TYPE logistic_company.week_day AS ENUM ('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday' , 'Sunday');

CREATE SEQUENCE "logistic_company"."main_seq_id"
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 10;

SELECT setval('"logistic_company"."main_seq_id"', 1, TRUE);

SET SEARCH_PATH TO logistic_company;

CREATE TABLE "logistic_company"."person" (
  "person_id"         INT4 DEFAULT nextval('main_seq_id' :: REGCLASS) NOT NULL,
  "first_name"        VARCHAR(45) COLLATE "default"                   NOT NULL,
  "last_name"         VARCHAR(45) COLLATE "default"                   NOT NULL,
  "user_name"         VARCHAR(45) COLLATE "default",
  "password"          VARCHAR(200) COLLATE "default"                  NOT NULL,
  "registration_date" timestamp                                       NOT NULL DEFAULT NOW(),
  "email"             VARCHAR(45) COLLATE "default"                   NOT NULL,
  "phone_number"      VARCHAR(45) COLLATE "default"                   NOT NULL,
  "role_id"           INT4                                            NOT NULL
)
WITH (OIDS = FALSE
);

CREATE TABLE "logistic_company"."role" (
  "role_id"   INT4 DEFAULT nextval('main_seq_id' :: REGCLASS) NOT NULL,
  "role_name" VARCHAR(45) COLLATE "default"                   NOT NULL,
  "bonus_id" INT4
);

CREATE TABLE "logistic_company"."employee_role"
(
  "employee_id" INT4,
  "role_id"     INT4
);



CREATE TABLE "logistic_company"."bonus"
(
  "bonus_id" INT4 DEFAULT nextval('main_seq_id' :: REGCLASS)         NOT NULL,
  "discount"      NUMERIC,
  "priority"      VARCHAR(45) COLLATE "default"                       NOT NULL
)
WITH (OIDS = FALSE
);

CREATE TABLE "logistic_company"."advertisement"
(
  "advertisement_id"      INT4 DEFAULT nextval('main_seq_id' :: REGCLASS)     NOT NULL,
  "description"           VARCHAR(1000) COLLATE "default"                     NOT NULL,
  "type_advertisement_id" INT4                                                NOT NULL
)
WITH (OIDS = FALSE
);

CREATE TABLE "logistic_company"."advertisement_type"
(
  "type_advertisement_id" INT4 DEFAULT nextval('main_seq_id' :: REGCLASS)    NOT NULL,
  "advertisement_name"    VARCHAR(60) COLLATE "default"                      NOT NULL
)
WITH (OIDS = FALSE
);


CREATE TABLE "logistic_company"."work_day"
(
  "employee_id"      INT4        NOT NULL,
  "week_day"         week_day    NOT NULL,
  "begin_time"       TIME        NOT NULL,
  "end_time"         TIME        NOT NULL
)
WITH (OIDS = FALSE
);

CREATE TABLE "logistic_company"."order"
(
  "order_id" INT4 DEFAULT nextval('main_seq_id' :: REGCLASS)    NOT NULL,
  "address_sender"    VARCHAR(60) COLLATE "default"             NOT NULL,
  "address_receiver"  VARCHAR(60) COLLATE "default"             NOT NULL,
  "delivery_time"     TIME                                      NOT NULL,
  "reseiver_id"       INT4                                      NOT NULL,
  "sender_id"         INT4                                      NOT NULL,
  "office_id"         INT4                                      NOT NULL,
  "order_status_id"   INT4
)
WITH (OIDS = FALSE
);

CREATE TABLE "logistic_company"."office"
(
  "office_id" INT4 DEFAULT nextval('main_seq_id' :: REGCLASS)    NOT NULL,
  "name"      VARCHAR(60) COLLATE "default"                      NOT NULL,
  "address"   VARCHAR(60) COLLATE "default"                      NOT NULL
)
WITH (OIDS = FALSE
);

CREATE TABLE "logistic_company"."order_status"
(
  "order_status_id" INT4 DEFAULT nextval('main_seq_id' :: REGCLASS)    NOT NULL,
  "status_name"      VARCHAR(60) COLLATE "default"             NOT NULL
)
WITH (OIDS = FALSE
);

ALTER TABLE "logistic_company"."person"  ADD UNIQUE("user_name");
ALTER TABLE "logistic_company"."person"  ADD UNIQUE("email");
ALTER TABLE "logistic_company"."person"  ADD UNIQUE("phone_number");



ALTER TABLE "logistic_company"."person"
  ADD PRIMARY KEY ("person_id");
ALTER TABLE "logistic_company"."role"
  ADD PRIMARY KEY ("role_id");
ALTER TABLE "logistic_company"."bonus"
  ADD PRIMARY KEY ("bonus_id");
ALTER TABLE "logistic_company"."advertisement"
  ADD PRIMARY KEY ("advertisement_id");
ALTER TABLE "logistic_company"."advertisement_type"
  ADD PRIMARY KEY ("type_advertisement_id");
ALTER  TABLE "logistic_company"."office"
  ADD PRIMARY KEY ("office_id");
ALTER  TABLE "logistic_company"."order_status"
  ADD PRIMARY KEY ("order_status_id");




ALTER TABLE "logistic_company"."employee_role"
  ADD FOREIGN KEY ("role_id") REFERENCES "logistic_company"."role" ("role_id");

ALTER TABLE "logistic_company"."employee_role"
  ADD FOREIGN KEY ("employee_id") REFERENCES "logistic_company"."person" ("person_id");

ALTER TABLE "logistic_company"."role"
  ADD FOREIGN KEY ("bonus_id") REFERENCES "logistic_company"."bonus" ("bonus_id");

ALTER TABLE "logistic_company"."advertisement"
  ADD FOREIGN KEY ("type_advertisement_id") REFERENCES "logistic_company".advertisement_type ("type_advertisement_id");

ALTER TABLE  "logistic_company"."work_day"
  ADD FOREIGN KEY ("employee_id") REFERENCES  "logistic_company"."person"(person_id);

ALTER TABLE  "logistic_company"."person"
  ADD FOREIGN KEY ("role_id") REFERENCES "logistic_company"."role"("role_id");


ALTER TABLE "logistic_company"."order"
  ADD FOREIGN KEY ("reseiver_id") REFERENCES "logistic_company"."person"("person_id");

ALTER TABLE "logistic_company"."order"
  ADD FOREIGN KEY ("sender_id") REFERENCES "logistic_company"."person"("person_id");

ALTER TABLE "logistic_company"."order"
  ADD FOREIGN KEY ("office_id") REFERENCES "logistic_company"."office"("office_id");

ALTER TABLE "logistic_company"."order"
  ADD FOREIGN KEY ("order_status_id") REFERENCES "logistic_company"."order_status"("order_status_id");



--
-- CREATE FUNCTION delete_old_rows() RETURNS trigger
-- AS $lol$  DECLARE
--   row_count int;
-- BEGIN
--   DELETE FROM person  WHERE registration_date < NOW() - INTERVAL '20 second' AND role_id IN (SELECT role.role_id FROM role WHERE role.role_name = 'ROLE_UNCONFIRMED') ;
--   IF found THEN
--     GET DIAGNOSTICS row_count = ROW_COUNT;
--     RAISE NOTICE 'DELETED % row(s) FROM person', row_count;
--   END IF;
--   RETURN NULL;
-- END;
-- $lol$ LANGUAGE plpgsql;
--
-- CREATE TRIGGER trigger_delete_old_rows
--   AFTER INSERT ON person
-- EXECUTE PROCEDURE delete_old_rows();

SELECT person_id, first_name, last_name, user_name, registration_date, password, email, phone_number, role_id FROM person;

