package com.example.tonytea.evenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainDisplayActivity extends AppCompatActivity {


    DatabaseReference reference;
    RecyclerView mRecyclerView;
    ArrayList<Event> list;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_activity);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //list = new ArrayList<Event>();

        reference = FirebaseDatabase.getInstance().getReference().child("events");
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<Event>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Event e = dataSnapshot1.getValue(Event.class);
                    list.add(e);
                }

                adapter = new MyAdapter(MainDisplayActivity.this, list);
                mRecyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainDisplayActivity.this, "oops", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // executes when create Event button is clicked
   // public void createEvent(View v){
     //   Intent intent = new Intent(this, CreateEventActivity.class);
       // finish();
        //startActivity(intent);

    //}

}
