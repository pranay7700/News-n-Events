package com.vaagdevi.newsnevents;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Regdatabase {

    private FirebaseAuth firebaseauth;
    private FirebaseUser firebaseuser;

    public String email,username,mobilenumber,password;


    public Regdatabase(String email,String username,String mobilenumber,String password)
    {

        this.email=email;
        this.username=username;
        this.mobilenumber=mobilenumber;
        this.password=password;


    }

    public Regdatabase() {

    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

