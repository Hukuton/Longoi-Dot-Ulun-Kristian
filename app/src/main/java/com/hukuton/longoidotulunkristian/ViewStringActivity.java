package com.hukuton.longoidotulunkristian;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.hukuton.longoidotulunkristian.enums.Type;
import com.hukuton.longoidotulunkristian.factory.SimpleTypeTitleFactory;
import com.hukuton.longoidotulunkristian.fragment.view.ViewDefaultStringFragment;
import com.hukuton.longoidotulunkristian.fragment.view.ViewHoturanSumambayangStringFragment;
import com.hukuton.longoidotulunkristian.fragment.view.ViewLongoiLyricFragment;
import com.hukuton.longoidotulunkristian.model.ActionBarTitle;
import com.hukuton.longoidotulunkristian.model.IntentData;

/**
 * Created by Alixson on 01-Jul-16.
 */
public class ViewStringActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private IntentData intentData;
    public static final String JUSTDOITALREADY = "its_something";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_string);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        String jsonObject;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            jsonObject = extras.getString(JUSTDOITALREADY);
            intentData = new Gson().fromJson(jsonObject, IntentData.class);

        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if(viewPager != null)
            setupViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(intentData.getPosition());
        viewPager.addOnPageChangeListener(this);
        ActionBarTitle actionBarTitle = new SimpleTypeTitleFactory().getInstantTitleAndSubtitle(intentData.getType(), intentData.getPosition());
        changeTitleAndSubtitle(actionBarTitle);
    }

    @Override
    public void onPageSelected(int position) {
        ActionBarTitle actionBarTitle = new SimpleTypeTitleFactory().getInstantTitleAndSubtitle(intentData.getType(), position);
        changeTitleAndSubtitle(actionBarTitle);
    }

    private void changeTitleAndSubtitle(ActionBarTitle abt) {
        getSupportActionBar().setTitle(abt.getTitle());
        getSupportActionBar().setSubtitle(abt.getSubtitle());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageScrollStateChanged(int state) {}

    private class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //Because Longoi and Hoturan has its own ViewString
            //Others go to default
            if (intentData.getType() == Type.LONGOI)
                return new ViewLongoiLyricFragment().newInstance(intentData.getType(), position);
            else if (intentData.getType() == Type.HOTURAN_SUMAMBAYANG)
                return new ViewHoturanSumambayangStringFragment().newInstance(intentData.getType(), position);
            else
                return new ViewDefaultStringFragment().newInstance(intentData.getType(), position);
        }

        @Override
        public int getCount() {
            return intentData.getCount();
        }
    }
}
