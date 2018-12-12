package com.example.tonytea.evenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class CreateEventActivity extends AppCompatActivity {

    private EditText mEventTitleView;
    private EditText mEventLocation;
    private EditText mDate;
    private EditText mTime;
    private EditText mDescription;
    private EditText mKeywords;

    private DatabaseReference databaseReference;
    private String this_user_id = FirebaseAuth.getInstance().getUid();

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_activity);

        mEventTitleView = (EditText) findViewById(R.id.event_title);
        mEventLocation = (EditText) findViewById(R.id.event_location);
        mDate = (EditText) findViewById(R.id.event_date);
        mTime = (EditText) findViewById(R.id.event_time);
        mDescription = (EditText) findViewById(R.id.event_description);
        mKeywords = (EditText) findViewById(R.id.event_keywords);
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    // view points to the button
    public void createEventAttempt (View view){

        String eventTitle = mEventTitleView.getText().toString();
        String eventLocation = mEventLocation.getText().toString();
        String eventDate = mDate.getText().toString();
        String eventTime = mTime.getText().toString();
        String eventDescription = mDescription.getText().toString();
        String eventKeywords = mKeywords.getText().toString();

        if(eventTitle.isEmpty()){
            Toast.makeText(this, "Please enter a name.", Toast.LENGTH_SHORT).show();
            return;
        }

        else if(eventLocation.isEmpty()){
            Toast.makeText(this, "Please enter a location.", Toast.LENGTH_SHORT).show();
            return;
        }

        else if(eventDate.isEmpty()){
            Toast.makeText(this, "Please enter a valid date.", Toast.LENGTH_SHORT).show();
            return;
        }

        else if(eventTime.isEmpty()){
            Toast.makeText(this, "Please enter a time.", Toast.LENGTH_SHORT).show();
            return;
        }

        else if(eventDescription.isEmpty()){
            Toast.makeText(this, "Please enter a description.", Toast.LENGTH_SHORT).show();
            return;
        }

        else if(eventKeywords.isEmpty()){
            Toast.makeText(this, "Please enter at least one keyword.", Toast.LENGTH_SHORT).show();
            return;
        }

        else{
            String id = databaseReference.child("events").push().getKey();

            Event newEvent = new Event(eventTitle, eventLocation, eventDate,
                    eventTime, eventDescription, id, createKeywords(eventKeywords), this_user_id);

            databaseReference.child("events").child(id).setValue(newEvent).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(), "Event successfully created", Toast.LENGTH_SHORT).show();
                    leave();
                }
            });

            databaseReference.child("userhostedevents").child(this_user_id).child(id).setValue(true);
        }
    }

    private void leave(){
        Intent intent = new Intent(this, MainDisplayActivity.class);
        finish();
        startActivity(intent);
    }

    private ArrayList<String> createKeywords(String original){
        String[] all = original.split(" ");
        ArrayList<String> ks = new ArrayList<String>();
        for(int i = 0; i<all.length; i++){
            ks.add(all[i]);
        }
        return ks;
    }

}
