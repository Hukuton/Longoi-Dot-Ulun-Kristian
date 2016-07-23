package com.hukuton.guitarlibrary.util;

import java.util.Locale;

/**
 * Created by Alixson on 22-Apr-16.
 */
public class Utility {

    /**
     * @author Alixson
     * Method to convert hexadecimal to decimal integer.
     * @param hex String of hex to be converted
     */
    public static int hex2dec(String hex){
        String digits = "0123456789ABCDEF";
        hex = hex.toUpperCase(Locale.getDefault());
        int val = 0;
        for (int i = 0; i < hex.length(); i++) {
            char c = hex.charAt(i);
            int d = digits.indexOf(c);
            val = 16*val + d;
        }
        return val;
    }

    public static int[] hexString2IntArray(String str) {
        int[] intArray = new int[str.length()];
        for(int i = 0; i < str.length(); i++) {
            if(Character.toString(str.charAt(i)).contentEquals("x"))
                intArray[i] = -1;
            else
                intArray[i] = hex2dec(Character.toString(str.charAt(i)));
        }
        return intArray;
    }

}
