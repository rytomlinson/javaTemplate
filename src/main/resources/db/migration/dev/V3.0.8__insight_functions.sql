SET SCHEMA 'insight';
CREATE OR REPLACE FUNCTION GetSelectionListJson(slid bigint, lang text) RETURNS json AS $$
        BEGIN
            return (select row_to_json(selectionList) from (
            Select
        sl.id as "db/id",
        sl.is_custom as "selection-list/is-custom",
        sl.is_library as "artifact/is-library",
        (select selection_list_id from select_question where selection_list_id = sl.id limit 1) as "selection-list/in-use",
        json_build_object('auth/property', owner) as "auth/owner",
        json_agg((select x from (Select
                tl.id as "db/id",
                tl.localized_string as "localized-string/value",
                tl.locale as "language/locale"
                from selection_list sl2
                left join translation tl
                on tl.i18n_string_id = sl2.description_id  and tl.locale = lang::language_locale
                 where sl2.id = sl.id) as x)) AS "selection-list/description",
        json_agg((select y from (Select
                tl.id as "db/id",
                tl.localized_string as "localized-string/value",
                tl.locale as "language/locale"
                from selection_list sl3
                left join translation tl
                on tl.i18n_string_id = sl3.placeholder_id  and tl.locale = lang::language_locale
                 where sl3.id = sl.id) as y)) AS "selection-list/placeholder",
        (select json_agg(selectionlists) from (Select
                                 s3.id as "db/id",
                                 (select json_agg(selections) from (select
                                              tl2.id as "db/id",
                                              tl2.localized_string as "localized-string/value",
                			                        tl2.locale as "language/locale"

                                              from translation tl2
                                              where s3.display_title_id = tl2.i18n_string_id and sl.id = s3.selection_list_id and tl2.locale = lang::language_locale) as selections)
                  			as "selection/display-title"
   				 from selection s3
                                 where s3.selection_list_id = sl.id
                                 group by s3.id, s3.display_title_id, s3.selection_list_id, s3.external_id
                                 order by s3.external_id, s3.id
                                ) as selectionlists) as "selection-list/selections"

        from
        selection_list sl
        where (slid is null) or (sl.id = slid) GROUP BY sl.id, sl.is_custom, sl.is_library, sl.owner) as selectionList);
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
                where q.deleted = false and ((qid is null) or (mq.group_question_id = qid)) GROUP BY q.id, q.type, q.external_id
                order by q.external_id, q.id) as questions);
        END;
$$ LANGUAGE plpgsql;

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
                where ((qid is null) or (mq.group_question_id = qid)) and q.deleted = false  GROUP BY q.id, q.type, q.display_title_id, q.semantic_title_id
                order by q.external_id, q.id) as questions);
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
                where (qid is null) or (mq.group_question_id = qid) GROUP BY q.id, q.type
                order by q.external_id, q.id) as questions);
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
                where (qid is null) or (mq.group_question_id = qid) GROUP BY q.id, q.type, q.display_title_id, q.semantic_title_id
                order by q.external_id, q.id) as questions);
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
                where (qid is null) or (mq.group_question_id = qid) GROUP BY q.id, q.type, q.display_title_id, q.semantic_title_id
                order by q.external_id, q.id) as questions);
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
                 where sr.completion_date is not null
                 group by ra.answer
                 order by ra.answer);
        END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION GetResponseCountForSurveyTagId(tagId BIGINT, ownerId uuid, surveyId BIGINT) RETURNS BIGINT as $$
        BEGIN
          RETURN (Select count(*)
                 from
                 GetQuestionIdsUnderTagIdForSurvey(tagId, ownerId, surveyId) ts
                 join survey_request_range_answer ra on ts.id = ra.question_id
                 join survey_request sr on ra.survey_request_id = sr.id
                 where sr.completion_date is not null and ra.deleted = false);

        END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION GetResponseAverageForSurveyTagId(tagId BIGINT, ownerId uuid, surveyId BIGINT) RETURNS decimal as $$
        BEGIN
          RETURN (Select Coalesce(avg(ra.answer),0)
                 from
                 GetQuestionIdsUnderTagIdForSurvey(tagId, ownerId, surveyId) ts
                 join survey_request_range_answer ra on ts.id = ra.question_id
                 join survey_request sr on ra.survey_request_id = sr.id
                 where sr.completion_date is not null and ra.deleted = false);
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
                         join survey_request sr on sa.survey_request_id = sr.id
                         where sa.question_id = qid and sa.deleted = false and sr.completion_date is not null
                         group by sa.selection_id) as response);
                WHEN 'boolean' THEN
                 return (select json_agg(response) from
                            (select ba.answer as "report/answer-value",
                            (count(ba.answer)) as "report/answer-count"
                            from survey_request_bool_answer ba
                            join survey_request sr on ba.survey_request_id = sr.id
                            where ba.question_id = qid and ba.deleted = false and sr.completion_date is not null
                            group by ba.answer) as response);
                WHEN 'text' THEN
                 return null;
                ELSE
                    return (select json_agg(response) from
                            (select ra.answer as "report/answer-value",
                            (count(ra.answer)) as "report/answer-count"
                            from survey_request_range_answer ra
                            join survey_request sr on ra.survey_request_id = sr.id
                            where ra.question_id = qid and ra.deleted = false and sr.completion_date is not null
                            group by ra.answer) as response);
                    END CASE;
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
            where c.question_id = questionId and q.deleted = false
            order by q.external_id, q.id
            ) as "conditionalquestion");
        END;
$$ LANGUAGE plpgsql;

