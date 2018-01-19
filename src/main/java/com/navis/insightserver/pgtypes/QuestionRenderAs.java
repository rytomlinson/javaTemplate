package com.navis.insightserver.pgtypes;

/**
 * Created by darrell-shofstall on 1/17/18.
 */
public enum QuestionRenderAs implements PostgreSQLEnum {
    radio,
    checkbox,
    dropdown,
    text_input,
    textarea,
    yes_no,
    radio_group;
}
