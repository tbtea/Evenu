package com.example.tonytea.evenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;

public class MainDisplayActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_activity);

    }

    // executes when create Event button is clicked
    public void createEvent(View v){
        Intent intent = new Intent(this, CreateEventActivity.class);
        finish();
        startActivity(intent);
    }

}
