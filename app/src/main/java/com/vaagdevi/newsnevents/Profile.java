package com.vaagdevi.newsnevents;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    String currentId;

    TextView profileemail;
    TextView profileusername;
    TextView profilemobilenumber;
    TextView profilepassword;
    ImageView profilephoto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        firebaseAuth = FirebaseAuth.getInstance();
        currentId = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("News n Events").child(currentId);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                profileemail = (TextView) findViewById(R.id.profile_emailTV);
                profileusername = (TextView) findViewById(R.id.profile_usernameTV);
                profilemobilenumber = (TextView) findViewById(R.id.profile_mobilenumberTV);
                profilepassword = (TextView) findViewById(R.id.profile_passwordTV);
                profilephoto = (ImageView) findViewById(R.id.profile_photo);


                String Email = dataSnapshot.child("email").getValue().toString();
                String Username = dataSnapshot.child("username").getValue().toString();
                String MobileNumber = dataSnapshot.child("mobilenumber").getValue().toString();
                String Password = dataSnapshot.child("password").getValue().toString();


                profileemail.setText("Email : "+Email);
                profileusername.setText("Username : "+Username);
                profilemobilenumber.setText("Mobile Number : "+MobileNumber);
                profilepassword.setText("Password : "+Password);
                profilephoto.setImageResource(R.mipmap.ic_launcher);


                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();

                // Build a GoogleSignInClient with the options specified by gso.
                mGoogleSignInClient = GoogleSignIn.getClient(Profile.this, gso);

                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(Profile.this);
                if (acct != null) {
                    String personName = acct.getDisplayName();
                    String personGivenName = acct.getGivenName();
                    String personFamilyName = acct.getFamilyName();
                    String personEmail = acct.getEmail();
                    String personId = acct.getId();

                    Uri personPhoto = acct.getPhotoUrl();

                    profileemail.setText("Email : "+personEmail);
                    profileusername.setText("Username : "+personName);
                    profilemobilenumber.setText(null);
                    profilepassword.setText(null);

                    if (personPhoto != null) {

                        Glide.with(Profile.this).load(personPhoto).into(profilephoto);

                    } else {
                        profilephoto.setImageResource(R.mipmap.ic_launcher);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });




    }
}
