package com.example.jeremy.artgenerator.ui;

import android.app.Application;

import com.example.jeremy.artgenerator.business_logic.databases.AppDatabaseManager;

public class GlobalPadApplication extends Application {

    private static GlobalPadApplication instance;

    private AppDatabaseManager appDatabaseManager;

    public AppDatabaseManager getAppDatabaseManager() {
        return appDatabaseManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appDatabaseManager = new AppDatabaseManager();
    }

    public static synchronized GlobalPadApplication getInstance() {
        return instance;
    }
}
