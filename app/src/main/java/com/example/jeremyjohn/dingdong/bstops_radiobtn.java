package com.example.jeremyjohn.dingdong;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class bstops_radiobtn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bstops_radiobtn);

        createRadioButtons();

        int savedValue = getNumPanelsSelected(this);
        Toast.makeText(this, "Saved Value: " + savedValue, Toast.LENGTH_SHORT).show();
        //setupPrintSelected();
    }
/*
    private void setupPrintSelected() {
        Button btn = (Button) findViewById(R.id.find_selected);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup group = (RadioGroup) findViewById(R.id.radio_group_install_size);
                int idOfSelected = group.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(idOfSelected);
                String message = radioButton.getText().toString();

                Toast.makeText(bstops_radiobtn.this, "Selected button's text is: " + message, Toast.LENGTH_SHORT).show();
            }
        });
    } */

    private void createRadioButtons() {
        RadioGroup group = (RadioGroup) findViewById(R.id.radio_group_install_size);
        int[] numPanels = getResources().getIntArray(R.array.num_bus_stops);

        //Create the buttons:
        for (int i = 0; i < numPanels.length; i++)
        {
            final int numPanel = numPanels[i];

            RadioButton button = new RadioButton(this);
            button.setText(getString(R.string.bus_stop_number, numPanel));

            //TODO: Set on-click callbacks
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(bstops_radiobtn.this, "You clicked " + numPanel, Toast.LENGTH_SHORT).show();

                    saveNumPanelsSelected(numPanel);
                }
            });

            // Add to radio group:
            group.addView(button);

            //Select default button:
            if (numPanel == getNumPanelsSelected(this)) {
                button.setChecked(true);
            }
            
        }
    }

    private void saveNumPanelsSelected(int numPanel) {
        SharedPreferences prefs = this.getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("bus_stop_number", numPanel);
        editor.apply();
    }

    static public int getNumPanelsSelected(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences("AppPrefs", MODE_PRIVATE);
        return prefs.getInt("bus_stop_number", 1);

    }

    public void confirmbtn(View view){
        //Finish Intent
        finish();

    }

}
