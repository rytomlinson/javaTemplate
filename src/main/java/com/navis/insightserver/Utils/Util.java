package com.navis.insightserver.Utils;

/**
 * Created by darrell-shofstall on 9/21/17.
 */
public class Util {
    /**
     * Same a String.trim() except returns an empty string instead of null
     *
     * @param s the string or null
     * @return A trimmed string or an empty string if passed a null.
     */
    public static String trim(final String s) {
        if (s == null) return "";
        return s.trim();
    }
}
