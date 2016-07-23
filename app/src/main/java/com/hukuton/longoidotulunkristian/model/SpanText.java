package com.hukuton.longoidotulunkristian.model;

/**
 * Created by Alixson on 23-Feb-16.
 */
public class SpanText {

    private String chord;
    private int start;
    private int end;

    public SpanText() {

    }

    public SpanText(String chord, int start, int end) {
        this.setChord(chord);
        this.setStart(start);
        this.setEnd(end);
    }

    public String getChord() {
        return chord;
    }

    public void setChord(String chord) {
        this.chord = chord;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

}