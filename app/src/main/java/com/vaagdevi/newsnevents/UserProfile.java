package com.vaagdevi.newsnevents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends Fragment {

    private UserProfileViewModel mViewModel;

    TextView userprofileemail;
    TextView userprofileusername;
    TextView userprofilepassword;
    TextView userprofileconfirmpassword;
    TextView userprofilemobilenumber;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    String currentId;





    public static UserProfile newInstance() {
        return new UserProfile();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View userprofileview = inflater.inflate(R.layout.user_profile_fragment, container, false);

        userprofileemail = (TextView) userprofileview.findViewById(R.id.userprofile_emailTV);
        userprofileusername = (TextView) userprofileview.findViewById(R.id.userprofile_usernameTV);
        userprofilepassword = (TextView) userprofileview.findViewById(R.id.userprofile_passwordTV);
        userprofileconfirmpassword = (TextView) userprofileview.findViewById(R.id.userprofile_confirmpasswordTV);
        userprofilemobilenumber = (TextView) userprofileview.findViewById(R.id.userprofile_mobilenumberTV);

        firebaseAuth = FirebaseAuth.getInstance();
        currentId = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("News n Events").child(currentId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String Email = dataSnapshot.child("email").getValue(Regdatabase.class).toString().trim();
                String Username = dataSnapshot.child("username").getValue(Regdatabase.class).toString().trim();
                String Password = dataSnapshot.child("password").getValue(Regdatabase.class).toString().trim();
                String ConfirmPassword = dataSnapshot.child("confirmpassword").getValue(Regdatabase.class).toString().trim();
                String MobileNumber = dataSnapshot.child("mobilenumber").getValue(Regdatabase.class).toString().trim();

                userprofileemail.setText(Email);
                userprofileusername.setText(Username);
                userprofilepassword.setText(Password);
                userprofileconfirmpassword.setText(ConfirmPassword);
                userprofilemobilenumber.setText(MobileNumber);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {



            }
        });


        return userprofileview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UserProfileViewModel.class);
        // TODO: Use the ViewModel


    }




}
