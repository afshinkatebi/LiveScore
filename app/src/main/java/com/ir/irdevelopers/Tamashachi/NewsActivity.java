package com.ir.irdevelopers.Tamashachi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.wooplr.spotlight.utils.SpotlightListener;

import org.json.JSONArray;

import java.util.ArrayList;

import Adapter.EventAdapter;
import Adapter.NewsAdapter;
import DataModel.Event;
import DataModel.News;
import Helpers.ConstantHelper;
import Helpers.IntroCreator;
import Helpers.NetworkErrorHandler;
import Helpers.TimeoutJsonArrayRequest;
import Helpers.VolleySingleton;

public class NewsActivity extends AppCompatActivity {

    private RecyclerView recycleView;
    private RequestQueue requestQueue;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        context = this;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        recycleView = (RecyclerView) findViewById(R.id.recycleView);

// load main events
        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        requestQueue.add(new TimeoutJsonArrayRequest(Request.Method.POST, ConstantHelper.NEWS, "{}", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<News> newses = News.parse(response);
                NewsAdapter adapter = new NewsAdapter(context, newses);
                recycleView.setLayoutManager(new LinearLayoutManager(context));
                recycleView.setAdapter(adapter);
//                recycleView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //help
//                        View view = findViewById(R.id.help);
//                        IntroCreator.showIntro(activity,view,"MainHelp","انتخاب رویداد","اینجا میتونید رویداد ورزشی مورد علاقتون رو انتخاب کنید.", new SpotlightListener() {            @Override
//                        public void onUserClicked(String s) {
//                            ImageButton imageButton = (ImageButton) findViewById(R.id.share);
//                            IntroCreator.showIntro((Activity) context, imageButton, "MainHelp2", "معرفی به دوستان", "ما را به دوستان خودتان معرفی کنید.", new SpotlightListener() {
//                                @Override
//                                public void onUserClicked(String s) {
//
//                                }
//                            });
//                        }
//                        });
//                    }
//                },2);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkErrorHandler.handleThisError(context,error);

            }
        }));
    }

    public void ShareOnClick(View view) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,"شما به اپلیکیشن تماشاچی دعوت شده اید." + "\n"+"تماشاچی شما را از جدیدترین رویدادهای ورزشی با خبر می سازد و زمان پخش بازیهای تیم مورد علاقتان را به شما اطلاع می دهد."+"\n\n" + "دانلود اپلیکیشن برای اندروید" + "\n" + "http://Tamashachi.AriTec.ir/getapp");        startActivity(Intent.createChooser(sharingIntent, "Share using"));

    }

    public void backOnClick(View view) {
        finish();
    }
}
