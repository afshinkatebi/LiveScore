package com.ir.irdevelopers.Tamashachi;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import Helpers.ConstantHelper;
import Helpers.SharedPrefrence;
import Helpers.VolleySingleton;

public class SettingActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private Context context;
    private String user_uid;
    private int value;
    private Switch switch_1_hour_befor;
    private Switch switch_5_min_befor;
    private Switch switch_on_start;
    private int op_1_hr_befor;
    private int op_5_min_befor;
    private int op_on_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        context = this;
         switch_1_hour_befor = (Switch) findViewById(R.id.switch1);
         switch_5_min_befor = (Switch) findViewById(R.id.switch2);
         switch_on_start = (Switch) findViewById(R.id.switch3);

        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        JSONObject parameters = null;
        user_uid = SharedPrefrence.read(context,"user_uid");
        requestQueue.add(new JsonObjectRequest(Request.Method.GET, ConstantHelper.GETOPTION+"?user_uid="+user_uid, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    op_1_hr_befor = response.getInt("op_1_hr_befor");
                    op_5_min_befor = response.getInt("op_5_min_befor");
                    op_on_start = response.getInt("op_on_start");
                    switch_1_hour_befor.setChecked(op_1_hr_befor==1);
                    switch_5_min_befor.setChecked(op_5_min_befor==1);
                    switch_on_start.setChecked(op_on_start==1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }));
    }

    public void backOnClick(View view) {
        finish();
    }

    public void oneHorseBeforeGameOnclick(View view) {
        switch_1_hour_befor.setChecked(!switch_1_hour_befor.isChecked());


        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        JSONObject parameters = null;
        user_uid = SharedPrefrence.read(context,"user_uid");
        value = switch_1_hour_befor.isChecked() ? 1 : 0;
        requestQueue.add(new JsonObjectRequest(Request.Method.GET, ConstantHelper.OPTION+"?user_uid="+user_uid+"&option=op_1_hr_befor&value="+value, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Toast.makeText(context,response.getString("result"),Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }));
    }

    public void fiveMinBeforeGameOnclick(View view) {
        switch_5_min_befor.setChecked(!switch_5_min_befor.isChecked());
        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        JSONObject parameters = null;
        user_uid = SharedPrefrence.read(context,"user_uid");
        value = switch_5_min_befor.isChecked() ? 1 : 0;
        requestQueue.add(new JsonObjectRequest(Request.Method.GET, ConstantHelper.OPTION+"?user_uid="+user_uid+"&option=op_5_min_befor&value="+value, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Toast.makeText(context,response.getString("result"),Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }));

    }

    public void startGameOnClick(View view) {
        switch_on_start.setChecked(!switch_on_start.isChecked());

        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        JSONObject parameters = null;
        user_uid = SharedPrefrence.read(context,"user_uid");
        value = switch_on_start.isChecked() ? 1 : 0;
        requestQueue.add(new JsonObjectRequest(Request.Method.GET, ConstantHelper.OPTION+"?user_uid="+user_uid+"&option=op_on_start&value="+value, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Toast.makeText(context,response.getString("result"),Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }));
    }
}
