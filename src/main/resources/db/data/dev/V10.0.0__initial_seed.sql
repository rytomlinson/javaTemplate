
SET SCHEMA 'insight';


-- support functions for seeding
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
Insert into tag (type, name_id, is_survey, owner) values ('navis', (Select CreateTranslation(_name, 'en-US')), true, NavisUuid() );
END;
$$ LANGUAGE plpgsql volatile cost 100;

CREATE OR replace function insertMarketSegmentTag(_name text ) returns void as $$
BEGIN
Insert into tag (type, name_id, is_market_segment, owner) values ('navis', (Select CreateTranslation(_name, 'en-US')), true, NavisUuid() );
END;
$$ LANGUAGE plpgsql volatile cost 100;

CREATE OR replace function insertSurveyTypeTag(_name text ) returns void as $$
BEGIN
Insert into tag (type, name_id, is_survey_type, owner) values ('navis', (Select CreateTranslation(_name, 'en-US')), true, NavisUuid() );
END;
$$ LANGUAGE plpgsql volatile cost 100;

CREATE OR replace function insertDepartmentTag(_name text ) returns void as $$
BEGIN
Insert into tag (name_id, owner) values ((Select CreateTranslation(_name, 'en-US')), NavisUuid() );
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

create or replace function insertQuestion(_display_title text, _short_label text, _benefit text, _tip text, _render_as question_render_as, _type text )
returns bigint as $$
    DECLARE question_id BIGINT;
BEGIN

INSERT into question(display_title_id, short_label_id, semantic_title_id, benefit_id, tip_id, render_as, is_template, is_library, owner)
values((select CreateTranslation(_display_title, 'en-US'))
 , (select CreateTranslation(_short_label, 'en-US'))
 , (select CreateTranslation(_display_title, 'en-US'))
 , (select CreateTranslation(_benefit, 'en-US'))
 , (select CreateTranslation(_tip, 'en-US'))
 , _render_as
 , TRUE
 , TRUE
 , NavisUuid()) returning id into question_id;

 return (select question_id);
END;
$$ LANGUAGE plpgsql volatile cost 100;

CREATE OR replace function insertTextQuestion(_display_title text, _short_label text, _benefit text, _tip text, _render_as question_render_as, _type text ) returns void as $$
BEGIN
Insert into text_question(text_columns, text_rows, question_id)
values (50, 5, insertQuestion(_display_title, _short_label, _benefit, _tip, _render_as, _type));
END;
$$ LANGUAGE plpgsql volatile cost 100;

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


