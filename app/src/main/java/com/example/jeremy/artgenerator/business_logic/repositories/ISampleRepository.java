package com.example.jeremy.artgenerator.business_logic.repositories;

import com.example.jeremy.artgenerator.business_logic.data.Sample;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface ISampleRepository {
    void removeAll();
    LiveData<List<Sample>> getAllSamples();
    void remove(int id);
    LiveData<Sample> getSample(int id);
    void insert(Sample sample);
    void update(Sample sample);
    void close();
    //    void incrementSamplePriority(int priority);
//    void decrementSamplePriority(int priority);
}
