package com.tutorial.androiddreamer.myhomework;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import model.Note;
import model.NoteAdapter;
import model.NoteViewModel;

import static com.tutorial.androiddreamer.myhomework.AddNoteActivity.EXTRA_NOTE;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = "MainActivity";
    public static final int ADD_NOTE_REQUEST_CODE = 1;
    private NoteViewModel noteViewModel;
    private RecyclerView recyclerView;
    private FloatingActionButton fabAddNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fabAddNote = findViewById(R.id.fab_activity_main_add_note);
        fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, AddNoteActivity.class),
                        ADD_NOTE_REQUEST_CODE);
            }
        });
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
                Log.d(LOG_TAG, "observed a change");
                noteAdapter.setNotes(notes);
            }
        });





    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_NOTE_REQUEST_CODE && resultCode == RESULT_OK){

            String subject = data.getStringExtra(AddNoteActivity.EXTRA_SUBJECT);
            String note = data.getStringExtra(AddNoteActivity.EXTRA_NOTE);
            int importance = data.getIntExtra(AddNoteActivity.EXTRA_IMPORTANCE,1);

            Note newNote = new Note(System.currentTimeMillis(), note, subject, importance);
            noteViewModel.getNoteRepository().insertNote(newNote);
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();

        }
    }
}
