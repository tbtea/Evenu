package com.example.tonytea.evenu;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class EventDisplayActivity extends AppCompatActivity {

    Typeface myFont;
    private Event this_event;
    private String mDisplayName;
    private EditText mInputText;
    private ImageButton mSendButton;
    private TextView date, time, title, description, location;
    private ListView mChatListView;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference base_database_reference = FirebaseDatabase.getInstance().getReference();
    private CommentAdapter mCommentAdapter;
    private String this_user_id = FirebaseAuth.getInstance().getUid();
    private Button buy_ticket_button;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_display_activity);
        declareHandles();
        setupDisplayName();

        base_database_reference.child("eventattendees").child(this_event.getEventID()).child(this_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    changeToGoing();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



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

        buy_ticket_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!buy_ticket_button.getText().toString().equals("Bought")) {
                    buyTicket();
                }
            }
        });
    }

    private void declareHandles(){
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
        buy_ticket_button = findViewById(R.id.buy_ticket_button);
    }

    private void setupDisplayName(){

        base_database_reference.child("users").child(this_user_id).child("user_name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    mDisplayName = dataSnapshot.getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        SharedPreferences prefs = getSharedPreferences(RegisterActivity.CHAT_PREFS, MODE_PRIVATE);
//        mDisplayName = prefs.getString(RegisterActivity.DISPLAY_NAME_KEY, null);
//        if (mDisplayName == null) mDisplayName = "Anonymous";
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

    private void buyTicket(){
        String ticketId = base_database_reference.child("tickets").push().getKey();
        Ticket newTicket = new Ticket(ticketId, this_user_id, this_event.getEventID());
        base_database_reference.child("tickets").child(ticketId).setValue(newTicket).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
               changeToGoing();
            }
        });
        createTransaction();
    }

    private void createTransaction(){
        String transactionId = base_database_reference.child("transactions").push().getKey();
        Transaction newTransaction = new Transaction(transactionId, this_user_id, this_event.getHost_id());
        base_database_reference.child("transactions").child(transactionId).setValue(newTransaction);
    }

    private void changeToGoing(){
        buy_ticket_button.setText("Bought");
        buy_ticket_button.setEnabled(false);
        base_database_reference.child("eventattendees").child(this_event.getEventID()).child(this_user_id).setValue(true);
    }

    @Override
    public void onStart(){
        super.onStart();
        mCommentAdapter = new CommentAdapter(this, mDatabaseReference, this_event.getEventID());
        mChatListView.setAdapter(mCommentAdapter);
    }

    @Override
    public void onStop(){
        super.onStop();
        mCommentAdapter.cleanup();
    }


}
