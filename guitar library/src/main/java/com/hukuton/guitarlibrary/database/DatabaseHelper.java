package com.hukuton.guitarlibrary.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hukuton.guitarlibrary.classes.UnformattedPosition;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

/**
 * Created by Alixson on 22-Apr-16.
 */
public class DatabaseHelper extends SQLiteAssetHelper {

    private final static String DB_NAME 	= "database_chord.db";
    private final static int VERSION 		= 1;

    public static final String TABLE_CHORDS = "chords";

    private static DatabaseHelper mInstance = null;

    public static final String ID 			= "_id";
    public static final String ROOT			= "root";
    public static final String TYPE			= "type";
    public static final String BASS			= "bass";
    public static final String FRETS		= "frets";
    public static final String FINGERS		= "fingers";
    public static final String BARRE		= "barre";
    public static final String FIRST		= "first";
    public static final String INVERSION	= "inversion";

    public static DatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    /**
     *@param root Root of the chords, Eg: <b>C#</b>. Must not null
     *@param type Type of the chord, Eg: <b>m</b> for minor. Can be null
     *@param bass Bass of the chord, Eg: <b>B</b>. Can be null
     *
     *<p>Full chord name for the root, type and bass above is <b>C#m/B</p>
     */
    public Long[] filter(String root, String type, String bass) {
        SQLiteDatabase database = getReadableDatabase();

        if(root == null)
            root = "C";
        if (type == null)
            type = "";
        if (bass == null)
            bass = "";

        //String[] columns = new String[]{BASS, FINGERS, BARRE, FIRST, INVERSION};
        String selection = ROOT + "=?" + " AND " + TYPE + "=?" + " AND " + BASS + "=?";
        String[] selectionArgs = new String[]{root, type, bass};
        Cursor cursor = database.query(TABLE_CHORDS,  new String[] {ID}, selection, selectionArgs, null, null, null);


        ArrayList<Long> ids = null;

        try {
            if (cursor != null) {
                cursor.moveToFirst();
                ids = new ArrayList<>();

                while(!cursor.isAfterLast()) {
                    ids.add(cursor.getLong(cursor.getColumnIndex(ID)));
                    cursor.moveToNext();
                }
            }
        } finally {
            cursor.close();
        }
        return ids.toArray(new Long[ids.size()]);
    }

    public UnformattedPosition getUnformattedChordFromId(long id){
        SQLiteDatabase database = getReadableDatabase();
        UnformattedPosition uPos = new UnformattedPosition();

        String[] columns = new String[]{FRETS, FINGERS, BARRE, FIRST, INVERSION};
        String selection = ID + "=" + id;
        Cursor cursor = database.query(TABLE_CHORDS, columns, selection, null, null, null, null);

        try {
            if(cursor != null) {
                cursor.moveToFirst();
                uPos.setFret(cursor.getString(cursor.getColumnIndex(FRETS)));
                uPos.setFingers(cursor.getString(cursor.getColumnIndex(FINGERS)));
                uPos.setBarre(cursor.getString(cursor.getColumnIndex(BARRE)));
                uPos.setFirst(cursor.getString(cursor.getColumnIndex(FIRST)));
                uPos.setInversion(cursor.getString(cursor.getColumnIndex(INVERSION)));
            }
        } finally {
            cursor.close();
        }

        return uPos;
    }
}
