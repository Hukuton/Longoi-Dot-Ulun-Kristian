package com.hukuton.longoidotulunkristianpcs.gesture;

import android.util.TypedValue;
import android.view.ScaleGestureDetector;
import android.widget.TextView;

/**
 * Created by Alixson on 23-Apr-16.
 */
public class MySimpleOnScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

    private TextView textView;
    private float safeCheck;

    //Smallest text size unzoomed
    public static final int SMALLEST_SIZE = 18;
    //Largest text size zoomed
    private static final int LARGEST_SIZE = 100;

    OnScaleEndListener onScaleEndListener;

    public MySimpleOnScaleGestureListener(TextView view) {
        textView = view;
    }

    public MySimpleOnScaleGestureListener(TextView view, OnScaleEndListener onScaleEndListener) {
        textView = view;
        this.onScaleEndListener = onScaleEndListener;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float oldSize = textView.getTextSize();
        float factor = Math.max(0.5f, Math.min(detector.getScaleFactor(), 2f));
        float newSize = oldSize * factor;
        safeCheck = Math.abs(newSize - oldSize);
        if (newSize >= SMALLEST_SIZE && newSize <= LARGEST_SIZE && safeCheck < 3) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, newSize);
            if(onScaleEndListener != null)
                onScaleEndListener.onScaleEnd(newSize);
        }
        return true; //super.onScale(detector);
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return super.onScaleBegin(detector);
    }

    public interface OnScaleEndListener{
        void onScaleEnd(float size);
    }
}