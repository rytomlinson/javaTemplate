package com.navis.insightserver.pgtypes;

/**
 * Created by darrell-shofstall on 1/17/18.
 */
public enum QuestionType implements PostgreSQLEnum {
    range ,
    select,
    text,
    range_group,
    range_group_member,
    yes_no;
}
