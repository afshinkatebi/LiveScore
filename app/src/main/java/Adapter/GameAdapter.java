package Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.ir.irdevelopers.Tamashachi.R;

import java.util.ArrayList;
import java.util.Calendar;

import DataModel.Game;
import Helpers.VolleySingleton;
import Views.TextViewFont;
import de.hdodenhof.circleimageview.CircleImageView;


public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {
    private ArrayList<Game> mDataset;


    private Context context;



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder


    public void add(Game item) {
        mDataset.add(item);
        notifyItemInserted(mDataset.size() - 1);
    }

    public void add(ArrayList<Game> items) {
        Integer oldCount = getItemCount();
        mDataset.addAll(items);
        notifyItemInserted(oldCount);
    }


    public void remove(Game item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public GameAdapter(Context context, ArrayList<Game> myDataset) {
        this.context = context;
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final Game game = mDataset.get(position);
        //TODO enja error daram
        holder.team_1_name.setText(game.team_1_name);
        holder.team_2_name.setText(game.team_2_name);
        VolleySingleton.getInstance(context).getImageLoader().get(game.getTeam1ImageAddress(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                holder.team_1_image.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleySingleton.getInstance(context).getImageLoader().get(game.getTeam2ImageAddress(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                holder.team_2_image.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        holder.date.setText(game.date.substring(11,16));

        holder.resault.setText(game.channel_name);

        if (game.team_1_score.length()>0)
            holder.resault.setText(game.team_1_score + " - " + game.team_2_score);




    }

    public static String getTime(String timestring){
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.parseLong(timestring));
        return cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextViewFont team_1_name;
        private final TextViewFont team_2_name;
        private final CircleImageView team_1_image;
        private final CircleImageView team_2_image;
        private final TextViewFont date;
        private final TextViewFont resault;

        // each data item is just a string in this case
        public View v;

        public ViewHolder(View v) {
            super(v);
            team_1_name = (TextViewFont) v.findViewById(R.id.team_1_name);
            team_2_name = (TextViewFont) v.findViewById(R.id.team_2_name);
            team_1_image = (de.hdodenhof.circleimageview.CircleImageView) v.findViewById(R.id.team_1_image);
            team_2_image = (de.hdodenhof.circleimageview.CircleImageView) v.findViewById(R.id.team_2_image);
            date = (TextViewFont) v.findViewById(R.id.date);
            resault = (TextViewFont) v.findViewById(R.id.resault);
            this.v=v;
        }
    }

}