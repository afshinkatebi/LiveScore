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
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.EventAdapter;
import Adapter.TeamAdapter;
import DataModel.Event;
import DataModel.Team;
import Helpers.ConstantHelper;
import Helpers.VolleySingleton;

public class TeamActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    Context context;
    private RecyclerView recycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        context = this;
        int event_uid = getIntent().getIntExtra("event_uid",0);
        recycleView = (RecyclerView)findViewById(R.id.recycleView);

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
    }
}
