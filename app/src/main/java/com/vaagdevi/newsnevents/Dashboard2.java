package com.vaagdevi.newsnevents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard2 extends AppCompatActivity {


    GoogleSignInClient mGoogleSignInClient;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    Button logout;
    TextView dashboardname;
    TextView dashboardemail;
    TextView dashboarduserid;

    ImageView dashboardphoto;

    Button dashboardupdateprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard2);

        logout=(Button)findViewById(R.id.logoutBTN);

        dashboardname=findViewById(R.id.nameTV);
        dashboardemail=findViewById(R.id.emailTV);
        dashboarduserid=findViewById(R.id.useridTV);

        dashboardphoto=findViewById(R.id.photoIV);

        dashboardupdateprofile=findViewById(R.id.updateprofileBTN);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Regdatabase userprofile = dataSnapshot.getValue(Regdatabase.class);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {



            }
        });


        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(Dashboard2.this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();

            Uri personPhoto = acct.getPhotoUrl();

            dashboardname.setText("Name : "+personName);
            dashboardemail.setText("Email : "+personEmail);
            dashboarduserid.setText("ID : "+personId);

            Glide.with(this).load(personPhoto).into(dashboardphoto);
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }

    private void signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(Dashboard2.this,"Successfully signed out",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Dashboard2.this, MainActivity.class));
                finish();
            }
        });



    }

}