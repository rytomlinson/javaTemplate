package com.navis.insightserver.pgtypes;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by darrell-shofstall on 1/12/18.
 */
public interface PostgreSQLEnum {

    // Expected to be applied to enums, which already have a name() method.
    String name();

    @JsonValue
    default String getPgName() {
        return PostgreSQLEnum.toPgIdentifier(name());
    }

    static String fromPgIdentifier(String pgIdentifier) {
        if (pgIdentifier == null) {
            return null;
        }
        return pgIdentifier.replaceAll("-", "_");
    }

    static String toPgIdentifier(String javaIdentifier) {
        if (javaIdentifier == null) {
            return null;
        }
        return javaIdentifier.replaceAll("_", "-");
    }

}
