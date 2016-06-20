package com.example.afshin.livescore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

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

import Adapter.TeamAdapter;
import DataModel.Team;
import Helpers.ConstantHelper;
import Helpers.VolleySingleton;

public class TeamsActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    Context context;
    private RecyclerView recycleView;
    private View jadval_pakhsh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        context = this;
        final int event_uid = getIntent().getIntExtra("event_uid",0);
        recycleView = (RecyclerView)findViewById(R.id.recycleView);
        jadval_pakhsh = findViewById(R.id.jadval_pakhsh);


        jadval_pakhsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,GameListActivity.class);
                intent.putExtra("event_uid",event_uid);
                startActivity(intent);


            }
        });

        // load main events
        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();

        Map<String, String> params = new HashMap();
        params.put("event_uid", event_uid+"");
        JSONObject parameters = new JSONObject(params);
        parameters = null;


        requestQueue.add(new JsonArrayRequest(Request.Method.GET, ConstantHelper.TEAM_OF_EVENT+"?event_uid="+event_uid, parameters, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Team> teams = Team.parse(response);
                TeamAdapter adapter = new TeamAdapter(context,teams);
                recycleView.setLayoutManager(new GridLayoutManager(context,3));
                recycleView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }
}
