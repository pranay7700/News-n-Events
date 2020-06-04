package com.vaagdevi.newsnevents;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity<gso, mGoogleSignInClient> extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;

    int RC_SIGN_IN = 0;
    GoogleSignInClient mGoogleSignInClient;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseref;



    EditText Emailid;
    EditText Passid;
    Button login;
    Button google;
    Button facebook;
    ImageButton register;
    TextView forgotpassid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Emailid = (EditText) findViewById(R.id.ETemailId);
        Passid = (EditText) findViewById(R.id.ETpassId);
        login = (Button) findViewById(R.id.BTNlogin);
        register = (ImageButton) findViewById(R.id.IMGBTNregister);
        google = (Button) findViewById(R.id.BTNgoogle);
        forgotpassid = (TextView) findViewById(R.id.TVForgot);

        mAuth = FirebaseAuth.getInstance();
        databaseref = FirebaseDatabase.getInstance().getReference("Google Users");
        progressDialog = new ProgressDialog(MainActivity.this);


        @SuppressLint("WrongViewCast") final AppCompatCheckBox checkBox = (AppCompatCheckBox) findViewById(R.id.show_hide_password);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    // show password
                    Passid.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // hide password
                    Passid.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setTitle("Logging In");
                progressDialog.setMessage("Please wait...");
                checkConnection();


                String emailid = Emailid.getText().toString();
                final String passid = Passid.getText().toString();


                if (emailid.isEmpty() && passid.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                } else if (emailid.isEmpty()) {
                    Emailid.setError("Provide Your Email");
                    Emailid.requestFocus();
                } else if (passid.isEmpty()) {
                    Passid.setError("Enter Your Password");
                    Passid.requestFocus();
                } else if (!(emailid.isEmpty() && passid.isEmpty())) {

                    progressDialog.show();

                    mAuth.signInWithEmailAndPassword(emailid, passid)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if ((task.isSuccessful())) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(MainActivity.this, "Logined Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(MainActivity.this, Dashboard.class));
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        progressDialog.dismiss();
                                        Toast.makeText(MainActivity.this, "Unable to Login", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }


        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, Registration.class));

            }
        });

        forgotpassid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, ForgotPassword.class));

            }
        });

    }


    private void signIn() {
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                progressDialog.dismiss();
                startActivity(new Intent(MainActivity.this, Dashboard.class));
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_LONG).show();

                // ...
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this,"you are not able to log in to google",Toast.LENGTH_LONG).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }


    public void checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (null == activeNetwork) {

            progressDialog.dismiss();
            Toast.makeText(MainActivity.this, "No Internet Connection!", Toast.LENGTH_LONG).show();
        }
    }


}