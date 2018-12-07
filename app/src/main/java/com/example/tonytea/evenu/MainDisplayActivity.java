package com.example.tonytea.evenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainDisplayActivity extends AppCompatActivity {

    private RecyclerView recycler_view;
    private ArrayList<Event> event_list_received = new ArrayList<>();
    private ArrayList<Event> event_list_sorted = new ArrayList<>();
    private ArrayList<String> user_keywords = new ArrayList<>();
    private EventListAdapter event_list_adapter;
    private ImageButton profile_button;
    private AutoCompleteTextView search_text;
    private String this_user_id = FirebaseAuth.getInstance().getUid();
    private DatabaseReference base_database_reference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_activity);
        declareHandles();
        getUserKeywords();
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

    private void getUserKeywords(){
        base_database_reference.child("userkeywords").child(this_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    if(dataSnapshot1.exists()){
                        user_keywords.add(dataSnapshot1.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setUpEventAdapter(){
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        base_database_reference.child("events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                event_list_received = new ArrayList<Event>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    Event e = dataSnapshot1.getValue(Event.class);
                    event_list_received.add(e);
                }
                sortBasedOnKeywords();
                event_list_adapter = new EventListAdapter(MainDisplayActivity.this, event_list_sorted);
                //event_list_adapter = new EventListAdapter(MainDisplayActivity.this, event_list_received);
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
        final PeterHoyerAdapter adapter = new PeterHoyerAdapter(getApplicationContext(), event_list_received);
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

    private void sortBasedOnKeywords(){
        ArrayList<Integer> matches = new ArrayList<Integer>();

        for(int a = 0; a<event_list_received.size(); a++){
            matches.add(a, 0);
        }

        //get the number of matches for corresponding event, Eg. matches[1] = 2 means that events_received[1] shares two keywords with the user
        for(int i = 0; i< event_list_received.size(); i++){
            for(int j = 0; j<user_keywords.size(); j++){
                if(event_list_received.get(i).getKeywords()!=null&&event_list_received.get(i).getKeywords().contains(user_keywords.get(j))){
                    matches.set(i, matches.get(i)+1);
                }
            }
        }


        for(int k = 0; k<matches.size(); k++) {
            Integer max = 0;
            Integer pos = 0;
            //go through the ints once and find the maximum
            for(int l = 0; l<matches.size(); l++){
                if(matches.get(l) >= max){
                    max = matches.get(l);
                    pos = l;
                }
            }
            //use the position of the maximum to add the event at that position to the sorted list
            event_list_sorted.add(event_list_received.get(pos));
            matches.set(pos, -1); //void the current maximum
            //Log.d("numbers", "pos: "+pos);
        }

        for(int b = 0; b<matches.size(); b++){
            Log.d("limitation", Integer.toString(b)+": "+matches.get(b).toString());
        }
    }

    private void leaveForProfile(){
        Intent intent = new Intent(this, ProfileActivity.class);
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
