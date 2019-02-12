package com.example.jeremy.artgenerator.business_logic.data;

import com.example.jeremy.artgenerator.constants.SqlConstants;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = SqlConstants.Tables.USER_TB_NAME)
public class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = SqlConstants.Tables.Columns.USER_ID)
    private int id;

    @ColumnInfo(name = SqlConstants.Tables.Columns.USER_NAME)
    private String name;

    @ColumnInfo(name = SqlConstants.Tables.Columns.USER_EMAIL)
    private String email;

    @ColumnInfo(name = SqlConstants.Tables.Columns.USER_PASSWORD)
    private String password;

    public User() {
        //empty constructor
    }

    public User(String name , String password , String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
