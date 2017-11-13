SET SCHEMA 'insight';

CREATE OR REPLACE FUNCTION GetRangeGroupMemberQuestionsJson(qid bigint, lang_locale text) RETURNS json AS $$
        BEGIN
            return (select json_agg(questions) from (Select
                q.id as "db/id",
                q.type as "question/type",
                (select json_agg(displayTitle) from (Select  tls.id as "db/id",
                                                     tls.locale as "language/locale",
                                                     tls.localized_string "localized-string/value"
                                                     from translation tls
                                                     where q.display_title_id = tls.i18n_string_id and tls.locale = lang_locale::language_locale) as displayTitle) as "artifact/display-title",
                (select json_agg(semanticTitle) from (Select  tls.id as "db/id",
                                                     tls.locale as "language/locale",
                                                     tls.localized_string "localized-string/value"
                                                     from translation tls
                                                     where q.semantic_title_id = tls.i18n_string_id and tls.locale = lang_locale::language_locale) as semanticTitle) as "artifact/semantic-title",
                (select GetArtifactTagsJson((select array_agg(tag_id)::BIGINT[] from question_tag qt where qt.question_id = q.id), lang_locale)) as "artifact/tags"
                from
                question_group_member_question mq
                join question q on mq.group_member_question_id = q.id
                where ((qid is null) or (mq.group_question_id = qid)) and q.deleted = false  GROUP BY q.id) as questions);
        END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION GetConditionalRangeGroupMemberQuestionsForSurveyJson(qid bigint, lang_locale text) RETURNS json AS $$
        BEGIN
            return (select json_agg(questions) from (Select
                q.id as "db/id",
                q.type as "question/type",
                (select json_agg(displayTitle) from (Select  tls.id as "db/id",
                                                     tls.locale as "language/locale",
                                                     tls.localized_string "localized-string/value"
                                                     from translation tls
                                                     where q.display_title_id = tls.i18n_string_id and tls.locale = lang_locale::language_locale) as displayTitle) as "artifact/display-title",
                (select json_agg(semanticTitle) from (Select  tls.id as "db/id",
                                                     tls.locale as "language/locale",
                                                     tls.localized_string "localized-string/value"
                                                     from translation tls
                                                     where q.semantic_title_id = tls.i18n_string_id and tls.locale = lang_locale::language_locale) as semanticTitle) as "artifact/semantic-title",
                (select row_to_json(questionSource) from (select
                q.id as "db/id",
                q.type as "question/type",
                (select json_agg(displayTitle) from (Select  tls.id as "db/id",
                                                     tls.locale as "language/locale",
                                                     tls.localized_string "localized-string/value"
                                                     from translation tls
                                                     where q.display_title_id = tls.i18n_string_id and tls.locale = lang_locale::language_locale) as displayTitle) as "artifact/display-title",
                (select json_agg(semanticTitle) from (Select  tls.id as "db/id",
                                                     tls.locale as "language/locale",
                                                     tls.localized_string "localized-string/value"
                                                     from translation tls
                                                     where q.semantic_title_id = tls.i18n_string_id and tls.locale = lang_locale::language_locale) as semanticTitle) as "artifact/semantic-title") as questionSource) as "question/source"
                from
                question_group_member_question mq
                join question q on mq.group_member_question_id = q.id
                where (qid is null) or (mq.group_question_id = qid) GROUP BY q.id) as questions);
        END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION GetRangeGroupMemberQuestionsWithSourceForSurveyJson(qid bigint, lang_locale text) RETURNS json AS $$
        BEGIN
            return (select json_agg(questions) from (Select
                q.id as "db/id",
                (select GetArtifactTagsJson((select array_agg(tag_id)::BIGINT[] from question_tag qt where qt.question_id = q.id), lang_locale)) as "artifact/tags",
                (select row_to_json(questionSource) from (select
                q.id as "db/id",
                q.type as "question/type",
                (select json_agg(displayTitle) from (Select  tls.id as "db/id",
                                                     tls.locale as "language/locale",
                                                     tls.localized_string "localized-string/value"
                                                     from translation tls
                                                     where q.display_title_id = tls.i18n_string_id and tls.locale = lang_locale::language_locale) as displayTitle) as "artifact/display-title",
                (select json_agg(semanticTitle) from (Select  tls.id as "db/id",
                                                     tls.locale as "language/locale",
                                                     tls.localized_string "localized-string/value"
                                                     from translation tls
                                                     where q.semantic_title_id = tls.i18n_string_id and tls.locale = lang_locale::language_locale) as semanticTitle) as "artifact/semantic-title"
                ) as questionSource) as "question/source"
                from
                question_group_member_question mq
                join question q on mq.group_member_question_id = q.id
                where (qid is null) or (mq.group_question_id = qid) GROUP BY q.id) as questions);
        END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION GetRangeGroupMemberQuestionsWithoutSourceForSurveyJson(qid bigint, lang_locale text) RETURNS json AS $$
        BEGIN
            return (select json_agg(questions) from (Select
                q.id as "db/id",
                q.type as "question/type",
                (select json_agg(displayTitle) from (Select  tls.id as "db/id",
                                                     tls.locale as "language/locale",
                                                     tls.localized_string "localized-string/value"
                                                     from translation tls
                                                     where q.display_title_id = tls.i18n_string_id and tls.locale = lang_locale::language_locale) as displayTitle) as "artifact/display-title",
                (select json_agg(semanticTitle) from (Select  tls.id as "db/id",
                                                     tls.locale as "language/locale",
                                                     tls.localized_string "localized-string/value"
                                                     from translation tls
                                                     where q.semantic_title_id = tls.i18n_string_id and tls.locale = lang_locale::language_locale) as semanticTitle) as "artifact/semantic-title"
                from
                question_group_member_question mq
                join question q on mq.group_member_question_id = q.id
                where (qid is null) or (mq.group_question_id = qid) GROUP BY q.id) as questions);
        END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION GetConditionalQuestionsForQuestions(questionId BIGINT, locationLocale text, includeTags boolean, parentQType question_type) RETURNS json AS $$
        BEGIN
        return (select json_agg(conditionalquestion) from (
            Select
            q.id as "db/id",
            q.rank as "item/rank",
            case q.type
            when 'range-group' then (select GetConditionalRangeGroupMemberQuestionsForSurveyJson(q.id, locationLocale))
            else null
            end as "question/group-members",
            (select row_to_json(questionSource) from (select
            CASE WHEN q.source_id is null THEN q.id ELSE q.source_id END as "db/id",
            q.type as "question/type",
            q.render_as as "question/render-as",
            q.required as "question/required",
            q.source_id as "question/source",
            q.web_service as "question/web-service",
            (select json_agg(displayTitle) from (Select  tls.id as "db/id",
                                                 tls.locale as "language/locale",
                                                 tls.localized_string "localized-string/value"
                                                 from translation tls
                                                 where q.display_title_id = tls.i18n_string_id and tls.locale = locationLocale::language_locale) as displayTitle) as "artifact/display-title",
            (select json_agg(semanticTitle) from (Select  tls.id as "db/id",
                                                 tls.locale as "language/locale",
                                                 tls.localized_string "localized-string/value"
                                                 from translation tls
                                                 where q.semantic_title_id = tls.i18n_string_id and tls.locale = locationLocale::language_locale) as semanticTitle) as "artifact/semantic-title",
            (select json_agg(questionTip) from (Select  tls.id as "db/id",
                                                 tls.locale as "language/locale",
                                                 tls.localized_string "localized-string/value"
                                                 from translation tls
                                                 where q.tip_id = tls.i18n_string_id and tls.locale = locationLocale::language_locale) as questionTip) as "question/tip",
            q.is_library as "artifact/is-library",
            (select json_agg(questionBenefit) from (Select  tls.id as "db/id",
                                                 tls.locale as "language/locale",
                                                 tls.localized_string "localized-string/value"
                                                 from translation tls
                                                 where q.benefit_id = tls.i18n_string_id and tls.locale = locationLocale::language_locale) as questionBenefit) as "question/benefit",
            (select json_agg(questionShortLabel) from (Select  tls.id as "db/id",
                                                 tls.locale as "language/locale",
                                                 tls.localized_string "localized-string/value"
                                                 from translation tls
                                                 where q.short_label_id = tls.i18n_string_id and tls.locale = locationLocale::language_locale) as questionShortLabel) as "question/short-label",
            (select json_agg(questionPlaceholder) from (Select  tls.id as "db/id",
                                                 tls.locale as "language/locale",
                                                 tls.localized_string "localized-string/value"
                                                 from
                                                 text_question tq
                                                 join translation tls on tq.placeholder_id = tls.i18n_string_id
                                                 where tq.question_id = q.id and tls.locale = locationlocale::language_locale) as questionPlaceholder) as "text-question/placeholder",
           case q.type
            when 'range-group' then (select GetConditionalRangeGroupMemberQuestionsForSurveyJson(q.id, locationLocale))
            else null
            end as "question/group-members",
            Case includeTags
            when true then (select GetArtifactTagsJson((select array_agg(tag_id)::BIGINT[] from question_tag qt where qt.question_id = q.id), locationLocale))
            else null end as "artifact/tags",

            (case when sq.selection_list_id is null then null else (select GetSelectionListJson(sq.selection_list_id, locationLocale)) end) as "select-question/selection-list",
            rq.maximum_value as "range-question/maximum-value",
            rq.minimum_value as "range-question/minimum-value",
            sq.allowed_selection_count as "select-question/allowed-selection-count",
            (select json_agg(lowRangeLabel) from (Select  tls.id as "db/id",
                                                 tls.locale as "language/locale",
                                                 tls.localized_string "localized-string/value"
                                                 from translation tls
                                                 where rq.low_range_label_id = tls.i18n_string_id and tls.locale = locationLocale::language_locale) as lowRangeLabel) as "range-question/low-range-label",
            (select json_agg(highRangeLabel) from (Select  tls.id as "db/id",
                                                 tls.locale as "language/locale",
                                                 tls.localized_string "localized-string/value"
                                                 from translation tls
                                                 where rq.high_range_label_id = tls.i18n_string_id and tls.locale = locationLocale::language_locale) as highRangeLabel) as "range-question/high-range-label",
            tq.text_columns as "text-question/text-columns",
            tq.text_rows as "text-question/text-rows") as questionSource) as "question/source",
            (case when
                (select question_id from condition c3 where c3.question_id = q.id) is null then null
             	else GetConditionalQuestionsForQuestions(q.id, locationLocale, includeTags, parentQType) end) as "question/conditional-questions",
            (select row_to_json(preconditions) from (Select c.id as "db/id",
                                                    c.equality as "condition/equality",
                                                    c.selection as "condition/selection",
                                                    q.rank,
                                                     case when (parentQType = 'range' and c.range_question_maximum_value is null) THEN
                                                       c.range_question_minimum_value
                                                     end as "range-question/selection",

                                                     case when (parentQType = 'range' and c.range_question_maximum_value is not null) THEN
                                                       c.range_question_minimum_value
                                                     end as "range-question/minimum-value",
                                                     case when (parentQType = 'range' and c.range_question_maximum_value is not null) THEN
                                                       c.range_question_maximum_value
                                                     end as "range-question/maximum-value",
                                                    case when (parentQType = 'select') then
                                                    (select json_agg(selections) from (Select
                                      			              s2.id as "db/id",
                                                          q.rank as "item/rank",
                                                         (select json_agg(displayTitles) from (select
                                                          tl2.id as "db/id",
                                                          tl2.localized_string as "localized-string/value",
                                                          tl2.locale as "language/locale"
                                                          ) as displayTitles)
                                                          as "selection/display-title"
                                                           from selection s2
                                                             join condition_question_selections cqs2 on s2.id = cqs2.selection_id
                                                             join translation tl2 on s2.display_title_id = tl2.i18n_string_id
                                                           where cqs2.condition_id = c.id and tl2.locale = locationLocale::language_locale) as selections) end as "select-question/selections"
							) as preconditions) as "question/precondition"

           from
            condition c
            join question q on c.id = q.condition_id
            left join select_question sq on q.id = sq.question_id
            left join range_question rq on q.id = rq.question_id
            left join text_question tq on q.id = tq.question_id
            left join condition_question_selections cqs on c.id = cqs.condition_id
            where c.question_id = questionId and q.deleted = false) as "conditionalquestion");
        END;
$$ LANGUAGE plpgsql;




CREATE OR REPLACE FUNCTION GetNonGroupedQuestionByIdJson(questionId bigint, locationLocale text) RETURNS json AS $$
BEGIN
	  return (select row_to_json(answerQuestion) from (
          Select
            q.id as "db/id",
            q.rank as "item/rank",
            (select row_to_json(questionSource) from (select
            CASE WHEN q.source_id is null THEN q.id ELSE q.source_id END as "db/id",
            q.type as "question/type",
            q.render_as as "question/render-as",
            q.required as "question/required",
            q.source_id as "question/source",
            q.web_service as "question/web-service",
            (select json_agg(displayTitle) from (Select  tls.id as "db/id",
                                                 tls.locale as "language/locale",
                                                 tls.localized_string "localized-string/value"
                                                 from translation tls
                                                 where q.display_title_id = tls.i18n_string_id and tls.locale = locationLocale::language_locale) as displayTitle) as "artifact/display-title",
            (select json_agg(semanticTitle) from (Select  tls.id as "db/id",
                                                 tls.locale as "language/locale",
                                                 tls.localized_string "localized-string/value"
                                                 from translation tls
                                                 where q.semantic_title_id = tls.i18n_string_id and tls.locale = locationLocale::language_locale) as semanticTitle) as "artifact/semantic-title",
            json_build_object('auth/property', q.owner) as "auth/owner",
            (select json_agg(questionTip) from (Select  tls.id as "db/id",
                                                 tls.locale as "language/locale",
                                                 tls.localized_string "localized-string/value"
                                                 from translation tls
                                                 where q.tip_id = tls.i18n_string_id and tls.locale = locationLocale::language_locale) as questionTip) as "question/tip",
            q.is_library as "artifact/is-library",
            (select json_agg(questionBenefit) from (Select  tls.id as "db/id",
                                                 tls.locale as "language/locale",
                                                 tls.localized_string "localized-string/value"
                                                 from translation tls
                                                 where q.benefit_id = tls.i18n_string_id and tls.locale = locationLocale::language_locale) as questionBenefit) as "question/benefit",
            (select json_agg(questionShortLabel) from (Select  tls.id as "db/id",
                                                 tls.locale as "language/locale",
                                                 tls.localized_string "localized-string/value"
                                                 from translation tls
                                                 where q.short_label_id = tls.i18n_string_id and tls.locale = locationLocale::language_locale) as questionShortLabel) as "question/short-label",
            (select json_agg(questionPlaceholder) from (Select  tls.id as "db/id",
                                                 tls.locale as "language/locale",
                                                 tls.localized_string "localized-string/value"
                                                 from
                                                 text_question tq
                                                 join translation tls on tq.placeholder_id = tls.i18n_string_id
                                                 where tq.question_id = q.id and tls.locale = locationLocale::language_locale) as questionPlaceholder) as "text-question/placeholder",
            (case when sq.selection_list_id is null then null else (select GetSelectionListJson(sq.selection_list_id, locationLocale)) end) as "select-question/selection-list",
            sq.allowed_selection_count as "select-question/allowed-selection-count",
            rq.maximum_value as "range-question/maximum-value",
            rq.minimum_value as "range-question/minimum-value",
            (select json_agg(lowRangeLabel) from (Select  tls.id as "db/id",
                                                 tls.locale as "language/locale",
                                                 tls.localized_string "localized-string/value"
                                                 from translation tls
                                                 where rq.low_range_label_id = tls.i18n_string_id and tls.locale = locationLocale::language_locale) as lowRangeLabel) as "range-question/low-range-label",
            (select json_agg(highRangeLabel) from (Select  tls.id as "db/id",
                                                 tls.locale as "language/locale",
                                                 tls.localized_string "localized-string/value"
                                                 from translation tls
                                                 where rq.high_range_label_id = tls.i18n_string_id and tls.locale = locationLocale::language_locale) as highRangeLabel) as "range-question/high-range-label",
            tq.text_columns as "text-question/text-columns",
            tq.text_rows as "text-question/text-rows"
            ) as questionSource) as "question/source",
            (select GetConditionalQuestionsForQuestions(q.Id, locationLocale, false, q.type)) as "question/conditional-questions"
            from question q
            left join select_question sq on q.id = sq.question_id
            left join range_question rq on q.id = rq.question_id
            left join text_question tq on q.id = tq.question_id
            where (q.type <> 'range-group' and q.type <> 'range-group-member')
            and q.id = questionId and q.deleted = false
        ) as answerQuestion );
      END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION GetSurveyRequestAnswers(surveyRequestId BIGINT, locationLocale text) RETURNS json AS $$
        BEGIN
        return (select json_agg(surveyRequestAnswers) from (
            Select
            case q.type when 'range' then ra.id
            	when 'select' then sa.id
                when 'text' then ta.id
                when 'boolean' then ba.id
            end as "db/id",
            ra.answer as "range-question/selection",
            ta.answer as "text-question/text-response",
            ba.answer as "boolean-question/selection",
            (select  GetNonGroupedQuestionByIdJson(q.id, locationLocale)) as "answer/question"
           from
            survey_request sr
            join survey s on sr.survey_id = s.id
            join survey_question sq on s.id = sq.survey_id
            join question q on sq.survey_question_id = q.id
            left join survey_request_bool_Answer ba on q.id = ba.question_id and ba.survey_request_id = surveyRequestId
            left join survey_request_range_Answer ra on q.id = ra.question_id and ra.survey_request_id = surveyRequestId
            left join survey_request_select_Answer sa on q.id = sa.question_id and sa.survey_request_id = surveyRequestId
            left join survey_request_text_Answer ta on q.id = ta.question_id and ta.survey_request_id = surveyRequestId
            where sr.id = surveyRequestId and (ba.question_id is not null or ra.question_id is not null or sa.question_id is not null or ta.question_id is not null)
            and (ba.deleted = false or ra.deleted = false or sa.deleted = false or ta.deleted = false)
            ) as surveyRequestAnswers);
        END;
$$ LANGUAGE plpgsql;


-- CREATE OR REPLACE FUNCTION GetSurveyRequestQuestionResponseJson(qtype question_type, srid bigint, qid bigint, lang_locale language_locale) RETURNS json AS $$
--         BEGIN
--         CASE qtype
--         	WHEN 'select' THEN
--                  return (select row_to_json(response) from
--                   	(Select sa.survey_request_id as "db/id",
--                                                      (select json_agg(selection) from (Select tls.id as "db/id",
--                                                      tls.locale as "language/locale",
--                                                      tls.localized_string "localized-string/value"
--                                                      from selection sl
--                                                      join translation tls on sl.display_title_id = tls.i18n_string_id
--                                                      where sl.id = sa.selection_id and tls.locale = lang_locale) as selection) as "select-question/selections"
--                          from survey_request_select_answer sa
--                          where sa.question_id = qid and sa.survey_request_id = srid) as response);
--                 WHEN 'boolean' THEN
--                  return (select row_to_json(response) from
--                   	(Select ba.survey_request_id as "db/id", ba.answer as "boolean-question/selection",
--                          json_build_object('db/id', ba.question_id) as "answer/question"
--                          from survey_request_bool_answer ba
--                          where ba.question_id = qid and ba.survey_request_id = srid) as response);
--                 ELSE
--                     return (select row_to_json(response) from
--                             (Select ba.survey_request_id as "db/id", ba.answer as "range-question/selection",
--                              json_build_object('db/id', ba.question_id) as "answer/question"
--                              from survey_request_range_answer ba
--                              where ba.question_id = qid and ba.survey_request_id = srid) as response);
--                     END CASE;
--         END;
-- $$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION GetSurveyRequestQuestionResponseJson(qtype question_type, srid bigint, qid bigint, lang_locale language_locale) RETURNS json AS $$
        BEGIN
        CASE qtype
        	WHEN 'select' THEN
                 return (select row_to_json(response) from
                         (Select srid as "db/id",
                                                     (select json_agg(selection) from (Select sl.id as "db/id",
                                                     tls.locale as "language/locale",
                                                     tls.localized_string "localized-string/value"
                                                     from
                                                     survey_request_select_answer sa
                                                     join selection sl on sa.selection_id = sl.id
                                                     join translation tls on sl.display_title_id = tls.i18n_string_id
                                                     where sa.question_id = qid and sa.survey_request_id = srid and sa.deleted = false) as selection) as "select-question/selections") as response);
                WHEN 'boolean' THEN
                 return (select row_to_json(response) from
                  	(Select ba.survey_request_id as "db/id", ba.answer as "boolean-question/selection",
                         json_build_object('db/id', ba.question_id) as "answer/question"
                         from survey_request_bool_answer ba
                         where ba.question_id = qid and ba.survey_request_id = srid and ba.deleted = false) as response);
                WHEN 'text' THEN
                 return (select row_to_json(response) from
                  	(Select ba.survey_request_id as "db/id", ba.answer as "text-question/text-response",
                         json_build_object('db/id', ba.question_id) as "answer/question"
                         from survey_request_text_answer ba
                         where ba.question_id = qid and ba.survey_request_id = srid and ba.deleted = false) as response);
                ELSE
                    return (select row_to_json(response) from
                            (Select ba.survey_request_id as "db/id", ba.answer as "range-question/selection",
                             json_build_object('db/id', ba.question_id) as "answer/question"
                             from survey_request_range_answer ba
                             where ba.question_id = qid and ba.survey_request_id = srid and ba.deleted = false) as response);
                    END CASE;
        END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION GetSelectionAnswerCountByQuestion(qid bigint, slid bigint, lang_locale language_locale) RETURNS json AS $$
     BEGIN
          return (select row_to_json(response) from
                  	     (select
                  	          sa.selection_id as "db/id",
                  	          (select json_agg(selection) from (Select tls.id as "db/id",
                                                           tls.locale as "language/locale",
                                                           tls.localized_string "localized-string/value"
                                                           from selection sl
                                                           join translation tls on sl.display_title_id = tls.i18n_string_id
                                                           where sl.id = sa.selection_id and tls.locale = lang_locale::language_locale) as selection) as "selection/display-title",
                         json_build_object('db/id', s.owner) as "auth/owner"
                         from survey_request_select_answer sa
                         join survey_request sr on sa.survey_request_id = sr.id
                         join survey s on sr.survey_id = s.id
                         where sa.question_id = qid and sa.selection_id = slid
                         group by sa.selection_id, s.owner) as response);
  END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION GetAnswerCountByQuestion(qtype question_type, qid bigint, lang_locale language_locale) RETURNS json AS $$
        BEGIN
        CASE qtype
        	WHEN 'select' THEN
                 return (select json_agg(response) from

                         (select GetSelectionAnswerCountByQuestion(qid, sa.selection_id, lang_locale) as "report/answer-value",
                  	 count(sa.id) as "report/answer-count"
                         from survey_request_select_answer sa
                         where sa.question_id = qid and sa.deleted = false
                         group by sa.selection_id) as response);
                WHEN 'boolean' THEN
                 return (select json_agg(response) from
                            (select ba.answer as "report/answer-value",
                            (count(ba.answer)) as "report/answer-count"
                            from survey_request_bool_answer ba
                            where ba.question_id = qid and ba.deleted = false
                            group by ba.answer) as response);
                WHEN 'text' THEN
                 return null;
                ELSE
                    return (select json_agg(response) from
                            (select ra.answer as "report/answer-value",
                            (count(ra.answer)) as "report/answer-count"
                            from survey_request_range_answer ra
                            where ra.question_id = qid and ra.deleted = false
                            group by ra.answer) as response);
                    END CASE;
        END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION GetTagParentTags(tagId BIGINT, locationLocale text, ownerIds uuid[]) RETURNS json AS $$
        BEGIN
          return (select json_agg(tagParents) from (select
                                             t.id as "db/id",
                                             t.type as "tag/type",
                                             t.is_survey as "tag/is-survey",
                                             t.is_survey_type as "tag/is-survey-type",
                                             t.is_market_segment as "tag/is-market-segment",
                                             t.is_question as "tag/is-question",
                                             (select json_agg(tagName) from (select tls.id as "db/id",
                                                                             tls.locale as "language/locale",
                                                                             tls.localized_string as "localized-string/value"
                                                                             from translation tls
                                                                             where t.name_id = tls.i18n_string_id and tls.locale = locationLocale::language_locale) as tagName) as "tag/name",
                                             (case when
                                                (select parent_tag_id from tag_tag where parent_tag_id = tt.parent_tag_id limit 1) is null then null
                                                else GetTagParentTags(tt.parent_tag_id, locationLocale, ownerIds) end) as "tag/parents"

                                             from tag_tag tt
                                             join tag t on tt.parent_tag_id = t.id
                                             where tt.tag_id = tagId
                                             and (ownerIds is null or (t.owner = ANY(ownerIds) and tt.owner = ANY(ownerIds)))) as tagParents);
        END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION GetTagChildTags(parentTagId BIGINT, ownerId uuid) RETURNS TABLE (id BIGINT) as $$
        BEGIN
      RETURN QUERY
            WITH RECURSIVE child_tags AS (
              Select tag_id, parent_tag_id
              from tag_tag tt
              where (tt.tag_id = parentTagId or tt.parent_tag_id = parentTagId) and  tt.owner = ownerId
              UNION
              Select tt.tag_id, tt.parent_tag_id
              from tag_tag tt
              join child_tags ct on ct.tag_id = tt.parent_tag_id)
            Select tag_id from child_tags;
        END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION GetQuestionIdsUnderTagIdForSurvey(tagId BIGINT, ownerId uuid, surveyId BIGINT) RETURNS TABLE (id BIGINT) as $$
        BEGIN
          RETURN QUERY
                 Select distinct question_id
                 from survey_question sq
                 join question_tag qt on sq.survey_question_id = qt.question_id
                 join question q on qt.question_id = q.id
                 where sq.survey_id = surveyId and sq.deleted = false
                 and q.type <> 'range-group' and qt.tag_id in (select ct.id from GetTagChildTags(tagId, ownerId) ct)
                 UNION
                 Select distinct mq.group_member_question_id as question_id
                 from survey_question sq
                 join question_group_member_question mq on sq.survey_question_id = mq.group_question_id
                 join question_tag qt on mq.group_member_question_id = qt.question_id
                 where sq.survey_id = surveyId and sq.deleted = false and qt.tag_id in (select ct.id from GetTagChildTags(tagId, ownerId) ct);
        END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION GetResponseCountForSurveyTagId(tagId BIGINT, ownerId uuid, surveyId BIGINT) RETURNS BIGINT as $$
        BEGIN
          RETURN (Select count(*)
                 from
                 GetQuestionIdsUnderTagIdForSurvey(tagId, ownerId, surveyId) ts
                 join survey_request_range_answer ra on ts.id = ra.question_id
                 where ra.deleted = false);

        END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION GetResponseAverageForSurveyTagId(tagId BIGINT, ownerId uuid, surveyId BIGINT) RETURNS decimal as $$
        BEGIN
          RETURN (Select Coalesce(avg(ra.answer),0)
                 from
                 GetQuestionIdsUnderTagIdForSurvey(tagId, ownerId, surveyId) ts
                 join survey_request_range_answer ra on ts.id = ra.question_id
                 where ra.deleted = false);
        END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION GetResponseAverageByDateForSurveyTagId(tagId BIGINT, ownerId uuid, surveyId BIGINT, beginDate TIMESTAMP, endDate TIMESTAMP) RETURNS Table (id DATE, average DECIMAL) as $$
        BEGIN
          RETURN QUERY
                 (Select
                 completion_date::DATE,
                 avg(ra.answer)
                 from
                 GetQuestionIdsUnderTagIdForSurvey(tagId, ownerId, surveyId) ts
                 join survey_request_range_answer ra on ts.id = ra.question_id
                 join survey_request sr on ra.survey_request_id = sr.id
                 where completion_date between beginDate and endDate
                 group by completion_date::DATE
                 order by completion_date::DATE);
        END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION GetResponseCountByAnswerForSurveyTagId(tagId BIGINT, ownerId uuid, surveyId BIGINT) RETURNS Table (id BIGINT, answer_count BIGINT) as $$
        BEGIN
          RETURN QUERY
                 (Select
                 ra.answer,
                 count(ra.answer)
                 from
                 GetQuestionIdsUnderTagIdForSurvey(tagId, ownerId, surveyId) ts
                 join survey_request_range_answer ra on ts.id = ra.question_id
                 join survey_request sr on ra.survey_request_id = sr.id
                 group by ra.answer
                 order by ra.answer);
        END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION GetDepartmentDataForSurvey(surveyId BIGINT, ownerId uuid, beginDate timestamp, endDate timestamp) RETURNS json as $$
        BEGIN
        return (select json_agg(response) from
           (Select DISTINCT ON ("report/department-id", "report/department-response-count",  "report/average-rating")
            surveyId || '-' || tt.tag_id as "report/department-id",
            (Select GetResponseCountForSurveyTagId(tt.tag_id, tt.owner, surveyId)) as "report/department-response-count",
            (Select GetResponseAverageForSurveyTagId(tt.tag_id, tt.owner, surveyId)) as "report/average-rating",
            (Select json_object_agg(id, average) from GetResponseAverageByDateForSurveyTagId(tt.tag_id, ownerId, surveyId, beginDate, endDate)) as "report/exec-trending",
            (Select json_object_agg(id, answer_count) from GetResponseCountByAnswerForSurveyTagId(tt.tag_id, ownerId, surveyId)) as "report/rating-counts"
            from tag_tag tt
            left join question_tag qt on tt.tag_id = qt.tag_id
            left join survey_question sq on qt.question_id = sq.survey_question_id
            left join question q on sq.survey_question_id = q.id
            where tt.owner = ownerId and (q.type is null or q.type <> 'range-group')
            and ((Select count(id) from GetQuestionIdsUnderTagIdForSurvey(tt.tag_id, ownerId, surveyId)) > 0)
            union all
            Select DISTINCT ON ("report/department-id", "report/department-response-count",  "report/average-rating")
            sq.survey_id || '-' || tt.tag_id as "report/department-id",
            (Select GetResponseCountForSurveyTagId(tt.tag_id, tt.owner, sq.survey_id)) as "report/department-response-count",
            (Select GetResponseAverageForSurveyTagId(tt.tag_id, tt.owner, sq.survey_id)) as "report/average-rating",
            (Select json_object_agg(id, average) from GetResponseAverageByDateForSurveyTagId(tt.tag_id, ownerId, sq.survey_id, beginDate, endDate)) as "report/exec-trending",
            (Select json_object_agg(id, answer_count) from GetResponseCountByAnswerForSurveyTagId(tt.tag_id, ownerId, sq.survey_id)) as "report/rating-counts"
            from tag_tag tt
            join question_tag qt on tt.tag_id = qt.tag_id
            join question_group_member_question mq on qt.question_id = mq.group_member_question_id
            join survey_question sq on mq.group_question_id = sq.survey_question_id
            where sq.survey_id = surveyId and owner = ownerId and sq.deleted = false
           ) as response);
        END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION GetDepartmentReportDataForSurvey(surveyId BIGINT, ownerId UUID, navisId UUID) RETURNS TEXT AS $$

DECLARE maxSurveyDate TIMESTAMP;

BEGIN
  Select coalesce(completion_date::DATE, CURRENT_DATE) from survey_request where completion_date is not null and survey_id = surveyId order by completion_date ASC LIMIT 1 INTO maxSurveyDate;

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
       where tt.owner = ownerId and tt.id =
                                    (Select t.id from tag t join translation tl on t.name_id = tl.i18n_string_id where tl.localized_string = 'department' and t.owner = navisId)) as response);


  RETURN (Select * from tmpTable);

END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION GetQuestionCompletionCountByDate(surveyId BIGINT, ownerId UUID) RETURNS Table (survey_question_id BIGINT, completion_date date, count BIGINT) AS $$

DECLARE maxSurveyDate TIMESTAMP;

BEGIN
  Select coalesce(sr.completion_date::Date, CURRENT_DATE) from survey_request sr where sr.completion_date is not null and sr.survey_id = surveyId order by sr.completion_date Desc LIMIT 1 INTO maxSurveyDate;

  CREATE TEMP TABLE tmpTable ON COMMIT DROP AS
    (Select
       sq.survey_question_id,
       sr.completion_date::DATE as completion_date,
       count(*) as "count"
     from
       survey s
       join survey_request sr on s.id = sr.survey_id
       join survey_question sq on s.id = sq.survey_id
       join survey_request_select_answer a on sq.survey_question_id = a.question_id and sr.id = a.survey_request_id
     where s.id = surveyId
           and s.owner = ownerId
           and sq.deleted = false
           and sr.completion_date::Date between maxSurveyDate + INTERVAL '-4 day' and  maxSurveyDate + INTERVAL '1 day'
     group by sq.survey_question_id, sr.completion_date::DATE
     UNION
     Select
       sq.survey_question_id,
       sr.completion_date::DATE as completion_date,
       count(*) as "count"
     from
       survey s
       join survey_request sr on s.id = sr.survey_id
       join survey_question sq on s.id = sq.survey_id
       join question q on sq.survey_question_id = q.id
       join survey_request_range_answer a on sq.survey_question_id = a.question_id and sr.id = a.survey_request_id
     where s.id = surveyId
           and s.owner = ownerId
           and sq.deleted = false
           and q.type <> 'range-group'
           and sr.completion_date::Date between maxSurveyDate + INTERVAL '-4 day' and  maxSurveyDate + INTERVAL '1 day'
     group by sq.survey_question_id, sr.completion_date::DATE
     UNION
     Select
       mq.group_member_question_id,
       sr.completion_date::DATE as completion_date,
       count(*) as "count"
     from
       survey s
       join survey_request sr on s.id = sr.survey_id
       join survey_question sq on s.id = sq.survey_id
       join question q on sq.survey_question_id = q.id
       join question_group_member_question mq on sq.survey_question_id = mq.group_question_id
       join survey_request_range_answer a on mq.group_member_question_id = a.question_id and sr.id = a.survey_request_id
     where s.id = surveyId
           and s.owner = ownerId
           and sq.deleted = false
           and q.type = 'range-group'
           and sr.completion_date::Date between maxSurveyDate + INTERVAL '-4 day' and  maxSurveyDate + INTERVAL '1 day'
     group by mq.group_member_question_id, sr.completion_date::DATE
     UNION
     Select
       sq.survey_question_id,
       sr.completion_date::DATE as completion_date,
       count(*) as "count"
     from
       survey s
       join survey_request sr on s.id = sr.survey_id
       join survey_question sq on s.id = sq.survey_id
       join survey_request_bool_answer a on sq.survey_question_id = a.question_id and sr.id = a.survey_request_id
     where s.id = surveyId
           and s.owner = ownerId
           and sq.deleted = false
           and sr.completion_date::Date between maxSurveyDate + INTERVAL '-4 day' and  maxSurveyDate + INTERVAL '1 day'
     group by sq.survey_question_id, sr.completion_date::DATE
     UNION
     Select
       sq.survey_question_id,
       sr.completion_date::DATE as completion_date,
       count(*) as "count"
     from
       survey s
       join survey_request sr on s.id = sr.survey_id
       join survey_question sq on s.id = sq.survey_id
       and sq.deleted = false
       join survey_request_text_answer a on sq.survey_question_id = a.question_id and sr.id = a.survey_request_id
     where s.id = surveyId
           and s.owner = ownerId
           and sr.completion_date::Date between maxSurveyDate + INTERVAL '-4 day' and  maxSurveyDate + INTERVAL '1 day'
     group by sq.survey_question_id, sr.completion_date::DATE
    );

  RETURN QUERY (Select t.survey_question_id, t.completion_date, t.count from tmpTable t order by t.survey_question_id, t.completion_date desc);

END;
$$ LANGUAGE plpgsql;

