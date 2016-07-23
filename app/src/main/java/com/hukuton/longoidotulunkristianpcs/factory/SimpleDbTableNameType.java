package com.hukuton.longoidotulunkristianpcs.factory;

import com.hukuton.longoidotulunkristianpcs.database.BookDatabaseHelper;
import com.hukuton.longoidotulunkristianpcs.enums.Type;

/**
 * Created by Alixson on 06-Jul-16.
 */
public class SimpleDbTableNameType {
    public String getDbTableNameFromType(Type type) {
        if (type == Type.LONGOI)
            return BookDatabaseHelper.TABLE_LONGOI;
        else if (type == Type.HOTURAN_SUMAMBAYANG)
            return BookDatabaseHelper.TABLE_HOTURAN_SUMAMBAYANG;
        else if (type == Type.ZABUR)
            return BookDatabaseHelper.TABLE_ZABUR;
        else if (type == Type.ONGOVOKON)
            return BookDatabaseHelper.TABLE_ONGOVOKON;
        else if (type == Type.POMINTAAN_DUA)
            return BookDatabaseHelper.TABLE_POMINTAAN_DOA;
        return null;
    }
}
