package com.hukuton.longoidotulunkristianpcs.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hukuton.longoidotulunkristianpcs.R;
import com.hukuton.longoidotulunkristianpcs.database.BookDatabaseHelper;
import com.hukuton.longoidotulunkristianpcs.deco.SimpleDividerItemDecoration;
import com.hukuton.longoidotulunkristianpcs.listener.OnItemAdapterClickListener;
import com.hukuton.longoidotulunkristianpcs.model.Item;
import com.hukuton.longoidotulunkristianpcs.types.IType;

import java.util.List;

/**
 * Created by Alixson on 01-Jul-16.
 */
public abstract class BaseListFragment extends Fragment implements OnItemAdapterClickListener, IType {

    protected BookDatabaseHelper mBookDbHelper;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;
    private boolean mSearchViewExpanded;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBookDbHelper = BookDatabaseHelper.getInstance(getContext());
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_base_list, container, false);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = setAdapter();
        mRecyclerView.setAdapter(adapter);
        setHasOptionsMenu(setHasOptionMenu());

        return mRecyclerView;
    }

    protected boolean setHasOptionMenu() {
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        RecyclerView.Adapter adapter = setAdapter();
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * Set adapter
     *
     * @return Adapter
     */
    protected abstract RecyclerView.Adapter setAdapter();

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_base_list, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                //MenuItemCompat.collapseActionView(item);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<Item> filteredModelList = filter(newText);
                setFilter(filteredModelList);
                return true;
            }
        });
    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(true);
    }

    protected abstract void setFilter(List<Item> filteredModelList);

    protected abstract List<Item> filter(String newText);
}
