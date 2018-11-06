package model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.tutorial.androiddreamer.myhomework.SettingsActivity;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository noteRepository;
    private LiveData<PagedList<Note>> allNotes;
    private Note lastNote;





    public NoteViewModel(@NonNull Application application) {
        super(application);
        SharedPreferences sharedPref = application.getSharedPreferences(
                SettingsActivity.MY_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        noteRepository = new NoteRepository(application, sharedPref.getInt(
                SettingsActivity.SHARED_PREFERENCE_ORDER, 0));

        DataSource.Factory<Integer, Note> tempNotes = noteRepository.getAllNotes();
        LivePagedListBuilder<Integer, Note> livePagedListBuilder = new LivePagedListBuilder<>(tempNotes, 10);
        allNotes = livePagedListBuilder.build();





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

    public LiveData<PagedList<Note>> getAllNotes() {
        return allNotes;
    }
}
