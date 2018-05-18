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
import com.newsapp.rohit.newsapplication.activity.WebViewActivity;
import com.newsapp.rohit.newsapplication.dataModel.NewsFromSource;
import com.newsapp.rohit.newsapplication.utlis.Constants;

import java.util.ArrayList;

/**
 * Created by ROHIT on 5/17/2018.
 */

public class RecyclerNewsFromTheSource extends RecyclerView.Adapter<RecyclerNewsFromTheSource.RecyclerNews> {

    private Context mContext;

    private ArrayList<NewsFromSource> newsFromSources  = new ArrayList<>();

    public RecyclerNewsFromTheSource(Context mContext, ArrayList<NewsFromSource> newsFromSources) {
        this.mContext = mContext;
        this.newsFromSources = newsFromSources;
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
    public RecyclerNews onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_news_from_source,parent,false);

        RecyclerNews news = new RecyclerNews(view);

        return news;
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerNews holder, int position) {
        Glide.with(mContext).load(newsFromSources.get(position).getmImageUrl()).into(holder.banner);

        holder.title.setText("" + newsFromSources.get(position).getmTitle());
        holder.description.setText("" + newsFromSources.get(position).getmDescription());
        holder.author.setText("Author - " + newsFromSources.get(position).getmAuthor());
        holder.date.setText("" + newsFromSources.get(position).getmDate());
    }

    /**
     * Returns total size of items in recyclerview
     * @return
     */
    @Override
    public int getItemCount() {
        return newsFromSources.size();
    }

    class RecyclerNews extends  RecyclerView.ViewHolder {

        ImageView banner;
        TextView author,title,description,date;
        LinearLayout news;

        public RecyclerNews(View itemView) {
            super(itemView);

            banner = (ImageView)itemView.findViewById(R.id.imageView_news_image);
            author = (TextView)itemView.findViewById(R.id.textView_author);
            title = (TextView)itemView.findViewById(R.id.textView_news_title);
            description = (TextView)itemView.findViewById(R.id.textView_news_description);
            date = (TextView)itemView.findViewById(R.id.textView_date);

            news = (LinearLayout)itemView.findViewById(R.id.linearLayout_from_source);

            news.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra(Constants.SOURCE_URL,newsFromSources.get(getAdapterPosition()).getmUrl());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
