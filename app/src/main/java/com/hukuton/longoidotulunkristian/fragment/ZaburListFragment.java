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
public class ZaburListFragment extends BaseListFragment {

    private List<Item> zaburList;

    @Override
    protected RecyclerView.Adapter setAdapter() {
        return new ItemAdapter(getZaburList(), this);
    }

    private List<Item> getZaburList() {
        zaburList = new ArrayList<>();

        if(zaburList.size() > 0)
            return zaburList;

        for(int i = 0; i < ListValues.ZABUR.length; i++) {
            boolean fav = mBookDbHelper.isFavorite(BookDatabaseHelper.TABLE_ZABUR, i+1);
            Item zabur = new Item(ListValues.ZABUR[i], fav);
            zaburList.add(zabur);
        }
        return zaburList;
    }

    @Override
    public Type getType() {
        return Type.ZABUR;
    }

    @Override
    public void onImageViewClick(View item, int position, boolean favorite) {
        mBookDbHelper.setFavourite(BookDatabaseHelper.TABLE_ZABUR, position + 1, favorite);//+1 because sqlite start from id=1
    }

    @Override
    public void onTextWrapperClick(View view, int position) {
        Intent i = new Intent(view.getContext(), ViewStringActivity.class);
        String json = new Gson().toJson(new IntentData(Type.ZABUR, zaburList.size(), position));
        i.putExtra(ViewStringActivity.JUSTDOITALREADY, json);
        view.getContext().startActivity(i);
    }
}
