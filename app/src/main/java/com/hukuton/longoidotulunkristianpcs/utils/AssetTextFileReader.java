package com.hukuton.longoidotulunkristianpcs.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Alixson on 13-Apr-16.
 */
public class AssetTextFileReader {

    private String text;

    public AssetTextFileReader(Context c, String s){
        init(c, s);
    }

    private void init(Context c, String s) {
        try {
            InputStream is = c.getAssets().open(s);

            // We guarantee that the available method returns the total
            // size of the asset...  of course, this does mean that a single
            // asset can't be more than 2 gigs.
            int size = is.available();

            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Convert the buffer into a string.
            text = new String(buffer);

        } catch (IOException e) {
            // Should never happen!
            throw new RuntimeException(e);
        }
    }

    public String getText() {
        return text;
    }

}
