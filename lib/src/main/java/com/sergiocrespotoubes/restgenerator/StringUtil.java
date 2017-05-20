package com.sergiocrespotoubes.restgenerator;

public class StringUtil {

    static String regex_url = "(https?:\\/\\/)?(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";

    public static CharSequence trim(CharSequence s) {
        int start = 0;
        int end   = s.length();
        while (start < end && Character.isWhitespace(s.charAt(start))) {
            start++;
        }

        while (end > start && Character.isWhitespace(s.charAt(end - 1))) {
            end--;
        }

        return s.subSequence(start, end);
    }

    public static boolean isNull(String chars){
        return "".equals(chars) || chars == null || ( chars != null && "".equals(chars.trim()) ) || "null".equalsIgnoreCase(chars);
    }

    public static String capitalize(String text) {
        if(StringUtil.isNull(text)) return "";

        return text.substring(0,1).toUpperCase().concat(text.substring(1,text.length()).toLowerCase());

    }

}