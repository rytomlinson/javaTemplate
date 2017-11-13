SET SCHEMA 'insight';

CREATE OR REPLACE FUNCTION GetTagInUseCnt(tagId BIGINT) RETURNS BIGINT as $$

BEGIN

return (select t.id from tag t
left join question_tag qt on t.id = qt.tag_id
left join survey_tag st on t.id = st.tag_id
left join tag_tag tt on t.id = tt.tag_id
where t.id = tagId  and (qt.tag_id is not null or st.tag_id is not null or tt.tag_id is not null) limit 1);

        END;
$$ LANGUAGE plpgsql;


CREATE FUNCTION MarkQuestionAsDeleted(questionId BIGINT) RETURNS void AS $$
BEGIN
update survey_question set deleted = true where survey_question_id = questionId;
update question set deleted = true where id = questionId;
update survey_request_reachable_questions set deleted = true where question_id = questionId;

update survey_request_reachable_questions set deleted = true where question_id in
(select group_member_question_id from question_group_member_question where group_question_id = questionId);

END;
$$ LANGUAGE plpgsql;
