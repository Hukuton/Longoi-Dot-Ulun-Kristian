package com.hukuton.longoidotulunkristianpcs.gesture;

/**
 * Created by Alixson on 01-Jul-16.
 */

import android.graphics.Color;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.hukuton.longoidotulunkristianpcs.listener.OnChordClickListener;
import com.hukuton.widget.TextViewWithChords;

/**
 * Created by Alixson on 23-Apr-16.
 *
 * This class extend ClickableSpan to enable to get the String clicked.
 * Besides, it remove the highlight and make the text color is different with non-clickable
 */
public class ClickAbleSpan extends ClickableSpan {

    private CharSequence clickedSequence;
    private OnChordClickListener onChordClickListener;

    public ClickAbleSpan(OnChordClickListener onChordClickListener) {
        this.onChordClickListener = onChordClickListener;
    }

    @Override
    public void onClick(View widget) {
        TextViewWithChords tv = (TextViewWithChords) widget;
        Spanned s = (Spanned) tv.getText();


        int start = s.getSpanStart(this);
        int end = s.getSpanEnd(this);

        clickedSequence = s.subSequence(start, end);
        onChordClickListener.onChordClick(clickedSequence.toString());
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
        ds.setColor(Color.RED);
    }

    public CharSequence getClickedSequence() {
        return clickedSequence;
    }

}