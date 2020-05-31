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

public class Workshops extends AppCompatActivity {

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<WorkshopsRegdatabase> list;
    WorkshopsMyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshops);

        recyclerView = (RecyclerView) findViewById(R.id.workshopsRV);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        reference = FirebaseDatabase.getInstance().getReference().child("Workshops");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<WorkshopsRegdatabase>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    WorkshopsRegdatabase p = dataSnapshot1.getValue(WorkshopsRegdatabase.class);
                    list.add(p);
                }
                adapter = new WorkshopsMyAdapter(Workshops.this,list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Workshops.this, "Opsss!!!.... Something went wrong", Toast.LENGTH_SHORT).show();

            }


        });

    }
}
