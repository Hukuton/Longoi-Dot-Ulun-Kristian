package com.hukuton.longoidotulunkristian.listener;

import android.view.View;

/**
 * Created by Alixson on 01-Jul-16.
 */
public interface OnItemAdapterClickListener {
    void onImageViewClick(View item, int position, boolean favorite);
    void onTextWrapperClick(View view, int position);
}
