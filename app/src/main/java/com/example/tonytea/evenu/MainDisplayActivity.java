package com.example.tonytea.evenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainDisplayActivity extends AppCompatActivity {

    private RecyclerView recycler_view;
    private ArrayList<Event> all_events_list;
    private EventListAdapter event_list_adapter;
    private ImageButton profile_button;
    private AutoCompleteTextView search_text;
    private DatabaseReference base_database_reference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_activity);
        declareHandles();
        setUpEventAdapter();
        setUpProfileListener();
    }

    private void setUpProfileListener(){
        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaveForProfile();
            }
        });
    }

    private void setUpEventAdapter(){
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        base_database_reference.child("events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                all_events_list = new ArrayList<Event>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Event e = dataSnapshot1.getValue(Event.class);
                    all_events_list.add(e);
                }
                event_list_adapter = new EventListAdapter(MainDisplayActivity.this, all_events_list);
                recycler_view.setAdapter(event_list_adapter);
                setUpSearchSuggestions();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainDisplayActivity.this, "oops", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpSearchSuggestions() {
        final PeterHoyerAdapter adapter = new PeterHoyerAdapter(getApplicationContext(), all_events_list);
        search_text.setAdapter(adapter);
        search_text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayEvent(adapter.getSuggestions().get(position));
            }
        });
    }

    private void declareHandles(){
        profile_button = findViewById(R.id.profile_button);
        recycler_view = findViewById(R.id.my_recycler_view);
        search_text = findViewById(R.id.search_text);
    }

    private void leaveForProfile(){
        Intent intent = new Intent(this, ProfileActivity.class);
        finish();
        startActivity(intent);
    }

    public void displayEvent(Event e){
        Intent intent = new Intent(this, EventDisplayActivity.class);
        intent.putExtra("event", e);
        startActivity(intent);
    }

    // executes when create Event button is clicked
    public void createEvent(View v){
        Intent intent = new Intent(this, CreateEventActivity.class);
        startActivity(intent);
    }


}
