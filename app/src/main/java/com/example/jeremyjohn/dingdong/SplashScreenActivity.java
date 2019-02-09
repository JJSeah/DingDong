package com.example.jeremyjohn.dingdong;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.io.IOException;

public class SplashScreenActivity extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    DBHelper mDatabaseHelper;
    Cursor c = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //fetchBusStop process = new fetchBusStop();
        //process.execute();
        mDatabaseHelper =  new DBHelper(this, null, null, 1);
        //DatabaseHelper myDbHelper = new DatabaseHelper(MainActivity.this);
        try {
            mDatabaseHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            mDatabaseHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }
        //Toast.makeText(SplashScreenActivity.this, "Successfully Imported", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
