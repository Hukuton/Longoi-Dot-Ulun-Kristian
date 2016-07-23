package com.hukuton.longoidotulunkristianpcs;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.hukuton.longoidotulunkristianpcs.fragment.HoturanSumambayangListFragment;
import com.hukuton.longoidotulunkristianpcs.fragment.LongoiListFragment;
import com.hukuton.longoidotulunkristianpcs.fragment.OngovokonListFragment;
import com.hukuton.longoidotulunkristianpcs.fragment.PomintaanDuaListFragment;
import com.hukuton.longoidotulunkristianpcs.fragment.ZaburListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alixson on 26-Mar-16.
 */
public class MainListActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if(viewPager != null) {
            setupViewPager(viewPager);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new LongoiListFragment(), "Longoi");
        adapter.addFragment(new HoturanSumambayangListFragment(), "Hoturan Sumambayang");
        adapter.addFragment(new ZaburListFragment(), "Zabur");
        adapter.addFragment(new OngovokonListFragment(), "Ongovokon");
        adapter.addFragment(new PomintaanDuaListFragment(), "Pomintaan Dua");
        viewPager.setAdapter(adapter);

    }

    private static class Adapter extends FragmentPagerAdapter {

        private final List<Fragment> fragments = new ArrayList<>();
        private final List<String> fragmentTitle = new ArrayList<>();
        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitle.add(title);
        }

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_exit){
            finish();
            overridePendingTransition(0, R.anim.slide_out_left);
        }if(item.getItemId() == R.id.action_about){
            Intent i = new Intent(this, AboutActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
