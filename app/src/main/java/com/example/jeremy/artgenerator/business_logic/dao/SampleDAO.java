package com.example.jeremy.artgenerator.business_logic.dao;

import com.example.jeremy.artgenerator.constants.SqlConstants;
import com.example.jeremy.artgenerator.business_logic.data.Duration;
import com.example.jeremy.artgenerator.business_logic.data.Path;
import com.example.jeremy.artgenerator.business_logic.data.Sample;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface SampleDAO {
    //insert sample to table
    @Insert(onConflict = REPLACE)
    void insert(Sample sample);
    //update sample for table
    @Update
    void update(Sample sample);
    //get all samples from table
    @Query("SELECT * FROM " + SqlConstants.Tables.SAMPLE_TB_NAME)
    LiveData<List<Sample>> getSamples();
    //get appropriate sample by id
    @Query("SELECT * FROM " + SqlConstants.Tables.SAMPLE_TB_NAME + " WHERE sample_id = :id")
    LiveData<Sample> getSample(int id);
    //remove appropriate sample from table
    @Delete
    void remove(Sample sample);
    //remove sample by id from table
    @Query("DELETE FROM " + SqlConstants.Tables.SAMPLE_TB_NAME + " WHERE sample_id = :id")
    void remove(int id);
    //remove all samples from table
    @Query("DELETE FROM " + SqlConstants.Tables.SAMPLE_TB_NAME)
    void removeAll();

    interface PathDAO {
        //insert path to table
        @Insert(onConflict = REPLACE)
        void insert(Path path);
        //update path for table
        @Update
        void update(Path path);
        //get all paths from table
        @Query("SELECT * FROM " + SqlConstants.Tables.PATH_TB_NAME)
        LiveData<List<Path>> getPaths();
        //get appropriate sample by id
        @Query("SELECT * FROM " + SqlConstants.Tables.PATH_TB_NAME + " WHERE path_id = :id")
        LiveData<Path> getPath(int id);
        //remove appropriate path from table
        @Delete
        void remove(Path path);
        //remove path by id from table
        @Query("DELETE FROM " + SqlConstants.Tables.PATH_TB_NAME + " WHERE path_id = :id")
        void remove(int id);
        //remove all paths from table
        @Query("DELETE FROM " + SqlConstants.Tables.PATH_TB_NAME)
        void removeAll();
    }

    interface DurationDAO {
        //insert duration to table
        @Insert(onConflict = REPLACE)
        void insert(Duration duration);
        //update duration for table
        @Update
        void update(Duration duration);
        //get all durations from table
        @Query("SELECT * FROM " + SqlConstants.Tables.DURATION_TB_NAME)
        LiveData<List<Duration>> getPaths();
        //get appropriate sample by id
        @Query("SELECT * FROM " + SqlConstants.Tables.DURATION_TB_NAME + " WHERE duration_id = :id")
        LiveData<Duration> getDuration(int id);
        //remove appropriate duration from table
        @Delete
        void remove(Duration duration);
        //remove duration by id from table
        @Query("DELETE FROM " + SqlConstants.Tables.DURATION_TB_NAME + " WHERE duration_id = :id")
        void remove(int id);
        //remove all durations from table
        @Query("DELETE FROM " + SqlConstants.Tables.DURATION_TB_NAME)
        void removeAll();
    }
}