package com.tutorial.androiddreamer.myhomework.Model;

import android.app.Application;

public class SharedPrefRepository {
    private SharedPreferencesDAO sharedPreferencesDAO;


    public SharedPrefRepository(Application application){
        sharedPreferencesDAO = new SharedPreferencesDAO(application);
    }

    public SharedPreferencesDAO getSharedPreferencesDAO(){
        return sharedPreferencesDAO;
    }

}
