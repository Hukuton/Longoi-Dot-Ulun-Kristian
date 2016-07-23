package com.hukuton.longoidotulunkristianpcs.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Alixson on 14-Jul-16.
 */
public class CopyAssetToSDCard {


    public static void copyAssets(Context c, String pathFrom, String pathTo) {
        AssetManager assetManager = c.getAssets();
        String[] files = null;
        try {
            files = assetManager.list(pathFrom);
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        File sd_path = Environment.getExternalStorageDirectory();
        String dest_dir_path = sd_path + "/" + pathTo;
        File dest_dir = new File(dest_dir_path);

        try {
            createDir(dest_dir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String filename : files) {
            //System.out.println("File name => " + filename);
            InputStream in;
            OutputStream out;
            try {
                File myFile = new File(dest_dir_path + "/" + filename);
                if(!myFile.exists()) {
                    in = assetManager.open(pathFrom + "/" + filename);
                    out = new FileOutputStream(dest_dir_path + "/" + filename);
                    copyFile(in, out);
                    in.close();
                    //in = null;
                    out.flush();
                    out.close();
                    //out = null;
                }
            } catch (Exception e) {
                Log.e("tag", e.getMessage());
            }
        }
    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    private static void createDir(File dir) throws IOException {
        if (dir.exists()) {
            if (!dir.isDirectory()) {
                throw new IOException("Can't create directory, a file is in the way");
            }
        } else {
            dir.mkdirs();
            if (!dir.isDirectory()) {
                throw new IOException("Unable to create directory");
            }
        }
    }

    /**
     *
     * @param path to the textfiles in sdcard
     * @return number of textfiles in path directory
     */
    public static int getFileCount(String path) {
        File sd_path = Environment.getExternalStorageDirectory();
        String dest_dir_path = sd_path + "/" + path;
        File dest_dir = new File(dest_dir_path);
        if(!dest_dir.exists()) {
            dest_dir.mkdir();
            return -1;
        } else {
            return dest_dir.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".txt");
                }
            }).length;
        }
    }
}
