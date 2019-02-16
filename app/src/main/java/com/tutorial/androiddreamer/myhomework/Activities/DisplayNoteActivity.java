package com.tutorial.androiddreamer.myhomework.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.tutorial.androiddreamer.myhomework.Model.Note;
import com.tutorial.androiddreamer.myhomework.R;

import butterknife.ButterKnife;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

public class DisplayNoteActivity extends BaseDisplayNoteActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
