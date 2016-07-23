package com.hukuton.longoidotulunkristian.fragment.view;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.hukuton.longoidotulunkristian.R;
import com.hukuton.longoidotulunkristian.database.BookDatabaseHelper;
import com.hukuton.longoidotulunkristian.enums.Type;

/**
 * Created by Alixson on 05-Jul-16.
 */
public class ViewHoturanSumambayangStringFragment extends ViewBaseStringFragment{

    public Fragment newInstance(Type type, int position) {
        ViewHoturanSumambayangStringFragment instance = new ViewHoturanSumambayangStringFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TYPE_ENUM, type);
        bundle.putInt(POSITION, position);
        instance.setArguments(bundle);
        return (instance);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_hoturan_view_string;
    }

    @Override
    protected void onSetupView(View view) {
        WebView webView = (WebView) view.findViewById(R.id.webView);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // call requies API Level 11 ( Android 3.0 + )
            webView.getSettings().setDisplayZoomControls(false);
        }
        if (Build.VERSION.SDK_INT >= 11) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }
        String path = "file:///android_asset/book_component/hoturan_sumambayang/hots" + (mPagePosition + 1) + ".html";
        webView.loadUrl(path);
        setHasOptionsMenu(true);
    }

    @Override
    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
        mBookDbHelper.setFavourite(BookDatabaseHelper.TABLE_HOTURAN_SUMAMBAYANG, mPagePosition + 1, favorite);//+1 because sqlite start from id=1
    }

    @Override
    protected boolean setMaterialButtonFavourite() {
        return mBookDbHelper.isFavorite(BookDatabaseHelper.TABLE_HOTURAN_SUMAMBAYANG, mPagePosition + 1);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }
}
