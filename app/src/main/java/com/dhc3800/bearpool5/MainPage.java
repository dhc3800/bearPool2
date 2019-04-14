package com.dhc3800.bearpool5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class MainPage extends AppCompatActivity implements View.OnClickListener{

    RecyclerView rv;
    FlightsAdapter adapter;
    private DatabaseReference databaseRef;
    private ArrayList<Flight> flights;
    private ArrayList<String> flightIDs;
    FloatingActionButton fabAdd;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        flights = new ArrayList<>();
        flightIDs = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();


        rv = findViewById(R.id.rv);
        adapter = new FlightsAdapter(this, flightIDs);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        databaseRef = FirebaseDatabase.getInstance().getReference().child("users").child(mCurrentUser.getUid()).child("userFlights");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String flightID = ds.getValue(String.class);
                    flightIDs.add(flightID);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });



        //databaseRef.orderByValue()

        // handles adding a new flight
        fabAdd = findViewById(R.id.fab);
        fabAdd.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.fab):
                Intent i = new Intent(this, NewFlightActivity.class);
                startActivity(i);
        }
    }
}