package com.vaagdevi.newsnevents;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GuestLectures extends AppCompatActivity {

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<GuestLecturesRegdatabase> list;
    GuestLecturesMyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_lectures);

        recyclerView = (RecyclerView) findViewById(R.id.guestlectureRV);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        list = new ArrayList<GuestLecturesRegdatabase>();
        reference = FirebaseDatabase.getInstance().getReference().child("Guest Lectures");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    GuestLecturesRegdatabase guestLecturesRegdatabase = dataSnapshot1.getValue(GuestLecturesRegdatabase.class);
                    list.add(guestLecturesRegdatabase);
                }
                adapter = new GuestLecturesMyAdapter(GuestLectures.this,list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(GuestLectures.this, "Opsss!!!.... Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
