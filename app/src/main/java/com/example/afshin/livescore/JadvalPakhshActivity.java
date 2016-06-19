package com.example.afshin.livescore;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.GameAdapter;
import Adapter.PinnedGameAdapter;
import Adapter.TeamAdapter;
import DataModel.Game;
import DataModel.Team;
import Helpers.ConstantHelper;
import Helpers.VolleySingleton;
import Views.PinnedSectionListView;

public class JadvalPakhshActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView recycleView;
    private RequestQueue requestQueue;
    private PinnedSectionListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadval_pakhsh);

        context = this;
        final int event_uid = getIntent().getIntExtra("event_uid",0);
        recycleView = (RecyclerView)findViewById(R.id.recycleView);
        listview = (PinnedSectionListView)findViewById(R.id.listview);

        Map<String, String> params = new HashMap();
        params.put("event_uid", event_uid+"");
        JSONObject parameters = new JSONObject(params);
        parameters=null;

        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        requestQueue.add(new JsonArrayRequest(Request.Method.GET, ConstantHelper.GAME+"?event_uid="+event_uid, parameters, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Game> games = Game.parse(response);
                PinnedGameAdapter pinnedGameAdapter = new PinnedGameAdapter(context,games);
                listview.setAdapter(pinnedGameAdapter);
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
}
