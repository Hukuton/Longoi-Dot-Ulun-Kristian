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
public class PomintaanDuaListFragment extends BaseListFragment {

    private List<Item> pomintaadDuaList;

    @Override
    protected RecyclerView.Adapter setAdapter() {
        return new ItemAdapter(getPomintaanDuaList(), this);
    }

    private List<Item> getPomintaanDuaList() {
        pomintaadDuaList = new ArrayList<>();

        if(pomintaadDuaList.size() > 0)
            return pomintaadDuaList;

        for(int i = 0; i < ListValues.POMINTAAN_DUA.length; i++) {
            boolean fav = mBookDbHelper.isFavorite(BookDatabaseHelper.TABLE_POMINTAAN_DOA, i+1);
            Item pomintaanDua = new Item(ListValues.POMINTAAN_DUA[i], fav);
            pomintaadDuaList.add(pomintaanDua);
        }
        return pomintaadDuaList;
    }

    @Override
    public Type getType() {
        return Type.POMINTAAN_DUA;
    }

    @Override
    public void onImageViewClick(View item, int position, boolean favorite) {
        mBookDbHelper.setFavourite(BookDatabaseHelper.TABLE_POMINTAAN_DOA, position + 1, favorite);//+1 because sqlite start from id=1
    }

    @Override
    public void onTextWrapperClick(View view, int position) {
        Intent i = new Intent(view.getContext(), ViewStringActivity.class);
        String json = new Gson().toJson(new IntentData(Type.POMINTAAN_DUA, pomintaadDuaList.size(), position));
        i.putExtra(ViewStringActivity.JUSTDOITALREADY, json);
        view.getContext().startActivity(i);
    }
}
