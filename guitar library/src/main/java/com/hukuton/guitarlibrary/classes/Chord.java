package com.hukuton.guitarlibrary.classes;

/**
 * Created by Alixson on 22-Feb-16.
 */
public class Chord extends Position{

    private String chordName;

    public Chord() {
    }

    public Chord(int[] fret, int[] fingers, int[] barre, int first, int inversion) {
        super(fret, fingers, barre, first, inversion);
    }

    public Chord(String chordName, int[] fret, int[] fingers, int[] barre, int first, int inversion) {
        super(fret, fingers, barre, first, inversion);
        this.setChordName(chordName);
    }

    public String getChordName() {
        return chordName;
    }

    public void setChordName(String chordName) {
        this.chordName = chordName;
    }

}
