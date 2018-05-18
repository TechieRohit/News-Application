package com.newsapp.rohit.newsapplication.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.newsapp.rohit.newsapplication.R;
import com.newsapp.rohit.newsapplication.adapter.RecyclerNewsSource;
import com.newsapp.rohit.newsapplication.dataModel.NewsSource;
import com.newsapp.rohit.newsapplication.utlis.Constants;
import com.newsapp.rohit.newsapplication.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private ArrayList<NewsSource> mArrayList = new ArrayList<>();

    private RecyclerNewsSource mNewsSource;

    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.actionbar_main);
        setSupportActionBar(toolbar);

        TextView toolbarTextView = (TextView)findViewById(R.id.actionbar_title);
        toolbarTextView.setText(R.string.news_source);
        toolbarTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setRefreshing(true);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_news_source);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mNewsSource = new RecyclerNewsSource(this,mArrayList);
        mRecyclerView.setAdapter(mNewsSource);

        makeRequest();

        onClickListeners();
    }

    /**
     * Making a request to the server for response
     */
    private void makeRequest() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,Constants.NEWS_SOURCE,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this,"Something went wrong !\n" +
                        "Check your internet connection",Toast.LENGTH_LONG).show();

                Log.d("NEWS_SOURCE", error.toString());
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        RequestQueue requestQueue = VolleySingleton.getInstance(this).getRequestQueue();
        requestQueue.add(jsonObjectRequest);
    }

    /**
     * mArrayList carry the data from APIs
     * the data is being assigned using a constructor from the data model of NewsSource
     * here the data is put into an ArrayList and notifyDataSetChanged is called to notify the adapter
     */
    private void parseResponse(JSONObject response) {

        mArrayList.clear();

        try {
            JSONArray jsonArray = response.getJSONArray("articles");

            for (int i = 0; i<jsonArray.length(); i++) {
                JSONObject arrayObj = jsonArray.getJSONObject(i);

                JSONObject sourceObj = arrayObj.getJSONObject("source");

                String sourceId = sourceObj.getString("id");
                String sourceName = sourceObj.getString("name");

                if (!sourceId.equals("null")) {
                    String urlToImage = arrayObj.getString("urlToImage");

                    mArrayList.add(new NewsSource(sourceId,urlToImage,sourceName));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Notifying the adapter that the data has been changed
        mNewsSource.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
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
}
