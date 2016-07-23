package com.hukuton.guitarlibrary.classes;

/**
 * Created by Alixson on 22-Feb-16.
 */
public class UnformattedPosition {
    private String fret;
    private String fingers;
    private String barre;
    private String first;
    private String inversion;

    public UnformattedPosition() {
    }

    public UnformattedPosition(String fret, String fingers, String barre, String first, String inversion) {
        super();
        this.fret = fret;
        this.fingers = fingers;
        this.barre = barre;
        this.first = first;
        this.inversion = inversion;
    }

    public String getFret() {
        return fret;
    }

    public void setFret(String fret) {
        this.fret = fret;
    }

    public String getFingers() {
        return fingers;
    }

    public void setFingers(String fingers) {
        this.fingers = fingers;
    }

    public String getBarre() {
        return barre;
    }

    public void setBarre(String barre) {
        this.barre = barre;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getInversion() {
        return inversion;
    }

    public void setInversion(String inversion) {
        this.inversion = inversion;
    }

}
