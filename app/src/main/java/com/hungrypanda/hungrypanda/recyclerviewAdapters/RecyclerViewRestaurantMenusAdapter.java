package com.hungrypanda.hungrypanda.recyclerviewAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hungrypanda.hungrypanda.R;
import com.hungrypanda.hungrypanda.datamodels.MenuItemGrid;

import java.util.ArrayList;

/**
 * Created by Keji's Lab on 26/12/2017.
 */

public class RecyclerViewRestaurantMenusAdapter extends RecyclerView.Adapter<RecyclerViewRestaurantMenusAdapter.MyViewHolder> {

    private ArrayList<MenuItemGrid> menuItemGridArrayList;
    private Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView menuLabel,menuPrice;
        public ImageView menuBanner;
        public MyViewHolder(View view){
            super(view);
            menuLabel = (TextView) view.findViewById(R.id.menuLabel);
            menuPrice = (TextView) view.findViewById(R.id.menuPrice);
            menuBanner = (ImageView) view.findViewById(R.id.menuBanner);

        }
    }

    public RecyclerViewRestaurantMenusAdapter(Context c, ArrayList<MenuItemGrid> menuItemGridArrayList){
        this.menuItemGridArrayList = menuItemGridArrayList;
        this.context = c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_menus_row,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewRestaurantMenusAdapter.MyViewHolder holder, final int position) {
        final MenuItemGrid menuItemGridModel = menuItemGridArrayList.get(position);
        Glide.with(context).load(menuItemGridModel.getItemBannerUrl()).into(holder.menuBanner);
        holder.menuLabel.setText(menuItemGridModel.getiName());
        holder.menuPrice.setText(menuItemGridModel.getItemPrice());

    }

    @Override
    public int getItemCount() {
        return menuItemGridArrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

    }
    public interface OnItemLongClickListener{
        void onItemLongClick(View view, int posistion);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickListener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    private OnItemLongClickListener monItemLongClickListener;

    public void setonItemLongClickListener(OnItemLongClickListener monItemLongClickListener){
        this.monItemLongClickListener = monItemLongClickListener;
    }
}