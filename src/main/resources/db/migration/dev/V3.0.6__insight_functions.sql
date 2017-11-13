SET SCHEMA 'insight';
CREATE OR REPLACE FUNCTION GetDepartmentReportDataForSurvey(surveyId BIGINT, ownerId UUID, navisId UUID) RETURNS TEXT AS $$

DECLARE maxSurveyDate TIMESTAMP;

BEGIN
  Select coalesce(completion_date::DATE, CURRENT_DATE) from survey_request where completion_date is not null and survey_id = surveyId order by completion_date desc LIMIT 1 INTO maxSurveyDate;

  CREATE TEMP TABLE tmpTable ON COMMIT DROP AS
    (select row_to_json(response) from
      (Select
         surveyId "report/survey-id",
         (Select GetResponseCountForSurveyTagId(tt.parent_tag_id, tt.owner, surveyId)) as "report/department-response-count",
         (Select GetResponseAverageForSurveyTagId(tt.parent_tag_id, tt.owner, surveyId)) as "report/average-rating",
         (Select json_object_agg(id, average) from GetResponseAverageByDateForSurveyTagId(tt.parent_tag_id, ownerId, surveyId,  maxSurveyDate + INTERVAL '-4 day',  maxSurveyDate + INTERVAL '1 day')) as "report/exec-trending",
         (Select json_object_agg(id, answer_count) from GetResponseCountByAnswerForSurveyTagId(tt.parent_tag_id, ownerId, surveyId)) as "report/rating-counts",
         (select GetDepartmentDataForSurvey(surveyId, ownerId,  maxSurveyDate + INTERVAL '-4 day',  maxSurveyDate + INTERVAL '1 day')) as "report/departments"
       from tag_tag tt
       where tt.owner = ownerId and tt.parent_tag_id =
                                    (Select t.id from tag t join translation tl on t.name_id = tl.i18n_string_id where tl.localized_string = 'department' and t.owner = navisId limit 1) limit 1) as response);


  RETURN (Select * from tmpTable);

END;
$$ LANGUAGE plpgsql;
