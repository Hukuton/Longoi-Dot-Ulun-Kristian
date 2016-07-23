package com.hukuton.longoidotulunkristian.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Alixson on 01-Jul-16.
 */
public class BookDatabaseHelper extends SQLiteAssetHelper {

    public final static String DATABASE     = "lduk.db";
    public final static int VERSION         = 1;

    public final static String ID           = "id";
    public final static String FAVOURITE    = "favourite";
    public static final String EDITED       = "edited";
    public static final String TEXT_SIZE    = "text_size";
    public static final String ORIGINAL_CHORD    = "original_chord";
    public static final String TRANSPOSED_CHORD_DISTANCE = "transposed_chord";


    public static final String TABLE_LONGOI = "table_longoi";
    public static final String TABLE_ZABUR  = "table_zabur";
    public static final String TABLE_HOTURAN_SUMAMBAYANG    = "table_hoturan_sumambayang";
    public static final String TABLE_ONGOVOKON              = "table_ongovokon";
    public static final String TABLE_POMINTAAN_DOA          = "table_pomintaan_doa";

    private static BookDatabaseHelper mInstance = null;

    public static BookDatabaseHelper getInstance(Context context){
        if (mInstance == null) {
            mInstance = new BookDatabaseHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    public BookDatabaseHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    /**
     *
     * @param table name of the table
     * @param id id of the row
     * @param bookmark true to set as favourte, false otherwise
     */
    public void setFavourite(String table, long id, boolean bookmark) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if(bookmark)
            contentValues.put(FAVOURITE, 1);
        else
            contentValues.put(FAVOURITE, 0);
        database.update(table, contentValues, ID + "=" + id, null);
    }

    /**
     *
     * @param table name of the table
     * @param id id of the row
     * @return true if is a favourite, false otherwise
     */
    public boolean isFavorite(String table, long id) {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] columns = new String[] { ID, FAVOURITE };
        Cursor c = database.query(table, columns, ID + "=" + id, null, null, null, null);

        try {
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                int value = c.getInt(1);
                if(value == 0){
                    return false;
                }else {
                    return true;
                }

            }
        } finally {
            c.close();
        }

        return false;
    }

    /**
     *  Save the text size to the database
     * @param table name of the table
     * @param id id of the row
     * @param size size of the text
     */
    public void saveTextSize(String table, long id, float size) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TEXT_SIZE, size);
        database.update(table, contentValues, ID + "=" + id, null);
    }

    /**
     *
     * @param table name of the table
     * @param id id of the row
     * @return size
     */
    public float getTextSize(String table, long id) {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] columns = new String[] { ID, TEXT_SIZE };
        Cursor c = database.query(table, columns, ID + "=" + id, null, null, null, null);
        try {
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                return c.getLong(1);
            } else
                return 0; //return medium size if null
        } finally {
            c.close();
        }

    }

    /**
     * @param db The database
     * @param table Name of the table
     * @param id id of the row
     * @return true if exist. False otherwise
     */
    private boolean isExist(SQLiteDatabase db, String table, long id) {
        String[] columns = new String[] { ID };
        Cursor c = db.query(table, columns, ID + "=" + id, null, null, null, null);
        try {
            if(c.getCount() != 0)
                return true;
            else
                return false;
        } finally {
            c.close();
        }
    }

    /**
     *
     * @param id id or position of song
     * @return original chord based on Longoi Dot Ulun Kristian book
     */
    public String getOriginalChord(long id) {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] columns = new String[] { ID, ORIGINAL_CHORD };
        Cursor c = database.query(TABLE_LONGOI, columns, ID + "=" + id, null, null, null, null);

        try {
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                return c.getString(1);
            } else
                return "";
        } finally {
            c.close();
        }
    }

    /**
     *
     * @param id id or position of song
     * @param distance of the transpose value. From:
     * <p>-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6</p>
     */
    public void saveTransposeDistance(long id, int distance) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRANSPOSED_CHORD_DISTANCE, distance);
        database.update(TABLE_LONGOI, contentValues, ID + "=" + id, null);
    }

    /**
     *
     * @param id id or position of song
     * @return the transpose value. From:
     * <p>-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6</p>
     */
    public int getTransposeDistance(long id) {
        SQLiteDatabase database = this.getReadableDatabase();
        String[] columns = new String[] { ID, TRANSPOSED_CHORD_DISTANCE };
        Cursor c = database.query(TABLE_LONGOI, columns, ID + "=" + id, null, null, null, null);

        try {
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                return c.getInt(1);
            } else
                return 0;
        } finally {
            c.close();
        }
    }
}
