package DataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Helpers.ConstantHelper;

/**
 * Created by Alip on 6/19/2016.
 */
public class Game {
    public int uid = 0 ;
    public int event_uid = 0 ;
    public String team_1_name = "";
    public String team_2_name = "";
    public String team_1_image = "";
    public String team_2_image = "";
    public String date = "";
    public String channel_name = "";
    public String team_1_score = "" ;
    public String team_2_score = "" ;


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

}



