package com.vaagdevi.newsnevents;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class NewsActivity extends AppCompatActivity {

    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        refreshLayout = findViewById(R.id.refresh_news);

        refreshLayout.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 1500);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkConnection();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), NewsActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                    }
                }, 1500);
            }
        });

        Toast.makeText(NewsActivity.this,"Loading News",Toast.LENGTH_LONG).show();
        WebView newsview =(WebView) findViewById(R.id.webview_news);
        newsview.getSettings().setJavaScriptEnabled(true);
        newsview.setWebViewClient(new WebViewClient());
        newsview.loadUrl("https://vaagdevinewsnevents.blogspot.com/2020/03/news-n-events.html");

    }

    public void checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (null == activeNetwork) {
            Toast.makeText(NewsActivity.this, "No Internet Connection!",
                    Toast.LENGTH_LONG).show();
        }
    }
}
