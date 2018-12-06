package com.example.tonytea.evenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Text;


public class EventDisplayActivity extends AppCompatActivity {

    Event this_event;
    private TextView date, time, title, description, location;

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


    }


    //TODO display the Event's title, description, date, time ..etc, for the specific event_list that the user clicked


}
