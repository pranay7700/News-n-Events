package com.vaagdevi.newsnevents.ui.home;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.vaagdevi.newsnevents.Events.Events;
import com.vaagdevi.newsnevents.GuestLectures;
import com.vaagdevi.newsnevents.News.News;
import com.vaagdevi.newsnevents.Notifications;
import com.vaagdevi.newsnevents.Profile;
import com.vaagdevi.newsnevents.R;
import com.vaagdevi.newsnevents.Workshops;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    ImageView HomeBackground,HomeLogo,HomeClover,HomeEvents,HomeNews,HomeNotifications,HomeProfile,HomeGuestLectures,HomeWorkshops;
    LinearLayout HomeTextsplash, HomeExplore, HomeMenus;
    Animation FromBottom;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);

        FromBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.frombottom);


        HomeBackground = (ImageView) root.findViewById(R.id.home_background);
        HomeLogo = (ImageView) root.findViewById(R.id.home_splashlogo);
        HomeClover = (ImageView) root.findViewById(R.id.home_clover);
        HomeEvents = (ImageView) root.findViewById(R.id.home_events);
        HomeNews = (ImageView) root.findViewById(R.id.home_news);
        HomeNotifications = (ImageView) root.findViewById(R.id.home_notifications);
        HomeProfile = (ImageView) root.findViewById(R.id.home_profile);
        HomeGuestLectures = (ImageView) root.findViewById(R.id.home_guestlectures);
        HomeWorkshops = (ImageView) root.findViewById(R.id.home_workshops);


        HomeTextsplash = (LinearLayout) root.findViewById(R.id.home_textsplash);
        HomeExplore = (LinearLayout) root.findViewById(R.id.home_explore);
        HomeMenus = (LinearLayout) root.findViewById(R.id.home_menus);


        //HomeBackground.animate().translationY(-1900).setDuration(800).setStartDelay(500);
        //HomeLogo.animate().alpha(0).setDuration(800).setStartDelay(800);
        HomeClover.animate().alpha(0).setDuration(800).setStartDelay(800);
        HomeLogo.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(800);
        HomeTextsplash.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(800);

        HomeBackground.startAnimation(FromBottom);
        HomeExplore.startAnimation(FromBottom);
        HomeMenus.startAnimation(FromBottom);


        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        HomeEvents.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                Events eventsFragment = new Events();
                FragmentTransaction eventsFragmentTransaction = getFragmentManager().beginTransaction();
                eventsFragmentTransaction.replace(R.id.home_fragment,eventsFragment);
                eventsFragmentTransaction.commit();


            }
        });

        HomeNews.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                News newsFragment =new News();
                FragmentTransaction newsFragmentTransaction = getFragmentManager().beginTransaction();
                newsFragmentTransaction.replace(R.id.home_fragment,newsFragment);
                newsFragmentTransaction.commit();

            }
        });

        HomeNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), Notifications.class));

            }
        });

        HomeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), Profile.class));

            }
        });

        HomeGuestLectures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), GuestLectures.class));

            }
        });

        HomeWorkshops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), Workshops.class));

            }
        });

        return root;
    }

    private FragmentManager getSupportFragmentManager() {
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
    }
}