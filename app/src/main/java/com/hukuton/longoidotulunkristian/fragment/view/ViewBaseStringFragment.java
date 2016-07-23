package com.hukuton.longoidotulunkristian.fragment.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.hukuton.longoidotulunkristian.R;
import com.hukuton.longoidotulunkristian.database.BookDatabaseHelper;
import com.hukuton.longoidotulunkristian.enums.Type;

/**
 * Created by Alixson on 05-Jul-16.
 */
public abstract class ViewBaseStringFragment extends Fragment implements MaterialFavoriteButton.OnFavoriteChangeListener{

    protected int mPagePosition;
    protected static String POSITION = "position";
    protected final String TYPE_ENUM = "serializable_enum";
    protected Type mType;

    protected BookDatabaseHelper mBookDbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mPagePosition = getArguments().getInt(POSITION);
        mType = (Type) getArguments().getSerializable(TYPE_ENUM);
        mBookDbHelper = BookDatabaseHelper.getInstance(getContext());
        View view = inflater.inflate(setLayoutId(), container, false);
        onSetupView(view);
        return view;
    }

    protected void onSetupView(View view) {
    }

    protected abstract int setLayoutId();

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_base_view_string, menu);
        MenuItem menuItem = menu.findItem(R.id.action_favorite);

        MaterialFavoriteButton mfb = (MaterialFavoriteButton) MenuItemCompat.getActionView(menuItem);
        mfb.setOnFavoriteChangeListener(this);
        mfb.setFavorite(setMaterialButtonFavourite(), false);

        if(setMaterialButtonFavourite()){
            menuItem.setTitle("Unfavorite");
        } else
            menuItem.setTitle("Favorite");
    }

    protected abstract boolean setMaterialButtonFavourite();
}
