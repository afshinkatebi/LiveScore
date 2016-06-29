package DataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Helpers.ConstantHelper;

/**
 * Created by Alip on 6/29/2016.
 */
public class News {
    public int uid;
    public String title = "";
    public String image = "";
    public String description = "";

    public static ArrayList<News> parse(JSONArray jsonArray){
        ArrayList<News> newses = new ArrayList<>();
        for(int i = 0 ; i< jsonArray.length();i++){
            try {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                News news = new News();
                news.uid = jsonObject.getInt("uid");
                news.title = jsonObject.getString("title");
                news.image = jsonObject.getString("image");
                news.description = jsonObject.getString("description");
                newses.add(news);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return newses;
    }

    public String getImageAddress() {
        return ConstantHelper.NEWS_IMAGE_FOLDER + image;
    }

}
