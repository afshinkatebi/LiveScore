package Helpers;

//import android.content.Context;
//import android.graphics.Bitmap;
//import android.util.Base64;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//
//import org.json.JSONObject;
//
//import java.io.ByteArrayOutputStream;
//import java.util.HashMap;
//import java.util.Random;
//
//import DataModels.User;
//import Helpers.Constants;
//import Helpers.CustomRequest;
//import Helpers.Dialogs.EPAlertDialog;
//import Helpers.Dialogs.EPPasswordRequestDialog;
//import Helpers.ImageHelper;
//import Helpers.MessageContainer;
//import Helpers.SharedPrefrence;
//import Intefaces.CallbackConfirmPhone;
//import Intefaces.CallbackForgetPassword;
//import Intefaces.CallbackOkDuplicateError;
//import Intefaces.CallbackYesNo;
//import Intefaces.OkErrorListener;
//import Intefaces.OnDateModelResultListener;
//import Intefaces.OnGetUserResultListener;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.TeamAdapter;
import DataModel.Team;

/**
 * Created by Alip on 1/6/2016.
 */
public class UserManager {
    private static RequestQueue requestQueue;


    public static void sendGcmTokenToServer(final Context context, final String token) {

        // save token to disk

        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();

        Map<String, String> params = new HashMap();
        JSONObject parameters = new JSONObject(params);
        parameters = null;


        requestQueue.add(new JsonObjectRequest(Request.Method.GET, ConstantHelper.REGISTRATION+"?token="+token, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Toast.makeText(context,"id recived " + response.getString("user_uid"),Toast.LENGTH_SHORT).show();
                    SharedPrefrence.write(context,"user_uid",response.getString("user_uid"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"token error " + error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }));
    }

}