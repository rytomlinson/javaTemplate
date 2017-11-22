
SET SCHEMA 'insight';


-- Primary Tables


create table email (
  id bigserial primary key
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , email text not null default ''
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW());

CREATE TRIGGER update_email_updated_at
BEFORE UPDATE
  ON email
FOR EACH ROW
EXECUTE PROCEDURE public.update_updated_at_column();

create table report_frequency_type (
    id bigserial primary key
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , code text not null default ''
  , description text not null default ''
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW());

create table report_type (
    id bigserial primary key
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , code text not null default ''
  , description text not null default ''
  , report_frequency_type_id BIGINT NOT NULL REFERENCES report_frequency_type (id) ON DELETE CASCADE
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW());

create table survey_report_recipients (
    id bigserial primary key
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , email_id BIGINT NOT NULL REFERENCES email (id) ON DELETE CASCADE
  , survey_id BIGINT NOT NULL REFERENCES survey (id) ON DELETE CASCADE
  , report_type_id BIGINT NOT NULL REFERENCES report_type (id) ON DELETE CASCADE
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW());

CREATE TRIGGER update_survey_report_recipients_updated_at
BEFORE UPDATE
  ON survey_report_recipients
FOR EACH ROW
EXECUTE PROCEDURE public.update_updated_at_column();

create table report_sends (
    id bigserial primary key
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , email_id BIGINT NOT NULL REFERENCES email (id) ON DELETE CASCADE
  , survey_id BIGINT NOT NULL REFERENCES survey (id) ON DELETE CASCADE
  , report_type_id BIGINT NOT NULL REFERENCES report_type (id) ON DELETE CASCADE
  , survey_request_id BIGINT NOT NULL REFERENCES survey_request (id) ON DELETE CASCADE
  , success BOOLEAN NOT NULL DEFAULT FALSE
  , error_message text not null default ''
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW());
