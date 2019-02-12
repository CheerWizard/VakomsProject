package com.example.jeremy.artgenerator.business_logic.data;

import com.example.jeremy.artgenerator.constants.SqlConstants;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = SqlConstants.Tables.DURATION_TB_NAME)
public class Duration {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = SqlConstants.Tables.Columns.DURATION_ID)
    private int id;

    @ColumnInfo(name = SqlConstants.Tables.Columns.DURATION_TIME)
    private int milliseconds;

    public Duration(int milliseconds) {
        this.milliseconds = milliseconds;
    }

    public int getMilliseconds() {
        return milliseconds;
    }

    public int toSeconds() {
        return milliseconds/1000;
    }

    public int toMinute() {
        return milliseconds/(1000*60);
    }

    public int toHours() {
        return milliseconds/(1000*60*60);
    }

    public Integer toInteger() {
        return milliseconds;
    }
}
