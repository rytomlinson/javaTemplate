SET SCHEMA 'insight';
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
                where q.deleted = false and ((qid is null) or (mq.group_question_id = qid)) GROUP BY q.id) as questions);
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
                where q.deleted = false and ((qid is null) or (mq.group_question_id = qid)) GROUP BY q.id) as questions);
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
                where q.deleted = false and ((qid is null) or (mq.group_question_id = qid)) GROUP BY q.id) as questions);
        END;
$$ LANGUAGE plpgsql;
