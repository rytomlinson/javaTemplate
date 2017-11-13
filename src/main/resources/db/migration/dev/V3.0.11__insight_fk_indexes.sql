SET SCHEMA 'insight';
SET search_path TO insight;

-- navis.insight.boolean_question --
CREATE INDEX boolean_question_question_id_fkidx
   ON insight.boolean_question USING btree (question_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.boolean_question_question_id_fkidx
  IS 'Foreign Key Index insight.question (id)';

-- navis.insight.condition --
CREATE INDEX condition_question_selections_question_id_fkidx
   ON insight.condition USING btree (question_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.condition_question_selections_question_id_fkidx
  IS 'Foreign Key Index insight.question (id)';

-- navis.insight.delivery_channel --
CREATE INDEX delivery_channel_title_id_fkidx
   ON insight.delivery_channel USING btree (title_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.delivery_channel_title_id_fkidx
  IS 'Foreign Key Index insight.title (id)';

-- navis.insight.image --
CREATE INDEX image_alternate_text_id_fkidx
   ON insight."image" USING btree (alternate_text_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.image_alternate_text_id_fkidx
  IS 'Foreign Key Index insight.i18n_string (id)';

-- navis.insight.question --
CREATE INDEX question_benefit_id_fkidx
   ON insight.question USING btree (benefit_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.question_benefit_id_fkidx
  IS 'Foreign Key Index insight.i18n_string (id)';

CREATE INDEX question_condition_id_fkidx
   ON insight.question USING btree (condition_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.question_condition_id_fkidx
  IS 'Foreign Key Index insight.condition (id)';

CREATE INDEX question_display_title_id_fkidx
   ON insight.question USING btree (display_title_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.question_display_title_id_fkidx
  IS 'Foreign Key Index insight.i18n_string (id)';

CREATE INDEX question_semantic_title_id_fkidx
   ON insight.question USING btree (semantic_title_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.question_semantic_title_id_fkidx
  IS 'Foreign Key Index insight.i18n_string (id)';

CREATE INDEX question_short_label_id_fkidx
   ON insight.question USING btree (short_label_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.question_short_label_id_fkidx
  IS 'Foreign Key Index insight.i18n_string (id)';

CREATE INDEX question_source_id_fkidx
   ON insight.question USING btree (source_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.question_source_id_fkidx
  IS 'Foreign Key Index insight.question (id)';

CREATE INDEX question_tip_id_fkidx
   ON insight.question USING btree (tip_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.question_tip_id_fkidx
  IS 'Foreign Key Index insight.i18n_string (id)';

-- navis.insight.question_group_memeber_question --
CREATE INDEX question_group_member_question_group_member_question_id_fkidx
   ON insight.question_group_member_question USING btree (group_member_question_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.question_group_member_question_group_member_question_id_fkidx
  IS 'Foreign Key Index insight.question (id)';

CREATE INDEX question_group_member_question_group_question_id_fkidx
   ON insight.question_group_member_question USING btree (group_question_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.question_group_member_question_group_question_id_fkidx
  IS 'Foreign Key Index insight.question (id)';
  
-- navis.insight.question_tag --
CREATE INDEX question_tag_question_id_fkidx
   ON insight.question_tag USING btree (question_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.question_tag_question_id_fkidx
  IS 'Foreign Key Index insight.question (id)';

CREATE INDEX question_tag_tag_id_fkidx
   ON insight.question_tag USING btree (tag_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.question_tag_tag_id_fkidx
  IS 'Foreign Key Index insight.tag (id)';

-- navis.insight.range_question --
CREATE INDEX range_question_high_range_label_id_fkidx
   ON insight.range_question USING btree (high_range_label_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.range_question_high_range_label_id_fkidx
  IS 'Foreign Key Index insight.i18n_string (id)';

CREATE INDEX range_question_low_range_label_id_fkidx
   ON insight.range_question USING btree (low_range_label_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.range_question_low_range_label_id_fkidx
  IS 'Foreign Key Index insight.i18n_string (id)';

CREATE INDEX range_question_medium_range_label_id_fkidx
   ON insight.range_question USING btree (medium_range_label_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.range_question_medium_range_label_id_fkidx
  IS 'Foreign Key Index insight.i18n_string (id)';

CREATE INDEX range_question_question_id_fkidx
   ON insight.range_question USING btree (question_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.range_question_question_id_fkidx
  IS 'Foreign Key Index insight.question (id)';

-- navis.insight.select_question --
CREATE INDEX selection_question_question_id_fkidx
   ON insight.select_question USING btree (question_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.selection_question_question_id_fkidx
  IS 'Foreign Key Index insight.question (id)';

CREATE INDEX selection_question_selection_list_id_fkidx
   ON insight.select_question USING btree (selection_list_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.selection_question_selection_list_id_fkidx
  IS 'Foreign Key Index insight.selection_list (id)';

-- navis.insight.selection --
CREATE INDEX selection_display_title_id_fkidx
   ON insight.selection USING btree (display_title_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.selection_display_title_id_fkidx
  IS 'Foreign Key Index insight.i18n_string (id)';

CREATE INDEX selection_selection_list_id_fkidx
   ON insight.selection USING btree (selection_list_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.selection_selection_list_id_fkidx
  IS 'Foreign Key Index insight.selection_list (id)';

CREATE INDEX selection_semantic_title_id_fkidx
   ON insight.selection USING btree (semantic_title_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.selection_semantic_title_id_fkidx
  IS 'Foreign Key Index insight.i18n_string (id)';

-- navis.insight.selection_list --
CREATE INDEX selection_list_description_id_fkidx
   ON insight.selection_list USING btree (description_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.selection_list_description_id_fkidx
  IS 'Foreign Key Index insight.i18n_string (id)';

CREATE INDEX selection_list_placeholder_id_fkidx
   ON insight.selection_list USING btree (placeholder_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.selection_list_placeholder_id_fkidx
  IS 'Foreign Key Index insight.i18n_string (id)';

-- navis.insight.style --
CREATE INDEX style_description_id_fkidx
   ON insight.style USING btree (description_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.style_description_id_fkidx
  IS 'Foreign Key Index insight.i18n_string (id)';

CREATE INDEX style_image_id_fkidx
   ON insight.style USING btree (image_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.style_image_id_fkidx
  IS 'Foreign Key Index insight.image (id)';

CREATE INDEX style_name_id_fkidx
   ON insight.style USING btree (name_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.style_name_id_fkidx
  IS 'Foreign Key Index insight.i18n_string (id)';

-- navis.insight.survey -- 
CREATE INDEX survey_description_id_fkidx
   ON insight.survey USING btree (description_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.survey_description_id_fkidx
  IS 'Foreign Key Index insight.i18n_string (id)';

CREATE INDEX survey_display_title_id_fkidx
   ON insight.survey USING btree (display_title_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.survey_display_title_id_fkidx
  IS 'Foreign Key Index insight.i18n_string (id)';

CREATE INDEX survey_instructions_id_fkidx
   ON insight.survey USING btree (instructions_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.survey_instructions_id_fkidx
  IS 'Foreign Key Index insight.i18n_string (id)';

CREATE INDEX survey_style_id_fkidx
   ON insight.survey USING btree (style_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.survey_style_id_fkidx
  IS 'Foreign Key Index insight.style (id)';

-- navis.insight.survey_delivery_channel --
CREATE INDEX survey_delivery_channel_delivery_channel_id_fkidx
   ON insight.survey_delivery_channel USING btree (delivery_channel_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.survey_delivery_channel_delivery_channel_id_fkidx
  IS 'Foreign Key Index insight.delivery_channel (id)';

CREATE INDEX survey_delivery_channel_survey_id_fkidx
   ON insight.survey_delivery_channel USING btree (survey_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.survey_delivery_channel_survey_id_fkidx
  IS 'Foreign Key Index insight.survey (id)';

-- navis.insight.survey_question --
CREATE INDEX survey_question_survey_id_fkidx
   ON insight.survey_question USING btree (survey_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.survey_question_survey_id_fkidx
  IS 'Foreign Key Index insight.survey (id)';

CREATE INDEX survey_question_survey_question_id_fkidx
   ON insight.survey_question USING btree (survey_question_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.survey_question_survey_question_id_fkidx
  IS 'Foreign Key Index insight.question (id)';

-- navis.insight.survey_request --
CREATE INDEX survey_request_survey_id_fkidx
   ON insight.survey_request USING btree (survey_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.survey_request_survey_id_fkidx
  IS 'Foreign Key Index insight.survey (id)';

-- navis.insight.survey_request_bool_answer --
CREATE INDEX survey_request_bool_answer_question_id_fkidx
   ON insight.survey_request_bool_answer USING btree (question_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.survey_request_bool_answer_question_id_fkidx
  IS 'Foreign Key Index insight.question (id)';

CREATE INDEX survey_request_bool_answer_survey_request_id_fkidx
   ON insight.survey_request_bool_answer USING btree (survey_request_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.survey_request_bool_answer_survey_request_id_fkidx
  IS 'Foreign Key Index insight.survey_request (id)';

-- navis.insight.survey_request_range_answer --
CREATE INDEX survey_request_range_answer_question_id_fkidx
   ON insight.survey_request_range_answer USING btree (question_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.survey_request_range_answer_question_id_fkidx
  IS 'Foreign Key Index insight.question (id)';

CREATE INDEX survey_request_range_answer_survey_request_id_fkidx
   ON insight.survey_request_range_answer USING btree (survey_request_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.survey_request_range_answer_survey_request_id_fkidx
  IS 'Foreign Key Index insight.survey_request (id)';

-- navis.insight.survey_request_reachable_questions -- 
CREATE INDEX survey_request_reachable_questions_question_id_fkidx
   ON insight.survey_request_reachable_questions USING btree (question_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.survey_request_reachable_questions_question_id_fkidx
  IS 'Foreign Key Index insight.question (id)';

CREATE INDEX survey_request_reachable_questions_survey_request_id_fkidx
   ON insight.survey_request_range_answer USING btree (survey_request_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.survey_request_reachable_questions_survey_request_id_fkidx
  IS 'Foreign Key Index insight.survey_request (id)';

-- navis.insight.survey_request_select_answer --
CREATE INDEX survey_request_select_answer_question_id_fkidx
   ON insight.survey_request_select_answer USING btree (question_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.survey_request_select_answer_question_id_fkidx
  IS 'Foreign Key Index insight.question (id)';

CREATE INDEX survey_request_select_answer_selection_id_fkidx
   ON insight.survey_request_select_answer USING btree (selection_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.survey_request_select_answer_selection_id_fkidx
  IS 'Foreign Key Index insight.selection (id)';

CREATE INDEX survey_request_select_answer_survey_request_id_fkidx
   ON insight.survey_request_select_answer USING btree (survey_request_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.survey_request_select_answer_survey_request_id_fkidx
  IS 'Foreign Key Index insight.survey_request (id)';

-- navis.insight.survey_request_text_answer --
CREATE INDEX survey_request_text_answer_question_id_fkidx
   ON insight.survey_request_text_answer USING btree (question_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.survey_request_text_answer_question_id_fkidx
  IS 'Foreign Key Index insight.question (id)';

CREATE INDEX survey_request_text_answer_survey_request_id_fkidx
   ON insight.survey_request_text_answer USING btree (survey_request_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.survey_request_text_answer_survey_request_id_fkidx
  IS 'Foreign Key Index insight.survey_request (id)';

-- navis.insight.survey_tag --
CREATE INDEX survey_tag_survey_id_fkidx
   ON insight.survey_tag USING btree (survey_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.survey_tag_survey_id_fkidx
  IS 'Foreign Key Index insight.survey (id)';

CREATE INDEX survey_tag_tag_id_fkidx
   ON insight.survey_tag USING btree (tag_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.survey_tag_tag_id_fkidx
  IS 'Foreign Key Index insight.tag (id)';

-- navis.insight.tag --
CREATE INDEX tag_description_id_fkidx
   ON insight.tag USING btree (description_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.tag_description_id_fkidx
  IS 'Foreign Key Index insight.i18n_string (id)';

CREATE INDEX tag_name_id_fkidx
   ON insight.tag USING btree (name_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.tag_name_id_fkidx
  IS 'Foreign Key Index insight.i18n_string (id)';

-- navis.insight.tag_tag --
CREATE INDEX tag_tag_parent_tag_id_fkidx
   ON insight.tag_tag USING btree (parent_tag_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.tag_tag_parent_tag_id_fkidx
  IS 'Foreign Key Index insight.tag (id)';

CREATE INDEX tag_tag_tag_id_fkidx
   ON insight.tag_tag USING btree (tag_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.tag_tag_tag_id_fkidx
  IS 'Foreign Key Index insight.tag (id)';

-- navis.insight.text_question --
CREATE INDEX text_question_placeholder_id_fkidx
   ON insight.text_question USING btree (placeholder_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.text_question_placeholder_id_fkidx
  IS 'Foreign Key Index insight.i18n_string (id)';
  
CREATE INDEX text_question_question_id_fkidx
   ON insight.text_question USING btree (question_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.text_question_question_id_fkidx
  IS 'Foreign Key Index insight.question (id)';

-- navis.insight.translation --
CREATE INDEX translation_i18n_string_id_fkidx
   ON insight.translation USING btree (i18n_string_id ASC NULLS LAST)
  TABLESPACE pg_default;
COMMENT ON INDEX insight.translation_i18n_string_id_fkidx
  IS 'Foreign Key Index insight.i18n_string (id)';
