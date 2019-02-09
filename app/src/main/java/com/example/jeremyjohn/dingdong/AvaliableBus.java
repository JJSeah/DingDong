package com.example.jeremyjohn.dingdong;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jeremyjohn.dingdong.models.BusNoModel;
import com.example.jeremyjohn.dingdong.models.Favourites;
import com.example.jeremyjohn.dingdong.models.ServiceNoModel;

import java.util.ArrayList;

import static android.view.View.inflate;
import static com.google.android.gms.plus.PlusOneDummyView.TAG;

public class AvaliableBus extends AppCompatActivity {
    private RecyclerView bRecyclerView;
    public static busstopAdapter bAdapter;
    private ArrayList<ServiceNoModel> stopname;
    ArrayList<ServiceNoModel> filteredList = new ArrayList<>();
    public static busAdapter busAdapter;
    public static String desno;
    public static String desname;
    public static String bustop;
    public static String bustopname;
    public static int toggle = 1;
    String s;
    String a;
    String lat;
    String lon;
    DBHelper mDatabaseHelper;
    ArrayList<Favourites> Favlist = new ArrayList<Favourites>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avaliablebus);
        TextView t = findViewById(R.id.busNo);

        bRecyclerView = findViewById(R.id.rvbus);
        bRecyclerView.setHasFixedSize(true);
        bRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bRecyclerView.setItemAnimator(new DefaultItemAnimator());
        bAdapter = new busstopAdapter(this, MainActivity.Busstoplist);
        bRecyclerView.setAdapter(bAdapter);


        lat = getIntent().getStringExtra("Lat");
        lon = getIntent().getStringExtra("Long");
        if(lat!=null){
            s=lat.substring(0,7);
            a=lon.substring(0,7);
            desno = getIntent().getStringExtra("destinationstop");
            desname = getIntent().getStringExtra("destinationstopname");
            filtercurrent(s.toString(),a.toString());
        }
        else{
            desno = getIntent().getStringExtra("Desno");
            bustop = getIntent().getStringExtra("Curno");
            desname = getIntent().getStringExtra("Destinationame");
            bustopname = getIntent().getStringExtra("Currentname");
            compare();
        }
        Log.d(TAG,"THISWORKS "+ bustopname);

        mDatabaseHelper =  new DBHelper(this, null, null, 1);
        Cursor data = mDatabaseHelper.getData();
        while(data.moveToNext()){
            //get the value from the database
            //then add it to the ArrayList
            Favlist.add(new Favourites(data.getInt(0),data.getString(1),data.getString(2),data.getString(3),data.getString(4)));
        }
        ImageButton btnStar =(ImageButton)findViewById(R.id.imgtbn);
        for (Favourites x:Favlist){
            if (x.getDesname().equals(desname) && x.getCurname().equals(bustopname)){
                toggle = 0;
                btnStar.setImageResource(R.drawable.favouriteon);
            }
        }

    }

    private void filtercurrent(String lat,String lon) {
        for (ServiceNoModel item : MainActivity.Busstoplist) {
            //filter by lat
            if (item.getLatitude().contains(lat)) {
                if (item.getLongitude().contains(lon)) {
                    filteredList.clear();
                    filteredList.add(item);
                    bustop = item.getBusStopCode();
                    bustopname = item.getDescription();
                }
            }
        }
        /*TextView Current = findViewById(R.id.txtCurrentStop);
        TextView Destination = findViewById(R.id.txtDesStop);
        Current.setText(bustopname);
        Destination.setText(desname);*/
        compare();

    }
    private void compare(){
        TextView Current = findViewById(R.id.txtCurrentStop);
        TextView Destination = findViewById(R.id.txtDesStop);
        Current.setText(bustopname);
        Destination.setText(desname);
        Log.d(TAG,"THISWORKS "+ desname);
        bAdapter.filterList(filteredList);
        fetchData processe = new fetchData(this);
        processe.execute();
        busAdapter = new busAdapter(this, fetchData.CommonList);
        bRecyclerView.setAdapter(busAdapter);
        /*if (fetchData.ava.equals("no")){
            //t.setText("Bus not Avaliable");
        }*/
    }
    /*Bottom navigation bar*/
    public void btnHome (View view) { startActivity(new Intent(AvaliableBus.this, MainActivity.class));overridePendingTransition(0,0); }
    public void btnFavourite (View view) { startActivity(new Intent(AvaliableBus.this, Favourite.class));overridePendingTransition(0,0); }
    public void btnSetting (View view) { startActivity(new Intent(AvaliableBus.this, Settings.class));overridePendingTransition(0,0); }

    public void newfav (View view) {
        ImageButton btnStar =(ImageButton)findViewById(R.id.imgtbn);
        DBHelper dbHandler = new DBHelper(this, null, null, 1);
        if (toggle == 1){
            Favourites favourites = new Favourites();
            favourites.setDesname(desname);
            favourites.setDesno(desno);
            favourites.setCurname(bustopname);
            favourites.setCurno(bustop);
            dbHandler.addFav(desname,desno,bustopname,bustop);
            btnStar.setImageResource(R.drawable.favouriteon);
            toggle = 0;
        }
        else {
            Favourites favourites = new Favourites();
            dbHandler.deleteFav(desname,bustopname);
            btnStar.setImageResource(R.drawable.favouriteoff);
            toggle = 1;

        }

    }

}
