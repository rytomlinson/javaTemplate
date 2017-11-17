
SET SCHEMA 'insight';


-- support functions for seeding
CREATE OR replace function NavisTagType() returns tag_type AS $$
  BEGIN
  return 'navis';
  END;
  $$ LANGUAGE plpgsql;

CREATE OR replace function en_US() returns text as $$
  BEGIN
  return 'en-US';
  END;
  $$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION NavisUuid() RETURNS uuid AS $$
        BEGIN
            return 'eb2e731e-4cdc-495f-91a5-9ddee7189b28';
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION DisneyUuid() RETURNS uuid AS $$
        BEGIN
            return '56f356bb-3ca8-46aa-b669-8b87fd66c9c4';
END;
$$ LANGUAGE plpgsql;

CREATE OR replace function insertSurveyTag(_name text ) returns void as $$
BEGIN
Insert into tag (type, name_id, is_survey, owner) values (NavisTagType(), (Select CreateTranslation(_name, en_US())), true, NavisUuid() );
END;
$$ LANGUAGE plpgsql volatile cost 100;

CREATE OR replace function insertMarketSegmentTag(_name text ) returns void as $$
BEGIN
Insert into tag (type, name_id, is_market_segment, owner) values (NavisTagType(), (Select CreateTranslation(_name, en_US())), true, NavisUuid() );
END;
$$ LANGUAGE plpgsql volatile cost 100;

CREATE OR replace function insertSurveyTypeTag(_name text ) returns void as $$
BEGIN
Insert into tag (type, name_id, is_survey_type, owner) values (NavisTagType(), (Select CreateTranslation(_name, en_US())), true, NavisUuid() );
END;
$$ LANGUAGE plpgsql volatile cost 100;

CREATE OR replace function insertDepartmentTag(_name text ) returns void as $$
BEGIN
Insert into tag (name_id, owner) values ((Select CreateTranslation(_name, en_US())), NavisUuid() );
END;
$$ LANGUAGE plpgsql volatile cost 100;

CREATE OR replace function returnTagId(_name text ) returns bigint as $$
BEGIN
return (
select tag.id from tag tag join translation t on tag.name_id = t.i18n_string_id where t.localized_string = _name);
END;
$$ LANGUAGE plpgsql;

CREATE OR replace function insertTagTag(_name text, _parentName text ) returns void as $$
BEGIN
Insert into tag_tag (owner, tag_id, parent_tag_id) values (DisneyUuid(), returnTagId(_name), returnTagId(_parentName));
END;
$$ LANGUAGE plpgsql volatile cost 100;

create or replace function insertQuestion(_display_title text, _short_label text, _benefit text, _tip text, _render_as question_render_as, _type question_type)
returns bigint as $$
    DECLARE question_id BIGINT;
BEGIN

INSERT into question(display_title_id, short_label_id, semantic_title_id, benefit_id, tip_id, render_as, type, is_template, is_library, owner)
values((select CreateTranslation(_display_title, en_US()))
 , (select CreateTranslation(_short_label, en_US()))
 , (select CreateTranslation(_display_title, en_US()))
 , (select CreateTranslation(_benefit, en_US()))
 , (select CreateTranslation(_tip, en_US()))
 , _render_as
 , _type
 , TRUE
 , TRUE
 , NavisUuid()) returning id into question_id;

 return (select question_id);
END;
$$ LANGUAGE plpgsql volatile cost 100;

CREATE OR replace function insertTextQuestion(_display_title text, _short_label text, _benefit text, _tip text
, _render_as question_render_as, _type question_type) returns void as $$
BEGIN
Insert into text_question(text_columns, text_rows, question_id)
values (50, 5, insertQuestion(_display_title, _short_label, _benefit, _tip, _render_as, _type));
END;
$$ LANGUAGE plpgsql volatile cost 100;


CREATE OR replace function insertBooleanQuestion(_display_title text, _short_label text, _benefit text
, _tip text, _render_as question_render_as, _type question_type) returns void as $$
BEGIN
Insert into boolean_question(question_id)
values (insertQuestion(_display_title, _short_label, _benefit, _tip, _render_as, _type));
END;
$$ LANGUAGE plpgsql volatile cost 100;

CREATE OR replace function insertRangeQuestion(_display_title text, _short_label text, _benefit text
, _tip text, _render_as question_render_as, _type question_type, _low_range_label text, _medium_range_label text
, _high_range_label text, _min_value integer, _max_value integer) returns void as $$
BEGIN
Insert into range_question(question_id, low_range_label_id, medium_range_label_id, high_range_label_id, minimum_value, maximum_value)
values (insertQuestion(_display_title, _short_label, _benefit, _tip, _render_as, _type)
, (select CreateTranslation(_low_range_label, en_US()))
, (select CreateTranslation(_medium_range_label, en_US()))
, (select CreateTranslation(_high_range_label, en_US()))
, _min_value
, _max_value
);
END;
$$ LANGUAGE plpgsql volatile cost 100;

CREATE OR replace function insertSelectionList(_description_title text, _placeholder_title text, _owner uuid, _is_library boolean) returns void as $$
  BEGIN
    INSERT into selection_list(description_id, placeholder_id, owner, is_library)
    VALUES((select CreateTranslation(_description_title, en_US())), (select CreateTranslation(_placeholder_title, en_US())), _owner, _is_library);
  END;
  $$ LANGUAGE plpgsql volatile cost 100;


CREATE OR replace function returnSelectionListId(_name text ) returns bigint as $$
BEGIN
return (
select sl.id from selection_list sl join translation t on sl.description_id = t.i18n_string_id where t.localized_string = _name);
END;
$$ LANGUAGE plpgsql;

CREATE OR replace function insertSelection(_display_title text, _selection_list_description text) returns void as $$
  BEGIN
    INSERT into selection(display_title_id, semantic_title_id, selection_list_id)
      VALUES((select CreateTranslation(_display_title, en_US())), (select CreateTranslation(_display_title, en_US()))
      , returnSelectionListId(_selection_list_description));
  END;
  $$ LANGUAGE plpgsql volatile cost 100;

CREATE OR replace function insertSelectQuestion(_display_title text, _short_label text, _benefit text
, _tip text, _render_as question_render_as, _type question_type, _allowed_selection_count integer, _selection_list_description text)
returns void as $$
  BEGIN
  insert into select_question(question_id, allowed_selection_count, selection_list_id)
    values(insertQuestion(_display_title, _short_label, _benefit, _tip, _render_as, _type)
    ,_allowed_selection_count
    ,returnSelectionListId(_selection_list_description));
  END;
  $$ LANGUAGE plpgsql volatile cost 100;

CREATE OR replace function insertSurvey(_display_title text, _description text, _enabled boolean, _owner uuid, _deleted boolean) returns void as $$
  BEGIN
    insert into survey(display_title_id, description_id, enabled, owner, deleted)
      values((select CreateTranslation(_display_title, en_US())), (select CreateTranslation(_description, en_US())), _enabled, _owner, _deleted);
  END;
  $$ LANGUAGE plpgsql volatile cost 100;


CREATE OR REPLACE FUNCTION insertReportFrequencyType(_code text, _description text) RETURNS VOID AS $$
BEGIN
  insert into report_frequency_type(code, description)
  values(_code, _description);
END;
$$ LANGUAGE plpgsql volatile cost 100;

CREATE OR REPLACE FUNCTION returnReportFrequencyTypeId(_code text) RETURNS BIGINT as $$
BEGIN
  return (
    select id from report_frequency_type where code = _code);
END;
$$ LANGUAGE plpgsql;

CREATE OR replace function insertReportType(_code text, _description text, _report_frequency_type_code text) returns void as $$
BEGIN
  INSERT into report_type(code, description, report_frequency_type_id)
  VALUES(_code, _description, returnReportFrequencyTypeId(_report_frequency_type_code));
END;
$$ LANGUAGE plpgsql volatile cost 100;


-- data seed section --

-- survey tags
select * from insertSurveyTag('primary rating');
select * from insertSurveyTag('stay');
select * from insertSurveyTag('event');
select * from insertSurveyTag('department');

-- market segment tags
select * from insertMarketSegmentTag('leisure');
select * from insertMarketSegmentTag('group');
select * from insertMarketSegmentTag('corporate');
select * from insertMarketSegmentTag('homeowner');
select * from insertMarketSegmentTag('vacation');

-- survey type tags
select * from insertSurveyTypeTag('pre event');
select * from insertSurveyTypeTag('post event');
select * from insertSurveyTypeTag('pre stay');
select * from insertSurveyTypeTag('mid stay');
select * from insertSurveyTypeTag('post stay');

-- department tags
select * from insertDepartmentTag('rooms');
select * from insertDepartmentTag('activities');
select * from insertDepartmentTag('indoor activities');
select * from insertDepartmentTag('outdoor activities');
select * from insertDepartmentTag('reservations');
select * from insertDepartmentTag('front desk');
select * from insertDepartmentTag('check in');
select * from insertDepartmentTag('check out');
select * from insertDepartmentTag('housekeeping');
select * from insertDepartmentTag('golf');
select * from insertDepartmentTag('spa');

-- tag tags
select * from insertTagTag('activities', 'department');
select * from insertTagTag('indoor activities', 'activities');
select * from insertTagTag('outdoor activities', 'activities');
select * from insertTagTag('rooms', 'department');
select * from insertTagTag('reservations', 'rooms');
select * from insertTagTag('housekeeping', 'rooms');

-- text questions
select * from insertTextQuestion(
'What was the primary reason for choosing this resort'
, 'Reason for choosing this resort'
, 'Reliable trendlines that tell you when things are good, bad, or status quo.'
, 'Follow up with a question to answer the why behind their rating.'
, 'textarea'
, 'text');

-- boolean questions
select * from insertBooleanQuestion(
'Did you experience any problems during your stay with us?'
, 'Any problems during stay'
, 'Reliable trendlines that tell you when things are good, bad, or status quo.'
, 'Follow up with a question to answer the why behind their rating.'
, 'boolean'
, 'boolean');

-- range questions
select * from insertRangeQuestion(
'How would you rate your booking experience?'
, 'Booking experience'
, 'Reliable trendlines that tell you when things are good, bad, or status quo.'
, 'Follow up with a question to answer the why behind their rating.'
, 'radio'
, 'range'
, 'Unacceptable'
, 'Okay'
, 'Fantastic'
, 1
, 5);

-- selection lists
select * from insertSelectionList(
'outdoor activities'
, 'choose activities...'
, DisneyUuid()
, false
);

select * from insertSelectionList(
'pillow types'
, 'pillow type selections'
, NavisUuid()
, true
);

-- selection list selections
select * from insertSelection(
'road biking'
, 'outdoor activities'
);
select * from insertSelection(
'mountain biking'
, 'outdoor activities'
);
select * from insertSelection(
'golf'
, 'outdoor activities'
);

select * from insertSelection(
'feather'
, 'pillow types'
);
select * from insertSelection(
'memory foam'
, 'pillow types'
);

-- select questions
select * from insertSelectQuestion(
'What is your pillow preference?'
, 'Pillow preference'
, 'Learning what pillow type most people like to find in their room when they arrive.'
, 'Follow up with a question to answer the why behind their rating.'
, 'dropdown'
, 'select'
, 1
, 'pillow types'
);

select * from insertSelectQuestion(
'What outdoor activities are you interested in?'
, 'Outdoor Activities'
, 'Learning what outdoor activities most people like to find at or around the given property'
, 'Use the patrons response to provide a customized experience on their next visit..'
, 'checkbox'
, 'select'
, 1
, 'outdoor activities'
);

-- surveys
select * from insertSurvey(
'Post Stay - Crux Ranch'
, 'Post Stay Survey'
, TRUE
, DisneyUuid()
, FALSE
);

select * from insertSurvey(
'Mid Stay - Crux Ranch'
, 'Mid Stay Survey'
, TRUE
, DisneyUuid()
, FALSE
);

select * from insertSurvey(
    'Deleted survey - Test'
    , 'Deleted Survey'
    , TRUE
    , DisneyUuid()
    , TRUE
);

-- report frequency types
select * from insertReportFrequencyType(
  'HOURLY'
  ,'Hourly distribution'
);
select * from insertReportFrequencyType(
    'DAILY'
    ,'Daily distribution'
);
select * from insertReportFrequencyType(
    'WEEKLY'
    ,'Weekly distribution'
);
select * from insertReportFrequencyType(
    'MONTHLY'
    ,'Monthly distribution'
);

-- report types
SELECT * FROM insertReportType(
  'INDIVIDUAL_RESPONSE'
  ,'Individual response report'
  ,'HOURLY'
);

