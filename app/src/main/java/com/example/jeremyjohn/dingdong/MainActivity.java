package com.example.jeremyjohn.dingdong;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jeremyjohn.dingdong.models.ServiceNoModel;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DBHelper mDatabaseHelper;
    private RecyclerView bRecyclerView;
    public static busstopAdapter bAdapter;
    Cursor c = null;
    static ArrayList<ServiceNoModel> Busstoplist = new ArrayList<ServiceNoModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bRecyclerView = findViewById(R.id.rvbusstop);
        bRecyclerView.setHasFixedSize(true);
        bRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mDatabaseHelper =  new DBHelper(this, null, null, 1);
        if (Busstoplist != null) ;
        {
            Busstoplist.clear();
        }
        try {
            mDatabaseHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }
        c = mDatabaseHelper.query("bus", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
            } while (c.moveToNext());
        }
        Cursor data = mDatabaseHelper.getBusStop();

        while(data.moveToNext()){
            //get the value from the database
            //then add it to the ArrayList
            Busstoplist.add(new ServiceNoModel(data.getInt(0),data.getString(1),data.getString(2),data.getString(3),data.getString(4),""));
        }
        bAdapter = new busstopAdapter(this, Busstoplist);
        //bAdapter = new busstopAdapter(this, fetchBusStop.ServiceNoList);
        bRecyclerView.setAdapter(bAdapter);

        //search bar function
        EditText editText = findViewById(R.id.searchbar);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override //to change display after search
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }


        });


    }
    /*Bottom navigation bar*/
    public void btnHome (View view) { startActivity(new Intent(MainActivity.this, MainActivity.class));overridePendingTransition(0,0); }
    public void btnFavourite (View view) { startActivity(new Intent(MainActivity.this, Favourite.class));overridePendingTransition(0,0); }
    public void btnSetting (View view) { startActivity(new Intent(MainActivity.this, Settings.class));overridePendingTransition(0,0); }
    private void filter(String text) {
        ArrayList<ServiceNoModel> filteredList = new ArrayList<>();

        for (ServiceNoModel item : Busstoplist) {
            //filter by name
            if (item.getDescription().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        bAdapter.filterList(filteredList);
    }
}
