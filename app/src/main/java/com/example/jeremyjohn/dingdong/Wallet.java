package com.example.jeremyjohn.dingdong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.jeremyjohn.dingdong.models.Money;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

public class Wallet extends AppCompatActivity {
    private TextView balance;
    String amount;
    double bal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);



        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Wallet/0xe066eaa2ceL");

// Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //String value = dataSnapshot.getValue(String.class);
                Money money = dataSnapshot.getValue(Money.class);
                //System.out.println(money);
                //amount = String.valueOf(money.getBalance());
                amount = String.format("%.2f", money.getBalance());
                bal = money.getBalance();
                balance = (TextView)findViewById(R.id.txtbal);

                balance.setText("$"+amount);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

    public void btntopup10 (View view){
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseref = FirebaseDatabase.getInstance().getReference("Wallet");
        Money money = new Money(10 + bal);
        databaseref.child("0xe066eaa2ceL").setValue(money);
    }
    public void btntopup20 (View view){
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseref = FirebaseDatabase.getInstance().getReference("Wallet");
        Money money = new Money(20 + bal);
        databaseref.child("0xe066eaa2ceL").setValue(money);
    }
    public void btntopup30 (View view){
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseref = FirebaseDatabase.getInstance().getReference("Wallet");
        Money money = new Money(30 + bal);
        databaseref.child("0xe066eaa2ceL").setValue(money);
    }
    public void btntopup50 (View view){
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseref = FirebaseDatabase.getInstance().getReference("Wallet");
        Money money = new Money(50 + bal);
        databaseref.child("0xe066eaa2ceL").setValue(money);
    }

    /*Bottom navigation bar*/
    public void btnHome (View view) { startActivity(new Intent(Wallet.this, MainActivity.class));overridePendingTransition(0,0); }
    public void btnFavourite (View view) { startActivity(new Intent(Wallet.this, Favourite.class));overridePendingTransition(0,0); }
    public void btnSetting (View view) { startActivity(new Intent(Wallet.this, Settings.class));overridePendingTransition(0,0); }
}
