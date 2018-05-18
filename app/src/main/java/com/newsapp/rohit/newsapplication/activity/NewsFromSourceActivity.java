package com.newsapp.rohit.newsapplication.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.newsapp.rohit.newsapplication.R;
import com.newsapp.rohit.newsapplication.adapter.RecyclerNewsFromTheSource;
import com.newsapp.rohit.newsapplication.dataModel.NewsFromSource;
import com.newsapp.rohit.newsapplication.utlis.Constants;
import com.newsapp.rohit.newsapplication.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsFromSourceActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private ArrayList<NewsFromSource> mArrayList = new ArrayList<>();

    RecyclerNewsFromTheSource newsFromSource;

    private Toolbar mToolbar;

    SwipeRefreshLayout swipeRefreshLayout;

    private String mSourceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_from_source);
        mSourceId = getIntent().getStringExtra(Constants.SOURCE_ID);
        mToolbar = (Toolbar)findViewById(R.id.actionbar);
        setSupportActionBar(mToolbar);

        TextView toolbarTextView = (TextView)findViewById(R.id.actionbar_title);
        toolbarTextView.setText(getIntent().getStringExtra(Constants.SOURCE_NAME));
        toolbarTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (Exception e) {

        }

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setRefreshing(true);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_news_from_source);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsFromSource = new RecyclerNewsFromTheSource(this,mArrayList);
        mRecyclerView.setAdapter(newsFromSource);

        makeRequest();

        onClickListeners();
    }

    private void onClickListeners() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                makeRequest();
            }
        });
    }

    /**
     * Making a request to the server for response
     */
    private void makeRequest() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.NEWS_SOURCE_ID + mSourceId +
                Constants.PLUS_API_KEY,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewsFromSourceActivity.this,"Something went wrong !\n" +
                        "Check your internet connection",Toast.LENGTH_LONG).show();

                Log.d("NEWS_ITEM", error.toString());
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        RequestQueue requestQueue = VolleySingleton.getInstance(this).getRequestQueue();
        requestQueue.add(jsonObjectRequest);
    }

    /**
     * mArrayList carry the data from APIs
     * the data is being assigned using a constructor from the data model of NewsFromSource
     * here the data is put into an ArrayList and notifyDataSetChanged is called to notify the adapter
     */
    private void parseResponse(JSONObject response) {
        mArrayList.clear();

        try {
            JSONArray jsonArray = response.getJSONArray("articles");

            for (int i = 0; i<jsonArray.length(); i++) {
                JSONObject arrayObj = jsonArray.getJSONObject(i);

                mArrayList.add(new NewsFromSource(arrayObj.getString("author"),arrayObj.getString("title"),
                        arrayObj.getString("description"),arrayObj.getString("url"),
                        arrayObj.getString("urlToImage"),arrayObj.getString("publishedAt")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        newsFromSource.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
