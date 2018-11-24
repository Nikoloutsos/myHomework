package com.tutorial.androiddreamer.myhomework.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.tutorial.androiddreamer.myhomework.Model.ArchivedNote;
import com.tutorial.androiddreamer.myhomework.Model.ArchivedNoteRepository;
import com.tutorial.androiddreamer.myhomework.Model.SharedPrefRepository;

public class ArchivedNoteFragmentViewModel extends AndroidViewModel {
    private ArchivedNoteRepository archivedNoteRepository;
    private SharedPrefRepository sharedPrefRepository;
    private LiveData<PagedList<ArchivedNote>> allArchivedNotes;


    public ArchivedNoteFragmentViewModel(@NonNull Application application) {
        super(application);
        archivedNoteRepository = new ArchivedNoteRepository(application);
        sharedPrefRepository = new SharedPrefRepository(application);

        DataSource.Factory<Integer, ArchivedNote> tempNotes = archivedNoteRepository.getAllOrderedArchivedNotes(
                sharedPrefRepository.getSharedPreferencesDAO().getSharedPrefOrder());
        LivePagedListBuilder<Integer, ArchivedNote> livePagedListBuilder = new LivePagedListBuilder<>(tempNotes, 15);
        allArchivedNotes = livePagedListBuilder.build();
    }

    public ArchivedNoteRepository getArchivedNoteRepository() {
        return archivedNoteRepository;
    }

    public SharedPrefRepository getSharedPrefRepository() {
        return sharedPrefRepository;
    }

    public LiveData<PagedList<ArchivedNote>> getAllArchivedNotes() {
        return allArchivedNotes;
    }
}
