package DataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Helpers.ConstantHelper;

/**
 * Created by Alip on 6/19/2016.
 */
public class Team {
    public int uid = 0 ;
    public String name = "";
    public String image = "";
    public boolean isSelected = false;
    public boolean isSelected_old=false;
    public boolean isClickable=true;

    public static ArrayList<Team> parse(JSONArray jsonArray){
        ArrayList<Team> teams = new ArrayList<>();
        for(int i = 0 ; i< jsonArray.length();i++){
            try {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Team team = new Team();
                team.uid = jsonObject.getInt("uid");
                team.name = jsonObject.getString("name");
                team.image = jsonObject.getString("image");
                team.isSelected = jsonObject.getInt("is_selected") == 1;
                teams.add(team);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return teams;
    }

    public String getImageAddress() {
        return ConstantHelper.TEAM_IMAGE_FOLDER + image;
    }

}



