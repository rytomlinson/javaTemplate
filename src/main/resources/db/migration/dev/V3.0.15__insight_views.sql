SET SCHEMA 'insight';
CREATE OR REPLACE VIEW insight.vw_unsent_contacts AS
   SELECT DISTINCT s.account_id AS accountnumber,
      sr.id AS surveyrequestid,
      ( SELECT ffa2.answer
             FROM insight.survey_request_form_field_answer ffa2
            WHERE ffa2.survey_request_id = ffa.survey_request_id AND ffa2.form_field_id = (( SELECT ff.id
                     FROM insight.form_field ff
                    WHERE ff.form_id = 1 AND ff.name = 'lname'::text))) AS lname,
      ( SELECT ffa2.answer
             FROM insight.survey_request_form_field_answer ffa2
            WHERE ffa2.survey_request_id = ffa.survey_request_id AND ffa2.form_field_id = (( SELECT ff.id
                     FROM insight.form_field ff
                    WHERE ff.form_id = 1 AND ff.name = 'fname'::text))) AS fname,
      ( SELECT ffa2.answer
             FROM insight.survey_request_form_field_answer ffa2
            WHERE ffa2.survey_request_id = ffa.survey_request_id AND ffa2.form_field_id = (( SELECT ff.id
                     FROM insight.form_field ff
                    WHERE ff.form_id = 1 AND ff.name = 'email'::text))) AS email,
      ( SELECT ffa2.answer
             FROM insight.survey_request_form_field_answer ffa2
            WHERE ffa2.survey_request_id = ffa.survey_request_id AND ffa2.form_field_id = (( SELECT ff.id
                     FROM insight.form_field ff
                    WHERE ff.form_id = 1 AND ff.name = 'phone'::text))) AS phone
     FROM insight.survey_request_form_field_answer ffa
       JOIN insight.survey_request sr ON ffa.survey_request_id = sr.id
       join insight.survey s on sr.survey_id = s.id
       LEFT JOIN insight.contacts_sent cs ON sr.id = cs.survey_request_id
    WHERE cs.id IS NULL AND sr.completion_date IS NOT NULL;

CREATE OR REPLACE VIEW insight.vw_valid_unsent_contacts AS
 SELECT vw_unsent_contacts.accountnumber,
    vw_unsent_contacts.surveyrequestid,
    vw_unsent_contacts.lname,
    vw_unsent_contacts.fname,
    vw_unsent_contacts.email,
    vw_unsent_contacts.phone
   FROM insight.vw_unsent_contacts
  WHERE (vw_unsent_contacts.accountnumber is not null) and (vw_unsent_contacts.email IS NOT NULL OR vw_unsent_contacts.phone IS NOT NULL);
