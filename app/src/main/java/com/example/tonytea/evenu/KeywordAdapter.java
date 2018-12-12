package com.example.tonytea.evenu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class KeywordAdapter extends RecyclerView.Adapter<KeywordAdapter.KeywordHolder>{

    private String this_user_id;
    private Context context;
    private DatabaseReference base_database_reference = FirebaseDatabase.getInstance().getReference();
    private ArrayList<String> keyword_list = new ArrayList<String>();
    private ChildEventListener keyword_listener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            if(dataSnapshot.exists()) {
                keyword_list.add(dataSnapshot.getKey());
                notifyDataSetChanged();
            }
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

    public KeywordAdapter(Context con, String id){
        this.this_user_id = id;
        this.context = con;
        base_database_reference.child("userkeywords").child(id).addChildEventListener(keyword_listener);
    }

    class KeywordHolder extends RecyclerView.ViewHolder{
        private TextView text;
        private ImageButton delete_button;

        public KeywordHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.profile_keyword_item_text);
            delete_button = itemView.findViewById(R.id.keyword_delete_button);
        }
    }
    @NonNull
    @Override
    public KeywordHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new KeywordHolder(LayoutInflater.from(context).inflate(R.layout.profile_keyword_item, viewGroup, false ));
    }

    @Override
    public void onBindViewHolder(@NonNull final KeywordHolder keywordHolder, final int i) {
        keywordHolder.text.setText(keyword_list.get(i));

        keywordHolder.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base_database_reference.child("userkeywords").child(this_user_id).child(keyword_list.get(i)).removeValue();
                keyword_list.remove(i);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return keyword_list.size();
    }

    public void cleanup(){
        base_database_reference.child("userkeywords").child(this_user_id).removeEventListener(keyword_listener);
    }
}
