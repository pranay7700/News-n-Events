package com.vaagdevi.newsnevents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class News extends Fragment {

    private NewsViewModel mViewModel;

    public static News newInstance() {

        return new News();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.news_fragment, container, false);

       WebView newsview =(WebView) view.findViewById(R.id.webviewnews);

        newsview.getSettings().setJavaScriptEnabled(true);

        newsview.setWebViewClient(new WebViewClient());

        newsview.loadUrl("https://vaagdevinewsevents.blogspot.com/2020/03/news-n-events.html");


        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        // TODO: Use the ViewModel

    }




}
