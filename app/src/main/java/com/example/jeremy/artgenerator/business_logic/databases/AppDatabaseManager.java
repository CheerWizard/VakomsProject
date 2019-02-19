package com.example.jeremy.artgenerator.business_logic.databases;

import com.example.jeremy.artgenerator.constants.SqlConstants;
import com.example.jeremy.artgenerator.ui.VakomsApplication;

import androidx.room.Room;

public class AppDatabaseManager {

    private AppDatabase appDatabase;

    public AppDatabaseManager() {
        appDatabase = Room.databaseBuilder(VakomsApplication.getInstance() , AppDatabase.class , SqlConstants.Databases.APP_DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    public void close() {
        appDatabase.close();
    }

    public boolean isOpen() {
        return appDatabase.isOpen();
    }
}
