package com.hukuton.longoidotulunkristian.fragment;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.hukuton.longoidotulunkristian.ListValues;
import com.hukuton.longoidotulunkristian.ViewStringActivity;
import com.hukuton.longoidotulunkristian.adapter.ItemAdapter;
import com.hukuton.longoidotulunkristian.database.BookDatabaseHelper;
import com.hukuton.longoidotulunkristian.enums.Type;
import com.hukuton.longoidotulunkristian.model.IntentData;
import com.hukuton.longoidotulunkristian.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alixson on 01-Jul-16.
 */
public class LongoiListFragment extends BaseListFragment {

    private List<Item> longoiList;
    @Override
    protected RecyclerView.Adapter setAdapter() {
        return new ItemAdapter(getLongoiList(), this);
    }

    private List<Item> getLongoiList() {
        longoiList = new ArrayList<>();

        if(longoiList.size() > 0)
            return longoiList;

        for(int i = 0; i < ListValues.LONGOI.length; i++) {
            boolean fav = mBookDbHelper.isFavorite(BookDatabaseHelper.TABLE_LONGOI, i+1);
            Item longoi = new Item(ListValues.LONGOI[i], fav);
            longoiList.add(longoi);
        }
        return longoiList;
    }

    @Override
    public Type getType() {
        return Type.LONGOI;
    }

    @Override
    public void onImageViewClick(View item, int position, boolean favorite) {
        mBookDbHelper.setFavourite(BookDatabaseHelper.TABLE_LONGOI, position + 1, favorite);//+1 because sqlite start from id=1
    }

    @Override
    public void onTextWrapperClick(View view, int position) {
        Intent i = new Intent(view.getContext(), ViewStringActivity.class);
        String json = new Gson().toJson(new IntentData(Type.LONGOI, longoiList.size(), position));
        i.putExtra(ViewStringActivity.JUSTDOITALREADY, json);
        view.getContext().startActivity(i);
    }
}
