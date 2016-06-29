package Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.ir.irdevelopers.Tamashachi.R;
import com.ir.irdevelopers.Tamashachi.TeamsActivity;

import java.util.ArrayList;

import DataModel.Event;
import DataModel.News;
import Helpers.VolleySingleton;
import Views.AutoNetworkImageView;
import Views.TextViewFont;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private ArrayList<News> mDataset;


    private Context context;



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public AutoNetworkImageView image;
        public TextViewFont title;
        public TextViewFont date;
        public View v;

        public ViewHolder(View v) {
            super(v);
            title = (TextViewFont) v.findViewById(R.id.title);
            date = (TextViewFont) v.findViewById(R.id.date);
            image = (AutoNetworkImageView) v.findViewById(R.id.image);
            this.v=v;
        }
    }

    public void add(News item) {
        mDataset.add(item);
        notifyItemInserted(mDataset.size() - 1);
    }

    public void add(ArrayList<News> items) {
        Integer oldCount = getItemCount();
        mDataset.addAll(items);
        notifyItemInserted(oldCount);
    }


    public void remove(Event item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the knnind of dataset)
    public NewsAdapter(Context context, ArrayList<News> myDataset) {
        this.context = context;
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final News news = mDataset.get(position);
        //TODO enja error daram
        holder.title.setText(news.title);
        holder.date.setText(news.getPersianDate());

        holder.image.setImageUrlWithAnim(news.getImageAddress(), VolleySingleton.getInstance(context).getImageLoader());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(context, TeamsActivity.class);
//                intent.putExtra("event",event);
//                context.startActivity(intent);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View dialog = LayoutInflater.from(context).inflate(R.layout.dialog_news,null);
                TextViewFont title = (TextViewFont) dialog.findViewById(R.id.title);
                TextViewFont description = (TextViewFont) dialog.findViewById(R.id.description);
                TextViewFont date = (TextViewFont) dialog.findViewById(R.id.date);

                AutoNetworkImageView image = (AutoNetworkImageView) dialog.findViewById(R.id.image);
                title.setText(news.title);
                date.setText(news.getPersianDate());
                description.setText(news.description);
                image.setImageUrlWithAnim(news.getImageAddress(), VolleySingleton.getInstance(context).getImageLoader());
                builder.setView(dialog);
                builder.show();


            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}