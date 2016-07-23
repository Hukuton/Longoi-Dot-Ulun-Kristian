package com.hukuton.longoidotulunkristian.utils;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Alixson on 15-Jul-16.
 */
public class TextFileUtil {

    public static String readTextFile(String path) {
        StringBuilder text = new StringBuilder();

        try {
            File sdcard = Environment.getExternalStorageDirectory();
            File file = new File(sdcard, path);

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException e) {
            Log.e("Exception", "File read failed: " + e.toString());
        }
        return text.toString();
    }

    public static void writeToTextFile(String path, String content) throws IOException {
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, path);

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file));
        outputStreamWriter.write(content);
        outputStreamWriter.close();
    }
}