package DataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import Helpers.ConstantHelper;

/**
 * Created by Alip on 6/19/2016.
 */
public class Event implements Serializable {
    public int uid = 0 ;
    public String name = "";
    public String image = "";

    public static ArrayList<Event> parse(JSONArray jsonArray){
        ArrayList<Event> events = new ArrayList<>();
        for(int i = 0 ; i< jsonArray.length();i++){
            try {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Event event = new Event();
                event.uid = jsonObject.getInt("uid");
                event.name = jsonObject.getString("name");
                event.image = jsonObject.getString("image");
                events.add(event);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return events;
    }

    public String getImageAddress() {
        return ConstantHelper.EVENT_IMAGE_FOLDER + image;
    }

}



