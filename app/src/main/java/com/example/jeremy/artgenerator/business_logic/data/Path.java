package com.example.jeremy.artgenerator.business_logic.data;

import android.net.Uri;

import com.example.jeremy.artgenerator.constants.SqlConstants;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = SqlConstants.Tables.PATH_TB_NAME)
public class Path {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = SqlConstants.Tables.Columns.PATH_ID)
    private int id;

    @ColumnInfo(name = SqlConstants.Tables.Columns.PATH_BODY)
    private String stringPath;

    public Path(String stringPath) {
        this.stringPath = stringPath;
    }

    @Override
    public String toString() {
        return stringPath;
    }

    public Uri toUri() {
        return Uri.parse(stringPath);
    }
}
