package com.hukuton.longoidotulunkristian.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hukuton.longoidotulunkristian.R;
import com.hukuton.longoidotulunkristian.database.BookDatabaseHelper;
import com.hukuton.longoidotulunkristian.deco.SimpleDividerItemDecoration;
import com.hukuton.longoidotulunkristian.listener.OnItemAdapterClickListener;
import com.hukuton.longoidotulunkristian.types.IType;

/**
 * Created by Alixson on 01-Jul-16.
 */
public abstract class BaseListFragment extends Fragment implements OnItemAdapterClickListener, IType {

    protected BookDatabaseHelper mBookDbHelper;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;

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

        return mRecyclerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        RecyclerView.Adapter adapter = setAdapter();
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);
    }

    /**Set adapter
     * @return Adapter
     */
    protected abstract RecyclerView.Adapter setAdapter();

}
