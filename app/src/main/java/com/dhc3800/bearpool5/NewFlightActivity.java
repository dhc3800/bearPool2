package com.dhc3800.bearpool5;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class NewFlightActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    int year;
    int month;
    int day;
    int hour;
    int minute;
    boolean dateSelected;
    boolean timeSelected;

    Button dateButton;
    Button timeButton;
    Button submit;
    TextView dateField;
    TextView timeField;
    EditText flightNumberField;
    EditText departingAirportField;
    EditText arrivingAirportField;
    EditText spotField;

    private DatabaseReference databaseRef;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_flight);
        dateButton = findViewById(R.id.date);
        timeButton = findViewById(R.id.time);
        submit = findViewById(R.id.submit);

        // inputs
        dateField = findViewById(R.id.dateField);
        timeField = findViewById(R.id.timeField);
        flightNumberField = findViewById(R.id.flightNumberField);
        departingAirportField = findViewById(R.id.depart);
        arrivingAirportField = findViewById(R.id.arrive);
        spotField = findViewById(R.id.spot);

        databaseRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        dateButton.setOnClickListener(this);
        timeButton.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.date):
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        NewFlightActivity.this, this, year, month, day);
                dialog.show();
                break;
            case (R.id.time):
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog dialog1 = new TimePickerDialog(NewFlightActivity.this, this, hour, minute, false);
                dialog1.show();
                break;
            case (R.id.submit):

                if (!dateSelected || !timeSelected || TextUtils.isEmpty(flightNumberField.getText().toString())
                        || TextUtils.isEmpty(departingAirportField.getText().toString())
                        || TextUtils.isEmpty(arrivingAirportField.getText().toString())
                        || TextUtils.isEmpty(spotField.getText().toString())) {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    break;
                }

                // add string checks to make sure valid
                Flight f = new Flight(flightNumberField.getText().toString(),
                        departingAirportField.getText().toString(),
                        arrivingAirportField.getText().toString(),
                        spotField.getText().toString(),
                        this.day, this.month, this.year, this.hour, this.minute);

                // add to flights db
                key = databaseRef.child("flights").push().getKey();
                databaseRef.child("flights").child(key).child("flightNumber").setValue(f.getFlightNum());
                databaseRef.child("flights").child(key).child("to").setValue(f.getTo());
                databaseRef.child("flights").child(key).child("from").setValue(f.getFrom());
                databaseRef.child("flights").child(key).child("leavingSpot").setValue(f.getLeavingSpot());
                databaseRef.child("flights").child(key).child("uid").setValue(mCurrentUser.getUid());
                databaseRef.child("flights").child(key).child("grouped").setValue(false);
                long millis = Utils.parseCalendarValues(this.day, this.month, this.year, this.hour, this.minute);
                databaseRef.child("flights").child(key).child("datetime").setValue(millis);

                DatabaseReference incomingRef = databaseRef.child("airports").child(f.getTo()).child("incoming");
                incomingRef.child(key).setValue(millis);

                DatabaseReference outgoingRef = databaseRef.child("airports").child(f.getFrom()).child("outgoing");
                outgoingRef.child(key).setValue(millis);

                String uid = mCurrentUser.getUid();
                DatabaseReference usersRef = databaseRef.child("users").child(uid).child("userFlights");
                usersRef.child(usersRef.push().getKey()).setValue(key);


                // running algorithm
                updateGroups();


                startActivity(new Intent(NewFlightActivity.this, MainActivity.class));
        }
    }

    //checking if there are people to group together and grouping them
    private void updateGroups() {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month + 1;
        this.day = dayOfMonth;
        dateSelected = true;
        String sentence = this.month + "/" + this.day + "/" + this.year;
        dateField.setText(sentence);
    }

    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
        timeSelected = true;
        String sentence = this.hour + ":" + this.minute;
        timeField.setText(sentence);
    }

}
