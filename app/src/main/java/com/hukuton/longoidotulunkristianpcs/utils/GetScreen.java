package com.hukuton.longoidotulunkristianpcs.utils;

import android.content.res.Resources;

/**
 * Created by Alixson on 06-Jul-16.
 */
public class GetScreen {
    public static int width() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int height() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
