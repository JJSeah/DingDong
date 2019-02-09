package com.example.jeremyjohn.dingdong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OTP extends AppCompatActivity {

    TextView code,error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        code = (TextView)findViewById(R.id.txtcode);

    }
    public void btntopup (View view){

        if (Settings.otp.equals(String.valueOf(code.getText()))){
            finish();
            startActivity(new Intent(OTP.this, com.example.jeremyjohn.dingdong.Wallet.class));overridePendingTransition(0,0);
        }
        else {
            error = (TextView)findViewById(R.id.txtError);
            error.setText("Invalid OTP");
        }

    }
}
