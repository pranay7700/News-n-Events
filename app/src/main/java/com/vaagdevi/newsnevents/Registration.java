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
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

    private DatabaseReference databaseref;

    EditText Email;
    EditText Username;
    EditText Mobilenumber;
    EditText Password;
    Button signup;
    TextView alreadyregistered;
    private ProgressDialog progressDialog;

    String Emailpattern = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    String Passwordvalidate = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Email = (EditText) findViewById(R.id.etemail);
        Username = (EditText) findViewById(R.id.etusername);
        Mobilenumber = (EditText) findViewById(R.id.etmobilenumber);
        Password = (EditText) findViewById(R.id.etpassword);

        signup = (Button) findViewById(R.id.btnsignup);

        alreadyregistered = (TextView) findViewById(R.id.tvalreadyregistered);

        final FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(Registration.this);
        databaseref = FirebaseDatabase.getInstance().getReference("News n Events");


        @SuppressLint("WrongViewCast") final AppCompatCheckBox checkBox = (AppCompatCheckBox) findViewById(R.id.show_hide_password);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    // show password
                    Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // hide password
                    Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = Email.getText().toString();
                final String username = Username.getText().toString();
                final String mobilenumber = Mobilenumber.getText().toString();
                final String password = Password.getText().toString();
                final String rollno = "";
                final String branch = "";
                final String college = "";
                final String address = "";
                final String profileimage = "";


                progressDialog.setTitle("Signing Up");
                progressDialog.setMessage("Please wait...");
                checkConnection();


                if (email.isEmpty() && username.isEmpty() && mobilenumber.isEmpty() && password.isEmpty()) {
                    Toast.makeText(Registration.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    Email.setError("Email is Required");
                    Email.requestFocus();
                } else if (username.isEmpty()) {
                    Username.setError("Username is Required");
                    Username.requestFocus();
                } else if (mobilenumber.isEmpty()) {
                    Mobilenumber.setError("Mobile Number is Required");
                    Mobilenumber.requestFocus();
                } else if (password.isEmpty()) {
                    Password.setError("Password is Required");
                    Password.requestFocus();
                } else if (!(email.isEmpty() && username.isEmpty() && mobilenumber.isEmpty() && password.isEmpty())) {
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if ((task.isSuccessful())) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Regdatabase regdatabase = new Regdatabase( email, username, mobilenumber, password, rollno, branch, college, address, profileimage);

                                        FirebaseDatabase.getInstance().getReference(databaseref.getKey()).child(mAuth.getCurrentUser().getUid())
                                                .setValue(regdatabase).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                progressDialog.dismiss();
                                                startActivity(new Intent(Registration.this, MainActivity.class));
                                                Toast.makeText(Registration.this, "Signed Up Successfully!!!", Toast.LENGTH_SHORT).show();
                                                finish();


                                            }
                                        });


                                    } else {
                                        // If sign in fails, display a message to the user.
                                        progressDialog.dismiss();
                                        Toast.makeText(Registration.this, "Email Already Exists", Toast.LENGTH_SHORT).show();

                                    }


                                }
                            });


                } else {
                    Toast.makeText(Registration.this, "Sign Up Error", Toast.LENGTH_LONG).show();
                }

            }
        });

        alreadyregistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Registration.this, MainActivity.class));

            }
        });

    }

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    private boolean validateEmail() {
        String emailInput = Email.getText().toString().trim();

        if (emailInput.isEmpty()) {
            Email.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            Email.setError("Please enter a valid email address");
            return false;
        } else {
            Email.setError(null);
            return true;
        }
    }


    private boolean validateUsername() {
        String usernameInput = Username.getText().toString().trim();

        if (usernameInput.isEmpty()) {
            Username.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 15) {
            Username.setError("Username too long");
            return false;
        } else {
            Username.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = Password.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            Password.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            Password.setError("Password too weak");
            return false;
        } else {
            Password.setError(null);
            return true;
        }
    }

    public void confirmInput(View v) {
        if (!validateEmail() | !validateUsername() | !validatePassword()) {
            return;
        }

        String input = "Email: " + Email.getText().toString();
        input += "\n";
        input += "Username: " + Username.getText().toString();
        input += "\n";
        input += "Password: " + Password.getText().toString();

        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
    }

    public void checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (null == activeNetwork) {

            progressDialog.dismiss();
            Toast.makeText(Registration.this, "Check Your Internet Connection!",
                    Toast.LENGTH_LONG).show();
        }
    }

}
