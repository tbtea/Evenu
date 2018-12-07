package com.example.tonytea.evenu;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JalalAdapter extends RecyclerView.Adapter<JalalAdapter.HostedEventViewHolder> {


    private Context context;
    private DatabaseReference base_database_reference = FirebaseDatabase.getInstance().getReference();
    private ArrayList<Event> event_list = new ArrayList<>();
    private String this_user_id;
    private ChildEventListener event_listener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            base_database_reference.child("events").child(dataSnapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        event_list.add(dataSnapshot.getValue(Event.class));
                        notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    public JalalAdapter(Context c, String id)
    {
        context = c;
        this_user_id = id;
        base_database_reference.child("userhostedevents").child(this_user_id).addChildEventListener(event_listener);
    }

    @NonNull
    @Override
    public JalalAdapter.HostedEventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new JalalAdapter.HostedEventViewHolder(LayoutInflater.from(context).inflate(R.layout.event_list_item, viewGroup, false ));
    }

    @Override
    public void onBindViewHolder(@NonNull JalalAdapter.HostedEventViewHolder myViewHolder, final int pos) {
        myViewHolder.title.setText(event_list.get(pos).getEventTitle());

        myViewHolder.carrier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayEvent(event_list.get(pos));
            }
        });

        // to get images do this
        // Picasso.get().load(events.get(position).getProfilePic()).into(holder.profilePic)

    }

    public void displayEvent(Event e){
        Intent intent = new Intent(context, EventDisplayActivity.class);
        intent.putExtra("event", e);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return event_list.size();
    }

    class HostedEventViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        LinearLayout carrier;


        public HostedEventViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            carrier = itemView.findViewById(R.id.carrier);
        }
    }

    public void cleanup(){
        base_database_reference.child("userhostedevents").child(this_user_id).removeEventListener(event_listener);
    }
}
