package com.example.jeremyjohn.dingdong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
    }
    public void btnHome (View view) { startActivity(new Intent(AboutUs.this, MainActivity.class));overridePendingTransition(0,0); }
    public void btnFavourite (View view) { startActivity(new Intent(AboutUs.this, Favourite.class));overridePendingTransition(0,0); }
    public void btnSetting (View view) { startActivity(new Intent(AboutUs.this, Settings.class));overridePendingTransition(0,0); }

}
