SET SCHEMA 'insight';
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
            q.id as "db/id",
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
            case when c.id is not null then
             (select row_to_json(preconditions) from (Select c.id as "db/id",
                                                    c.equality as "condition/equality",
                                                    c.selection as "condition/selection",
                                                    q.rank,
                                                     case when (cq.type = 'range' and c.range_question_maximum_value is null) THEN
                                                       c.range_question_minimum_value
                                                     end as "range-question/selection",

                                                     case when (cq.type = 'range' and c.range_question_maximum_value is not null) THEN
                                                       c.range_question_minimum_value
                                                     end as "range-question/minimum-value",
                                                     case when (cq.type = 'range' and c.range_question_maximum_value is not null) THEN
                                                       c.range_question_maximum_value
                                                     end as "range-question/maximum-value",
                                                    case when (cq.type = 'select') then
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
							) as preconditions) end as "question/precondition",

            (select row_to_json(questionSource) from (select
            q.id as "db/id",
            q.type as "question/type",
            q.render_as as "question/render-as",
            q.required as "question/required",
            --q.source_id as "question/source",
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
            left join condition c on q.condition_id = c.id
            left join question cq on c.question_id = cq.id
            where (q.type <> 'range-group' and q.type <> 'range-group-member')
            and q.id = questionId and q.deleted = false
        ) as answerQuestion );
      END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION GetGroupedMemberQuestionByIdForAnswerJson(questionId bigint, locationLocale text) RETURNS json AS $$
BEGIN
	  return (select row_to_json(answerQuestion) from (
             Select
             q.id as "db/id",
             (select json_agg(displayTitle) from (Select  tls.id as "db/id",
                                                 tls.locale as "language/locale",
                                                 tls.localized_string "localized-string/value"
                                                 from translation tls
                                                 where q.display_title_id = tls.i18n_string_id and tls.locale = locationLocale::language_locale) as displayTitle) as "artifact/display-title",
            (select row_to_json(questionSource) from (select
            q.id as "db/id",
            q.type as "question/type",
            (select json_agg(displayTitle) from (Select  tls.id as "db/id",
                                                 tls.locale as "language/locale",
                                                 tls.localized_string "localized-string/value"
                                                 from translation tls
                                                 where q.display_title_id = tls.i18n_string_id and tls.locale = locationLocale::language_locale) as displayTitle) as "artifact/display-title"
            ) as questionSource) as "question/source"
            from question q
            left join range_question rq on q.id = rq.question_id
            where q.id = questionId and q.deleted = false
        ) as answerQuestion );
      END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE FUNCTION GetSurveyRequestAnswers(surveyRequestId BIGINT, locationLocale text) RETURNS json AS $$
        BEGIN
        return (select json_agg(surveyRequestAnswers) from (
              Select
              case q.type when 'range' then 'ra-' || ra.id::TEXT
                  when 'text' then 'ta-' || ta.id::TEXT
                  when 'boolean' then 'ba-' || ba.id::TEXT
              end as "db/id",
              ra.answer as "range-question/selection",
              ta.answer as "text-question/text-response",
              ba.answer as "boolean-question/selection",
              null as "select-question/selections",
              (select  GetNonGroupedQuestionByIdJson(q.id, locationLocale)) as "answer/question"
             from
              survey_request sr
              join survey s on sr.survey_id = s.id
              join survey_question sq on s.id = sq.survey_id
              join question q on sq.survey_question_id = q.id
              left join survey_request_bool_Answer ba on q.id = ba.question_id and ba.survey_request_id = surveyRequestId
              left join survey_request_range_Answer ra on q.id = ra.question_id and ra.survey_request_id = surveyRequestId
              left join survey_request_text_Answer ta on q.id = ta.question_id and ta.survey_request_id = surveyRequestId
              where sr.id = surveyRequestId and (ba.question_id is not null or ra.question_id is not null or ta.question_id is not null)
              and (ba.deleted = false or ra.deleted = false or ta.deleted = false)
            UNION ALL
              Select
              'sa-' || x.id::TEXT as "db/id",
              null as "range-question/selection",
              null as "text-question/text-response",
              null as "boolean-question/selection",
              (select json_agg(selections) from (
                Select sa2.selection_id as "db/id",
                (select json_agg(displayTitle) from (Select  tls.id as "db/id",
                                                   tls.locale as "language/locale",
                                                   tls.localized_string "localized-string/value"
                                                   from
                                                   selection s
                                                   join translation tls on s.display_title_id = tls.i18n_string_id
                                                   where sa2.selection_id = s.id and tls.locale = locationLocale::language_locale) as displayTitle) as "selection/display-title"
                from survey_request_select_Answer sa2 where sa2.survey_request_id = surveyRequestId and sa2.question_id = x.question_id
              ) as selections) as "select-question/selections",
              (select  GetNonGroupedQuestionByIdJson(sq.survey_question_id, locationLocale)) as "answer/question"
             from
              survey_request sr
              join survey s on sr.survey_id = s.id
              join survey_question sq on s.id = sq.survey_id
              join (select distinct on (sa.question_id) sa.id, sa.selection_id, sa.question_id from survey_request_select_Answer sa
                    where sa.survey_request_id = surveyRequestId and sa.deleted = false) x on sq.survey_question_id = x.question_id
                    where sr.id = surveyRequestId
            ) as surveyRequestAnswers);
        END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION GetSurveyRequestRangeGroupAnswers(surveyRequestId BIGINT, locationLocale text) RETURNS json AS $$
        BEGIN
        return (select json_agg(surveyRequestAnswers) from (
            Select
            'ra-' || ra.id::TEXT as "db/id",
            ra.answer as "range-question/selection",
            (select GetGroupedMemberQuestionByIdForAnswerJson(mq.group_member_question_id, locationLocale)) as "answer/question"
            from
            survey_request sr
            join survey s on sr.survey_id = s.id
            join survey_question sq on s.id = sq.survey_id
            join question q on sq.survey_question_id = q.id
            join question_group_member_question mq on q.id = mq.group_question_id
            join survey_request_range_answer ra on mq.group_member_question_id = ra.question_id and ra.survey_request_id = surveyRequestId
            where sr.id = surveyRequestId
            and ra.deleted = false)
            as surveyRequestAnswers);
        END;
$$ LANGUAGE plpgsql;
