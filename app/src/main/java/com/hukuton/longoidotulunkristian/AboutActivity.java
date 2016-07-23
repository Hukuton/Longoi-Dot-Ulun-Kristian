package com.hukuton.longoidotulunkristian;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Alixson on 19-Jun-16.
 */
public class AboutActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.fb).setOnClickListener(this);
        TextView tv = (TextView) findViewById(R.id.version);

        String versionName = "2";

        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        tv.setText("Version: " + versionName);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        String pageId = "199305283786450";
        try{
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + pageId));
        } catch (Exception e){
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook/" + pageId));
        }
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        } if(item.getItemId() == R.id.action_github){
            openGithubInBrowser();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openGithubInBrowser() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_about, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
