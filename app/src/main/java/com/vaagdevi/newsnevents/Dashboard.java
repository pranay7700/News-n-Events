package com.vaagdevi.newsnevents;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.RemoteMessage;

import static com.vaagdevi.newsnevents.R.id.action_logout;
import static com.vaagdevi.newsnevents.R.id.action_settings;
import static com.vaagdevi.newsnevents.R.id.drawer_layout;
import static com.vaagdevi.newsnevents.R.id.emailTV;
import static com.vaagdevi.newsnevents.R.id.nameTV;
import static com.vaagdevi.newsnevents.R.id.nav_home;
import static com.vaagdevi.newsnevents.R.id.nav_host_fragment;
import static com.vaagdevi.newsnevents.R.id.nav_view;
import static com.vaagdevi.newsnevents.R.id.photoIV;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "Dashboard" ;
    private FirebaseAuth firebaseAuth;
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String currentId;


    TextView dashboardname;
    TextView dashboardemail;
    ImageView dashboardphoto;

    private long backPressedTime;
    private Toast backToast;


    private AppBarConfiguration mAppBarConfiguration;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        firebaseAuth = FirebaseAuth.getInstance();
        currentId = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("News n Events Users").child(currentId);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.faqs);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent telegramintent = new Intent(Intent.ACTION_VIEW);
                telegramintent.setData(Uri.parse("https://t.me/joinchat/LS4qKRRpAosY0QwEPTuLQg"));
                startActivity(telegramintent);

            }
        });

        DrawerLayout drawer = findViewById(drawer_layout);
        NavigationView navigationView = findViewById(nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(nav_home).setDrawerLayout(drawer).build();

        NavController navController = Navigation.findNavController(this, nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        updateGoogleNavheader();

        updateLoginNavHeader();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);


        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == action_settings) {
            startActivity(new Intent(Dashboard.this, Settings.class));
            return true;

        } else if (id == action_logout) {

            signOut();
            finish();
            return false;

        }

        return super.onOptionsItemSelected(item);
    }



    private void signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(Dashboard.this, "Successfully signed out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Dashboard.this, MainActivity.class));
                finish();
            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return false;
    }

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void updateGoogleNavheader() {

        NavigationView navigationView = (NavigationView) findViewById(nav_view);
        View headerView = navigationView.getHeaderView(0);

        dashboardname = (TextView) headerView.findViewById(nameTV);
        dashboardemail = (TextView) headerView.findViewById(emailTV);
        dashboardphoto = (ImageView) headerView.findViewById(photoIV);


        //Using Google SignIn

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(Dashboard.this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();

            Uri personPhoto = acct.getPhotoUrl();

            dashboardname.setText(personName);
            dashboardemail.setText(personEmail);

            if (personPhoto != null) {

                Glide.with(this).load(personPhoto).into(dashboardphoto);

            } else {
                dashboardphoto = null;
            }
        }
    }


    public void updateLoginNavHeader(){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String ProfileImage = dataSnapshot.child("profileimage").getValue().toString();
                String Email = dataSnapshot.child("email").getValue().toString();
                String Username = dataSnapshot.child("username").getValue().toString();

                Glide.with(Dashboard.this).load(ProfileImage).placeholder(R.drawable.profile_image3).into(dashboardphoto);
                dashboardemail.setText(Email);
                dashboardname.setText(Username);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }


    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
        finish();

    }


    public void onMessageReceived(RemoteMessage remoteMessage) {
        showNotification(remoteMessage.getNotification().getBody());
    }

    public void showNotification(String message) {
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, Notification.class), 0);
        android.app.Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_image)
                .setContentTitle("News n Events")
                .setContentText(message)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }



}
