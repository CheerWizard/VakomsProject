package com.example.jeremy.artgenerator.business_logic.data;

import com.example.jeremy.artgenerator.constants.SqlConstants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = SqlConstants.Tables.USER_TB_NAME)
public class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = SqlConstants.Tables.Columns.USER_ID)
    @SerializedName("id")
    @Expose
    private int id;

    @ColumnInfo(name = SqlConstants.Tables.Columns.USER_NAME)
    @SerializedName("name")
    @Expose
    private String name;

    @ColumnInfo(name = SqlConstants.Tables.Columns.USER_LOGIN)
    @SerializedName("username")
    @Expose
    private String login;

    @ColumnInfo(name = SqlConstants.Tables.Columns.USER_EMAIL)
    @SerializedName("email")
    @Expose
    private String email;

    @ColumnInfo(name = SqlConstants.Tables.Columns.USER_PHONE)
    @SerializedName("phone")
    @Expose
    private String phone;

    @ColumnInfo(name = SqlConstants.Tables.Columns.USER_WEBSITE)
    @SerializedName("website")
    @Expose
    private String website;

    @ColumnInfo(name = SqlConstants.Tables.Columns.USER_PASSWORD)
    private String password;
    /**
     * No args constructor for use in serialization
     *
     */
    public User() {
    }
    /**
     *
     * @param id
     * @param phone
     * @param website
     * @param email
     * @param name
     */
    @Ignore
    public User(int id, String name, String login, String email, String phone, String website, String password) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.email = email;
        this.phone = phone;
        this.website = website;
        this.password = password;
    }
    @Ignore
    public User(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

