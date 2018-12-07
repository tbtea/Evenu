package com.example.tonytea.evenu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class EventDisplayActivity extends AppCompatActivity {

    Event this_event;
    private String mDisplayName;
    private EditText mInputText;
    private ImageButton mSendButton;
    private TextView date, time, title, description, location;
    private ListView mChatListView;
    private DatabaseReference mDatabaseReference;
    private ChatListAdapter mChatListAdapter;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_display_activity);
        this_event = (Event) getIntent().getSerializableExtra("event");
        date = findViewById(R.id.display_date);
        date.setText(this_event.getEventDate());
        time = findViewById(R.id.display_time);
        time.setText(this_event.getEventTime());
        title = findViewById(R.id.title);
        title.setText(this_event.getEventTitle());
        description = findViewById(R.id.display_description);
        description.setText(this_event.getEventDescription());
        location = findViewById(R.id.display_location);
        location.setText(this_event.getEventLocation());

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mInputText = (EditText) findViewById(R.id.messageInput);
        mSendButton = (ImageButton) findViewById(R.id.sendButton);
        mChatListView = (ListView) findViewById(R.id.chat_list_view);

        mInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return true;
            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

    }

    private void setupDisplayName(){

        SharedPreferences prefs = getSharedPreferences(RegisterActivity.CHAT_PREFS, MODE_PRIVATE);

        mDisplayName = prefs.getString(RegisterActivity.DISPLAY_NAME_KEY, null);

        if (mDisplayName == null) mDisplayName = "Anonymous";
    }

    private void sendMessage(){
        String input = mInputText.getText().toString();
        if(!input.equals("")){

            String id = mDatabaseReference.child("comments").push().getKey();
            Comment chat = new Comment(input, mDisplayName, id);
            mDatabaseReference.child("comments").child(id).setValue(chat);
            mDatabaseReference.child("commentsections").child(this_event.getEventID()).child(id).setValue(true);
            mInputText.setText("");
        }

    }

    @Override
    public void onStart(){
        super.onStart();
        mChatListAdapter = new ChatListAdapter(this, mDatabaseReference, mDisplayName, this_event.getEventID());
        mChatListView.setAdapter(mChatListAdapter);
    }

    @Override
    public void onStop(){
        super.onStop();
        mChatListAdapter.cleanup();
    }


}
