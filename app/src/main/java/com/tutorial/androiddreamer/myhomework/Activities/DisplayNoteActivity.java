package com.tutorial.androiddreamer.myhomework.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tutorial.androiddreamer.myhomework.Helpers.ActivityMethods;
import com.tutorial.androiddreamer.myhomework.Model.Note;
import com.tutorial.androiddreamer.myhomework.R;
import com.tutorial.androiddreamer.myhomework.ViewModels.AddNoteActivityViewModel;
import com.tutorial.androiddreamer.myhomework.ViewModels.DisplayNoteActivityViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

public class DisplayNoteActivity extends AppCompatActivity {

    private DisplayNoteActivityViewModel viewModel;
    @BindView(R.id.tv_description_activity_display_note)
    TextView tvDescription;
    @BindView(R.id.cl_parent_layout_activity_display_note)
    ConstraintLayout parentConstraintLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(DisplayNoteActivityViewModel.class);
        super.onCreate(savedInstanceState);
        setUITheme();
        setContentView(R.layout.activity_display_note);
        ButterKnife.bind(this);
        setUIThemeForElements();


        /**
         * Extract information from intent
         */

        Intent receivedIntent = getIntent();

        viewModel.setTitle(receivedIntent.getStringExtra(MainActivity.EXTRA_SUBJECT));
        viewModel.setDescription(receivedIntent.getStringExtra(MainActivity.EXTRA_NOTE));
        viewModel.setImportance(receivedIntent.getIntExtra(MainActivity.EXTRA_IMPORTANCE,0));
        viewModel.setId(receivedIntent.getIntExtra(MainActivity.EXTRA_ID,0));


        /**
         * Update UI state
         */
        setTitle(viewModel.getTitle());
        tvDescription.setText(viewModel.getDescription());
        tvDescription.setMovementMethod(new ScrollingMovementMethod());


        /**
         * Enable the guard so as to prevent loss of data due to configuration changes.
         */
        viewModel.setGuard(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_show_note_activity, menu);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MainActivity.EDIT_NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
            String subject = data.getStringExtra(MainActivity.EXTRA_SUBJECT);
            String note = data.getStringExtra(MainActivity.EXTRA_NOTE);
            int importance = data.getIntExtra(MainActivity.EXTRA_IMPORTANCE, 1);
            int id = data.getIntExtra(MainActivity.EXTRA_ID, 0);

            Note noteToBeUpdate = new Note(System.currentTimeMillis(), note, subject, importance);
            noteToBeUpdate.setId(id);
            viewModel.getNoteRepository().updateNote(noteToBeUpdate);
            viewModel.getSharedPrefRepository().getSharedPreferencesDAO().increaseTotalNotesEdited();

            viewModel.setGuard(false);
            viewModel.setTitle(subject);
            viewModel.setDescription(note);
            viewModel.setImportance(importance);
            viewModel.setGuard(true);

            setTitle(viewModel.getTitle());
            tvDescription.setText(viewModel.getDescription());


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_menu_edit_note:
                /**
                 * Start Activity for result and edit the note
                 * Then change it from database
                 */
                editNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    private void editNote() {
        Intent intent = new Intent(this, AddNoteActivity.class)
                .setFlags(FLAG_ACTIVITY_SINGLE_TOP | FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(MainActivity.EXTRA_MODE, AddNoteActivity.MODE_EDIT_NOTE)
                .putExtra(MainActivity.EXTRA_SUBJECT, viewModel.getTitle())
                .putExtra(MainActivity.EXTRA_NOTE, viewModel.getDescription())
                .putExtra(MainActivity.EXTRA_ID, viewModel.getId())
                .putExtra(MainActivity.EXTRA_IMPORTANCE, viewModel.getImportance());
        startActivityForResult(intent, MainActivity.EDIT_NOTE_REQUEST_CODE);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }


    private void setUITheme(){
        if(viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefTheme() == 0){
            setTheme(R.style.AppTheme);
        }else{
            setTheme(R.style.DarkTheme);
        }
    }
    private void setUIThemeForElements(){
        if(viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefTheme() == 0){
            parentConstraintLayout.setBackground(getDrawable(R.drawable.veneer_repeating));
        }else if(viewModel.getSharedPrefRepository().getSharedPreferencesDAO().getSharedPrefTheme() == 1){
            parentConstraintLayout.setBackground(getDrawable(R.drawable.stardust_repeating));
        }
    }



}
