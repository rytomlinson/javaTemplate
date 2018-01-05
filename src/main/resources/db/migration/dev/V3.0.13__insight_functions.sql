SET SCHEMA 'insight';
CREATE OR REPLACE FUNCTION CreateTranslation(displayTitle text, locale text) RETURNS BIGINT AS $$

    DECLARE i18n_id BIGINT;

    DECLARE translation_id BIGINT;

    BEGIN
      insert into i18n_string (created_at) values (CURRENT_TIMESTAMP) returning id into i18n_id;

      insert into translation (locale, displayTitle, i18n_string_id) values (locale::language_locale, displayTitle, i18n_id) returning id into translation_id;

      return (Select i18n_id);
    END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION CreateSurveyForm(surveyId BIGINT, formId BIGINT, rank BIGINT, locale text) RETURNS BIGINT AS $$

    DECLARE i18n_id BIGINT;

    DECLARE surveyFormId BIGINT;

    BEGIN
      Select CreateTranslation((select displayTitle from
      form f join translation t on f.default_preface_id = t.i18n_string_id limit 1), locale) into i18n_id;

      insert into survey_form (survey_id, form_id, rank, preface_id, deleted, required) values (surveyId, formId, rank, i18n_id, false, false) returning id into surveyFormId;

      insert into survey_form_form_field (form_field_id, survey_form_id, required)
      Select id, surveyFormId, required from form_field where form_id = formId;

      return (Select SurveyFormId);
    END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION MarkSurveyFormAsDeleted(surveyFormId BIGINT) RETURNS void AS $$
  BEGIN
    update survey_form set deleted = true where id = surveyFormId;
    update survey_form_form_field set deleted = true where survey_form_id = surveyFormId;
    update survey_request_form_field_answer set deleted = true where survey_form_id = surveyFormId;
  END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION GetSurveyRequestFormFieldAnswers(surveyrequestid bigint)
    RETURNS json
    LANGUAGE 'plpgsql'
    COST 100.0
    VOLATILE NOT LEAKPROOF
AS $function$

        BEGIN
        return (select json_agg(surveyRequestFormFieldAnswers) from (
            Select
              fa.id as "db/id",
              fa.form_field_id as "form/field-id",
              fa.answer as "form/answer"
           from
              survey_request sr
              join survey s on sr.survey_id = s.id
              join survey_form sf on s.id = sf.survey_id
              join survey_form_form_field sff on sf.id = sff.survey_form_id
              left join survey_request_form_field_Answer fa on sf.id = fa.survey_form_id and fa.form_field_id = sff.form_field_id and fa.survey_request_id = surveyRequestId
              where sr.id = surveyRequestId and fa.form_field_id is not null and fa.deleted = false) as surveyRequestFormFieldAnswers);
        END;

$function$;

CREATE OR REPLACE FUNCTION CreateTestSurveyRequestId() RETURNS BIGINT AS $$


    DECLARE i18nId BIGINT;
    DECLARE surveyId BIGINT;
    DECLARE surveyRequestId BIGINT;
    DECLARE surveyFormId BIGINT;


    BEGIN

        Select CreateTranslation('test survey', 'en-US') into i18nId;

        Insert into survey (description_id, display_title_id, enabled, launch_date, owner, account_id)
        values (i18nId, i18nId, true, current_timestamp, '56f356bb-3ca8-46aa-b669-8b87fd66c9c4'::UUID, 'QA1002') returning id into surveyId;

        Insert into survey_request (completion_date, completion_status, account_id, crm_stay_id, email, sent_date, survey_id)
        values (current_timestamp, 'completed', 'QA1002', '3938377398', 'peter.rabbit@thenavisway.com', current_timestamp, surveyId)
        returning id into surveyRequestId;

        Select insight.CreateTranslation('test survey', 'en-US') into i18nId;

        Insert into survey_form (survey_id, form_id, rank, preface_id, required)
        values (surveyId, 1, 1, i18nId, false) returning id into surveyFormId;

        Insert into survey_request_form_field_answer (survey_form_id, form_field_id, survey_request_id, answer)
        values (surveyFormId, 1, surveyRequestId, 'Peter');
        Insert into survey_request_form_field_answer (survey_form_id, form_field_id, survey_request_id, answer)
        values (surveyFormId, 2, surveyRequestId, 'Rabbit');
        Insert into survey_request_form_field_answer (survey_form_id, form_field_id, survey_request_id, answer)
        values (surveyFormId, 3, surveyRequestId, 'peter.rabbit@thenavisway.com');
        Insert into survey_request_form_field_answer (survey_form_id, form_field_id, survey_request_id, answer)
        values (surveyFormId, 4, surveyRequestId, '503-555-1212');

      insert into survey_form (survey_id, form_id, rank, preface_id, deleted, required) values (surveyId, 1, 1, i18nId, false, false) returning id into surveyFormId;

      insert into survey_form_form_field (form_field_id, survey_form_id, required)
      Select id, surveyformId, required from form_field where form_id = 1;

      return (Select surveyRequestId);
    END;
$$ LANGUAGE plpgsql;
