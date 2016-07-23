package com.hukuton.guitarlibrary.classes;

/**
 * Created by Alixson on 22-Feb-16.
 */
public class Position {

    private int[] fret = new int[6];
    private int[] fingers = new int[6];
    private int[] barre = new int[3];
    private int first;
    private int inversion;

    public Position() {
    }

    public Position(int[] fret, int[] fingers, int[] barre, int first, int inversion) {
        super();
        this.fret = fret;
        this.fingers = fingers;
        this.barre = barre;
        this.first = first;
        this.inversion = inversion;
    }

    public int[] getFret() {
        return fret;
    }

    public void setFret(int[] fret) {
        this.fret = fret;
    }

    public int[] getFingers() {
        return fingers;
    }

    public void setFingers(int[] fingers) {
        this.fingers = fingers;
    }

    public int[] getBarre() {
        return barre;
    }

    public void setBarre(int[] barre) {
        this.barre = barre;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getInversion() {
        return inversion;
    }

    public void setInversion(int inversion) {
        this.inversion = inversion;
    }

}