package com.tutorial.androiddreamer.myhomework;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import model.Note;
import model.NoteAdapter;
import model.NoteViewModel;

public class MainActivity extends AppCompatActivity {
    private NoteViewModel noteViewModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Note> dummydata = new ArrayList<>();
        dummydata.add(new Note(System.currentTimeMillis(), "description", "english", 1));
        dummydata.add(new Note(System.currentTimeMillis(), "description", "english", 1));
        recyclerView = findViewById(R.id.rv_activity_main);
        final NoteAdapter noteAdapter = new NoteAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(noteAdapter);


        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                //TODO fill the code when List<Note> data changes.


            }
        });

        noteAdapter.setNotes(dummydata);



    }
}
