package com.example.jeremy.artgenerator.business_logic.dao;

import com.example.jeremy.artgenerator.constants.SqlConstants;
import com.example.jeremy.artgenerator.business_logic.data.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDAO {
    @Insert(onConflict = REPLACE)
    void insert(User user);

    @Update
    void update(User user);

    @Query("SELECT * FROM " + SqlConstants.Tables.USER_TB_NAME)
    LiveData<List<User>> getUsers();

    @Query("SELECT * FROM " + SqlConstants.Tables.USER_TB_NAME)
    LiveData<User> getUser();

    @Query("SELECT user_id FROM " + SqlConstants.Tables.USER_TB_NAME + " WHERE user_email = :user_email")
    int getUserId(String user_email);

    @Delete
    void remove(User user);

    @Query("DELETE FROM " + SqlConstants.Tables.USER_TB_NAME + " WHERE user_id = :id")
    void remove(int id);

    @Query("DELETE FROM " + SqlConstants.Tables.USER_TB_NAME)
    void removeAll();
}
