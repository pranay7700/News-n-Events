package com.vaagdevi.newsnevents;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    private static final int PIC_CROP = 0;
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference, databaseref;

    private ProgressDialog progressDialog;
    private StorageReference storageReference;
    private ArrayAdapter<CharSequence> arrayAdapter;
    final static int GalleryPick = 1;

    CircleImageView profilephoto;
    EditText profileusername, profileemail, profilepassword, profilemobilenumber, profilerollno, profilebranch, profilecollege, profileaddress;
    String UsernameStr, EmailStr, PasswordStr, MobileNumberStr, RollNoStr, YearStr, BranchStr, CollegeStr, AddressStr;
    Spinner profileyear;
    String currentId;
    FloatingActionButton profileeditinfo, profileeditimage;
    String downloadUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        firebaseAuth = FirebaseAuth.getInstance();
        currentId = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Login Users").child(currentId);
        databaseref = FirebaseDatabase.getInstance().getReference().child("Google Users").child(currentId);
        progressDialog = new ProgressDialog(this);
        storageReference = FirebaseStorage.getInstance().getReference("Profile Images").child(currentId + ".jpg");

        profilephoto = (CircleImageView) findViewById(R.id.profile_photo);
        profileeditinfo = (FloatingActionButton) findViewById(R.id.profile_editinfo);
        profileeditimage = (FloatingActionButton) findViewById(R.id.profile_editimage);
        profileusername = (EditText) findViewById(R.id.profile_usernameET);
        profileemail = (EditText) findViewById(R.id.profile_emailET);
        profilepassword = (EditText) findViewById(R.id.profile_passwordET);
        profilemobilenumber = (EditText) findViewById(R.id.profile_mobilenumberET);
        profilerollno = (EditText) findViewById(R.id.profile_rollnoET);
        profileyear = (Spinner) findViewById(R.id.profile_yearSPI);
        profilebranch = (EditText) findViewById(R.id.profile_branchET);
        profilecollege = (EditText) findViewById(R.id.profile_collegeET);
        profileaddress = (EditText) findViewById(R.id.profile_addressET);


        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.year, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profileyear.setAdapter(arrayAdapter);
        profileyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        profileeditinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UsernameStr = profileusername.getText().toString();
                EmailStr = profileemail.getText().toString();
                PasswordStr = profilepassword.getText().toString();
                MobileNumberStr = profilemobilenumber.getText().toString();
                RollNoStr = profilerollno.getText().toString();
                YearStr = profileyear.getSelectedItem().toString();
                BranchStr = profilebranch.getText().toString();
                CollegeStr = profilecollege.getText().toString();
                AddressStr = profileaddress.getText().toString();

                if (findViewById(R.id.BTNlogin) != null) {
                    loginupdateProfile();
                } else {
                    googleupdateProfile();
                }

                //loginupdateProfile();

                //googleupdateProfile();

            }
        });

        profileeditimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, GalleryPick);
            }
        });

        if (findViewById(R.id.BTNlogin) != null) {
            loginprofiledata();
        } else {
            googleprofiledata();
        }

        //loginprofiledata();

        //googleprofiledata();
    }

    public void loginprofiledata() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String Email = dataSnapshot.child("email").getValue().toString();
                String Username = dataSnapshot.child("username").getValue().toString();
                String MobileNumber = dataSnapshot.child("mobilenumber").getValue().toString();
                String Password = dataSnapshot.child("password").getValue().toString();
                String RollNo = dataSnapshot.child("rollno").getValue().toString();
                String Branch = dataSnapshot.child("branch").getValue().toString();
                String College = dataSnapshot.child("college").getValue().toString();
                String Address = dataSnapshot.child("address").getValue().toString();
                String ProfileImage = dataSnapshot.child("profileimage").getValue().toString();

                profileemail.setText(Email);
                profileusername.setText(Username);
                profilemobilenumber.setText(MobileNumber);
                profilepassword.setText(Password);
                profilerollno.setText(RollNo);
                profileyear.setAdapter(arrayAdapter);
                profilebranch.setText(Branch);
                profilecollege.setText(College);
                profileaddress.setText(Address);

                //Picasso.with(getApplicationContext()).load(ProfileImage).placeholder(R.drawable.profile_image3).into(profilephoto);
                Glide.with(Profile.this).load(ProfileImage).placeholder(R.drawable.profile_image3).into(profilephoto);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    private void loginupdateProfile() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    progressDialog.setTitle("Updating your profile");
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                    HashMap<String, Object> postMap = new HashMap<>();
                    postMap.put("username", UsernameStr);
                    postMap.put("email", EmailStr);
                    postMap.put("mobilenumber", MobileNumberStr);
                    postMap.put("password", PasswordStr);
                    postMap.put("rollno", RollNoStr);
                    postMap.put("year", YearStr);
                    postMap.put("branch", BranchStr);
                    postMap.put("college", CollegeStr);
                    postMap.put("address", AddressStr);

                    databaseReference.updateChildren(postMap)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        startActivity(new Intent(Profile.this, Profile.class));
                                        overridePendingTransition(0, 0);
                                        finish();
                                        Toast.makeText(Profile.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(Profile.this, "Error occurred! Try again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void googleprofiledata() {

        databaseref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();

                // Build a GoogleSignInClient with the options specified by gso.
                mGoogleSignInClient = GoogleSignIn.getClient(Profile.this, gso);

                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(Profile.this);
                if (acct != null) {
                    String personName = acct.getDisplayName();
                    String personGivenName = acct.getGivenName();
                    String personFamilyName = acct.getFamilyName();
                    String personEmail = acct.getEmail();
                    String personId = acct.getId();


                    String GoogleMobileNumber = dataSnapshot.child("mobilenumber").getValue().toString();
                    String GoogleRollNo = dataSnapshot.child("rollno").getValue().toString();
                    String GoogleBranch = dataSnapshot.child("branch").getValue().toString();
                    String GoogleCollege = dataSnapshot.child("college").getValue().toString();
                    String GoogleAddress = dataSnapshot.child("address").getValue().toString();
                    //String GoogleProfileImage = dataSnapshot.child("profileimage").getValue().toString();


                    profileemail.setText(personEmail);
                    profileusername.setText(personName);
                    profilemobilenumber.setText(GoogleMobileNumber);
                    profilerollno.setText(GoogleRollNo);
                    profileyear.setAdapter(arrayAdapter);
                    profilebranch.setText(GoogleBranch);
                    profilecollege.setText(GoogleCollege);
                    profileaddress.setText(GoogleAddress);
                    profilepassword.setVisibility(View.GONE);
                    profileeditimage.setVisibility(View.GONE);


                    final StorageReference googlefilePath = storageReference;
                    final Uri personPhoto = acct.getPhotoUrl();

                    Glide.with(Profile.this).load(personPhoto).placeholder(R.drawable.profile_image3).into(profilephoto);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }

    private void googleupdateProfile() {
        databaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    progressDialog.setTitle("Updating your profile");
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                    HashMap<String, Object> postMap = new HashMap<>();
                    postMap.put("username", UsernameStr);
                    postMap.put("email", EmailStr);
                    postMap.put("mobilenumber", MobileNumberStr);
                    postMap.put("rollno", RollNoStr);
                    postMap.put("year", YearStr);
                    postMap.put("branch", BranchStr);
                    postMap.put("college", CollegeStr);
                    postMap.put("address", AddressStr);

                    databaseref.updateChildren(postMap)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        startActivity(new Intent(Profile.this, Profile.class));
                                        overridePendingTransition(0, 0);
                                        finish();
                                        Toast.makeText(Profile.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(Profile.this, "Error occurred! Try again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                final Uri resultUri = result.getUri();

                final StorageReference filePath = storageReference;

                progressDialog.setTitle("Updating Your Profile Photo");
                progressDialog.setMessage("Please wait...");
                checkConnection();

                progressDialog.show();

                filePath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                databaseReference.child("profileimage").setValue(String.valueOf(uri))
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(Profile.this, "Profile Image Updated", Toast.LENGTH_SHORT).show();
                                                    progressDialog.dismiss();
                                                } else {
                                                    progressDialog.dismiss();
                                                    String message = task.getException().getMessage();
                                                    Toast.makeText(Profile.this, "Error Occurred !!! Try again " + message, Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        });
                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (null == activeNetwork) {
            progressDialog.dismiss();
            Toast.makeText(Profile.this, "Try again!!!", Toast.LENGTH_LONG).show();
        }
    }


}
