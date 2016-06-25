package com.ir.irdevelopers.Tamashachi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AboteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abote);
    }

    public void backOnClick(View view) {
        finish();
    }
}
