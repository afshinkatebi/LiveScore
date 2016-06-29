package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.ir.irdevelopers.Tamashachi.R;

import java.util.ArrayList;

import DataModel.Game;
import Helpers.VolleySingleton;
import Views.PinnedSectionListView;
import Views.TextViewFont;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Alip on 6/19/2016.
 */
public class PinnedGameAdapter extends BaseAdapter
        implements PinnedSectionListView.PinnedSectionListAdapter {
    private final Context context;
    private ArrayList<Game> mDataset;

    public PinnedGameAdapter(Context context, ArrayList<Game> mDataset) {
        this.context = context;
        this.mDataset = mDataset;
        preProccessDataset();
    }


    // We implement this method to return 'true' for all view types we want to pin
    @Override
    public boolean isItemViewTypePinned(int viewType) {
        if (viewType == 1)
            return true;
        else
            return false;
    }

    @Override
    public int getCount() {
        return mDataset.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataset.get(position).isHeader)
            return 1;
        else
            return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        // fill Game View
        final Game game = mDataset.get(position);

        if (game.isHeader) {
            vh.header.setVisibility(View.VISIBLE);
            vh.content.setVisibility(View.GONE);

            vh.header.setText(game.getPersianDate());

        } else {
            vh.header.setVisibility(View.GONE);
            vh.content.setVisibility(View.VISIBLE);
            vh.team_1_name.setText(game.team_1_name);
            vh.team_2_name.setText(game.team_2_name);
            VolleySingleton.getInstance(context).getImageLoader().get(game.getTeam1ImageAddress(), new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    vh.team_1_image.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            VolleySingleton.getInstance(context).getImageLoader().get(game.getTeam2ImageAddress(), new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    vh.team_2_image.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });


            vh.date.setText(game.date.substring(11, 16));

            vh.resault.setText(game.channel_name);

            if (game.team_1_score.length() > 0)
                vh.resault.setText(game.team_1_score + " - " + game.team_2_score);

        }


        return convertView;
    }

    public void preProccessDataset() {
        ArrayList<Game> newDataset = new ArrayList<>();
        String date = "";
        for (Game game : mDataset) {


            if (!game.date.substring(0, 10).equals(date)) {
                newDataset.add(new Game(game.date));
                date = game.date.substring(0, 10);
            }
            newDataset.add(game);
        }

        mDataset.clear();
        mDataset.addAll(newDataset);
    }


    public int getUnplayedPosition(){
        for(int i = 0 ; i<mDataset.size();i++){
            if (mDataset.get(i).team_1_score.length()!=0)
                return i;
        }
        return 0;
    }


    public class ViewHolder {
        private final TextViewFont team_1_name;
        private final TextViewFont team_2_name;
        private final CircleImageView team_1_image;
        private final CircleImageView team_2_image;
        private final TextViewFont date;
        private final TextViewFont resault;
        private final TextViewFont header;
        private final View content;

        // each data item is just a string in this case
        public View v;

        public ViewHolder(View v) {
            team_1_name = (TextViewFont) v.findViewById(R.id.team_1_name);
            team_2_name = (TextViewFont) v.findViewById(R.id.team_2_name);
            team_1_image = (de.hdodenhof.circleimageview.CircleImageView) v.findViewById(R.id.team_1_image);
            team_2_image = (de.hdodenhof.circleimageview.CircleImageView) v.findViewById(R.id.team_2_image);
            date = (TextViewFont) v.findViewById(R.id.date);
            resault = (TextViewFont) v.findViewById(R.id.resault);
            header = (TextViewFont) v.findViewById(R.id.header);
            content = v.findViewById(R.id.content);
            this.v = v;
        }
    }
}