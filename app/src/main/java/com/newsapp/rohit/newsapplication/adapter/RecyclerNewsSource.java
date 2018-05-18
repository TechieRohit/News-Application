package com.newsapp.rohit.newsapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newsapp.rohit.newsapplication.R;
import com.newsapp.rohit.newsapplication.activity.NewsFromSourceActivity;
import com.newsapp.rohit.newsapplication.dataModel.NewsSource;
import com.newsapp.rohit.newsapplication.utlis.Constants;

import java.util.ArrayList;

/**
 * Created by ROHIT on 5/17/2018.
 */

public class RecyclerNewsSource extends RecyclerView.Adapter<RecyclerNewsSource.RecyclerSource> {

    private Context mContext;

    private ArrayList<NewsSource> mNewsSources = new ArrayList<>();

    public RecyclerNewsSource(Context context, ArrayList<NewsSource> newsSource) {
        this.mContext = context;
        this.mNewsSources = newsSource;
    }

    /**
     * Used for creating the view
     * we can also return different view types by using parameter viewType
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public RecyclerSource onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_news_sorce,parent,false);

        RecyclerSource recyclerSource = new RecyclerSource(view);

        return recyclerSource;
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerSource holder, int position) {

        Glide.with(mContext).load(mNewsSources.get(position).getmImageUrl()).placeholder(R.drawable.news).
                into(holder.sourceImagee);
        holder.sourceName.setText("SOURCE NAME - " + mNewsSources.get(position).getmName());
    }

    /**
     * Returns total size of items in recyclerview
     * @return
     */
    @Override
    public int getItemCount() {
        return mNewsSources.size();
    }

    class RecyclerSource extends RecyclerView.ViewHolder {

        ImageView sourceImagee;
        TextView sourceName;

        LinearLayout source;

        public RecyclerSource(View itemView) {
            super(itemView);

            sourceImagee = (ImageView)itemView.findViewById(R.id.imageView_source_image);
            sourceName = (TextView)itemView.findViewById(R.id.textView_source_name);

            source = (LinearLayout)itemView.findViewById(R.id.linearLayout_source);

            source.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, NewsFromSourceActivity.class);
                    intent.putExtra(Constants.SOURCE_ID,mNewsSources.get(getAdapterPosition()).getmId());
                    intent.putExtra(Constants.SOURCE_NAME,mNewsSources.get(getAdapterPosition()).getmName());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
