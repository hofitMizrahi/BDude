package com.edudb.bdude.general.utils;

public class Utils {

    public static boolean isNullOrWhiteSpace(String string) {
        return string == null || string.isEmpty() || string.equals("null");
    }

    public static String getNonNullText(String text) {
        return isNullOrWhiteSpace(text) ? "" : text;
    }

}
