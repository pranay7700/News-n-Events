package com.vaagdevi.newsnevents;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    EditText forgotemail;
    Button forgotresetpassword;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgotemail=findViewById(R.id.etforgotemail);
        forgotresetpassword=findViewById(R.id.btnresetpassword);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(ForgotPassword.this);

        forgotresetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String useremail = forgotemail.getText().toString().trim();

                if(useremail.equals("")){
                    Toast.makeText(ForgotPassword.this, "Please enter your registered email ID", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressDialog.setTitle("Recovering");
                    progressDialog.setMessage("Please wait...");
                    checkConnection();
                    progressDialog.show();

                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                startActivity(new Intent(ForgotPassword.this, MainActivity.class));
                                Toast.makeText(ForgotPassword.this, "Recover link is sent to your registered email, Please check!", Toast.LENGTH_SHORT).show();
                                finish();

                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(ForgotPassword.this, "Failed...! try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    public void checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (null == activeNetwork) {

            progressDialog.dismiss();
            Toast.makeText(ForgotPassword.this, "No Internet Connection!",
                    Toast.LENGTH_LONG).show();
        }
    }
}
