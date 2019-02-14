package com.tutorial.androiddreamer.myhomework.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.tutorial.androiddreamer.myhomework.Model.NoteRepository;
import com.tutorial.androiddreamer.myhomework.Model.SharedPrefRepository;

public class DisplayNoteActivityViewModel extends AndroidViewModel {
    private SharedPrefRepository sharedPrefRepository;
    private NoteRepository noteRepository;

    String title;
    String description;
    int importance;
    int id;

    boolean guard;




    public DisplayNoteActivityViewModel(@NonNull Application application) {
        super(application);
        sharedPrefRepository = new SharedPrefRepository(application);
        noteRepository = new NoteRepository(application);
    }


    public SharedPrefRepository getSharedPrefRepository() {
        return sharedPrefRepository;
    }

    public NoteRepository getNoteRepository() {
        return noteRepository;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {

        if(!guard) this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(!guard) this.description = description;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        if(!guard) this.importance = importance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(!guard) this.id = id;
    }

    public boolean isGuard() {
        return guard;
    }

    public void setGuard(boolean guard) {
        this.guard = guard;
    }
}
