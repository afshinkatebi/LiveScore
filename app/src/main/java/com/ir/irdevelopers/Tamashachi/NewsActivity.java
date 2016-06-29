package com.ir.irdevelopers.Tamashachi;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

    }

    public void ShareOnClick(View view) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,"شما به اپلیکیشن تماشاچی دعوت شده اید." + "\n"+"تماشاچی شما را از جدیدترین رویدادهای ورزشی با خبر می سازد و زمان پخش بازیهای تیم مورد علاقتان را به شما اطلاع می دهد."+"\n\n" + "دانلود اپلیکیشن برای اندروید" + "\n" + "http://Tamashachi.AriTec.com/getapp");        startActivity(Intent.createChooser(sharingIntent, "Share using"));

    }

    public void backOnClick(View view) {
        finish();
    }
}
