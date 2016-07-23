package com.hukuton.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class TextViews extends TextView {

    public static Typeface normalTypeface;

    public TextViews(Context context) {
        super(context);
    }

    public TextViews(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViews(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTypeface(Typeface tf, int style) {
        normalTypeface = Typeface.createFromAsset(getContext().getAssets(), "font/tahoma.ttf");
        Typeface boldTypeface = Typeface.createFromAsset(getContext().getAssets(), "font/tahomabd.ttf");

        if (style == Typeface.BOLD) {
            super.setTypeface(boldTypeface);
        } else {
            super.setTypeface(normalTypeface);
        }
    }
}
