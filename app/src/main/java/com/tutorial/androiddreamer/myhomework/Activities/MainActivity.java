package com.tutorial.androiddreamer.myhomework.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
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
import android.widget.TextView;
import android.widget.Toast;

import com.tutorial.androiddreamer.myhomework.Model.ArchivedNote;
import com.tutorial.androiddreamer.myhomework.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.tutorial.androiddreamer.myhomework.Model.Note;
import com.tutorial.androiddreamer.myhomework.ViewModels.MainActivityViewModel;
import com.tutorial.androiddreamer.myhomework.Adapters.PagedListNoteAdapter;
import com.tutorial.androiddreamer.myhomework.Helpers.SwipeToDeleteCallback;

import java.util.Observable;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    public static final String EXTRA_MODE = "com.tutorial.androiddreamer.myhomework.EXTRA_MODE";
    public static final String EXTRA_SUBJECT = "com.tutorial.androiddreamer.myhomework.EXTRA_SUBJECT ";
    public static final String EXTRA_NOTE = "com.tutorial.androiddreamer.myhomework.EXTRA_NOTE";
    public static final String EXTRA_IMPORTANCE = "com.tutorial.androiddreamer.myhomework.EXTRA_IMPORTANCE";
    public static final String EXTRA_ID = "com.tutorial.androiddreamer.myhomework.EXTRA_ID";
    public static final String EXTRA_TIME = "com.tutorial.androiddreamer.myhomework.EXTRA_TIME";

    private MainActivityViewModel viewModel;
    private PagedListAdapter adapter;

    public static final int ADD_NOTE_REQUEST_CODE = 1;
    public static final int EDIT_NOTE_REQUEST_CODE = 2;
    public static final int ALTER_SETTINGS_REQUEST_CODE = 3;

    private int appearanceMode;
    private int orderMode;

    @BindView(R.id.rv_activity_main) RecyclerView recyclerView;
    @BindView(R.id.fab_activity_main_add_note) FloatingActionButton fabAddNote;
    @BindView(R.id.cl_activity_main_empty_rv) ConstraintLayout clEmptyRecyclerView;
    @BindView(R.id.cl_activity_main) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.cl_activity_main_container) ConstraintLayout constraintLayoutContainer;
    @BindView(R.id.tc_activity_main_its_lonely) TextView tvEmptyListLonely;
    @BindView(R.id.tv_activity_main_get_started) TextView tvGetStartedAddaNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        setUITheme();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUIThemeForElements();
        setTitle("My notes");
        fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, AddNoteActivity.class).setFlags( FLAG_ACTIVITY_SINGLE_TOP | FLAG_ACTIVITY_CLEAR_TOP)
                                .putExtra(EXTRA_MODE, AddNoteActivity.MODE_ADD_NOTE),
                        ADD_NOTE_REQUEST_CODE);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

            }
        });
        adapter = new PagedListNoteAdapter(this, new PagedListNoteAdapter.OnItemClickListener() {
            @Override
            public void onClick(Note note) {
                editNote(note);
            }
        }, new PagedListNoteAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(Note note, String str) {
                if(str.equalsIgnoreCase(getResources().getString(R.string.archive))){
                    archiveNote(note);
                }else if(str.equalsIgnoreCase(getResources().getString(R.string.edit))){
                    editNote(note);
                }else if(str.equalsIgnoreCase(getResources().getString(R.string.share))){
                    shareNote(note);
                }
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        viewModel.getAllNotes().observe(this, new Observer<PagedList<Note>>() {
            @Override
            public void onChanged(@Nullable PagedList<Note> notes) {
                Log.d(TAG, "observed a change");
                if (notes != null){
                    if(notes.isEmpty()){
                        clEmptyRecyclerView.setVisibility(View.VISIBLE);
                        adapter.submitList(notes);
                    }else{
                        clEmptyRecyclerView.setVisibility(View.GONE);
                        adapter.submitList(notes);
                    }
                }
            }
        });
        //It just give swipping functionality to the recyclerView.
        enableSwipeDeleteRV();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_NOTE_REQUEST_CODE && resultCode == RESULT_OK){

            String subject = data.getStringExtra(EXTRA_SUBJECT);
            String note = data.getStringExtra(EXTRA_NOTE);
            int importance = data.getIntExtra(EXTRA_IMPORTANCE,1);

            Note newNote = new Note(System.currentTimeMillis(), note, subject, importance);
            viewModel.getNoteRepository().insertNote(newNote);
            viewModel.getSharedPrefRepository().getSharedPreferencesDAO().increaseTotalNotes(); //statistics
            Toast.makeText(this, getResources().getString(R.string.note_saved_succesfully), Toast.LENGTH_SHORT).show();
        }

        if (requestCode == EDIT_NOTE_REQUEST_CODE && resultCode == RESULT_OK){
            String subject = data.getStringExtra(EXTRA_SUBJECT);
            String note = data.getStringExtra(EXTRA_NOTE);
            int importance = data.getIntExtra(EXTRA_IMPORTANCE,4);
            int id = data.getIntExtra(EXTRA_ID,-1);


            Note noteToBeUpdate = new Note(System.currentTimeMillis(), note, subject, importance);
            noteToBeUpdate.setId(id);
            viewModel.getNoteRepository().updateNote(noteToBeUpdate);
            viewModel.getSharedPrefRepository().getSharedPreferencesDAO().increaseTotalNotesEdited();
        }

        if(requestCode == ALTER_SETTINGS_REQUEST_CODE && resultCode == RESULT_OK){
            //Checking if theme has changed. In this way I am keeping the animation!

            if(appearanceMode != viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefTheme()){
                recreate(); //if appearance changed then recreate!
            }else if(orderMode != viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefOrder()) { //we make animation transition in this way!
                viewModel.getAllNotesOrderedByTime().removeObservers(this);
                viewModel.getAllNotesOrderedByImportance().removeObservers(this);
                viewModel.getAllNotes().observe(this, new Observer<PagedList<Note>>() {
                    @Override
                    public void onChanged(@Nullable PagedList<Note> notes) {
                        Log.d(TAG, "observed a change");
                        if (notes != null){
                            if(notes.isEmpty()){
                                clEmptyRecyclerView.setVisibility(View.VISIBLE);
                            }else{
                                clEmptyRecyclerView.setVisibility(View.GONE);
                                adapter.submitList(notes);
                            }
                        }
                    }
                });
            }
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
                //save apperancemode and ordermode and check for updates when settings activity finishes
                appearanceMode = viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefTheme();
                orderMode = viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefOrder();
                startActivityForResult(new Intent(MainActivity.this, SettingsActivity.class)
                        .setFlags( FLAG_ACTIVITY_SINGLE_TOP | FLAG_ACTIVITY_CLEAR_TOP), ALTER_SETTINGS_REQUEST_CODE);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                return true;
            case R.id.btn_menu_main_activity_history:
                startActivity(new Intent(MainActivity.this, OverviewActivity.class).setFlags( FLAG_ACTIVITY_SINGLE_TOP | FLAG_ACTIVITY_CLEAR_TOP));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void enableSwipeDeleteRV(){
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Note noteToDelete = viewModel.getAllNotes().getValue().get(viewHolder.getAdapterPosition());
                deleteNote(noteToDelete);
            }
        };
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }



    private void deleteNote(Note note){
        viewModel.setLastNote(note);
        viewModel.getNoteRepository().deleteNote(note);
        viewModel.getSharedPrefRepository().getSharedPreferencesDAO().increaseTotalNotesDeleted();
        Snackbar.make(coordinatorLayout, getResources().getString(R.string.note_deleted_succesfully), Snackbar.LENGTH_LONG)
                .setAction(getResources().getString(R.string.undo_button_snackbar), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(viewModel.getLastNote() != null){ //Fix bug where user taps at UNDO button furiously!
                            viewModel.getNoteRepository().insertNote(viewModel.getLastNote());
                            viewModel.deleteLastNote();
                        }

                    }
                }).setActionTextColor(getResources().getColor(R.color.colorAccent)).show();
    }

    private void editNote(Note note){
        Intent intent = new Intent(MainActivity.this, AddNoteActivity.class).setFlags( FLAG_ACTIVITY_SINGLE_TOP | FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(EXTRA_MODE, AddNoteActivity.MODE_EDIT_NOTE)
                .putExtra(EXTRA_SUBJECT, note.getSubject())
                .putExtra(EXTRA_NOTE, note.getDescription())
                .putExtra(EXTRA_ID, note.getId())
                .putExtra(EXTRA_IMPORTANCE, note.getPriority())
                .putExtra(EXTRA_TIME, note.getTime());
        startActivityForResult(intent, EDIT_NOTE_REQUEST_CODE);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    private void archiveNote(Note note){
        //It should update the database that the note was completed.
        ArchivedNote archivedNote = new ArchivedNote(note.getTime(), note.getDescription(), note.getSubject(), note.getPriority());
        viewModel.getArchivedNoteRepository().addArchivedNote(archivedNote);
        viewModel.getSharedPrefRepository().getSharedPreferencesDAO().increaseTotalNotesArchived();
        Toast.makeText(MainActivity.this, "Note archived", Toast.LENGTH_SHORT).show();
    }

    private void shareNote(Note note){
        viewModel.getSharedPrefRepository().getSharedPreferencesDAO().increaseTotalNotesShared();
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                "Title : " + note.getSubject()+ "\n" +
                "Note: "  + note.getDescription());
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                note.getSubject());
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_note_using)));

    }

    private void setUITheme(){
        switch (viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefTheme()){
            case SettingsActivity.APPEARANCE_LIGHT:
                break;
            case SettingsActivity.APPEARANCE_BLACK:
//                coordinatorLayout.setBackgroundColor(Color.BLACK);
                setTheme(R.style.DarkTheme);
        }
    }

    private void setUIThemeForElements(){
        if(viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefTheme() == 0){

        }else if(viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefTheme() == 1){
            coordinatorLayout.setBackgroundColor(getResources().getColor(R.color.DarkGrayBackground));
            tvEmptyListLonely.setTextColor(Color.WHITE);
            tvGetStartedAddaNote.setTextColor(Color.WHITE);

        }
    }
}

