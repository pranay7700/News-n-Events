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

public class EventsActivity extends AppCompatActivity {

    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        refreshLayout = findViewById(R.id.refresh_events);

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
                        startActivity(new Intent(getApplicationContext(), EventsActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                    }
                }, 1500);
            }
        });

        Toast.makeText(EventsActivity.this,"Loading Events",Toast.LENGTH_SHORT).show();
        WebView eventsview =(WebView) findViewById(R.id.webview_events);
        eventsview.getSettings().setJavaScriptEnabled(true);
        eventsview.setWebViewClient(new WebViewClient());
        eventsview.loadUrl("https://vaagdevinewsnevents.blogspot.com/2020/05/events.html");

    }

    public void checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (null == activeNetwork) {
            Toast.makeText(EventsActivity.this, "No Internet Connection!",
                    Toast.LENGTH_LONG).show();
        }
    }
}
