package com.example.jeremyjohn.dingdong;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jeremyjohn.dingdong.models.Favourites;

import java.util.ArrayList;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

public class Favourite extends AppCompatActivity {

    DBHelper mDatabaseHelper;
    private FavouriteAdapter FAdapter;
    private RecyclerView bRecyclerView;
    private  FavouriteAdapter bAdapter;
    ArrayList<Favourites> Favlist = new ArrayList<Favourites>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        mDatabaseHelper =  new DBHelper(this, null, null, 1);
        Cursor data = mDatabaseHelper.getData();
        while(data.moveToNext()){
            //get the value from the database
            //then add it to the ArrayList
            Favlist.add(new Favourites(data.getInt(0),data.getString(1),data.getString(2),data.getString(3),data.getString(4)));
        }
        bRecyclerView = findViewById(R.id.rvfav);
        bRecyclerView.setHasFixedSize(true);
        bRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bRecyclerView.setItemAnimator(new DefaultItemAnimator());
        bAdapter = new FavouriteAdapter(this, Favlist);
        bRecyclerView.setAdapter(bAdapter);
    }
    /*Bottom navigation bar*/
    public void btnHome (View view) { startActivity(new Intent(Favourite.this, MainActivity.class));overridePendingTransition(0,0); }
    public void btnFavourite (View view) { startActivity(new Intent(Favourite.this, Favourite.class)); overridePendingTransition(0,0);}
    public void btnSetting (View view) { startActivity(new Intent(Favourite.this, Settings.class));overridePendingTransition(0,0); }
    }


