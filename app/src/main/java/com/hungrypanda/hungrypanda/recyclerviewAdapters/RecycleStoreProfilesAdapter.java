package com.hungrypanda.hungrypanda.recyclerviewAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hungrypanda.hungrypanda.AppModules.GlideApp;
import com.hungrypanda.hungrypanda.R;
import com.hungrypanda.hungrypanda.activities.ScrollingActivity;
import com.hungrypanda.hungrypanda.datamodels.StoreProfileModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Keji's Lab on 26/12/2017.
 */

public class RecycleStoreProfilesAdapter extends RecyclerView.Adapter<RecycleStoreProfilesAdapter.MyViewHolder> {

    private ArrayList<StoreProfileModel> storeProfile;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView lblStoreName,lnlStoreLocation;
        public ImageView restoImageBanner;
        public ImageView restoIcon;
        public MyViewHolder(View view){

            super(view);
            restoIcon = (ImageView) view.findViewById(R.id.restoIcon);
          //  restoImageBanner = (ImageView) view.findViewById(R.id.restoImageBanner);
            lblStoreName = (TextView) view.findViewById(R.id.lblRestaurantName);
            lnlStoreLocation = (TextView) view.findViewById(R.id.lblRestaurantLocation);
        }
    }

    public RecycleStoreProfilesAdapter(Context c, ArrayList<StoreProfileModel> storeProfile){
        this.storeProfile = storeProfile;
        this.context = c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.restor_list_row,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleStoreProfilesAdapter.MyViewHolder holder, final int position) {
        final StoreProfileModel storeProfileModel = storeProfile.get(position);
        holder.lblStoreName.setText(storeProfileModel.getStoreName());
        holder.lnlStoreLocation.setText(storeProfileModel.getStoreAddress());
        holder.lblStoreName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ScrollingActivity.class);
                i.putExtra("key", storeProfileModel.getRestaurantID());
                context.startActivity(i);
            }
        });

        //GlideApp.with(context).load(storeProfileModel.getStoreBannerUrl()).into(holder.restoImageBanner);
        GlideApp.with(context).load(storeProfileModel.getStoreProfileUrl()).into(holder.restoIcon);


    }

    @Override
    public int getItemCount() {
        return storeProfile.size();
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