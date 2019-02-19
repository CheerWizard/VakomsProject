package com.example.jeremy.artgenerator.ui;

import android.app.Application;

import com.example.jeremy.artgenerator.business_logic.databases.AppDatabaseManager;
import com.example.jeremy.artgenerator.utils.containers.GarbageContainer;

public class VakomsApplication extends Application {

    private static VakomsApplication instance;

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

    @Override
    public void onTerminate() {
        super.onTerminate();
        appDatabaseManager.close();
        GarbageContainer.cleanUp();
    }

    public static synchronized VakomsApplication getInstance() {
        return instance;
    }
}
