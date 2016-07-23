package com.hukuton.longoidotulunkristianpcs.fragment.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.hukuton.guitarlibrary.helper.DrawHelper;
import com.hukuton.longoidotulunkristianpcs.EditLyricActivity;
import com.hukuton.longoidotulunkristianpcs.R;
import com.hukuton.longoidotulunkristianpcs.database.BookDatabaseHelper;
import com.hukuton.longoidotulunkristianpcs.enums.Type;
import com.hukuton.longoidotulunkristianpcs.factory.SimpleDbTableNameType;
import com.hukuton.longoidotulunkristianpcs.fragment.dialog.DialogChordFragment;
import com.hukuton.longoidotulunkristianpcs.fragment.dialog.DialogTransposeFragment;
import com.hukuton.longoidotulunkristianpcs.gesture.MySimpleOnScaleGestureListener;
import com.hukuton.longoidotulunkristianpcs.interfaces.OnTranspose;
import com.hukuton.longoidotulunkristianpcs.listener.OnChordClickListener;
import com.hukuton.longoidotulunkristianpcs.utils.AssetTextFileReader;
import com.hukuton.longoidotulunkristianpcs.utils.TextFileUtil;
import com.hukuton.widget.TextViewWithChords;
import com.hukuton.widget.TextViews;

/**
 * Created by Alixson on 05-Jul-16.
 */
public class ViewLongoiLyricFragment extends ViewBaseStringFragment implements View.OnTouchListener, MySimpleOnScaleGestureListener.OnScaleEndListener, OnChordClickListener, OnTranspose {

    private ScaleGestureDetector scaleGestureDetector;

    private TextViewWithChords textViewLyric;

    private String textLyric;

    SharedPreferences prefs;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String SHOW_HIDE_CHORDS = "show_hide_chords";

    public Fragment newInstance(Type type, int position) {
        ViewLongoiLyricFragment instance = new ViewLongoiLyricFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TYPE_ENUM, type);
        bundle.putInt(POSITION, position);
        instance.setArguments(bundle);
        return (instance);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_longoi_view_lyric;
    }

    @Override
    protected void onSetupView(View view) {
        textViewLyric = (TextViewWithChords) view.findViewById(R.id.textViewLyric);
        textViewLyric.setTextSize(TypedValue.COMPLEX_UNIT_PX, getTextSizeFromDb());

        prefs = getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        scaleGestureDetector = new ScaleGestureDetector(getContext(), new MySimpleOnScaleGestureListener(textViewLyric, this));

        setHasOptionsMenu(true);
        setupLyricView();
    }

    private void setupLyricView() {
        String path;
        if (prefs.getBoolean(SHOW_HIDE_CHORDS, true)) {
            //path = "book_component/longoi/with_chords/"+ (mPagePosition+1) +".txt";
            path = "Longoi Dot Ulun Kristian/longoi_with_chords/" + (mPagePosition + 1) + ".txt";
            textLyric = TextFileUtil.readTextFile(path);
            textViewLyric.setTypeface(Typeface.MONOSPACE);
        } else {
            path = "book_component/longoi/without_chords/" + (mPagePosition + 1) + ".txt";
            textViewLyric.setTypeface(TextViews.normalTypeface);
            textLyric = new AssetTextFileReader(getContext(), path).getText();
        }

        textViewLyric.setText(textLyric); //set text before transpose

        //transpose distace is saved and use first open
        textViewLyric.transpose(mBookDbHelper.getTransposeDistance(mPagePosition + 1));

        textViewLyric.setOnChordClickListener(this);
        textViewLyric.setChordClickable(true);
        textViewLyric.setOnTouchListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        setupLyricView();
    }

    @Override
    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
        mBookDbHelper.setFavourite(BookDatabaseHelper.TABLE_LONGOI, mPagePosition + 1, favorite);//+1 because sqlite start from id=1
    }

    @Override
    protected boolean setMaterialButtonFavourite() {
        return mBookDbHelper.isFavorite(BookDatabaseHelper.TABLE_LONGOI, mPagePosition + 1);
    }

    @Override
    public void onChordClick(String chordString) {
        try {
            DrawHelper.getBitmapChord(getContext(), chordString, 0, 0);
            Bundle bundle = new Bundle();
            bundle.putString("chord", chordString);

            DialogChordFragment dcFragment = new DialogChordFragment();
            dcFragment.setArguments(bundle);
            dcFragment.show(getChildFragmentManager(), "");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Sorry, " + chordString + " chord not found in database!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onScaleEnd(float size) {
        String tableName = new SimpleDbTableNameType().getDbTableNameFromType(mType);
        mBookDbHelper.saveTextSize(tableName, mPagePosition + 1, size);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int pointer = event.getPointerCount();

        if (pointer == 1) {
            //Do the scrolling and also spannable text
            textViewLyric.setMovementMethod(LinkMovementMethod.getInstance());
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

    private float getTextSizeFromDb() {
        return mBookDbHelper.getTextSize(BookDatabaseHelper.TABLE_LONGOI, mPagePosition + 1);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_longoi_lyric, menu);
        super.onCreateOptionsMenu(menu, inflater);
        if (prefs.getBoolean(SHOW_HIDE_CHORDS, true)) {
            menu.findItem(R.id.action_transpose).setVisible(true);
            menu.findItem(R.id.action_edit_lyric).setVisible(true);
            menu.findItem(R.id.action_show_hide_chords).setTitle("Hide Chords");
        } else {
            menu.findItem(R.id.action_transpose).setVisible(false);
            menu.findItem(R.id.action_edit_lyric).setVisible(false);
            menu.findItem(R.id.action_show_hide_chords).setTitle("Show Chords");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_transpose) {
            toTransposeDialog();
        } else if (item.getItemId() == R.id.action_show_hide_chords) {
            showOrHideChords();
        } else if (item.getItemId() == R.id.action_edit_lyric) {
            toEditTextActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void transpose(int transposeDistance) {
        textViewLyric.transpose(transposeDistance);
        textViewLyric.setText(textLyric);
        textViewLyric.setChordClickable(true);
        mBookDbHelper.saveTransposeDistance(mPagePosition + 1, transposeDistance);
    }

    private void toTransposeDialog() {
        String origChord = mBookDbHelper.getOriginalChord(mPagePosition + 1);
        int transDistance = mBookDbHelper.getTransposeDistance(mPagePosition + 1);

        Bundle bundle = new Bundle();
        bundle.putString(BookDatabaseHelper.ORIGINAL_CHORD, origChord);
        bundle.putInt(BookDatabaseHelper.TRANSPOSED_CHORD_DISTANCE, transDistance);

        DialogTransposeFragment dtFragment = new DialogTransposeFragment();
        dtFragment.setArguments(bundle);
        dtFragment.setTargetFragment(this, 0);
        dtFragment.show(getChildFragmentManager(), "");
    }

    private void showOrHideChords() {
        if (prefs.getBoolean(SHOW_HIDE_CHORDS, true)) {
            prefs.edit().putBoolean(SHOW_HIDE_CHORDS, false).commit();
            setupLyricView();
        } else {
            prefs.edit().putBoolean(SHOW_HIDE_CHORDS, true).commit();
            setupLyricView();
        }
        ActivityCompat.invalidateOptionsMenu(this.getActivity());
    }

    private void toEditTextActivity() {
        Intent i = new Intent(getContext(), EditLyricActivity.class);
        i.putExtra(EditLyricActivity.EDIT_LYRIC_NUMBER, mPagePosition);
        startActivity(i);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
