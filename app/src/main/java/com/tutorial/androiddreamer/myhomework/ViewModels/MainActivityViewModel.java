package com.tutorial.androiddreamer.myhomework.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.tutorial.androiddreamer.myhomework.Activities.SettingsActivity;
import com.tutorial.androiddreamer.myhomework.Model.ArchivedNoteRepository;
import com.tutorial.androiddreamer.myhomework.Model.Note;
import com.tutorial.androiddreamer.myhomework.Model.NoteRepository;
import com.tutorial.androiddreamer.myhomework.Model.SharedPrefRepository;

public class MainActivityViewModel extends AndroidViewModel {
    private static final String TAG = "MainActivityViewModel";
    private NoteRepository noteRepository;
    private SharedPrefRepository sharedPrefRepository;
    private ArchivedNoteRepository archivedNoteRepository;
    private LiveData<PagedList<Note>> allNotesOrderedByTime;
    private LiveData<PagedList<Note>> allNotesOrderedByImportance;
    private Note lastNote;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
        sharedPrefRepository = new SharedPrefRepository(application);
        archivedNoteRepository = new ArchivedNoteRepository(application);

        DataSource.Factory<Integer, Note> tempNotes = noteRepository.getAllNotesOrderedByTime();
        LivePagedListBuilder<Integer, Note> livePagedListBuilder = new LivePagedListBuilder<>(tempNotes, 15);
        allNotesOrderedByTime = livePagedListBuilder.build();

        DataSource.Factory<Integer, Note> tempNotes2 = noteRepository.getAllNotesOrderedByImportance();
        LivePagedListBuilder<Integer, Note> livePagedListBuilder2 = new LivePagedListBuilder<>(tempNotes2, 15);
        allNotesOrderedByImportance = livePagedListBuilder2.build();
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
    public SharedPrefRepository getSharedPrefRepository() { return sharedPrefRepository; }
    public ArchivedNoteRepository getArchivedNoteRepository() { return archivedNoteRepository; }

    public LiveData<PagedList<Note>> getAllNotesOrderedByTime() {
        return allNotesOrderedByTime;
    }
    public LiveData<PagedList<Note>> getAllNotesOrderedByImportance() { return allNotesOrderedByImportance; }

    public LiveData<PagedList<Note>> getAllNotes(){
        switch (sharedPrefRepository.getSharedPreferencesDAO().getSharedPrefOrder()){
            case SettingsActivity.ORDER_TIME:
                return  getAllNotesOrderedByTime();
            case SettingsActivity.ORDER_IMPORTANCE:
                return getAllNotesOrderedByImportance();
            default:
                return  getAllNotesOrderedByTime();
        }
    }
}
