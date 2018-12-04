package com.example.tonytea.evenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class CreateEventActivity extends AppCompatActivity {

    private EditText mEventTitleView;
    private EditText mEventLocation;
    private EditText mDate;
    private EditText mTime;
    private EditText mDescription;
    private EditText mKeywords;

    public DatabaseReference databaseReference;

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);

        mEventTitleView = (EditText) findViewById(R.id.event_title);
        mEventLocation = (EditText) findViewById(R.id.event_location);
        mDate = (EditText) findViewById(R.id.event_date);
        mTime = (EditText) findViewById(R.id.event_time);
        mDescription = (EditText) findViewById(R.id.event_description);
        mKeywords = (EditText) findViewById(R.id.event_keywords);

        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    // view points to the button
    public void handleSavedData(View view){

        String eventTitle = mEventTitleView.getText().toString();
        String eventLocation = mEventLocation.getText().toString();
        String eventDate = mDate.getText().toString();
        String eventTime = mTime.getText().toString();
        String eventDescription = mDescription.getText().toString();
        String eventKeywords = mKeywords.getText().toString();



        // Will randomly generate a unique key for each event
        String id = databaseReference.child("events").push().getKey();


        Event newEvent = new Event(eventTitle, eventLocation, eventDate,
                                   eventTime, eventDescription, eventKeywords, id);


        databaseReference.child("events").child(id).setValue(newEvent);

    }

}
