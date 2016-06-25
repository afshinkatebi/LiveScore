package DataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Helpers.ConstantHelper;
import Helpers.PersianCalendar;

/**
 * Created by Alip on 6/19/2016.
 */
public class Game {
    public boolean isHeader = false;
    public String team_1_name = "";
    public String team_2_name = "";
    public String team_1_image = "";
    public String team_2_image = "";
    public String date = "";
    public String channel_name = "";
    public String team_1_score = "" ;
    public String team_2_score = "" ;

    public Game(String date) {
        this.isHeader = true;
        this.date = date;
        // this is a fake game this is header
    }

    public Game() {
    }


    public static ArrayList<Game> parse(JSONArray jsonArray){
        ArrayList<Game> teams = new ArrayList<>();
        for(int i = 0 ; i< jsonArray.length();i++){
            try {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Game team = new Game();
                team.team_1_name = jsonObject.getString("team_1_name");
                team.team_2_name = jsonObject.getString("team_2_name");
                team.team_1_image = jsonObject.getString("team_1_image");
                team.team_2_image = jsonObject.getString("team_2_image");
                team.date = jsonObject.getString("date");
                team.channel_name = jsonObject.getString("channel_name");
                team.team_1_score = jsonObject.getString("team_1_score");
                team.team_2_score = jsonObject.getString("team_2_score");
                teams.add(team);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return teams;
    }

    public String getTeam1ImageAddress() {
        return ConstantHelper.TEAM_IMAGE_FOLDER + team_1_image;
    }
    public String getTeam2ImageAddress() {
        return ConstantHelper.TEAM_IMAGE_FOLDER + team_2_image;
    }

    public String getPersianDate2(){
        return date.substring(0,10);
    }

    public String getPersianDate() {
        String res="";
        try {
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(5, 7));
            int day = Integer.parseInt(date.substring(8, 10));
            PersianCalendar persianCalendar = new PersianCalendar();
            persianCalendar.setGregorianDate(year,month,day);
//            res =  persianCalendar.getIranianDate();

            res = persianCalendar.getPersianWeekDayStr()+" "+persianCalendar.irDay+" "+persianCalendar.getPersianMonthName();

        }catch (Exception e){
            e.printStackTrace();
        }
        return res;


    }

}



