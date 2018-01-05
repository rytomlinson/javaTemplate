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
            CASE WHEN q.source_id is null THEN q.id ELSE q.source_id END as "db/id",
            q.type as "question/type",
            q.render_as as "question/render-as",
            q.required as "question/required",
            q.source_id as "question/source",
            q.web_service as "question/web-service",
            (select json_agg(displayTitle) from (Select  tls.id as "db/id",
                                                 tls.locale as "language/locale",
                                                 tls.displayTitle "localized-string/value"
                                                 from translation tls
                                                 where q.display_title_id = tls.i18n_string_id and tls.locale = locationLocale::language_locale) as displayTitle) as "artifact/display-title",
            (select json_agg(semanticTitle) from (Select  tls.id as "db/id",
                                                 tls.locale as "language/locale",
                                                 tls.displayTitle "localized-string/value"
                                                 from translation tls
                                                 where q.semantic_title_id = tls.i18n_string_id and tls.locale = locationLocale::language_locale) as semanticTitle) as "artifact/semantic-title",
            (select json_agg(questionTip) from (Select  tls.id as "db/id",
                                                 tls.locale as "language/locale",
                                                 tls.displayTitle "localized-string/value"
                                                 from translation tls
                                                 where q.tip_id = tls.i18n_string_id and tls.locale = locationLocale::language_locale) as questionTip) as "question/tip",
            q.is_library as "artifact/is-library",
            (select json_agg(questionBenefit) from (Select  tls.id as "db/id",
                                                 tls.locale as "language/locale",
                                                 tls.displayTitle "localized-string/value"
                                                 from translation tls
                                                 where q.benefit_id = tls.i18n_string_id and tls.locale = locationLocale::language_locale) as questionBenefit) as "question/benefit",
            (select json_agg(questionShortLabel) from (Select  tls.id as "db/id",
                                                 tls.locale as "language/locale",
                                                 tls.displayTitle "localized-string/value"
                                                 from translation tls
                                                 where q.short_label_id = tls.i18n_string_id and tls.locale = locationLocale::language_locale) as questionShortLabel) as "question/short-label",
            (select json_agg(questionPlaceholder) from (Select  tls.id as "db/id",
                                                 tls.locale as "language/locale",
                                                 tls.displayTitle "localized-string/value"
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
                                                 tls.displayTitle "localized-string/value"
                                                 from translation tls
                                                 where rq.low_range_label_id = tls.i18n_string_id and tls.locale = locationLocale::language_locale) as lowRangeLabel) as "range-question/low-range-label",
            (select json_agg(highRangeLabel) from (Select  tls.id as "db/id",
                                                 tls.locale as "language/locale",
                                                 tls.displayTitle "localized-string/value"
                                                 from translation tls
                                                 where rq.high_range_label_id = tls.i18n_string_id and tls.locale = locationLocale::language_locale) as highRangeLabel) as "range-question/high-range-label",
            tq.text_columns as "text-question/text-columns",
            tq.text_rows as "text-question/text-rows") as questionSource) as "question/source",
            (case when
                (select question_id from condition c3 where c3.question_id = q.id and c3.deleted = false limit 1) is null then null
             	else GetConditionalQuestionsForQuestions(q.id, locationLocale, includeTags, parentQType) end) as "question/conditional-questions",
            (select row_to_json(preconditions) from (Select c.id as "db/id",
                                                    c.equality as "condition/equality",
                                                    c.selection as "condition/selection",
                                                    q.rank,
                                                     case when (c.range_question_maximum_value is null) THEN
                                                       c.range_question_minimum_value
                                                     end as "range-question/selection",

                                                     case when (c.range_question_maximum_value is not null) THEN
                                                       c.range_question_minimum_value end as "range-question/minimum-value",

                                                     c.range_question_maximum_value as "range-question/maximum-value",

                                                    (select json_agg(selections) from (Select
                                      			              s2.id as "db/id",
                                                          q.rank as "item/rank",
                                                         (select json_agg(displayTitles) from (select
                                                          tl2.id as "db/id",
                                                          tl2.displayTitle as "localized-string/value",
                                                          tl2.locale as "language/locale"
                                                          ) as displayTitles)
                                                          as "selection/display-title"
                                                           from selection s2
                                                             join condition_question_selections cqs2 on s2.id = cqs2.selection_id
                                                             join translation tl2 on s2.display_title_id = tl2.i18n_string_id
                                                           where cqs2.condition_id = c.id and tl2.locale = locationLocale::language_locale) as selections) as "select-question/selections"
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
