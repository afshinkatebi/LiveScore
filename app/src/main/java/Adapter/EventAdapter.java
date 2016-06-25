package Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ir.irdevelopers.Tamashachi.R;
import com.ir.irdevelopers.Tamashachi.TeamsActivity;

import java.util.ArrayList;
import DataModel.Event;
import Helpers.VolleySingleton;
import Views.AutoNetworkImageView;
import Views.TextViewFont;


public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private ArrayList<Event> mDataset;


    private Context context;



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public AutoNetworkImageView image;
        public TextViewFont name;
        public View v;

        public ViewHolder(View v) {
            super(v);
            name = (TextViewFont) v.findViewById(R.id.event_name);
            image = (AutoNetworkImageView) v.findViewById(R.id.event_image);
            this.v=v;
        }
    }

    public void add(Event item) {
        mDataset.add(item);
        notifyItemInserted(mDataset.size() - 1);
    }

    public void add(ArrayList<Event> items) {
        Integer oldCount = getItemCount();
        mDataset.addAll(items);
        notifyItemInserted(oldCount);
    }


    public void remove(Event item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public EventAdapter(Context context, ArrayList<Event> myDataset) {
        this.context = context;
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final Event event = mDataset.get(position);
        //TODO enja error daram
        holder.name.setText(event.name);
        holder.image.setImageUrlWithAnim(event.getImageAddress(), VolleySingleton.getInstance(context).getImageLoader());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, TeamsActivity.class);
                intent.putExtra("event",event);
                context.startActivity(intent);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}