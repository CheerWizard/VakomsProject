package com.example.jeremy.artgenerator.business_logic.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.jeremy.artgenerator.constants.SamplePriorities;
import com.example.jeremy.artgenerator.constants.SqlConstants;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = SqlConstants.Tables.SAMPLE_TB_NAME)
public class Sample implements Comparable<Sample> , Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = SqlConstants.Tables.Columns.SAMPLE_ID)
    private int id;

    @ColumnInfo(name = SqlConstants.Tables.Columns.SAMPLE_PATH_ID)
    private int path_id;

    @ColumnInfo(name = SqlConstants.Tables.Columns.SAMPLE_NAME)
    private String sampleName;

    @ColumnInfo(name = SqlConstants.Tables.Columns.SAMPLE_PRIORITY)
    private Integer samplePriority;

    @ColumnInfo(name = SqlConstants.Tables.Columns.SAMPLE_DURATION_ID)
    private int duration_id;

    @ColumnInfo(name = SqlConstants.Tables.Columns.SAMPLE_USER_EMAIL)
    private String userEmail;

    public Sample(int id, int path_id, String sampleName, Integer samplePriority, int duration_id, String userEmail) {
        this.id = id;
        this.path_id = path_id;
        this.sampleName = sampleName;
        this.samplePriority = samplePriority;
        this.duration_id = duration_id;
        this.userEmail = userEmail;
    }

    public Sample(String sampleName, Integer samplePriority) {
        this.sampleName = sampleName;
        this.samplePriority = samplePriority;
    }

    public Sample(Parcel parcel) {
        this.id = parcel.readInt();
        this.path_id = parcel.readInt();
        this.sampleName = parcel.readString();
        this.samplePriority = parcel.readInt();
        this.duration_id = parcel.readInt();
        this.userEmail = parcel.readString();
    }

    public Sample() {
        //default empty constructor
        samplePriority = SamplePriorities.DEFAULT_PRIORITY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSamplePriority(int samplePriority) {
        this.samplePriority = samplePriority;
    }

    public int getSamplePriority() {
        return samplePriority;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getSampleName() {
        return sampleName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getPath_id() {
        return path_id;
    }

    public void setPath_id(int path_id) {
        this.path_id = path_id;
    }

    public void setSamplePriority(Integer samplePriority) {
        this.samplePriority = samplePriority;
    }

    public int getDuration_id() {
        return duration_id;
    }

    public void setDuration_id(int duration_id) {
        this.duration_id = duration_id;
    }

    public static final Parcelable.Creator<Sample> CREATOR = new Parcelable.Creator<Sample>(){

        @Override
        public Sample createFromParcel(Parcel parcel) {
            return new Sample(parcel);
        }

        @Override
        public Sample[] newArray(int size) {
            return new Sample[0];
        }
    };

    @Override
    public int compareTo(Sample sample) {
        return this.samplePriority.compareTo(sample.getSamplePriority());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sampleName);
        dest.writeString(userEmail);
        dest.writeInt(samplePriority);
        dest.writeInt(id);
        dest.writeInt(path_id);
        dest.writeInt(duration_id);
    }
}
