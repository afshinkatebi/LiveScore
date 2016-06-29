package DataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Helpers.ConstantHelper;
import Helpers.PersianCalendar;

/**
 * Created by Alip on 6/29/2016.
 */
public class News {
    public int uid;
    public String title = "";
    public String image = "";
    public String description = "";
    private String create_at = "";

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
                news.create_at = jsonObject.getString("create_at");

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
    public String getPersianDate() {
        String res="";
        try {
            int year = Integer.parseInt(create_at.substring(0, 4));
            int month = Integer.parseInt(create_at.substring(5, 7));
            int day = Integer.parseInt(create_at.substring(8, 10));
            PersianCalendar persianCalendar = new PersianCalendar();
            persianCalendar.setGregorianDate(year,month,day);
            res = persianCalendar.getPersianWeekDayStr()+" "+persianCalendar.irDay+" "+persianCalendar.getPersianMonthName();

        }catch (Exception e){
            e.printStackTrace();
        }
        return res;


    }
}
