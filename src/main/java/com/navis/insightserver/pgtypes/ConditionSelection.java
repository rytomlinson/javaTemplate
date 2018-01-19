package com.navis.insightserver.pgtypes;

/**
 * Created by darrell-shofstall on 1/17/18.
 */
public enum ConditionSelection implements PostgreSQLEnum {
    any,
    all,
    one_of,
    range ,
    exactly;
}
