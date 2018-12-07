package com.example.tonytea.evenu;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String this_user_id = auth.getUid();
    private User this_user_profile;
    private DatabaseReference base_database_reference = FirebaseDatabase.getInstance().getReference();
    private TextView user_name;
    private TextView email;
    private ImageButton add_keyword_button;
    private RecyclerView keyword_recycler;
    private KeywordAdapter keyword_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        declareHandles();
        getThisUserData();
        setUpButtonListener();
    }

    private void declareHandles(){
        user_name = findViewById(R.id.profile_user_name);
        email = findViewById(R.id.profile_email);
        add_keyword_button = findViewById(R.id.profile_add_button);
        keyword_recycler = findViewById(R.id.profile_recycler_view);
    }

    private void getThisUserData(){
        base_database_reference.child("users").child(this_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    this_user_profile = dataSnapshot.getValue(User.class);
                    createKeywordAdapter();
                    updateUi();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setUpButtonListener(){
        add_keyword_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildAddKeywordDialog();
            }
        });
    }

    private void buildAddKeywordDialog(){
        final Dialog keywordDialog = new Dialog(this);
        keywordDialog.setContentView(R.layout.add_keyword_dialog);
        Button cancelButton = keywordDialog.findViewById(R.id.dialog_cancel_button);
        Button saveButton = keywordDialog.findViewById(R.id.dialog_save_button);
        final EditText editText = keywordDialog.findViewById(R.id.dialog_edit_text);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keywordDialog.cancel();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                if(text.length()>2){
                    base_database_reference.child("userkeywords").child(this_user_id).child(text).setValue(true);
                    keywordDialog.cancel();
                }else{
                    Toast.makeText(getApplicationContext(), "The keyword needs to be at least 3 characters long", Toast.LENGTH_LONG).show();
                }

            }
        });
        keywordDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        keywordDialog.setCancelable(true);
        keywordDialog.show();
    }

    private void updateUi(){
        user_name.setText(this_user_profile.getUser_name());
        email.setText(this_user_profile.getEmail());
    }

    private void createKeywordAdapter(){
        keyword_adapter = new KeywordAdapter(getApplicationContext(), this_user_id);
        keyword_recycler.setLayoutManager(new LinearLayoutManager(this));
        keyword_recycler.setAdapter(keyword_adapter);
    }

    @Override
    protected void onStop(){
        super.onStop();
        keyword_adapter.cleanup();
    }
}
