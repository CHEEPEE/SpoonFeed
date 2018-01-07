package com.hungrypanda.hungrypanda.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hungrypanda.hungrypanda.R;
import com.hungrypanda.hungrypanda.datamodels.MenuItemGrid;
import com.hungrypanda.hungrypanda.datamodels.StoreProfileModel;
import com.hungrypanda.hungrypanda.mapModels.MenusItemMapModel;
import com.hungrypanda.hungrypanda.mapModels.StoreProfileInformationMap;
import com.hungrypanda.hungrypanda.recyclerviewAdapters.RecycleStoreProfilesAdapter;
import com.hungrypanda.hungrypanda.recyclerviewAdapters.RecyclerViewRestaurantMenusAdapter;
import com.hungrypanda.hungrypanda.utils.Utils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScrollingActivity extends AppCompatActivity {
private  String restaurantID;
TextView lblRestaurantName,lblRestorLocation;
DatabaseReference mDatabase;
CircleImageView restaurantIcon;
ImageView storeProfileBackground;
private Context context;
private ArrayList<StoreProfileModel> arrayStoreProfile = new ArrayList<>();
private ArrayList<MenuItemGrid> menuItemGridArrayList = new ArrayList<>();
RecyclerView rv_MenuItemList;
RecyclerViewRestaurantMenusAdapter recyclerViewRestaurantMenusAdapter;
RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        context = ScrollingActivity.this;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        restaurantID = getIntent().getExtras().getString("key");
        lblRestaurantName = (TextView) findViewById(R.id.lblRestoName);
        lblRestorLocation = (TextView)findViewById(R.id.lblRestoLocation);
        restaurantIcon = (CircleImageView) findViewById(R.id.restaurantIcon);
        storeProfileBackground = (ImageView) findViewById(R.id.storeProfileBackground);
        rv_MenuItemList = (RecyclerView) findViewById(R.id.rv_menuItemList);
        mDatabase.child(Utils.storeProfiles).child(restaurantID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                StoreProfileModel storeProfileModel = new StoreProfileModel();
                StoreProfileInformationMap storeProfileInformationMap = dataSnapshot.getValue(StoreProfileInformationMap.class);
                storeProfileModel.setStoreName(storeProfileInformationMap.storeName);
                storeProfileModel.setStoreBannerUrl(storeProfileInformationMap.storeBannerUrl);
                storeProfileModel.setStoreProfileUrl(storeProfileInformationMap.storeProfileUrl);
                storeProfileModel.setStoreAddress(storeProfileInformationMap.storeAddress);
                storeProfileModel.setStoreContact(storeProfileInformationMap.storeContact);
                storeProfileModel.setRestaurantID(storeProfileInformationMap.restaurantID);
                arrayStoreProfile.add(storeProfileModel);
                lblRestaurantName.setText(arrayStoreProfile.get(0).getStoreName());
                lblRestorLocation.setText(arrayStoreProfile.get(0).getStoreAddress());
                Glide.with(context).load(arrayStoreProfile.get(0).getStoreProfileUrl()).into(restaurantIcon);
                Glide.with(context).load(arrayStoreProfile.get(0).getStoreBannerUrl()).into(storeProfileBackground);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_arrow_back_orange_24dp));

        mDatabase.child(Utils.restaurantItems).child(restaurantID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                menuItemGridArrayList.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    MenuItemGrid menuItemGrid = new MenuItemGrid();
                    MenusItemMapModel menusItemMapModel = dataSnapshot1.getValue(MenusItemMapModel.class);
                    menuItemGrid.setiName(menusItemMapModel.itemName);
                    menuItemGrid.setItemBannerUrl(menusItemMapModel.itemBannerURL);
                    menuItemGrid.setItemCategory(menusItemMapModel.itemCategory);
                    menuItemGrid.setItemKey(menusItemMapModel.itemKey);
                    menuItemGrid.setItemPrice(menusItemMapModel.itemPrice);
                    menuItemGrid.setItemCode(menusItemMapModel.itemCode);
                    menuItemGridArrayList.add(menuItemGrid);


                }
                recyclerViewRestaurantMenusAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*recycleStoreProfilesAdapter = new RecycleStoreProfilesAdapter(context,storeProfileModels);
        layoutManager = new LinearLayoutManager(context);
        rvRestaurantList.setLayoutManager(layoutManager);
        rvRestaurantList.setAdapter(recycleStoreProfilesAdapter);*/
        recyclerViewRestaurantMenusAdapter = new RecyclerViewRestaurantMenusAdapter(context,menuItemGridArrayList);
        layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rv_MenuItemList.setLayoutManager(layoutManager);
        rv_MenuItemList.setAdapter(recyclerViewRestaurantMenusAdapter);
        rv_MenuItemList.setNestedScrollingEnabled(false);



    }
}
