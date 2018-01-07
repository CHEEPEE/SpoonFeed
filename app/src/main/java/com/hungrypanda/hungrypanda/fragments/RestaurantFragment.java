package com.hungrypanda.hungrypanda.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hungrypanda.hungrypanda.R;
import com.hungrypanda.hungrypanda.activities.RestaurantMenusActivities;
import com.hungrypanda.hungrypanda.datamodels.StoreProfileModel;
import com.hungrypanda.hungrypanda.mapModels.StoreProfileInformationMap;
import com.hungrypanda.hungrypanda.recyclerviewAdapters.RecycleStoreProfilesAdapter;
import com.hungrypanda.hungrypanda.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Keji's Lab on 26/12/2017.
 */

public class RestaurantFragment extends Fragment {
    TextView lblRestaurantName,lblRestaurantAddress;
    ArrayList<StoreProfileModel> storeProfileModels = new ArrayList<>();
    RecyclerView rvRestaurantList;
    DatabaseReference mDatabase;
    RecycleStoreProfilesAdapter recycleStoreProfilesAdapter;
    RecyclerView.LayoutManager layoutManager;
    Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragement_restaurant, container, false);
        lblRestaurantName = (TextView)rootView.findViewById(R.id.lblRestaurantName);
        lblRestaurantAddress = (TextView) rootView.findViewById(R.id.lblRestaurantLocation);
        rvRestaurantList = (RecyclerView) rootView.findViewById(R.id.rvRestuarantsList);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        context = getActivity();
        recycleStoreProfilesAdapter = new RecycleStoreProfilesAdapter(context,storeProfileModels);
        layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rvRestaurantList.setLayoutManager(layoutManager);
        rvRestaurantList.setAdapter(recycleStoreProfilesAdapter);

        mDatabase.child(Utils.storeProfiles).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    StoreProfileModel storeProfileModel = new StoreProfileModel();
                    StoreProfileInformationMap storeProfileInformationMap = dataSnapshot1.getValue(StoreProfileInformationMap.class);
                    storeProfileModel.setStoreName(storeProfileInformationMap.storeName);
                    storeProfileModel.setStoreBannerUrl(storeProfileInformationMap.storeBannerUrl);
                    storeProfileModel.setStoreProfileUrl(storeProfileInformationMap.storeProfileUrl);
                    storeProfileModel.setStoreAddress(storeProfileInformationMap.storeAddress);
                    storeProfileModel.setStoreContact(storeProfileInformationMap.storeContact);
                    storeProfileModel.setRestaurantID(storeProfileInformationMap.restaurantID);
                    storeProfileModels.add(storeProfileModel);
                    recycleStoreProfilesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return rootView;
    }
}
