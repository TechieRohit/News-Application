package com.newsapp.rohit.newsapplication.activity;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.newsapp.rohit.newsapplication.R;
import com.newsapp.rohit.newsapplication.utlis.Constants;

public class WebViewActivity extends AppCompatActivity {

    private WebView mWebView;

    private ProgressBar mProgressBar;

    private String mUrl;

    SwipeRefreshLayout swipeRefreshLayout;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web_view);
        mUrl = getIntent().getStringExtra(Constants.SOURCE_URL);
        mWebView = (WebView)findViewById(R.id.webview);

        mProgressBar = (ProgressBar)findViewById(R.id.progressbar);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.actionbar);
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (Exception e) {

        }

        TextView toolbarTextView = (TextView)findViewById(R.id.actionbar_title);
        toolbarTextView.setText(R.string.browser);
        toolbarTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setRefreshing(true);

        handler = new Handler();

        configureWebView();
        loadUrl();

        onClickListeners();
    }

    /**
     * This method is used for loading an actual url in the webview
     * we can check the progress by setting us a chrome client which
     * returns the value of progress in integer
     */
    private void loadUrl() {

        mWebView.getSettings().setUserAgentString(Constants.USER_AGENT);

        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress)
            {
                mProgressBar.setProgress(progress);
                if (progress > 70) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (progress == 100) {

                    mProgressBar.setVisibility(View.GONE);
                }
            }

        });

        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String request) {
                view.loadUrl(request);
                return false;
            }
        });

        mWebView.loadUrl(mUrl);
    }

    /**
     * Here the basic webview settings are configured like enabling javascript etc
     */
    private void configureWebView() {

        WebSettings settings = mWebView.getSettings();
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setJavaScriptEnabled(true);

    }

    /**
     * Here is manages backpress in the default webview of android
     * it helps in navigating the page in the browser and override the
     * functionality of backpressed such that user will land on the previous page of browser
     * and will not exit the webview activity untill its the last web page in stack
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
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

    private void onClickListeners() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadUrl();
                swipeRefreshLayout.setRefreshing(true);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },2500);
            }
        });
    }
}
