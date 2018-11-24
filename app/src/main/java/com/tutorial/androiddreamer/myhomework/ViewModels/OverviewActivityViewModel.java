package com.tutorial.androiddreamer.myhomework.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.tutorial.androiddreamer.myhomework.Model.SharedPrefRepository;
import com.tutorial.androiddreamer.myhomework.Model.SharedPreferencesDAO;

public class OverviewActivityViewModel extends AndroidViewModel {
    private SharedPrefRepository sharedPrefRepository;

    public OverviewActivityViewModel(@NonNull Application application) {
        //TODO Rewrite the code code using dependency injection design pattern.
        super(application);
        sharedPrefRepository = new SharedPrefRepository(application);
    }

    public SharedPrefRepository getSharedPrefRepository() {
        return sharedPrefRepository;
    }


}
