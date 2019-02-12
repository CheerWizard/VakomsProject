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
    //insert note to table
    @Insert(onConflict = REPLACE)
    void insert(User user);
    //update note for table
    @Update
    void update(User user);
    //get all notes from table
    @Query("SELECT * FROM " + SqlConstants.Tables.USER_TB_NAME)
    LiveData<List<User>> getUsers();
    @Query("SELECT * FROM " + SqlConstants.Tables.USER_TB_NAME + " WHERE user_email = :email")
    LiveData<User> getUser(String email);
    //remove appropriate note from table
    @Delete
    void remove(User user);
    //remove note by id from table
    @Query("DELETE FROM " + SqlConstants.Tables.USER_TB_NAME + " WHERE user_id = :id")
    void remove(int id);
    //remove all notes from table
    @Query("DELETE FROM " + SqlConstants.Tables.USER_TB_NAME)
    void removeAll();
}
