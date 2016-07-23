package com.hukuton.longoidotulunkristian;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.hukuton.guitarlibrary.database.DatabaseHelper;
import com.hukuton.longoidotulunkristian.database.BookDatabaseHelper;
import com.hukuton.longoidotulunkristian.utils.CopyAssetToSDCard;

public class SplashActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String COPIED = "copied";
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        String pathFrom = "book_component/longoi/with_chords";
        String pathTo = "Longoi Dot Ulun Kristian/longoi_with_chords";
        // Always check this one. never know if someone delete any lyric
        if(CopyAssetToSDCard.getFileCount(pathTo) <= ListValues.LONGOI.length){
            CopyAssetToSDCard.copyAssets(this, pathFrom, pathTo);

        }

        setupSplashView();

        if (prefs.getBoolean(COPIED, true)) {
            new DatabaseCopier().execute(this);
            prefs.edit().putBoolean(COPIED, false).commit();
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    splashEnd();
                }
            }, 500);
        }

    }

    private void setupSplashView() {
        Typeface rage = Typeface.createFromAsset(getAssets(), "font/rage_italic.ttf");
        TextView tvSplash = (TextView) findViewById(R.id.textViewSplash);
        tvSplash.setTypeface(rage);
    }

    private void splashEnd() {
        Intent openListActivity = new Intent(this, MainListActivity.class);
        startActivity(openListActivity);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private class DatabaseCopier extends AsyncTask<Context, Context, Void> {
        @Override
        protected Void doInBackground(Context... params) {
            BookDatabaseHelper.getInstance(params[0]);
            DatabaseHelper.getInstance(params[0]);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            splashEnd();
        }
    }
}
