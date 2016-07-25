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
public class HoturanSumambayangListFragment extends BaseListFragment {

    private List<Item> hoturanList;
    private ItemAdapter adapter;

    @Override
    protected RecyclerView.Adapter setAdapter() {
        adapter = new ItemAdapter(getHoturanSumambayangList(), this);
        return adapter;
    }

    private List<Item> getHoturanSumambayangList() {
        hoturanList = new ArrayList<>();

        if(hoturanList.size() > 0)
            return hoturanList;

        for(int i = 0; i < ListValues.HOTURAN_SUMAMBAYANG.length; i++) {
            boolean fav = mBookDbHelper.isFavorite(BookDatabaseHelper.TABLE_HOTURAN_SUMAMBAYANG, i+1);
            Item hoturan = new Item(ListValues.HOTURAN_SUMAMBAYANG[i], fav);
            hoturanList.add(hoturan);
        }
        return hoturanList;
    }

    @Override
    public Type getType() {
        return Type.HOTURAN_SUMAMBAYANG;
    }

    @Override
    public void onImageViewClick(View item, int position, boolean favorite) {
        mBookDbHelper.setFavourite(BookDatabaseHelper.TABLE_HOTURAN_SUMAMBAYANG, position + 1, favorite);//+1 because sqlite start from id=1
    }

    @Override
    public void onTextWrapperClick(View view, int position) {
        Intent i = new Intent(view.getContext(), ViewStringActivity.class);
        String json = new Gson().toJson(new IntentData(Type.HOTURAN_SUMAMBAYANG, hoturanList.size(), position));
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
        return adapter.filter(hoturanList, newText);
    }
}
