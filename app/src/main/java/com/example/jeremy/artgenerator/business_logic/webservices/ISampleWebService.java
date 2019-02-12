package com.example.jeremy.artgenerator.business_logic.webservices;

import android.app.PendingIntent;

import com.example.jeremy.artgenerator.business_logic.data.Sample;

import java.util.List;

public interface ISampleWebService {
    List<Sample> getAllSampleOfUsers();
    List<Sample> getAll();
    void removeAll();
    void update(Sample sample);
    void insert(Sample sample);
    void insert(Sample sample , PendingIntent pendingIntent);
    void remove(int position);
    Sample get(int position);
    Sample get(int position , PendingIntent pendingIntent);
}
