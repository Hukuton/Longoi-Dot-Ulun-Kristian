package com.hukuton.longoidotulunkristian.fragment.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.hukuton.longoidotulunkristian.ListValues;
import com.hukuton.longoidotulunkristian.R;
import com.hukuton.longoidotulunkristian.enums.Type;
import com.hukuton.longoidotulunkristian.factory.SimpleDbTableNameType;
import com.hukuton.longoidotulunkristian.gesture.MySimpleOnScaleGestureListener;
import com.hukuton.widget.TextViews;

import net.nightwhistler.htmlspanner.HtmlSpanner;

/**
 * Created by Alixson on 05-Jul-16.
 */
public class ViewDefaultStringFragment extends ViewBaseStringFragment implements View.OnTouchListener, MySimpleOnScaleGestureListener.OnScaleEndListener {

    private ScaleGestureDetector scaleGestureDetector;

    private TextViews textViewString;

    public Fragment newInstance(Type type, int position) {
        ViewDefaultStringFragment instance = new ViewDefaultStringFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TYPE_ENUM, type);
        bundle.putInt(POSITION, position);
        instance.setArguments(bundle);
        return (instance);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_default_view_string;
    }

    @Override
    protected void onSetupView(View view) {

        textViewString = (TextViews) view.findViewById(R.id.textViewString);
        textViewString.setTextSize(TypedValue.COMPLEX_UNIT_PX, getTextSizeFromDb());
        textViewString.setOnTouchListener(this);

        scaleGestureDetector = new ScaleGestureDetector(getContext(), new MySimpleOnScaleGestureListener(textViewString, this));

        String text;

        switch (mType){
            case ZABUR:
                text = getString(ListValues.getZaburStringIDs[mPagePosition]);
                textViewString.setText(new HtmlSpanner().fromHtml(text));
                textViewString.setGravity(Gravity.CENTER_HORIZONTAL);
                break;
            case ONGOVOKON:
                text = getString(ListValues.getOngovokonStringIDs[mPagePosition]);
                textViewString.setText(new HtmlSpanner().fromHtml(text));
                break;
            case POMINTAAN_DUA:
                text = getString(ListValues.getPomintaanDuaIDs[mPagePosition]);
                textViewString.setText(new HtmlSpanner().fromHtml(text));
                break;
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onScaleEnd(float size) {
        String tableName = new SimpleDbTableNameType().getDbTableNameFromType(mType);
        mBookDbHelper.saveTextSize(tableName, mPagePosition+1, size);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int pointer = event.getPointerCount();

        if(pointer == 1) {
            //Do the scrolling and also spannable text
            textViewString.setMovementMethod(LinkMovementMethod.getInstance());
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
    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
        String tableName = new SimpleDbTableNameType().getDbTableNameFromType(mType);
        mBookDbHelper.setFavourite(tableName, mPagePosition + 1, favorite);//+1 because sqlite start from id=1
    }

    @Override
    protected boolean setMaterialButtonFavourite() {
        String tableName = new SimpleDbTableNameType().getDbTableNameFromType(mType);
        return mBookDbHelper.isFavorite(tableName, mPagePosition + 1);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    private float getTextSizeFromDb() {
        String tableName = new SimpleDbTableNameType().getDbTableNameFromType(mType);
        return mBookDbHelper.getTextSize(tableName, mPagePosition+1);
    }
}
