package com.ir.irdevelopers.Tamashachi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.wooplr.spotlight.utils.SpotlightListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import Adapter.EventAdapter;
import DataModel.Event;
import Helpers.ConstantHelper;
import Helpers.IntroCreator;
import Helpers.NetworkErrorHandler;
import Helpers.TimeoutJsonArrayRequest;
import Helpers.VolleySingleton;
import Views.SliderLayoutRec;
import ir.adad.client.AdListener;
import ir.adad.client.AdView;
import ir.adad.client.Adad;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Context context;
    private RequestQueue requestQueue;
    private RecyclerView recycleView;
    private SliderLayoutRec sliderLayout;
    private View header;
    NavigationView navigationView;
    private boolean googlePlayOk;
    public static final String PREF_KEY_FIRST_START = "com.heinrichreimersoftware.materialintro.demo.PREF_KEY_FIRST_START";
    public static final int REQUEST_CODE_INTRO = 1;
    private MainActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String languageToLoad  = "en"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());


        context = this;
        activity = this;

        Adad.initialize(getApplicationContext());


        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }



        //slider start

        boolean firstStart = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean(PREF_KEY_FIRST_START, true);

        if (firstStart) {
            Intent intent = new Intent(this, MaterialIntroActivity.class);
            PreferenceManager.getDefaultSharedPreferences(this).edit()
                    .putBoolean(PREF_KEY_FIRST_START, false)
                    .apply();
            startActivityForResult(intent, REQUEST_CODE_INTRO);
        }

//        Intent introIntent = new Intent(this, MaterialIntroActivity.class);
//        startActivity(introIntent);





        navigationView = (NavigationView) findViewById(R.id.nav_view);
        header = navigationView.getHeaderView(0);


        // check google play services is exist
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (status == 0) {
            googlePlayOk = true;
            Intent intent = new Intent(context, RegistrationIntentService.class);
            startService(intent);
        }

        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        sliderLayout = (SliderLayoutRec) findViewById(R.id.slider);

        recycleView.setNestedScrollingEnabled(false);



        // load slider
        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        requestQueue.add(new TimeoutJsonArrayRequest(Request.Method.POST, ConstantHelper.SLIDER, "{}", new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        final JSONObject jsonObject = (JSONObject) response.get(i);
                        TextSliderView defaultSliderView = new TextSliderView(context);
                        defaultSliderView.image(ConstantHelper.SLIDE_IMAGE_FOLDER + jsonObject.getString("image"));
                        defaultSliderView.description(jsonObject.getString("description"));
                        final String url = jsonObject.getString("url");
                        defaultSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                            @Override
                            public void onSliderClick(BaseSliderView slider) {
                                String url= null;
                                try {
                                    url = jsonObject.getString("url");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if (url!=null && url.length()>0){
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(browserIntent);
                                }

                            }
                        });
                        sliderLayout.addSlider(defaultSliderView);
                        sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkErrorHandler.handleThisError(context,error);
            }
        }));

        // load main events
        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        requestQueue.add(new TimeoutJsonArrayRequest(Request.Method.POST, ConstantHelper.FUNCTION, "{}", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Event> events = Event.parse(response);
                EventAdapter adapter = new EventAdapter(context, events);
                recycleView.setLayoutManager(new LinearLayoutManager(context));
                recycleView.setAdapter(adapter);
                recycleView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //help
                        View view = findViewById(R.id.help);
                        IntroCreator.showIntro(activity,view,"MainHelp","انتخاب رویداد","اینجا میتونید رویداد ورزشی مورد علاقتون رو انتخاب کنید.", new SpotlightListener() {            @Override
                        public void onUserClicked(String s) {
                            ImageButton imageButton = (ImageButton) findViewById(R.id.share);
                            IntroCreator.showIntro((Activity) context, imageButton, "MainHelp2", "معرفی به دوستان", "ما را به دوستان خودتان معرفی کنید.", new SpotlightListener() {
                                @Override
                                public void onUserClicked(String s) {

                                }
                            });
                        }
                        });
                    }
                },2);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkErrorHandler.handleThisError(context,error);

            }
        }));





    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawer(Gravity.RIGHT);
        } else {
            super.onBackPressed();
        }
    }

    public void OnClickToggle(View view) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawer(Gravity.RIGHT);
        } else {
            drawer.openDrawer(Gravity.RIGHT);
        }
    }


    public void closeDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(Gravity.RIGHT);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void settingOnClick(View view) {
        Intent intent = new Intent(context, SettingActivity.class);
        startActivity(intent);

    }

    public void abateOnClick(View view) {
        Intent intent = new Intent(context, AboutActivity.class);
        startActivity(intent);
    }

    public void ShareOnClick(View view) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,"شما به اپلیکیشن تماشاچی دعوت شده اید." + "\n"+"تماشاچی شما را از جدیدترین رویدادهای ورزشی با خبر می سازد و زمان پخش بازیهای تیم مورد علاقتان را به شما اطلاع می دهد."+"\n\n" + "دانلود اپلیکیشن برای اندروید" + "\n" + "http://Tamashachi.AriTec.ir/getapp");        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    public void ShareOnClickNav(View view) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,"شما به اپلیکیشن تماشاچی دعوت شده اید." + "\n"+"تماشاچی شما را از جدیدترین رویدادهای ورزشی با خبر می سازد و زمان پخش بازیهای تیم مورد علاقتان را به شما اطلاع می دهد."+"\n\n" + "دانلود اپلیکیشن برای اندروید" + "\n" + "http://Tamashachi.AriTec.ir/getapp");        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }


    public void OnclickNews(View view) {
        Intent intent = new Intent(context, NewsActivity.class);
        startActivity(intent);

    }
}
