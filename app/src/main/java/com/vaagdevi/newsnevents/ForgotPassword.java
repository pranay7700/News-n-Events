package com.vaagdevi.newsnevents;

import android.content.Intent;
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

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgotemail=findViewById(R.id.etforgotemail);
        forgotresetpassword=findViewById(R.id.btnresetpassword);

        firebaseAuth = FirebaseAuth.getInstance();

        forgotresetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String useremail = forgotemail.getText().toString().trim();

                if(useremail.equals("")){
                    Toast.makeText(ForgotPassword.this, "Please enter your registered email ID", Toast.LENGTH_SHORT).show();
                }
                else {

                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(ForgotPassword.this, "Password reset email sent!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ForgotPassword.this, MainActivity.class));
                            }else{
                                Toast.makeText(ForgotPassword.this, "Error in sending password reset email", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }

            }
        });


    }
}
