package com.hukuton.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.widget.TextView;

import com.hukuton.guitarlibrary.helper.ChordHelper;
import com.hukuton.longoidotulunkristian.gesture.ClickAbleSpan;
import com.hukuton.longoidotulunkristian.interfaces.OnTranspose;
import com.hukuton.longoidotulunkristian.listener.OnChordClickListener;
import com.hukuton.longoidotulunkristian.model.SpanText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alixson on 01-Jul-16.
 */
public class TextViewWithChords extends TextViews implements OnTranspose{

    private boolean clickableChord = false;
    private OnChordClickListener onChordClickListener;
    private int transposeValue = 0;

    public TextViewWithChords(Context context) {
        super(context);
    }

    public TextViewWithChords(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewWithChords(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     *
     * @param clickableChord true to set to clickable
     */
    public void setChordClickable(boolean clickableChord) {
        this.clickableChord = clickableChord;
        if(clickableChord){
            if(this.getText().length() > 0 && this.getText() != null)
                makeClickableLyric(this.getText().toString());
        }
    }

    /**
     *
     * @return true if the chord is set to clickable
     */
    public boolean isChordClickable(){
        return clickableChord;
    }

    public void setOnChordClickListener(OnChordClickListener onChordClickListener){
        this.onChordClickListener = onChordClickListener;
    }

    /**
     * @param s  String to make part of t clickable
     */
    private void makeClickableLyric(String s) {
        List<SpanText> spanTextArray = new ArrayList<>();

        boolean check = false;
        boolean check2 = false;
        int start = 0;
        int end = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '<' && !check) {
                start = i+1;
                check = true;
            }

            if (s.charAt(i) == '>' && check) {
                end = i;
                check = false;
                check2 = true;
            }

            if (!check && check2) {
                String ss = s.substring(start, end);
                //add transpose here
                //ss = transposerChord(ss, start, end);
                //SpanText spanText = new SpanText(ss, start, end);
                SpanText spanText = transposerChord(ss, start, end);
                //Log.i("sss" , "before " +  ss + "   after " + spanText.getChord());
                spanTextArray.add(spanText);
                check2 = false;

            }
        }

        s = s.replaceAll("<", " ");
        s = s.replaceAll(">", " ");
        this.setMovementMethod(new LinkMovementMethod());
        this.setText(s, TextView.BufferType.SPANNABLE);
        this.setHighlightColor(Color.TRANSPARENT);

        StringBuilder lyric = new StringBuilder(getText().toString());
        if(spanTextArray.size() > 0) {
            for (SpanText spantext : spanTextArray) {
                int charAfterChord = spantext.getStart() + spantext.getChord().length();
                lyric.replace(spantext.getStart(), charAfterChord, spantext.getChord());
            }
        }
        setText(lyric.toString());

        Spannable spannable = (Spannable) this.getText();
        if(spanTextArray.size() > 0 && spannable != null) {
            for (SpanText spantext : spanTextArray) {
                ClickAbleSpan clickableSpan = new ClickAbleSpan(onChordClickListener);
                spannable.setSpan(clickableSpan, spantext.getStart(), spantext.getEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    private SpanText transposerChord(String ss, int start, int end) {

        SpanText spanText;
        try {
            String chordString = ChordHelper.getChord(getContext(), ss, 0, transposeValue).getChordName();
            int lenBefore = ss.length();
            int lenAfter = chordString.length();

            if(lenBefore > lenAfter) {
                for(int i = 0; i < ss.length()-1; i++){
                    chordString = chordString + " ";
                }
            }
            spanText = new SpanText(chordString, start, start + lenAfter);

        } catch (Exception e){
            spanText = new SpanText(ss, start, end);
        };

        return spanText;
    }

    @Override
    public void transpose(int transpose) {
        transposeValue = transpose;
    }
}