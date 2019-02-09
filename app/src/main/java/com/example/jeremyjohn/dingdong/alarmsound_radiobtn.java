package com.example.jeremyjohn.dingdong;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class alarmsound_radiobtn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarmsound_radiobtn);

        createRadioButtons();

        String savedValue = getStrPanelsSelected(this);
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
        String[] strPanels = getResources().getStringArray(R.array.alarm_sounds);

        //Create the buttons:
        for (int i = 0; i < strPanels.length; i++)
        {
            final String strPanel = strPanels[i];

            RadioButton button = new RadioButton(this);
            button.setText(strPanel);

            //TODO: Set on-click callbacks
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(alarmsound_radiobtn.this, "You clicked " + strPanel, Toast.LENGTH_SHORT).show();

                    saveStrPanelsSelected(strPanel);
                }
            });

            // Add to radio group:
            group.addView(button);

            //Select default button:
            if (strPanel.equals(getStrPanelsSelected(this))) {
                button.setChecked(true);
            }
            
        }
    }

    private void saveStrPanelsSelected(String strPanel) {
        SharedPreferences prefs = this.getSharedPreferences("AlarmPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("alarm_sound", strPanel);
        editor.apply();
    }

    static public String getStrPanelsSelected(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences("AlarmPrefs", MODE_PRIVATE);
        return prefs.getString("alarm_sound", "Default");

    }

    public void confirmbtn(View view){
        //Finish Intent
        finish();

    }

}
