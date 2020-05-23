package com.vaagdevi.newsnevents.Developers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.vaagdevi.newsnevents.R;

public class Developers extends Fragment {

    private DevelopersViewModel mViewModel;

    ImageView DeveloperInstagram;
    ImageView DeveloperGmail;
    ImageView DeveloperPhone;

    public static Developers newInstance() {
        return new Developers();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View developerview = inflater.inflate(R.layout.developers_fragment, container, false);

        DeveloperInstagram = developerview.findViewById(R.id.developer_instagramBTN);
        DeveloperGmail = developerview.findViewById(R.id.developer_gmailBTN);
        DeveloperPhone = developerview.findViewById(R.id.developer_phoneBTN);


        DeveloperInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent instagramintent = new Intent(Intent.ACTION_VIEW);
                instagramintent.setData(Uri.parse("https://www.instagram.com/pranay_7700/?hl=en"));
                startActivity(instagramintent);

            }
        });

        DeveloperGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] recipients={"udayagiripranay7@gmail.com"};
                Intent gmailintent = new Intent(Intent.ACTION_SEND);
                gmailintent.putExtra(Intent.EXTRA_EMAIL, recipients);
                gmailintent.setType("text/plain");
                startActivity(gmailintent);


            }
        });

        DeveloperPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent phoneintent = new Intent(Intent.ACTION_DIAL);
                phoneintent.setData(Uri.parse("tel:+918008763661"));
                startActivity(phoneintent);

            }
        });

        return developerview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DevelopersViewModel.class);
        // TODO: Use the ViewModel
    }


    private static class LinkMovementMethod {
    }
}
