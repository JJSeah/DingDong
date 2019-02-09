package com.example.jeremyjohn.dingdong;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeremyjohn.dingdong.cardEmulation.KHostApduService;
import com.example.jeremyjohn.dingdong.models.BusNoModel;
import com.example.jeremyjohn.dingdong.models.Flag;
import com.example.jeremyjohn.dingdong.models.RouteModel;
import com.example.jeremyjohn.dingdong.models.ServiceNoModel;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

public class BusRoute extends AppCompatActivity {
    private RecyclerView bRecyclerView;
    public static busrouteAdapter bAdapter;
    private ArrayList<RouteModel> stopname;
    static List<RouteModel> Filter = new ArrayList<>();
    ArrayList<ServiceNoModel> filteredList = new ArrayList<>();
    public static String busno;
    public static int i;
    TextView busstop;
    Button btnStartUpdates;
    static int p = -1;
    static  int e = 0;
    public static int alertno;

    // location last updated time
    private String mLastUpdateTime;

    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000;

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 500;
    private static final int REQUEST_CHECK_SETTINGS = 100;


    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;

    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_route);
        int alertno = arrivalalert_radiobtn.getNumPanelsSelected(this);

        Log.d(TAG, "alertno" + alertno);
        busno = getIntent().getStringExtra("busno");
        fetchBus process = new fetchBus();
        process.execute();
        i=fetchBus.x;
        Log.d(TAG, "TIMING101 "+String.valueOf(i));
        bRecyclerView = findViewById(R.id.rvbusroute);
        bRecyclerView.setHasFixedSize(true);
        bRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bRecyclerView.setItemAnimator(new DefaultItemAnimator());
        btnStartUpdates=findViewById(R.id.btnStart);
        busstop=findViewById(R.id.textView4);
        busstop.setText(busno);
        ButterKnife.bind(this);
        // initialize the necessary libraries
        init();
        // restore the values from saved instance state
        restoreValuesFromBundle(savedInstanceState);

        String currentdirection = "";
        String currentseq = "";
        String desdirection = "";
        String desseq = "";
        for (RouteModel x:fetchData.RouteList){
            if (busno.equals(x.getServiceNo())){ //filter to the specific bus
                if(AvaliableBus.desno.equals(x.getBusStopCode())){ //filter to the specify des bus stop
                    desdirection = x.getDirection();
                    desseq = x.getStopSequence();
                }
                if(AvaliableBus.bustop.equals(x.getBusStopCode())){ //filter to the specify current bus stop
                    currentdirection = x.getDirection(); // get current direction
                    currentseq = x.getStopSequence(); //get current seq
                }
            }
        }
        if (Filter != null) ;
        {
            Filter.clear();
        }
        if (filteredList != null) ;
        {
            filteredList.clear();
        }
        for (RouteModel r : fetchData.RouteList){
            if(r.getServiceNo().equals(busno)) {
                if (r.getDirection().equals(currentdirection)){
                    if(Integer.valueOf(currentseq)<=Integer.valueOf(r.getStopSequence()) && Integer.valueOf(desseq)>=Integer.valueOf(r.getStopSequence())){
                        Filter.add(r);
                        Log.d(TAG,"stopseqno"+ r.getStopSequence());
                    }
                }
            }
        }
        String test = "";
        for (RouteModel y : Filter){
            for (ServiceNoModel item : MainActivity.Busstoplist) {
                if(item.getBusStopCode().equals(y.getBusStopCode())){
                    if (test.equals(item.getDescription())){
                        test = item.getDescription();
                    }
                    else{
                        test = item.getDescription();
                        filteredList.add(item);
                        Log.d(TAG,"stopseqno"+ item.getDescription());

                    }
                }
            }
        }
        bAdapter = new busrouteAdapter(this, filteredList);
        bRecyclerView.setAdapter(bAdapter);
        Intent intent = new Intent(this, KHostApduService.class);
        intent.putExtra("ndefMessage", "tap");
        startService(intent);

    }
    private void init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                updateLocationUI();
            }
        };

        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
        Log.d(TAG, "TIMING101 "+String.valueOf(i));
    }

    /**
     * Restoring values from saved instance state
     */
    private void restoreValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("is_requesting_updates")) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean("is_requesting_updates");
            }

            if (savedInstanceState.containsKey("last_known_location")) {
                mCurrentLocation = savedInstanceState.getParcelable("last_known_location");
            }

            if (savedInstanceState.containsKey("last_updated_on")) {
                mLastUpdateTime = savedInstanceState.getString("last_updated_on");
            }
        }

        updateLocationUI();
    }


    /**
     * Update the UI displaying the location data
     * and toggling the buttons
     */
    private void updateLocationUI() {
        TextView x = findViewById(R.id.txta);
        for (ServiceNoModel a : filteredList) {
            if (mCurrentLocation != null) {
                //String q= String.valueOf(mCurrentLocation.getLatitude()).substring(0,7);
                //String w= String.valueOf(mCurrentLocation.getLongitude()).substring(0,7);
                Location bustop = new Location("");
                Location currentPosition = new Location("");

                bustop.setLatitude(Double.valueOf(a.getLatitude()));
                bustop.setLongitude(Double.valueOf(a.getLongitude()));

                currentPosition.setLatitude(mCurrentLocation.getLatitude());
                currentPosition.setLongitude(mCurrentLocation.getLongitude());

                float dist = bustop.distanceTo(currentPosition);

                x.setText(String.valueOf(mCurrentLocation.getLatitude()));
                if(dist < 100){
                        a.setPass("Y");
                        x.setText("done");
                        bAdapter = new busrouteAdapter(this, filteredList);
                        bRecyclerView.setAdapter(bAdapter);

                    if(a.getPass().equals("Y")){
                        p=-1;
                        p++;
                    }
                    if(filteredList.size() == p){
                        mRequestingLocationUpdates = false;
                        stopLocationUpdates();
                    }

                }
            }

        }
        toggleButtons();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("is_requesting_updates", mRequestingLocationUpdates);
        outState.putParcelable("last_known_location", mCurrentLocation);
        outState.putString("last_updated_on", mLastUpdateTime);

    }

    private void toggleButtons() {
        if (mRequestingLocationUpdates) {
            btnStartUpdates.setEnabled(false);
            //btnStopUpdates.setEnabled(true);
        } else {
            btnStartUpdates.setEnabled(true);
            //btnStopUpdates.setEnabled(false);
        }
    }

    /**
     * Starting location updates
     * Check whether location settings are satisfied and then
     * location updates will be requested
     */
    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

                        Toast.makeText(getApplicationContext(), "Started location updates!", Toast.LENGTH_SHORT).show();

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        updateLocationUI();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(BusRoute.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(BusRoute.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        updateLocationUI();
                    }
                });
    }

    @OnClick(R.id.btnStart)
    public void startLocationButtonClick() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(this)
                .withPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    /*@OnClick(R.id.btn_stop_location_updates)
    public void stopLocationButtonClick() {
        mRequestingLocationUpdates = false;
        stopLocationUpdates();
    }*/
    public void stopLocationUpdates() {
        // Removing location updates
        mFusedLocationClient
                .removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Location updates stopped!", Toast.LENGTH_SHORT).show();
                        toggleButtons();
                    }
                });
    }

    /*@OnClick(R.id.btn_get_last_location)
    public void showLastKnownLocation() {
        if (mCurrentLocation != null) {
            Toast.makeText(getApplicationContext(), "Latitude: " + mCurrentLocation.getLatitude()
                    + "\nLongitude: " + mCurrentLocation.getLongitude(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Last known location is not available!", Toast.LENGTH_SHORT).show();
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e(TAG, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e(TAG, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        break;
                }
                break;
        }
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Resuming location updates depending on button state and
        // allowed permissions
        if (mRequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
        }

        updateLocationUI();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }
    /*Flagging bus*/
    public void btnFlag (View view){
    // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseref = FirebaseDatabase.getInstance().getReference("Flag");
        //DatabaseReference myRef = database.getReference(AvaliableBus.bustop);
        Flag flag = new Flag(busno,AvaliableBus.bustop);
        databaseref.child("0xe066eaa2ceL").setValue(flag);
        //databaseref.setValue(busno);
    }

    /*Bottom navigation bar*/
    public void btnHome (View view) { startActivity(new Intent(BusRoute.this, MainActivity.class));overridePendingTransition(0,0); }
    public void btnFavourite (View view) { startActivity(new Intent(BusRoute.this, Favourite.class)); overridePendingTransition(0,0);}
    public void btnSetting (View view) { startActivity(new Intent(BusRoute.this, Settings.class));overridePendingTransition(0,0); }
}



