CREATE SEQUENCE "logistic_company"."main_seq_id"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 10;
SELECT setval('"logistic_company"."main_seq_id"', 1, true);


CREATE TABLE "logistic_company"."person" (
"person_id" int4 DEFAULT nextval('main_seq_id'::regclass) NOT NULL,
"first_name" varchar(45) COLLATE "default" NOT NULL,
"last_name" varchar(45) COLLATE "default" NOT NULL,
"nick_name" varchar(45) COLLATE "default",
"password" varchar(200) COLLATE "default" NOT NULL,
"registration_date" date ,
"email" varchar(45) COLLATE "default" NOT NULL,
"phone_number" varchar(45) COLLATE "default"
)
WITH (OIDS=FALSE);


CREATE TABLE "logistic_company"."user" (
"user_id" int4,
"role_id " int4,
"permission_id" int4
)
WITH (OIDS=FALSE);

CREATE TABLE "logistic_company"."employee" (
"employee_id" int4,
"salary" numeric,
"manager_id" int4,
"work_days_id" int4
)
WITH (OIDS=FALSE);

CREATE TABLE "logistic_company"."role"(
"role_id" int4 DEFAULT nextval('main_seq_id'::regclass) NOT NULL,
"role_name" varchar(45) COLLATE "default" NOT NULL
);

CREATE  TABLE  "logistic_company"."employee_role"
(
 "employee_id" int4,
 "role_id" int4
);


ALTER  TABLE  "logistic_company"."person" ADD  PRIMARY KEY ("person_id");
ALTER  TABLE  "logistic_company"."employee" ADD PRIMARY KEY ("employee_id");
ALTER  TABLE "logistic_company"."user" ADD PRIMARY KEY ("user_id");
ALTER TABLE "logistic_company"."role" ADD PRIMARY KEY ("role_id");

ALTER TABLE "logistic_company"."user" ADD FOREIGN KEY("user_id") REFERENCES  "logistic_company"."person"("person_id");
ALTER  TABLE "logistic_company"."employee" ADD  FOREIGN KEY ("employee_id") REFERENCES  "logistic_company"."person"("person_id");
ALTER TABLE "logistic_company"."user" ADD  FOREIGN KEY ("role_id ") REFERENCES "logistic_company"."role"("role_id");
ALTER  TABLE  "logistic_company"."employee_role" ADD  FOREIGN KEY  ("role_id") REFERENCES "logistic_company"."role"("role_id");
ALTER  TABLE  "logistic_company"."employee_role" ADD  FOREIGN KEY ("employee_id") REFERENCES  "logistic_company"."employee"("employee_id");

DROP TABLE IF EXISTS "logistic_company"."employee_role";
DROP TABLE if EXISTS "logistic_company"."user";
DROP TABLE if EXISTS "logistic_company"."employee";
DROP  TABLE IF EXISTS "logistic_company"."person";
DROP TABLE IF EXISTS "logistic_company"."role";

