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

public class Events extends Fragment {



    private EventsViewModel mViewModel;

    public static Events newInstance() {
        return new Events();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.events_fragment, container, false);

        WebView eventsview =(WebView) view.findViewById(R.id.webviewevents);

        eventsview.getSettings().setJavaScriptEnabled(true);

        eventsview.setWebViewClient(new WebViewClient());

        eventsview.loadUrl("https://vaagdevinewsevents.blogspot.com/2020/05/events.html");


        return view;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EventsViewModel.class);
        // TODO: Use the ViewModel
    }

}
