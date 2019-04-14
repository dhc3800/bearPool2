package com.dhc3800.bearpool5;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FlightsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<String> flights;
    Context context;

    public FlightsAdapter(Context context, ArrayList<String> flights) {
        this.flights = flights;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        // our custom cell layout (card.xml)
        View row = inflater.inflate(R.layout.card, viewGroup, false);
        Item item = new Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        String s = flights.get(i);
        DatabaseReference flightsDatabaseRef = FirebaseDatabase.getInstance().getReference().child("flights");
        flightsDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                long datetime = dataSnapshot.child("dateTime").getValue(Long.class);
//                String flightNumber = dataSnapshot.child("flightNumber").getValue(String.class);
//                String leavingSpot = dataSnapshot.child("leavingSpot").getValue(String.class);
//                String to = dataSnapshot.child("to").getValue(String.class);
//                String from = dataSnapshot.child("from").getValue(String.class);
//
//                String tempSentence = to + " to " + from;
//                ((Item) viewHolder).toFrom.setText(tempSentence);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("ERROR", databaseError.getMessage());
            }
        });


    }

    @Override
    public int getItemCount() {
        return flights.size();
    }


    // class that holds all cell values in recycler view
    public class Item extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView toFrom;


        public Item(View itemView) {
            super(itemView);
            toFrom = itemView.findViewById(R.id.tofrom);

            // this allows the cell to be clicked
            itemView.setOnClickListener(this);
        }

        // handles when cell is clicked on
        @Override
        public void onClick(View v) {
            //Flight flight = flights.get(getAdapterPosition());
            Intent intent = new Intent(context, FlightDetail.class);
            context.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

}