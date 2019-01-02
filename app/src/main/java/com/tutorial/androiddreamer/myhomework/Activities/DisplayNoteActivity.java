package com.tutorial.androiddreamer.myhomework.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.tutorial.androiddreamer.myhomework.R;

public class DisplayNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_note);

        /**
         * 1) Get the intent
         * 2) update Views with the appropriate data
         */
        Intent receivedIntent =  getIntent();


    }
}
