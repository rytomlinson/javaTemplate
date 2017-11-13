SET SCHEMA 'insight';
CREATE TYPE form_validation_type AS ENUM ('email','phone','zip', 'none');

-- the following sequence is used in both form and survey form tables
create sequence survey_form_id_seq;


CREATE TABLE "form" (
    id BIGINT PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , name_id BIGINT NOT NULL REFERENCES i18n_string(id)
  , default_preface_id BIGINT NOT NULL REFERENCES i18n_string(id)
  , deleted bool NOT NULL DEFAULT false
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);

CREATE TRIGGER update_form_updated_at
  BEFORE UPDATE
  ON form
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

CREATE TABLE "survey_form" (
    id BIGINT PRIMARY KEY NOT NULL DEFAULT NEXTVAL('survey_form_id_seq'::regclass)
  , survey_id BIGSERIAL NOT NULL references survey(id)
  , form_id BIGSERIAL NOT NULL references form(id)
  , rank int NOT NULL
  , preface_id BIGINT REFERENCES i18n_string(id)
  , deleted bool NOT NULL DEFAULT false
  , required bool NOT NULL DEFAULT false
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);

CREATE TRIGGER update_survey_form_updated_at
  BEFORE UPDATE
  ON survey_form
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

CREATE TABLE "form_field" (
    id BIGSERIAL PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , form_id BIGSERIAL NOT NULL references form(id)
  , name text NOT NULL
  , display_name_id BIGINT NOT NULL REFERENCES i18n_string(id)
  , validation_type form_validation_type
  , max_length int NULL
  , rank int NOT NULL
  , required bool NOT NULL DEFAULT FALSE
  , deleted bool NOT NULL DEFAULT false
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);

CREATE TRIGGER update_form_field_updated_at
  BEFORE UPDATE
  ON form_field
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();


CREATE TABLE "survey_form_form_field" (
    id BIGSERIAL PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , form_field_id BIGSERIAL NOT NULL references form_field(id)
  , survey_form_id BIGSERIAL NOT NULL references survey_form(id)
  , required bool NOT NULL DEFAULT FALSE
  , deleted bool NOT NULL DEFAULT false
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);

CREATE TRIGGER update_survey_form_form_field_updated_at
  BEFORE UPDATE
  ON survey_form_form_field
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

CREATE TABLE "survey_request_form_field_answer" (
    id BIGSERIAL PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , survey_form_id BIGSERIAL NOT NULL references survey_form(id)
  , form_field_id BIGSERIAL NOT NULL references form_field(id)
  , survey_request_id BIGSERIAL NOT NULL references survey_request(id)
  , answer text
  , deleted bool NOT NULL DEFAULT false
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);

CREATE TRIGGER update_survey_request_form_field_answer_updated_at
  BEFORE UPDATE
  ON survey_request_form_field_answer
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();


CREATE TABLE "contacts_sent" (
    id BIGSERIAL PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , survey_request_id BIGSERIAL NOT NULL references survey_request(id)
  , lname text NOT NULL
  , fname text NOT NULL
  , email text NOT NULL
  , phone text NOT NULL
  , contact_id BIGINT NULL
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);

CREATE TRIGGER update_contacts_sent_updated_at
  BEFORE UPDATE
  ON contacts_sent
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

alter table survey add column account_id text null;

DROP INDEX style_image_id_fkidx;

CREATE INDEX style_image_id_fkidx
    ON insight.style USING btree
    (image_id)
    TABLESPACE pg_default
    where image_id is not null;

COMMENT ON INDEX insight.style_image_id_fkidx
    IS 'Foreign Key Index insight.image (id)';


-- seed contact form data
Insert into form (id, name_id, default_preface_id) values (1, (Select CreateTranslation('Contact Form', 'en-US')), (Select CreateTranslation('Please enter your contact information', 'en-US')));

-- seed contact form fields
Insert into form_field (form_id, name, display_name_id, validation_type, max_length, rank, required)
Select id, 'fname', (Select CreateTranslation('First Name', 'en-US')), 'none', 50, 5, TRUE from form f where f.id = 1;

-- seed contact form fields
Insert into form_field (form_id, name, display_name_id, validation_type, max_length, rank, required)
Select id, 'lname', (Select CreateTranslation('Last Name', 'en-US')), 'none', 50, 10, TRUE from form f where f.id = 1;

-- seed contact form fields
Insert into form_field (form_id, name, display_name_id, validation_type, max_length, rank, required)
Select id, 'email', (Select CreateTranslation('Email', 'en-US')), 'email', 120, 15, TRUE from form f where f.id = 1;

-- seed contact form fields
Insert into form_field (form_id, name, display_name_id, validation_type, max_length, rank, required)
Select id, 'phone', (Select CreateTranslation('Phone', 'en-US')), 'phone', 50, 20, false from form f where f.id = 1;


