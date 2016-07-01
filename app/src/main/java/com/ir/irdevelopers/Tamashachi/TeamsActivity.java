package com.ir.irdevelopers.Tamashachi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.wooplr.spotlight.utils.SpotlightListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.TeamAdapter;
import DataModel.Event;
import DataModel.Team;
import Helpers.ConstantHelper;
import Helpers.IntroCreator;
import Helpers.NetworkErrorHandler;
import Helpers.SharedPrefrence;
import Helpers.TimeoutJsonArrayRequest;
import Helpers.TimeoutJsonObjectRequest;
import Helpers.VolleySingleton;
import Interface.TeamOnClickListener;
import Views.AutoNetworkImageView;
import Views.TextViewFont;

public class TeamsActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    Context context;
    private RecyclerView recycleView;
    private View jadval_pakhsh;
    private Event event;
    private TextViewFont title;
    private AutoNetworkImageView header;
    private String user_uid;
    private View select_all;
    private TeamAdapter adapter;
    private View deselect_all;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View view = findViewById(R.id.help);
        IntroCreator.showIntro(this, view, "TeamHelp", "انتخاب تیم", "تیم های مورد علاقتون رو اینجا انتخاب کنید تا ما زمان پخش بازیهاش رو بهتون اطلاع بدیم.", new SpotlightListener() {
            @Override
            public void onUserClicked(String s) {

            }
        });

        title = (TextViewFont) findViewById(R.id.title);
        header = (AutoNetworkImageView) findViewById(R.id.header);
        select_all = findViewById(R.id.select_all);
        deselect_all = findViewById(R.id.deselect_all);
        context = this;

        if (getIntent().hasExtra("event")) {
            event = (Event) getIntent().getSerializableExtra("event");
        } else {
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();
        }

        title.setText(event.name);
        header.setImageUrlWithAnim(event.getImageAddress(), VolleySingleton.getInstance(context).getImageLoader());
        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        jadval_pakhsh = findViewById(R.id.jadval_pakhsh);


        jadval_pakhsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GameListActivity.class);
                intent.putExtra("event", event);
                startActivity(intent);


            }
        });


        // Check Token
        user_uid = SharedPrefrence.read(context, "user_uid");
        if (user_uid == null) {
            // APP NEED A RESTART
            Toast.makeText(context, "شما ثبت نام نشده اید! دوباره به صفحه خانگی فرستاده می شوید تا ثبت نام خودکار انجام شود", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            context.startActivity( intent );
        }


        // load main events
        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();

        Map<String, String> params = new HashMap();
        params.put("event_uid", event.uid + "");
        JSONObject parameters = new JSONObject(params);
        parameters = null;


        final JSONObject finalParameters = parameters;
        requestQueue.add(new TimeoutJsonArrayRequest(Request.Method.GET, ConstantHelper.TEAM_OF_EVENT + "?event_uid=" + event.uid + "&user_uid=" + user_uid, parameters, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Team> teams = Team.parse(response);
                adapter = new TeamAdapter(context, teams);
                adapter.setOnItemClickListener(new TeamOnClickListener() {
                    @Override
                    public void onClick(final Team team) {
                        if (team.isClickable == false) return;
                        adapter.disableClickForTeam(team);


                        if (team.isSelected) {
                            //TODO UNSUBSCRIBE
                            adapter.unsubscribe(team);
                            requestQueue.add(new TimeoutJsonObjectRequest(Request.Method.GET, ConstantHelper.UNSUBSCRIBE + "?user_uid=" + user_uid + "&event_uid=" + event.uid + "&team_uid=" + team.uid, finalParameters, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    adapter.enableClickForTeam(team);
                                    try {
                                        String result = response.getString("result");
                                        if (result.equals("interest_removed")) {
//                                            Toast.makeText(context, "interest_removed", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, "حذف علاقمندی ها موفقیت آمیز نبود", Toast.LENGTH_SHORT).show();
                                            adapter.subscribe(team);

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    adapter.enableClickForTeam(team);
                                    Toast.makeText(context, "حذف علاقمندی ها موفقیت آمیز نبود", Toast.LENGTH_SHORT).show();
                                    adapter.subscribe(team);
                                }
                            }));

                        } else {

                            //SUBSCRIBE
                            adapter.subscribe(team);
                            requestQueue.add(new TimeoutJsonObjectRequest(Request.Method.GET, ConstantHelper.SUBSCRIBE + "?user_uid=" + user_uid + "&event_uid=" + event.uid + "&team_uid=" + team.uid, finalParameters, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    adapter.enableClickForTeam(team);

                                    try {
                                        String result = response.getString("result");
                                        if (result.equals("interest_added")) {
//                                            Toast.makeText(context, "interest_added", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, "خطا در افزودن علاقمندی", Toast.LENGTH_SHORT).show();
                                            adapter.unsubscribe(team);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    adapter.enableClickForTeam(team);
                                    Toast.makeText(context, "خطا در افزودن علاقمندی", Toast.LENGTH_SHORT).show();
                                    adapter.unsubscribe(team);
                                }
                            }));

                        }
                    }
                });
                recycleView.setLayoutManager(new GridLayoutManager(context, 3));
                recycleView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkErrorHandler.handleThisError(context,error);

            }
        }));


        select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SUBSCRIBE
                adapter.subscribeAll();
                requestQueue.add(new TimeoutJsonObjectRequest(Request.Method.GET, ConstantHelper.SUBSCRIBE_ALL + "?user_uid=" + user_uid + "&event_uid=" + event.uid, finalParameters, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String result = response.getString("result");
                            if (result.equals("all_interests_added")) {
//                                Toast.makeText(context, "all_interest_added", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "خطا در افزودن علاقمندی", Toast.LENGTH_SHORT).show();
                                adapter.cancel_Subscribe_unSubscribe_All();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(context, "خطا در افزودن علاقمندی", Toast.LENGTH_SHORT).show();
                            adapter.cancel_Subscribe_unSubscribe_All();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "خطا در افزودن علاقمندی", Toast.LENGTH_SHORT).show();
                        adapter.cancel_Subscribe_unSubscribe_All();
                    }
                }));
            }
        });


        deselect_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SUBSCRIBE
                adapter.unsubscribeAll();
                requestQueue.add(new TimeoutJsonObjectRequest(Request.Method.GET, ConstantHelper.UNSUBSCRIBE_ALL + "?user_uid=" + user_uid + "&event_uid=" + event.uid, finalParameters, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String result = response.getString("result");
                            if (result.equals("all_interests_removed")) {
//                                Toast.makeText(context, "all_interests_removed", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "خطا در حذف علاقمندی", Toast.LENGTH_SHORT).show();
                                adapter.cancel_Subscribe_unSubscribe_All();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(context, "خطا در حذف علاقمندی", Toast.LENGTH_SHORT).show();
                            adapter.cancel_Subscribe_unSubscribe_All();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "خطا در حذف علاقمندی", Toast.LENGTH_SHORT).show();
                        adapter.cancel_Subscribe_unSubscribe_All();
                    }
                }));
            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
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
