package com.akabex86.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public static int countMatches(String regex, String text) {
        //This is used to track the number of e.g. swear words or other occurences of duplicates based on a regex pattern within a string
        //Example usage: Regex.countMatches("","");
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        int matches = 0;
        while (matcher.find()) {
            matches++;
        }
        return matches;
    }
    public static boolean containsIllegalCharacters(String input){
        //Returns TRUE if the input contains anything other than 0-9 or a-z. (plus a few special characters)
        Pattern pattern = Pattern.compile("[^a-z0-9üäöß_]",Pattern.CASE_INSENSITIVE);
        Matcher m = pattern.matcher(input);
        return m.find();
    }
    public static boolean isOutOfExtendedASCIIRange(String input){
        //Returns TRUE if the input contains anything outside of the extended ASCII range.
        String regex = "[^\\x00-\\xFF]";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        return matcher.find();
    }
}
