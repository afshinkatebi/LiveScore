package com.ir.irdevelopers.Tamashachi;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import Adapter.PinnedGameAdapter;
import DataModel.Event;
import DataModel.Game;
import Helpers.ConstantHelper;
import Helpers.TimeoutJsonArrayRequest;
import Helpers.VolleySingleton;
import Views.PinnedSectionListView;
import Views.TextViewFont;

public class GameListActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView recycleView;
    private RequestQueue requestQueue;
    private PinnedSectionListView listview;
    private TextViewFont header;
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        header = (TextViewFont)findViewById(R.id.header);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        context = this;
        if (getIntent().hasExtra("event")) {
            event = (Event) getIntent().getSerializableExtra("event");
        } else {
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();
        }

        header.setText(event.name);
        recycleView = (RecyclerView)findViewById(R.id.recycleView);
        listview = (PinnedSectionListView)findViewById(R.id.listview);

        Map<String, String> params = new HashMap();
        params.put("event_uid", event.uid+"");
        JSONObject parameters = new JSONObject(params);
        parameters=null;

        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        requestQueue.add(new TimeoutJsonArrayRequest(Request.Method.GET, ConstantHelper.GAME+"?event_uid="+event.uid, parameters, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Game> games = Game.parse(response);
                PinnedGameAdapter pinnedGameAdapter = new PinnedGameAdapter(context,games);
                listview.setAdapter(pinnedGameAdapter);
//                listview.smoothScrollToPosition(pinnedGameAdapter.getUnplayedPosition());
//                GameAdapter adapter = new GameAdapter(context,games);
//                recycleView.setLayoutManager(new LinearLayoutManager(context));
//                recycleView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (event==null){
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void backOnClick(View view) {
        finish();
    }

    public void ShareOnClick(View view) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,"شما به اپلیکیشن تماشاچی دعوت شده اید." + "\n"+"تماشاچی شما را از جدیدترین رویدادهای ورزشی با خبر می سازد و زمان پخش بازیهای تیم مورد علاقتان را به شما اطلاع می دهد."+"\n\n" + "دانلود اپلیکیشن برای اندروید" + "\n" + "http://Tamashachi.AriTec.ir/getapp");        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }
}
