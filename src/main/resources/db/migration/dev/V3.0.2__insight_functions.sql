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
                                 group by s3.id
                                ) as selectionlists) as "selection-list/selections"

        from
        selection_list sl
        where (slid is null) or (sl.id = slid) GROUP BY sl.id) as selectionList);
        END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION GetArtifactTagsJson(tagIds BIGINT[], location_locale text) RETURNS json AS $$
        BEGIN
            return (select json_agg(tags) from (Select
                    t.id as "db/id",
                    t.type as "tag/type",
                     t.is_survey as "tag/is-survey",
                     t.is_survey_type as "tag/is-survey-type",
                     t.is_market_segment as "tag/is-market-segment",
                     t.is_question as "tag/is-question",
                    (select json_agg(tagName) from
                    (select tls.id as "db/id",
                        tls.locale as "language/locale",
                        tls.localized_string as "localized-string/value"
                           from translation tls
                           where t.name_id = tls.i18n_string_id and tls.locale = location_locale::language_locale) as tagName) as "tag/name",
                    (Select GetTagParentTags(t.id, location_locale, null)) as "tag/parents",
                    (select GetTagInUseCnt(t.id)) as "tag/in-use"
                    from tag t
                    where t.id = ANY(tagIds)
                    ) as "tags");
        END;
$$ LANGUAGE plpgsql;

