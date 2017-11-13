/* ------------------------------------------------------------------------------------------------------------------ */
/* Reasoning behind the schema design                                                                                 */
/* All tables are created with a BIGSERIAL Primary Key                                                                */
/* All foreign key references are BIGINT                                                                              */
/* All tables are created with OIDS=false                                                                             */
/*                                                                                                                    */
/* Localization                                                                                                       */
/* The localization will be handled with the following tables:                                                        */
/* i18n_string  - This table contains the actual translated string into the particular locale. Default locale is 'en' */
/* translation - This table contains the id to group all locals for a given string in the i18n_string table           */
/* ------------------------------------------------------------------------------------------------------------------ */

SET SCHEMA 'insight';

-- Enumerations
CREATE TYPE condition_selection AS ENUM ('any', 'all', 'one-of', 'range', 'exactly');
CREATE TYPE delivery_channel_channel AS ENUM ('reach', 'twitter', 'facebook');
CREATE TYPE language_locale AS ENUM ('en', 'es', 'es-MX', 'de', 'en-US', 'en-CA', 'fr', 'fr-CA', 'fr-FR', 'de-DE');
CREATE TYPE question_web_service AS ENUM ('trip-advisor', 'trust-you');
CREATE TYPE question_render_as AS ENUM ('radio', 'checkbox', 'dropdown', 'text-input', 'textarea', 'boolean', 'radio-group');
CREATE TYPE question_type AS ENUM ('range', 'select', 'text', 'boolean', 'range-group', 'range-group-member');
CREATE TYPE share_target_type AS ENUM ('user', 'property', 'realm');
CREATE TYPE share_target_permission AS ENUM ('read', 'write');
CREATE TYPE survey_request_completion_status AS ENUM ('in-progress', 'completed', 'demo');
CREATE TYPE survey_request_completion_task AS ENUM ('trip-advisor', 'trust-you');
CREATE TYPE tag_type AS ENUM ('time', 'kind', 'property', 'segment', 'navis');


CREATE OR REPLACE FUNCTION public.update_updated_at_column()
  RETURNS trigger AS
$BODY$
BEGIN
    NEW.updated_at = now();
    RETURN NEW;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.update_updated_at_column()
  OWNER TO dba;
GRANT EXECUTE ON FUNCTION public.update_updated_at_column() TO public;


-- Primary Tables

create table i18n_string (
  id bigserial primary key
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW());

  CREATE TRIGGER update_i18n_string_updated_at
  BEFORE UPDATE
  ON i18n_string
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

create table translation (
  id bigserial primary key
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , locale language_locale not null
  , localized_string text not null default ''
  , i18n_string_id bigint not null references i18n_string(id) ON DELETE CASCADE
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW());

  CREATE TRIGGER update_translation_updated_at
  BEFORE UPDATE
  ON translation
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

CREATE TABLE "condition" (
    id BIGSERIAL PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , equality BOOLEAN
  , selection condition_selection
  , boolean_question_selection BOOLEAN
  , range_question_minimum_value INTEGER
  , range_question_maximum_value INTEGER
  , external_id BIGINT
  , deleted BOOLEAN NOT NULL DEFAULT FALSE
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);

CREATE TRIGGER update_condition_updated_at
  BEFORE UPDATE
  ON condition
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

CREATE TABLE "image" (
    id BIGSERIAL PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , alternate_text_id bigint REFERENCES i18n_string(id)
  , path TEXT
  , external_id BIGINT
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);

CREATE TRIGGER update_image_updated_at
  BEFORE UPDATE
  ON image
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

CREATE TABLE "style" (
    id BIGSERIAL PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , image_id BIGINT REFERENCES image (id)
  , name_id bigint not null REFERENCES i18n_string(id)
  , description_id bigint REFERENCES i18n_string(id)
  , external_id BIGINT
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);

CREATE TRIGGER update_style_updated_at
  BEFORE UPDATE
  ON style
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

CREATE TABLE "question" (
    id BIGSERIAL PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , display_title_id bigint not null REFERENCES i18n_string(id)
  , short_label_id bigint REFERENCES i18n_string(id)
  , semantic_title_id bigint not null REFERENCES i18n_string(id)
  , benefit_id bigint REFERENCES i18n_string(id)
  , tip_id bigint REFERENCES i18n_string(id)
  , condition_id BIGINT REFERENCES condition (id)
  , render_as question_render_as
  , required BOOLEAN NOT NULL DEFAULT FALSE
  , rank INTEGER
  , source_id BIGINT REFERENCES question (id)
  , type question_type
  , web_service question_web_service
  , is_library BOOLEAN NOT NULL DEFAULT FALSE
  , deleted BOOLEAN NOT NULL DEFAULT FALSE
  , is_template BOOLEAN NOT NULL DEFAULT FALSE
  , owner uuid NOT NULL
  , external_id BIGINT
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);

ALTER TABLE condition
ADD question_id bigint REFERENCES question(id);

CREATE TRIGGER update_question_updated_at
  BEFORE UPDATE
  ON question
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

CREATE TABLE "selection_list" (
    id BIGSERIAL PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , is_custom BOOLEAN
  , is_library BOOLEAN
  , description_id bigint not null REFERENCES i18n_string(id)
  , placeholder_id bigint null REFERENCES i18n_string(id)
  , owner uuid NOT NULL
  , external_id BIGINT
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);

CREATE TRIGGER update_selection_list_updated_at
  BEFORE UPDATE
  ON selection_list
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

CREATE TABLE "selection" (
    id BIGSERIAL PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , display_title_id bigint not null REFERENCES i18n_string(id)
  , semantic_title_id bigint null REFERENCES i18n_string(id)
  , selection_list_id bigint not null REFERENCES selection_list(id) ON DELETE CASCADE
  , external_id BIGINT
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);
CREATE INDEX selection_selection_list_id_idx ON selection (selection_list_id);

CREATE TRIGGER update_selection_updated_at
  BEFORE UPDATE
  ON selection
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

CREATE TABLE "boolean_question" (
    id BIGSERIAL PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , question_id BIGINT REFERENCES  question (id) ON DELETE CASCADE
  , image_path text
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);

CREATE TRIGGER update_boolean_question_updated_at
  BEFORE UPDATE
  ON boolean_question
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

CREATE TABLE "range_question" (
    id BIGSERIAL PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , low_range_label_id bigint not null REFERENCES i18n_string(id)
  , medium_range_label_id bigint REFERENCES i18n_string(id)
  , high_range_label_id bigint not null REFERENCES i18n_string(id)
  , inverted_display BOOLEAN
  , maximum_value INTEGER
  , minimum_value INTEGER
  , question_id BIGINT REFERENCES  question (id) ON DELETE CASCADE
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);

CREATE TRIGGER update_range_question_updated_at
  BEFORE UPDATE
  ON range_question
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

CREATE TABLE "select_question" (
    id BIGSERIAL PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , allowed_selection_count INTEGER
  , selection_list_id BIGINT REFERENCES selection_list (id)
  , question_id BIGINT REFERENCES  question (id) ON DELETE CASCADE
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);

CREATE TRIGGER update_select_question_updated_at
  BEFORE UPDATE
  ON select_question
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

CREATE TABLE "text_question" (
    id BIGSERIAL PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , text_columns INTEGER
  , text_rows INTEGER
  , placeholder_id bigint null REFERENCES i18n_string(id)
  , question_id BIGINT REFERENCES  question (id) ON DELETE CASCADE
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);

CREATE TRIGGER update_text_question_updated_at
  BEFORE UPDATE
  ON text_question
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

CREATE TABLE "auth" (
    id BIGSERIAL PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , owner VARCHAR(32)
  , property UUID
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);

CREATE TRIGGER update_auth_updated_at
  BEFORE UPDATE
  ON auth
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

CREATE TABLE "delivery_channel" (
    id BIGSERIAL PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , channel delivery_channel_channel
  , title_id bigint not null REFERENCES i18n_string(id)
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);

CREATE TRIGGER update_delivery_channel_updated_at
  BEFORE UPDATE
  ON delivery_channel
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

CREATE TABLE "language" (
    id BIGSERIAL PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , country_name TEXT
  , language_name TEXT
  , locale language_locale
  , title TEXT
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);

CREATE TRIGGER update_language_updated_at
  BEFORE UPDATE
  ON language
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

CREATE TABLE "share_target" (
    id BIGSERIAL PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , permission share_target_permission
  , type share_target_type
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);

CREATE TRIGGER update_share_target_updated_at
  BEFORE UPDATE
  ON share_target
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

CREATE TABLE "survey" (
    id BIGSERIAL PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , description_id bigint REFERENCES i18n_string(id)
  , display_title_id bigint not null REFERENCES i18n_string(id)
  , instructions_id bigint REFERENCES i18n_string(id)
  , enabled BOOLEAN
  , launch_date TIMESTAMP WITH TIME ZONE
  , create_date TIMESTAMP WITH TIME ZONE
  , created_by UUID
  , style_id BIGINT REFERENCES style (id)
  , deleted BOOLEAN NOT NULL DEFAULT FALSE
  , owner uuid NOT NULL
  , external_id BIGINT
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);

CREATE TRIGGER update_survey_updated_at
  BEFORE UPDATE
  ON survey
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

CREATE TABLE "survey_request" (
    id BIGSERIAL PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , active_until TIMESTAMP WITH TIME ZONE
  , completion_date TIMESTAMP WITH TIME ZONE
  , completion_status survey_request_completion_status
  , completion_task survey_request_completion_task
  , account_id TEXT
  , crm_contact_id TEXT
  , crm_stay_id BIGINT
  , email TEXT
  , sent_date TIMESTAMP WITH TIME ZONE
  , survey_id BIGINT REFERENCES survey (id)
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);

CREATE TRIGGER update_survey_request_updated_at
  BEFORE UPDATE
  ON survey_request
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

CREATE TABLE "tag" (
    id BIGSERIAL PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , type tag_type
  , name_id bigint not null REFERENCES i18n_string(id)
  , description_id bigint REFERENCES i18n_string(id)
  , is_market_segment BOOLEAN NOT NULL DEFAULT FALSE
  , is_survey BOOLEAN NOT NULL DEFAULT FALSE
  , is_survey_type BOOLEAN NOT NULL DEFAULT FALSE
  , is_question BOOLEAN NOT NULL DEFAULT FALSE
  , owner uuid NOT NULL
  , external_id BIGINT
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);

CREATE TRIGGER update_tag_updated_at
  BEFORE UPDATE
  ON tag
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

-- Join Tables
CREATE TABLE condition_question_selections (
    id BIGSERIAL PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , condition_id BIGINT REFERENCES condition (id) NOT NULL
  , selection_id BIGINT REFERENCES selection (id) NOT NULL
  , deleted BOOLEAN NOT NULL DEFAULT FALSE
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);
CREATE UNIQUE INDEX condition_question_selections_nat_idx ON condition_question_selections (condition_id, selection_id);

CREATE TRIGGER update_condition_question_selections_updated_at
  BEFORE UPDATE
  ON condition_question_selections
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

-- CREATE TABLE tag_tag (
--     id BIGSERIAL PRIMARY KEY NOT NULL
--   , created_at TIMESTAMP NOT NULL DEFAULT NOW()
--   , parent_tag_id BIGINT REFERENCES tag (id) NOT NULL
--   , tag_id BIGINT REFERENCES tag (id) NOT NULL
-- ) WITH (OIDS = false);
-- CREATE UNIQUE INDEX tag_tag_nat_idx ON tag_tag (parent_tag_id, tag_id);

CREATE TABLE question_tag (
    id BIGSERIAL PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , question_id BIGINT NOT NULL REFERENCES question (id) ON DELETE CASCADE
  , tag_id BIGINT REFERENCES tag (id) NOT NULL
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);
CREATE UNIQUE INDEX question_tag_nat_idx ON question_tag (question_id, tag_id);

CREATE TRIGGER update_question_tag_updated_at
  BEFORE UPDATE
  ON question_tag
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

CREATE TABLE survey_tag (
    id BIGSERIAL PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , survey_id BIGINT REFERENCES survey (id) NOT NULL
  , tag_id BIGINT REFERENCES tag (id) NOT NULL
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);
CREATE UNIQUE INDEX survey_tag_nat_idx ON survey_tag (survey_id, tag_id);

CREATE TRIGGER update_survey_tag_updated_at
  BEFORE UPDATE
  ON survey_tag
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

CREATE TABLE question_group_member_question (
    id BIGSERIAL PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , group_member_question_id BIGINT NOT NULL REFERENCES question (id) ON DELETE CASCADE
  , group_question_id BIGINT NOT NULL REFERENCES question (id) ON DELETE CASCADE
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);
CREATE UNIQUE INDEX question_group_member_question_nat_idx ON question_group_member_question (group_member_question_id, group_question_id);

CREATE TRIGGER update_question_group_member_question_updated_at
  BEFORE UPDATE
  ON question_group_member_question
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

CREATE TABLE survey_delivery_channel (
    id BIGSERIAL PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , delivery_channel_id BIGINT REFERENCES delivery_channel (id) NOT NULL
  , survey_id BIGINT REFERENCES survey (id) NOT NULL
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);
CREATE UNIQUE INDEX survey_deliver_channel_nat_idx ON survey_delivery_channel (delivery_channel_id, survey_id);

 CREATE TRIGGER update_survey_delivery_channel_updated_at
  BEFORE UPDATE
  ON survey_delivery_channel
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

 CREATE TABLE survey_question (
   id BIGSERIAL PRIMARY KEY NOT NULL
 , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
 , survey_question_id BIGINT REFERENCES question (id) NOT NULL
 , survey_id BIGINT REFERENCES survey (id) NOT NULL
 , deleted BOOLEAN NOT NULL DEFAULT FALSE
 , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
 ) WITH (OIDS = false);
 CREATE UNIQUE INDEX survey_question_nat_idx ON survey_question (survey_question_id, survey_id);

CREATE TRIGGER update_survey_question_updated_at
  BEFORE UPDATE
  ON survey_question
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

 CREATE TABLE survey_request_select_answer (
   id BIGSERIAL PRIMARY KEY NOT NULL
 , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
 , survey_request_id BIGINT REFERENCES survey_request (id) NOT NULL
 , question_id BIGINT REFERENCES question (id) NOT NULL
 , selection_id BIGINT REFERENCES selection (id) NOT NULL
 , deleted BOOLEAN NOT NULL DEFAULT FALSE
 , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
 ) WITH (OIDS = false);
 CREATE UNIQUE INDEX survey_request_select_answer_nat_idx ON survey_request_select_answer (survey_request_id, question_id, selection_id);

CREATE TRIGGER update_survey_request_select_answer_updated_at
  BEFORE UPDATE
  ON survey_request_select_answer
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

 CREATE TABLE survey_request_text_answer (
     id BIGSERIAL PRIMARY KEY NOT NULL
 ,   created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
 ,   survey_request_id bigint REFERENCES survey_request (id) NOT NULL
 ,   question_id bigint REFERENCES question (id) NOT NULL
 ,   deleted BOOLEAN NOT NULL DEFAULT FALSE
 ,   answer text
 ,   updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
 ) WITH (OIDS = false);
 CREATE UNIQUE INDEX survey_request_text_answer_nat_idx ON survey_request_text_answer (survey_request_id, question_id);

CREATE TRIGGER update_survey_request_text_answer_updated_at
  BEFORE UPDATE
  ON survey_request_text_answer
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

 CREATE TABLE survey_request_range_answer (
     id BIGSERIAL PRIMARY KEY NOT NULL
 ,   created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
 ,   survey_request_id bigint REFERENCES survey_request (id) NOT NULL
 ,   question_id bigint REFERENCES question (id) NOT NULL
 ,   deleted BOOLEAN NOT NULL DEFAULT FALSE
 ,   answer BIGINT
 ,   updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
 ) WITH (OIDS = false);
 CREATE UNIQUE INDEX survey_request_range_answer_nat_idx ON survey_request_range_answer (survey_request_id, question_id);

CREATE TRIGGER update_survey_request_range_answer_updated_at
  BEFORE UPDATE
  ON survey_request_range_answer
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

 CREATE TABLE survey_request_bool_answer (
     id BIGSERIAL PRIMARY KEY NOT NULL
 ,   created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
 ,   survey_request_id bigint REFERENCES survey_request (id) NOT NULL
 ,   question_id bigint REFERENCES question (id) NOT NULL
 ,   deleted BOOLEAN NOT NULL DEFAULT FALSE
 ,   answer boolean
 ,   updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
 ) WITH (OIDS = false);
 CREATE UNIQUE INDEX survey_request_bool_answer_nat_idx ON survey_request_bool_answer (survey_request_id, question_id);

CREATE TRIGGER update_survey_request_bool_answer_updated_at
  BEFORE UPDATE
  ON survey_request_bool_answer
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

 CREATE TABLE survey_request_reachable_questions (
   id BIGSERIAL PRIMARY KEY NOT NULL
 , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
 , survey_request_id BIGINT REFERENCES survey_request (id) NOT NULL
 , question_id BIGINT REFERENCES question (id) NOT NULL
 , deleted BOOLEAN NOT NULL DEFAULT FALSE
 , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
 ) WITH (OIDS = false);
 CREATE UNIQUE INDEX survey_request_reachable_question_nat_idx ON survey_request_reachable_questions (survey_request_id, question_id);

CREATE TRIGGER update_survey_request_reachable_questions_updated_at
  BEFORE UPDATE
  ON survey_request_reachable_questions
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

CREATE TABLE tag_tag (
    id BIGSERIAL PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , owner uuid NOT NULL
  , tag_id BIGINT REFERENCES tag (id) NOT NULL
  , parent_tag_id BIGINT REFERENCES tag (id) NOT NULL
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
) WITH (OIDS = false);
CREATE UNIQUE INDEX tag_tag_nat_idx ON tag_tag (owner, parent_tag_id, tag_id);

CREATE TRIGGER update_tag_tag_updated_at
  BEFORE UPDATE
  ON tag_tag
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();

CREATE TABLE conversion_log (
    id BIGSERIAL PRIMARY KEY NOT NULL
  , created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
  , external_id BIGINT
  , table_id_name text
  , table_id BIGINT
  , error_severity text
  , error_message text
  , survey_request_id BIGINT
  , updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW());

CREATE TRIGGER update_conversion_log_updated_at
  BEFORE UPDATE
  ON CONVERSION_LOG
  FOR EACH ROW
  EXECUTE PROCEDURE public.update_updated_at_column();
