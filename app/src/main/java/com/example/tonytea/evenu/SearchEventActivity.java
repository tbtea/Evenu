package com.example.tonytea.evenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class SearchEventActivity extends AppCompatActivity {

    private EditText mEventTitleView;
    public DatabaseReference databaseReference;

    protected void onCreate(Bundle savedInstanceState){


        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_event);

        mEventTitleView = (EditText) findViewById(R.id.event_title);
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    // view points to the button
    public void handleSavedData(View view){
        String eventTitle = mEventTitleView.getText().toString();
        Flower newFlower = new Flower(eventTitle, "", 10);
        //this is using the flower name as the key
        databaseReference.child(newFlower.name).setValue(newFlower);

    }

}
