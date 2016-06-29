package com.ir.irdevelopers.Tamashachi;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abote);
    }

    public void backOnClick(View view) {
        finish();
    }

    public void WebSiteOnClick(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("http://tamashachi.aritec.ir/"));
        startActivity(i);
    }

    public void CallonClick(View view) {
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "07183458567"));
        startActivity(i);
    }

    public void WebSitecompanyOnClick(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("http://www.aritec.ir/"));
        startActivity(i);
    }
}
