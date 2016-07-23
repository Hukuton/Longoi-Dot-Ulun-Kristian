package com.hukuton.longoidotulunkristian.utils;

import java.util.regex.Pattern;

/**
 * Created by Alixson on 20-Jul-16.
 */
public class DetectChord {

    private static String p1 = "[A-G]";
    private static String p2 = "[H-Z]";
    private static String p3 = "c|e|f|h|k|l|n|o|p|q|r|t|v|w|x|y|z";

    private static Pattern pattern1 = Pattern.compile(p1);
    private static Pattern pattern2 = Pattern.compile(p2);
    private static Pattern pattern3 = Pattern.compile(p3);


    public static String detectChord(String lyric) {
        String newLyricWithChord = "";
        String[] lineArray;

        lineArray = lyric.split("\\r?\\n");

        for (int i = 0; i < lineArray.length; i++) {

            if (pattern1.matcher(lineArray[i]).find() && !pattern2.matcher(lineArray[i]).find() && !pattern3.matcher(lineArray[i]).find()) {
                newLyricWithChord = newLyricWithChord + insertArrow(lineArray[i]) + "\n";   // Is a line consist of Chord only
            } else {    //Is a line consists of lyric only
                newLyricWithChord = newLyricWithChord + lineArray[i] + "\n";
            }
        }
        return newLyricWithChord;
    }

    private static String insertArrow(String s) {
        int lastLocation = 0;
        String[] splits = s.split("\\s+");
        String newLine = "";

        for (String sp : splits) {
            if (sp.length() > 0) {
                int start = s.indexOf(sp, lastLocation);

                if(start == 0){
                    newLine = "<" + sp + ">";
                } else {
                    newLine = newLine + insertWhiteSpaces(start - newLine.length() - 1) + "<" + sp + ">";
                }
            }
        }
        return newLine;
    }

    private static String insertWhiteSpaces(int numberOfSpaces) {
        String whiteSpaces = "";
        int i = 0;
        while(i<numberOfSpaces){
            whiteSpaces = whiteSpaces + " ";
            i++;
        }
        return whiteSpaces;
    }
}
