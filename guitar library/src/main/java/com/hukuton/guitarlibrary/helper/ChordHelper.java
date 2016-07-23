package com.hukuton.guitarlibrary.helper;

import android.content.Context;

import com.hukuton.guitarlibrary.classes.Chord;
import com.hukuton.guitarlibrary.classes.UnformattedPosition;
import com.hukuton.guitarlibrary.database.DatabaseHelper;
import com.hukuton.guitarlibrary.util.Utility;

import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alixson on 22-Feb-16.
 */

public class ChordHelper {

    public static Chord getChord(Context context, String chordName) throws Exception{
        return getChord(context, chordName, 0, 0);
    }

    public static Chord getChord(Context context, String chordName, int inversion) throws Exception{
        return getChord(context, chordName, inversion, 0);
    }

    public static Chord getChord(Context context, String chordName, int inversion, int transposeDistance) throws Exception{

        Chord chord = new Chord();
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(context);

        try {
            chordName = chordName.replaceAll(" ", "");

            //transpose if transposeDistance != 0
            if(transposeDistance != 0){
                String[] rtbt = chordTokenizer(chordName);
                if(rtbt[0].length() > 0)
                    rtbt[0] = transposeKey(rtbt[0], transposeDistance);
                if(rtbt[2].length() > 0)
                    rtbt[2] = transposeKey(rtbt[2], transposeDistance);

                chordName = rtbt[0] + rtbt[1];
                if(rtbt[2].length() > 0)
                    chordName = chordName + "/" + rtbt[2];
            }

            chord.setChordName(chordName);

            String[] rtb = chordTokenizer(chordName);

            //Because database have # only.
            if (rtb[0].contains("b")){
                rtb[0] = change2Sharp(rtb[0]);
            }

            Long[] ids = dbHelper.filter(rtb[0], rtb[1], rtb[2]);



            if(inversion > ids.length)
                inversion = ids.length - 1;
            if(inversion < 0)
                inversion = 1;

            Long id = ids[inversion];
            UnformattedPosition uPos = dbHelper.getUnformattedChordFromId(id);
            chord = formatChordPosition(chord, uPos);
            chord.setChordName(chordName);
            return chord;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbHelper.close();
        }
        return null;
    }

    /**
     *
     * @param context
     * @param chordName
     * @param transposeDistance
     * @return id array of chordName
     */
    public static Long[] getChordIds(Context context, String chordName, int transposeDistance){
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(context);

        try {
            chordName = chordName.replaceAll(" ", "");

            //transpose if transposeDistance != 0
            if(transposeDistance != 0){
                String[] rtbt = chordTokenizer(chordName);
                if(rtbt[0].length() > 0)
                    rtbt[0] = transposeKey(rtbt[0], transposeDistance);
                if(rtbt[2].length() > 0)
                    rtbt[2] = transposeKey(rtbt[2], transposeDistance);

                chordName = rtbt[0] + rtbt[1];
                if(rtbt[2].length() > 0)
                    chordName = chordName + "/" + rtbt[2];
            }


            String[] rtb = chordTokenizer(chordName);

            //Because database have # only.
            if (rtb[0].contains("b")){
                rtb[0] = change2Sharp(rtb[0]);
            }

            Long[] ids = dbHelper.filter(rtb[0], rtb[1], rtb[2]);

            return ids;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbHelper.close();
        }
        return null;
    }

    /**
     * Transpose a key with distance, return the transposed key name
     *
     * @param chordName
     * @param distance  value from -12 to 12
     */
    private static String transposeKey(String chordName, int distance) {
        String chord = chordName;
        if (chord == null) {
            return null;
        }

        chord = chord.toLowerCase(Locale.getDefault());
        // The first letter
        chord = Character.toString(chord.charAt(0)).toUpperCase(Locale.getDefault()) + chord.substring(1);
        // The letter after "/" character
        int theChar = chord.indexOf("/");
        if(theChar > -1) {
            chord = chord.substring(0, theChar) +
                    Character.toString(chord.charAt(theChar)).toUpperCase(Locale.getDefault()) +
                    chord.substring(theChar + 1); //here
        }

        String[] sameScale = new String[]{"Db", "C#", "Eb", "D#", "Gb", "F#", "Ab", "G#", "Bb", "A#"};
        String[] scale = new String[]{"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
        Pattern p;
        Matcher m;
        p = Pattern.compile("([DEGAB]b)");
        m = p.matcher(chord);
        while (m.find()) {
            String token = m.group(1);
            String newValue = sameScale[Arrays.asList(sameScale).indexOf(token) + 1];
            chord = chord.replaceAll(token, newValue);
        }

        p = Pattern.compile("([CDEFGAB]#?)");
        m = p.matcher(chord);
        while (m.find()) {
            String token = m.group(1);
            //String newValue = sameScale[(Arrays.asList(sameScale).indexOf(token) + 1)];
            int i = (Arrays.asList(scale).indexOf(token) + distance) % scale.length;
            String newValue = scale[i < 0 ? i + scale.length : i];
            chord = chord.replaceAll(token, newValue);
        }

        chord = chord.replaceAll("^A#", "Bb").replaceAll("^D#", "Eb");

        return chord;
    }

    /**
     * @param chord
     * @param uPos
     * @return Chord with formatted position
     */
    private static Chord formatChordPosition(Chord chord, UnformattedPosition uPos) {
        chord.setFret(Utility.hexString2IntArray(uPos.getFret()));
        chord.setFingers(Utility.hexString2IntArray(uPos.getFingers()));
        chord.setBarre(Utility.hexString2IntArray(uPos.getBarre()));
        chord.setFirst(Utility.hex2dec(uPos.getFirst()));
        chord.setInversion(Utility.hex2dec(uPos.getInversion()));
        return chord;
    }

    private static String[] chordTokenizer(String chordName) {
        if(chordName != null && chordName.length() > 0){
            //C#m/F#
            String root_type;
            String root;
            String type;
            String bass = "";

            //Have bass
            if(chordName.contains("/")) {
                String[] c = new String[2];
                c = chordName.split("/");
                root_type = c[0];
                bass = c[1];
                if(bass.length() > 2){
                    bass = bass.substring(0, 2);
                }
            }
            //Do not have bass
            else {
                root_type = chordName;
            }

            root = getChordBaseName(root_type);
            type = getChordBaseNameTail(root_type);

            return new String[]{root, type, bass};
        }
        return new String[]{chordName, "", ""};
    }

    /**
     * From Abm7 ==> Ab
     *
     * @param name
     * @return
     */
    private static String getChordBaseName(String name) {
        if (name != null && name.length() > 0) {
            String result = name.substring(0, 1);
            if (name.length() > 1) {
                if (name.substring(1, 2).equals("#")
                        || name.substring(1, 2).equals("b")) {
                    result += name.substring(1, 2);
                }
            }
            return result;
        } else {
            return null;
        }
    }

    /**
     * From Abm7 ==> m7
     *
     * @param name
     * @return
     */
    private static String getChordBaseNameTail(String name) {
        int start = 0;
        if (name.length() > 1) {
            if (name.substring(1, 2).equals("#") || name.substring(1, 2).equals("b")) {
                start = 1;
            }
        }
        return name.substring(start + 1, name.length());
    }

    private static String change2Sharp(String key) {
        String[] keySharp 	= {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
        String[] keyFlat	= {"C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B"};

        String flat2Sharp = null;

        for(int i = 0; i < keySharp.length; i++){
            if(keyFlat[i].contentEquals(key)){
                flat2Sharp = keySharp[i];
                break;
            }
        }
        return flat2Sharp;
    }


}