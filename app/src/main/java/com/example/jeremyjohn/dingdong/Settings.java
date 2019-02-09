package com.example.jeremyjohn.dingdong;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.jeremyjohn.dingdong.models.Flag;
import com.example.jeremyjohn.dingdong.models.Money;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

public class Settings extends AppCompatActivity {
    static String otp;
    String amount;
    double bal;
    Button share;
    private TextView balance;
    private static final int NOTIFICATION_CHANNEL_ID = 1000;
    private NotificationHandler notificationHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        notificationHandler = new NotificationHandler(this);
        refreshScreen();

        share = (Button)findViewById(R.id.btnshare);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = "Ding Dong\n www.google.com";
                myIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(myIntent, "Share using"));
            }
        });

        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Wallet/0xe066eaa2ceL");

// Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //String value = dataSnapshot.getValue(String.class);
                Money money = dataSnapshot.getValue(Money.class);
                //System.out.println(money);

                bal = money.getBalance();
                amount = String.format("%.2f", bal);
                balance = (TextView)findViewById(R.id.txtbal);
                balance.setText("$"+amount);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }



    /*Bottom navigation bar*/
    public void btnHome (View view) { startActivity(new Intent(Settings.this, MainActivity.class));overridePendingTransition(0,0); }
    public void btnFavourite (View view) { startActivity(new Intent(Settings.this, Favourite.class));overridePendingTransition(0,0); }
    public void btnSetting (View view) { startActivity(new Intent(Settings.this, Settings.class));overridePendingTransition(0,0); }

    public void btnalarmno(View view){
        //Create intent
        startActivity(new Intent(Settings.this, bstops_radiobtn.class));overridePendingTransition(0,0);

    }

    public void btnalarmsound(View view){
        //Create intent
        //startActivity(new Intent(Settings.this, alarmsound_radiobtn.class));overridePendingTransition(0,0);
        openNotificationSettingsForWatchMovieChannel
                (NotificationHandler.WATCH_MOVIE_NOTIFICATION_CHANNEL_ID);
    }

    public void btnarrivalalert(View view){
        //Create intent
        startActivity(new Intent(Settings.this, arrivalalert_radiobtn.class));overridePendingTransition(0,0);

    }
    public void btnaboutus(View view) {
        //Create intent
        startActivity(new Intent(Settings.this, AboutUs.class));
        overridePendingTransition(0, 0);

    }
    @TargetApi(Build.VERSION_CODES.O)
    private void openNotificationSettingsForWatchMovieChannel(String watchMovieChannelID){

        Intent settingsIntent = new Intent(android.provider.Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        settingsIntent.putExtra(android.provider.Settings.EXTRA_APP_PACKAGE, getPackageName());
        settingsIntent.putExtra(android.provider.Settings.EXTRA_CHANNEL_ID, watchMovieChannelID);
        startActivity(settingsIntent);
    }

    /*wallet*/
    public void btntopup (View view){

        int number = new Random().nextInt(900000) + 100000;
        otp = String.format("%06d", number);
        //otp = "123456";
        postNotificationToUserDevice(NOTIFICATION_CHANNEL_ID, otp);
        startActivity(new Intent(Settings.this, OTP.class));overridePendingTransition(0,0);
        //startActivity(new Intent(Settings.this, com.example.jeremyjohn.dingdong.Wallet.class));overridePendingTransition(0,0);

    }


    private void refreshScreen() {
        //Refresh Number of Stops Number display:
        Button alarmno = (Button) findViewById(R.id.alarmno);
        int stopNo = bstops_radiobtn.getNumPanelsSelected(this);
        alarmno.setText("" + stopNo);

        //Refresh Alarm Sound
        Button alarmsounds = (Button) findViewById(R.id.btnalarmsound);
        String alarmsound = alarmsound_radiobtn.getStrPanelsSelected(this);
        alarmsounds.setText(alarmsound);

        //Refresh Alert Timing
        Button arrivalalerts = (Button) findViewById(R.id.btnarrivalalert);
        int arrivalalert = arrivalalert_radiobtn.getNumPanelsSelected(this);
        arrivalalerts.setText("" + arrivalalert + " MIN");
    }

    private void postNotificationToUserDevice (int notificationID, String titleText){

        Notification.Builder notificationBuilder = null;

        switch (notificationID){
            case NOTIFICATION_CHANNEL_ID :
                notificationBuilder = notificationHandler.createAndReturnWatchMovieNotification
                        ("One time password for Ding Dong is",titleText);
                break;
        }

        if (notificationBuilder != null){
            notificationHandler.notifyTheUser(NOTIFICATION_CHANNEL_ID,
                    notificationBuilder);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshScreen();
    }


}

