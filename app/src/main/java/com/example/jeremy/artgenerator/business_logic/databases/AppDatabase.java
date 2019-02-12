package com.example.jeremy.artgenerator.business_logic.databases;

import com.example.jeremy.artgenerator.business_logic.dao.SampleDAO;
import com.example.jeremy.artgenerator.business_logic.dao.UserDAO;
import com.example.jeremy.artgenerator.constants.SqlConstants;
import com.example.jeremy.artgenerator.business_logic.data.Duration;
import com.example.jeremy.artgenerator.business_logic.data.Path;
import com.example.jeremy.artgenerator.business_logic.data.Sample;
import com.example.jeremy.artgenerator.business_logic.data.User;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Sample.class , User.class , Duration.class , Path.class} , version = SqlConstants.Databases.DB_VERSION_1 , exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
    public abstract SampleDAO sampleDAO();
}
