package com.vaagdevi.newsnevents;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Regdatabase {

    private FirebaseAuth firebaseauth;
    private FirebaseUser firebaseuser;

    public String email,username,password,confirmpassword,mobilenumber;


    public Regdatabase(String email,String username,String password,String confirmpassword,String mobilenumber)
    {

        this.email=email;
        this.username=username;
        this.password=password;
        this.confirmpassword=confirmpassword;
        this.mobilenumber=mobilenumber;

    }

}
