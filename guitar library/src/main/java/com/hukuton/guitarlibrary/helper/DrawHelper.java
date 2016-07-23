package com.hukuton.guitarlibrary.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;

import com.hukuton.guitarlibrary.classes.Chord;

/**
 * Created by Alixson on 22-Feb-16.
 */

public class DrawHelper {

    private static int width;
    private static int height;
    private static int nutThickness;
    private static int fretThickness;
    private static int stringThickness;
    private static float fretboardWidth;
    private static float fretboardHeight;
    private static float fretWidth;
    private static float fretHeight;
    private static int imageStartX;
    private static int imageStartY;
    private static float imageStopX;
    private static float imageStopY;

    /**
     * @param context Used to open database and to getResources()
     * @param chordName Chord to draw
     * @param inversion Inversion of the chord
     * @param transposeDistance Transpose the chord
     * @return
     */
    public static BitmapDrawable getBitmapChord(Context context, String chordName, int inversion, int transposeDistance) throws Exception {

        //Always make square bitmap to ease drawing
        //Predefine dimension
        int width = 600;
        int height = 600;

        Bitmap tempBitmap = Bitmap.createBitmap(width, height,  Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(tempBitmap);

        Chord chord = ChordHelper.getChord(context, chordName, inversion, transposeDistance);

        DrawHelper.drawChord(canvas, chord);
        return new BitmapDrawable(context.getResources(), tempBitmap);
    }

    /**
     * Draw a complete chord on the guitar neck
     *
     * @param canvas
     * @param chord
     * @throws Exception
     */
    private static void drawChord(Canvas canvas, Chord chord) throws Exception {

        drawBaseLines(canvas, chord.getFirst());
        drawBarre(canvas, chord.getBarre(), chord.getFirst());
        drawFingerPosition(canvas, chord.getFret(), chord.getFingers(), chord.getFirst(), chord.getBarre());
        drawFretPosition(canvas, chord.getFirst());
        drawChordName(canvas, chord.getChordName());
    }

    /**Draw the fret wires and strings
     *
     * @param canvas
     * @param firstFret
     */
    private static void drawBaseLines(Canvas canvas, int firstFret) {

        width = canvas.getWidth();
        height = canvas.getHeight();

        nutThickness = width/40;
        fretThickness = nutThickness/3;
        stringThickness = nutThickness/3;

        fretboardWidth = width / 1.6f;
        fretboardHeight = height / 1.6f;

        fretWidth = fretboardWidth/5;
        fretHeight = fretboardHeight/5;

        imageStartX = width/8;
        imageStartY = height/4;

        imageStopX = imageStartX + fretboardWidth;
        imageStopY = imageStartY + fretboardHeight;

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);

        //Draw the nut. Make it thicker than fret
        if(firstFret == 1){
            paint.setStrokeWidth(nutThickness);
        } else { //is a fret
            paint.setStrokeWidth(fretThickness);
        }

        canvas.drawLine(imageStartX, imageStartY, imageStopX, imageStartY, paint);

        paint.setStrokeWidth(fretThickness);

        //Draw the frets, horizontal line
        //2nd to 5th fret
        canvas.drawLine(imageStartX, imageStartY + fretHeight * 1, imageStopX, imageStartY + fretHeight * 1, paint);
        canvas.drawLine(imageStartX, imageStartY + fretHeight * 2, imageStopX, imageStartY + fretHeight * 2, paint);
        canvas.drawLine(imageStartX, imageStartY + fretHeight * 3, imageStopX, imageStartY + fretHeight * 3, paint);
        canvas.drawLine(imageStartX, imageStartY + fretHeight * 4, imageStopX, imageStartY + fretHeight * 4, paint);
        canvas.drawLine(imageStartX, imageStartY + fretHeight * 5, imageStopX, imageStartY + fretHeight * 5, paint);

        paint.setStrokeWidth(stringThickness);

        //Draw the strings, vertical line
        //1st to 6th fret
        canvas.drawLine(imageStartX + fretWidth * 0, imageStartY, imageStartX + fretWidth * 0, imageStopY, paint);
        canvas.drawLine(imageStartX + fretWidth * 1, imageStartY, imageStartX + fretWidth * 1, imageStopY, paint);
        canvas.drawLine(imageStartX + fretWidth * 2, imageStartY, imageStartX + fretWidth * 2, imageStopY, paint);
        canvas.drawLine(imageStartX + fretWidth * 3, imageStartY, imageStartX + fretWidth * 3, imageStopY, paint);
        canvas.drawLine(imageStartX + fretWidth * 4, imageStartY, imageStartX + fretWidth * 4, imageStopY, paint);
        canvas.drawLine(imageStartX + fretWidth * 5, imageStartY, imageStartX + fretWidth * 5, imageStopY, paint);

    }

    /**
     * Draw circles on the six strings and numbers
     *
     * <p>
     * <b>0</b> : Open string
     * <b>1</b> : Index finger
     * <b>2</b> : Middle finger
     * <b>3</b> : Ring finger
     * <b>4</b> : Pinky finger
     * <b>x</b> : Muted string
     * </p>
     *
     * @param canvas
     * @param frets
     */
    private static void drawFingerPosition(Canvas canvas, int[] frets, int[] fingers, int first, int[] barre) throws Exception{
        float radius = fretWidth / (2 * 1.2f);
        float fretWidthCenter = fretWidth / 2;
        float textSize = (float) Math.sqrt(height * height + width * width) / 16;



        Paint paintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintCircle.setColor(Color.BLACK);

        Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setTextAlign(Paint.Align.CENTER);
        paintText.setTextSize(textSize);

        Paint paintHollowCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintHollowCircle.setStyle(Paint.Style.STROKE);
        paintHollowCircle.setColor(Color.BLACK);
        paintHollowCircle.setStrokeWidth(fretThickness);

        //Normalize frets so it does not go higher than 5
        //due to short fretboard in the drawing
        if(first > 1) {
            for (int i = 0; i < frets.length; i++) {
                frets[i] = frets[i] - first + 1;
            }
        }

        for(int i = 0; i<frets.length; i++) {

            //Draw 'x' above fretboard
            if(frets[i] <= -1) {
                paintText.setColor(Color.BLACK);
                float textTopX = imageStartX + fretWidth * i;
                float textTopY = height / 4.5f;

                canvas.drawText("×", textTopX, textTopY, paintText);
            }

            //Draw 'x' above fretboard
            else if(frets[i] == 0) {
                float textTopX = imageStartX + fretWidth * i;
                float textTopY = height / 4.5f;

                paintHollowCircle.setStyle(Paint.Style.STROKE);
                canvas.drawCircle(textTopX, textTopY/1.15f, radius/2 , paintHollowCircle);
            }

            //Draw circle and text inside the circle
            else{	//if(frets[i] > 0){
                float centerX = imageStartX + fretWidth * i;// + fretWidthCenter/2;
                float centerY = imageStartY + fretHeight * frets[i] - fretWidthCenter;
                float centerTextY = centerY + fretWidthCenter/2;

                paintText.setColor(Color.WHITE);
                canvas.drawCircle(centerX, centerY, radius, paintCircle);
                canvas.drawText(String.valueOf(fingers[i]), centerX, centerTextY, paintText);
            }

        }
    }

    /**Draw fret position on the right
     *
     * @param canvas
     * @param firstFret
     */
    private static void drawFretPosition(Canvas canvas, int firstFret) {

        float textSize = (float) Math.sqrt(height * height + width * width) / 16;
        float posX = imageStartX + fretWidth * 6.2f;
        float posY = imageStartY + fretHeight/1.4f;

        Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setTextAlign(Paint.Align.CENTER);
        paintText.setTextSize(textSize);
        paintText.setColor(Color.BLACK);

        canvas.drawText(String.valueOf(firstFret) + " fr", posX, posY, paintText);
    }

    /**Draw barre if exist
     *
     * @param canvas
     * @param barre
     */
    private static void drawBarre(Canvas canvas, int[] barre, int firstFret) {
        //The string numbering start from E to e string, 0 -> 5
        if(barre.length > 2) {
            int barreFret = barre[0];
            int from = 6 - barre[1];
            int to = 6 - barre[2];

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(nutThickness);

            float startX = imageStartX + fretWidth * from;
            float startY = imageStartY + fretHeight * (barreFret - firstFret) + fretWidth/2;

            float stopX = imageStartX + fretWidth * to;
            float stopY = imageStartY + fretHeight * (barreFret - firstFret) + fretWidth/2;

            canvas.drawLine(startX, startY, stopX, stopY, paint);
        }
    }

    private static void drawChordName(Canvas canvas, String chordName) {

        float textSize = (float) Math.sqrt(height * height + width * width) / 10;
        float centerX = width/2;
        float centerY = height / 7f;

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);

        canvas.drawText(chordName, centerX, centerY, paint);
    }

}
