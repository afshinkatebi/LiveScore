package com.ir.irdevelopers.Tamashachi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public void backOnClick(View view) {
        finish();
    }

    public void oneHorseBeforeGameOnclick(View view) {
        Switch aSwitch= (Switch) findViewById(R.id.switch1);
        aSwitch.setChecked(!aSwitch.isChecked());
    }

    public void fiveMinBeforeGameOnclick(View view) {
        Switch aSwitch= (Switch) findViewById(R.id.switch2);
        aSwitch.setChecked(!aSwitch.isChecked());
    }

    public void startGameOnClick(View view) {
        Switch aSwitch= (Switch) findViewById(R.id.switch3);
        aSwitch.setChecked(!aSwitch.isChecked());
    }
}
