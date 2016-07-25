package com.hukuton.longoidotulunkristianpcs.fragment;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.hukuton.longoidotulunkristianpcs.ListValues;
import com.hukuton.longoidotulunkristianpcs.ViewStringActivity;
import com.hukuton.longoidotulunkristianpcs.adapter.ItemAdapter;
import com.hukuton.longoidotulunkristianpcs.database.BookDatabaseHelper;
import com.hukuton.longoidotulunkristianpcs.enums.Type;
import com.hukuton.longoidotulunkristianpcs.model.IntentData;
import com.hukuton.longoidotulunkristianpcs.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alixson on 01-Jul-16.
 */
public class PomintaanDuaListFragment extends BaseListFragment {

    private List<Item> pomintaanDuaList;
    private ItemAdapter adapter;

    @Override
    protected RecyclerView.Adapter setAdapter() {
        adapter = new ItemAdapter(getPomintaanDuaList(), this);
        return adapter;
    }

    private List<Item> getPomintaanDuaList() {
        pomintaanDuaList = new ArrayList<>();

        if(pomintaanDuaList.size() > 0)
            return pomintaanDuaList;

        for(int i = 0; i < ListValues.POMINTAAN_DUA.length; i++) {
            boolean fav = mBookDbHelper.isFavorite(BookDatabaseHelper.TABLE_POMINTAAN_DOA, i+1);
            Item pomintaanDua = new Item(ListValues.POMINTAAN_DUA[i], fav);
            pomintaanDuaList.add(pomintaanDua);
        }
        return pomintaanDuaList;
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
        String json = new Gson().toJson(new IntentData(Type.POMINTAAN_DUA, pomintaanDuaList.size(), position));
        i.putExtra(ViewStringActivity.JUSTDOITALREADY, json);
        view.getContext().startActivity(i);
    }

    @Override
    protected boolean setHasOptionMenu() {
        return true;
    }

    @Override
    protected void setFilter(List<Item> filteredModelList) {
        adapter.setFilter(filteredModelList);
    }

    @Override
    protected List<Item> filter(String newText) {
        return adapter.filter(pomintaanDuaList, newText);
    }
}
