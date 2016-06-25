package Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.ir.irdevelopers.Tamashachi.R;

import java.util.ArrayList;

import DataModel.Team;
import Helpers.VolleySingleton;
import Interface.TeamOnClickListener;
import Views.TextViewFont;


public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {
    private ArrayList<Team> mDataset;


    private Context context;
    private RequestQueue requestQueue;
    private TeamOnClickListener onItemClickListener;

    public void setOnItemClickListener(TeamOnClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void subscribe(Team team) {
        for (int i = 0; i < mDataset.size(); i++) {
            if (mDataset.get(i).uid == team.uid){
                mDataset.get(i).isSelected = true;
                notifyItemChanged(i);
                break;
            }


        }
    }

    public void unsubscribe(Team team) {
        for (int i = 0; i < mDataset.size(); i++) {
            if (mDataset.get(i).uid == team.uid){
                mDataset.get(i).isSelected = false;
                notifyItemChanged(i);
                break;
            }

        }
    }

    public void subscribeAll() {
        for (int i = 0; i < mDataset.size(); i++) {
            mDataset.get(i).isSelected_old = mDataset.get(i).isSelected;
            mDataset.get(i).isSelected = true;
        }
        notifyDataSetChanged();
    }

    public void cancel_Subscribe_unSubscribe_All() {
        for (int i = 0; i < mDataset.size(); i++) {
            mDataset.get(i).isSelected = mDataset.get(i).isSelected_old;
        }
        notifyDataSetChanged();
    }

    public void unsubscribeAll() {
        for (int i = 0; i < mDataset.size(); i++) {
            mDataset.get(i).isSelected_old = mDataset.get(i).isSelected;
            mDataset.get(i).isSelected = false;
        }
        notifyDataSetChanged();
    }

    public void disableClickForTeam(Team team) {
        for (Team item : mDataset) {
            if (item.uid == team.uid)
                item.isClickable = false;
            break;
        }
    }

    public void enableClickForTeam(Team team) {
        for (Team item : mDataset) {
            if (item.uid == team.uid)
                item.isClickable = true;
            break;
        }
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View checkmark;
        // each data item is just a string in this case
        public de.hdodenhof.circleimageview.CircleImageView image;
        public TextViewFont name;
        public View v;

        public ViewHolder(View v) {
            super(v);
            name = (TextViewFont) v.findViewById(R.id.team_name);
            image = (de.hdodenhof.circleimageview.CircleImageView) v.findViewById(R.id.team_image);
            checkmark = v.findViewById(R.id.checkmark);
            this.v = v;
        }
    }

    public void add(Team item) {
        mDataset.add(item);
        notifyItemInserted(mDataset.size() - 1);
    }

    public void add(ArrayList<Team> items) {
        Integer oldCount = getItemCount();
        mDataset.addAll(items);
        notifyItemInserted(oldCount);
    }


    public void remove(Team item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TeamAdapter(Context context, ArrayList<Team> myDataset) {
        this.context = context;
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final Team team = mDataset.get(position);
        //TODO enja error daram
        holder.name.setText(team.name);
        if (team.isSelected)
            holder.checkmark.setVisibility(View.VISIBLE);
        else
            holder.checkmark.setVisibility(View.GONE);

        VolleySingleton.getInstance(context).getImageLoader().get(team.getImageAddress(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                holder.image.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(team);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}