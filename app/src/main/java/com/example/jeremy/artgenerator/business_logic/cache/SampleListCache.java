package com.example.jeremy.artgenerator.business_logic.cache;

import com.example.jeremy.artgenerator.business_logic.data.Sample;

import java.util.List;

import androidx.lifecycle.LiveData;

public class SampleListCache {

    private LiveData<List<Sample>> sampleListLiveData;

    public SampleListCache() {
    }

    public LiveData<List<Sample>> getSampleListLiveData() {
        return sampleListLiveData;
    }

    public void setUserListLiveData(LiveData<List<Sample>> sampleListLiveData) {
        this.sampleListLiveData = sampleListLiveData;
    }
}
