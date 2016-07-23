package com.hukuton.longoidotulunkristianpcs.fragment.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.hukuton.guitarlibrary.helper.ChordHelper;
import com.hukuton.longoidotulunkristianpcs.R;
import com.hukuton.longoidotulunkristianpcs.utils.GetScreen;
import com.viewpagerindicator.CirclePageIndicator;

public class DialogChordFragment extends DialogFragment implements ViewPager.OnPageChangeListener {

    private int count;
    private String stringChord;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dialog_chord, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);

        stringChord = getArguments().getString("chord");
        count = ChordHelper.getChordIds(getContext(), stringChord, 0).length;
        setupViewPager();
        showIndicator(view, viewPager);
        return view;
    }

    private void showIndicator(View view, ViewPager pager) {
        CirclePageIndicator mViewPagerIndicator = (CirclePageIndicator) view.findViewById(R.id.viewpagerindicator);
        mViewPagerIndicator.setViewPager(pager);
        mViewPagerIndicator.setVisibility(View.VISIBLE);
        mViewPagerIndicator.setOnPageChangeListener(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        int w = GetScreen.width();
        w = w - w / 4;
        getDialog().getWindow().setLayout(w, w);
    }

    private void setupViewPager() {
        PagerAdapter adapter = new PagerAdapter(this.getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new ViewChordFragment().newInstance(stringChord, position);
        }

        @Override
        public int getCount() {
            return count;
        }
    }
}
