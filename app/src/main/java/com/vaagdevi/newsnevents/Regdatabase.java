package com.vaagdevi.newsnevents;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Regdatabase {

    private FirebaseAuth firebaseauth;
    private FirebaseUser firebaseuser;

    public String email, username, mobilenumber, password, rollno, branch, college, address, profileimage;


    public Regdatabase(String email, String username, String mobilenumber, String password, String rollno, String branch, String college, String address, String profileimage) {

        this.email = email;
        this.username = username;
        this.mobilenumber = mobilenumber;
        this.password = password;
        this.rollno = rollno;
        this.branch = branch;
        this.college = college;
        this.address = address;
        this.profileimage = profileimage;


    }

    public Regdatabase() {

    }


}

