package com.example.jeremyjohn.dingdong;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.jeremyjohn.dingdong.models.Place;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

import java.util.ArrayList;

public class Currentstop extends FragmentActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback {

    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1988;

    /**
     * Use KEYWORD and RADIUS to play with the search results
     * Did not implemented UI to take in these, due to time constraints
     */
    final String KEYWORD = "";//for google places search
    final int RADIUS = 1000;//in meters

    private RecyclerView recyclerView;
    private PlaceAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private LocationServicesManager mLocationServicesManager;
    private NetworkManager mNetworkManager;
    private LocationCallback mLocationCallback;
    ArrayList<Place> mPlace = new ArrayList<>();
    //For OnClick functionality in PlaceAdapter
    public interface OnItemClickListener{
        void onItemClick(Place place);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currentstop);
        //RecyclerView and Adapter
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PlaceAdapter(this, mPlace, new OnItemClickListener(){
            @Override
            public void onItemClick(Place place) {
                Double lat = place.getLat();
                Double lon = place.getLon();
                int zoom = 19;//zoom range 0-->21
                Uri gmmIntentUri = Uri.parse("geo:" +lat+ "," +lon + "?z=" + zoom);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                //startActivity(mapIntent);
                Intent intent = new Intent(Currentstop.this, AvaliableBus.class);
                intent.putExtra("Lat",  place.getLat().toString());
                intent.putExtra("Long",  place.getLon().toString());
                String num = getIntent().getStringExtra("stopnumber");
                String name = getIntent().getStringExtra("stopname");
                intent.putExtra("destinationstop",num);
                intent.putExtra("destinationstopname",name);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);

        //LocationServices and Network Handling
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {

                //get current location
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    mNetworkManager.getNearbyBusStop(location, RADIUS);
                }
            }
        };
        mLocationServicesManager = new LocationServicesManager(this);
        mNetworkManager = new NetworkManager(getApplicationContext(), mAdapter);
        startLocationUpdates();

        //search bar function
        EditText editText = findViewById(R.id.searchbar);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override //to change display after search
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }


        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted

                    startLocationUpdates();
                } else {
                    // permission denied
                }
                return;
            }
        }
    }

    private void startLocationUpdates(){
        mLocationServicesManager.startLocationUpdates(mLocationCallback);
    }
    /*Bottom navigation bar*/
    public void btnHome (View view) { startActivity(new Intent(Currentstop.this, MainActivity.class));overridePendingTransition(0,0); }
    public void btnFavourite (View view) { startActivity(new Intent(Currentstop.this, Favourite.class)); overridePendingTransition(0,0);}
    public void btnSetting (View view) { startActivity(new Intent(Currentstop.this, Settings.class));overridePendingTransition(0,0); }

    private void filter(String text) {
        ArrayList<Place> filteredList = new ArrayList<>();

        for (Place item : mPlace) {
            //filter by name
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
    }
}

