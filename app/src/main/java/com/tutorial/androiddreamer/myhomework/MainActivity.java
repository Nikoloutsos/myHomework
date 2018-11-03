package com.tutorial.androiddreamer.myhomework;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    public static final String EXTRA_MODE = "com.tutorial.androiddreamer.myhomework.EXTRA_MODE";
    public static final String EXTRA_SUBJECT = "com.tutorial.androiddreamer.myhomework.EXTRA_SUBJECT ";
    public static final String EXTRA_NOTE = "com.tutorial.androiddreamer.myhomework.EXTRA_NOTE";
    public static final String EXTRA_IMPORTANCE = "com.tutorial.androiddreamer.myhomework.EXTRA_IMPORTANCE";
    public static final String EXTRA_ID = "com.tutorial.androiddreamer.myhomework.EXTRA_ID";

    public static final int ADD_NOTE_REQUEST_CODE = 1;
    public static final int EDIT_NOTE_REQUEST_CODE = 2;
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
                startActivityForResult(new Intent(MainActivity.this, AddNoteActivity.class)
                                .putExtra(EXTRA_MODE, AddNoteActivity.MODE_ADD_NOTE),
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
                noteAdapter.submitList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Note noteToDelete = noteViewModel.getAllNotes().getValue().get(viewHolder.getAdapterPosition());
                noteViewModel.setLastNote(noteToDelete);
                noteViewModel.getNoteRepository().deleteNote(noteToDelete);
                Snackbar.make(findViewById(R.id.cl_activity_main), "note deleted" ,Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                noteViewModel.getNoteRepository().insertNote(noteViewModel.getLastNote());
                            }
                        }).setActionTextColor(getResources().getColor(R.color.colorAccent)).show();

            }
        }).attachToRecyclerView(recyclerView);

        noteAdapter.setListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                intent.putExtra(EXTRA_MODE, AddNoteActivity.MODE_EDIT_NOTE)
                        .putExtra(EXTRA_SUBJECT, note.getSubject())
                        .putExtra(EXTRA_NOTE, note.getDescription())
                        .putExtra(EXTRA_ID, note.getId())
                        .putExtra(EXTRA_IMPORTANCE, note.getPriority());
                startActivityForResult(intent, EDIT_NOTE_REQUEST_CODE);
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

        if (requestCode == EDIT_NOTE_REQUEST_CODE && resultCode == RESULT_OK){
            String subject = data.getStringExtra(AddNoteActivity.EXTRA_SUBJECT);
            String note = data.getStringExtra(AddNoteActivity.EXTRA_NOTE);
            int importance = data.getIntExtra(AddNoteActivity.EXTRA_IMPORTANCE,4);
            int id = data.getIntExtra(AddNoteActivity.EXTRA_ID,-1);

            Note noteToBeUpdate = new Note(System.currentTimeMillis(), note, subject, importance);
            noteToBeUpdate.setId(id);
            noteViewModel.getNoteRepository().updateNote(noteToBeUpdate);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main_activity_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_menu_main_activity_settings:
                //TODO save data and send it back to mainactivity to create the note and update the db.
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }
    }
}
