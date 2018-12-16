package com.tutorial.androiddreamer.myhomework.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.tutorial.androiddreamer.myhomework.Model.SharedPrefRepository;

public class AddNoteActivityViewModel extends AndroidViewModel {
    private int prioritySelected = 1;
    private boolean userHasInteracted = false;
    private SharedPrefRepository sharedPrefRepository;

    public AddNoteActivityViewModel(@NonNull Application application) {
        super(application);
        sharedPrefRepository = new SharedPrefRepository(application);
    }


    //Getters & Setters


    public SharedPrefRepository getSharedPrefRepository() {
        return sharedPrefRepository;
    }

    public int getPrioritySelected() {
        return prioritySelected;
    }

    public boolean getUserHasInteracted() {
        return userHasInteracted;
    }

    public void setPrioritySelected(int prioritySelected) {
        this.prioritySelected = prioritySelected;
    }

    public void setUserHasInteracted(boolean userHasInteracted) {
        this.userHasInteracted = userHasInteracted;
    }
}
