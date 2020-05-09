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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }
}

