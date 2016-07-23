package com.hukuton.longoidotulunkristianpcs;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hukuton.longoidotulunkristianpcs.fragment.dialog.DialogExitWithoutSave;
import com.hukuton.longoidotulunkristianpcs.gesture.MySimpleOnScaleGestureListener;
import com.hukuton.longoidotulunkristianpcs.interfaces.OnDialogItemClick;
import com.hukuton.longoidotulunkristianpcs.utils.AssetTextFileReader;
import com.hukuton.longoidotulunkristianpcs.utils.DetectChord;
import com.hukuton.longoidotulunkristianpcs.utils.TextFileUtil;

import java.io.IOException;

/**
 * Created by Alixson on 15-Jul-16.
 */
public class EditLyricActivity extends AppCompatActivity implements View.OnTouchListener, TextWatcher, OnDialogItemClick {

    public static final String EDIT_LYRIC_NUMBER = "edit_lyric_number";

    private ScaleGestureDetector scaleGestureDetector;

    private EditText editText;
    private String path;
    private int pageNumber;
    private boolean isTextChanged = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lyric);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editText = (EditText) findViewById(R.id.editText);
        scaleGestureDetector = new ScaleGestureDetector(this, new MySimpleOnScaleGestureListener(editText));

        Bundle extras = getIntent().getExtras();
        pageNumber = extras.getInt(EDIT_LYRIC_NUMBER);
        path = "Longoi Dot Ulun Kristian/longoi_with_chords/" + (pageNumber + 1) + ".txt";
        getSupportActionBar().setTitle(ListValues.LONGOI[pageNumber]);

        String content = TextFileUtil.readTextFile(path);
        editText.setText(content);
        editText.setOnTouchListener(this);
        editText.setTypeface(Typeface.MONOSPACE);
        editText.addTextChangedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_save){
            actionSaveEditedLyric();
        } else if (item.getItemId() == R.id.action_detect_chords) {
            actionDetectChords();
        } else if(item.getItemId() == R.id.action_default_lyric) {
            actionDefaultLyric();
        } else if (item.getItemId() == R.id.action_back) {
            actionBack();
        }
        return super.onOptionsItemSelected(item);
    }

    private void actionDefaultLyric() {
        String path2 = "book_component/longoi/with_chords/"+ (pageNumber+1) +".txt";
        String text = new AssetTextFileReader(this, path2).getText();
        editText.setText(text);
    }

    private void actionDetectChords() {
        String newLyricLine = editText.getText().toString();
        newLyricLine = newLyricLine.replaceAll("<", " ").replaceAll(">", " ");
        newLyricLine = DetectChord.detectChord(newLyricLine);
        editText.setText(newLyricLine);
    }

    private void actionBack() {
        if(isTextChanged) {
            DialogExitWithoutSave dialogExitWithoutSave = new DialogExitWithoutSave();
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().add(dialogExitWithoutSave, "dialog_exit").commit();
        } else
            finish();
    }

    private void actionSaveEditedLyric() {
        String editedLyric = editText.getText().toString();
        if(editedLyric.length() > 0){
            try {
                TextFileUtil.writeToTextFile(path, editedLyric);
                Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
                isTextChanged = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            Toast.makeText(this, "Write something!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_edit_lyric, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        actionBack();
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int pointer = event.getPointerCount();

        if(pointer == 1) {
            //Do the scrolling and also spannable text
            editText.setMovementMethod(LinkMovementMethod.getInstance());
        }
        //Do the scaling pinch
        else {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Disable ScrollView to intercept touch events
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    scaleGestureDetector.onTouchEvent(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    // Disable ScrollView to intercept touch events
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    scaleGestureDetector.onTouchEvent(event);
                    break;
                case MotionEvent.ACTION_UP:
                    // Allow ScrollView to intercept touch events
                    view.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void afterTextChanged(Editable editable) {
        isTextChanged = true;
    }

    @Override
    public void onPositiveButtonClick() {
        finish();
    }

    @Override
    public void onNegativeButtonClick() {

    }
}
