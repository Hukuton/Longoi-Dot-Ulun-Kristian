package com.hukuton.longoidotulunkristianpcs.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.hukuton.longoidotulunkristianpcs.R;
import com.hukuton.longoidotulunkristianpcs.listener.OnItemAdapterClickListener;
import com.hukuton.longoidotulunkristianpcs.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alixson on 10-Apr-16.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private List<Item> itemList;
    private List<Item> itemListUnchanged;

    private OnItemAdapterClickListener onItemAdapterClickListener;

    public ItemAdapter(List<Item> itemList, OnItemAdapterClickListener onItemAdapterClickListener) {
        this.itemList = itemList;
        this.itemListUnchanged = itemList;
        this.onItemAdapterClickListener = onItemAdapterClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Item item = itemList.get(position);
        holder.title.setText(item.getTitle());
        holder.materialFavoriteButton.setFavorite(item.isFavourite(), false);
    }

    public void setFilter(List<Item> itemModels) {
        itemList = new ArrayList<>();
        itemList.addAll(itemModels);
        notifyDataSetChanged();
    }

    private int getOriginalPosition(String title){
        for(int i = 0; i < itemListUnchanged.size(); i++){
            if(itemListUnchanged.get(i).getTitle().equals(title))
                return i;
        }
        return 0;
    }

    public List<Item> filter(List<Item> models, String query) {
        query = query.toLowerCase();

        final List<Item> filteredModelList = new ArrayList<>();
        for (Item model : models) {
            final String text = model.getTitle().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, MaterialFavoriteButton.OnFavoriteChangeListener{
        public View itemView;
        public LinearLayout linearLayout;
        public TextView title;
        public MaterialFavoriteButton materialFavoriteButton;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            linearLayout = (LinearLayout) view.findViewById(R.id.linear);
            title = (TextView) view.findViewById(R.id.title);
            materialFavoriteButton = (MaterialFavoriteButton) view.findViewById(R.id.favouriteImage);
            linearLayout.setOnClickListener(this);
            materialFavoriteButton.setOnFavoriteChangeListener(this);
        }
/*
        @Override
        public void onClick(View view) {
            onItemAdapterClickListener.onTextWrapperClick(view, getAdapterPosition());
        }
*/

        @Override
        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
            itemList.get(getAdapterPosition()).setFavourite(favorite);
            onItemAdapterClickListener.onImageViewClick(buttonView, getAdapterPosition(), favorite);
        }

        @Override
        public void onClick(View view) {
            int pos = getOriginalPosition(itemList.get(getAdapterPosition()).getTitle());
            onItemAdapterClickListener.onTextWrapperClick(view, pos);
        }
    }
}
