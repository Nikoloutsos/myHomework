package com.tutorial.androiddreamer.myhomework.Application;

import android.app.Application;

import com.tutorial.androiddreamer.myhomework.BuildConfig;

import net.alexandroid.utils.mylog.MyLog;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * Initialize the special log messages.
         */
        MyLog.init(this, "MyLog", BuildConfig.DEBUG);
        MyLog.setThreadIdVisibility(true);
        MyLog.setIsTimeVisible(false);
        MyLog.setTag("MyHomeworkTag");
    }
}
