package model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.tutorial.androiddreamer.myhomework.SettingsActivity;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository noteRepository;
    private LiveData<List<Note>> allNotes;
    private Note lastNote;





    public NoteViewModel(@NonNull Application application) {
        super(application);
        SharedPreferences sharedPref = application.getSharedPreferences(
                SettingsActivity.MY_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        noteRepository = new NoteRepository(application, sharedPref.getInt(
                SettingsActivity.SHARED_PREFERENCE_ORDER, 0));

        allNotes = noteRepository.getAllNotes();
    }

    public Note getLastNote() {
        return lastNote;
    }
    public void deleteLastNote() {lastNote = null;}

    public void setLastNote(Note lastNote) {
        this.lastNote = lastNote;
    }

    public NoteRepository getNoteRepository() {
        return noteRepository;
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
