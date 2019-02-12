package com.example.jeremy.artgenerator.services;

import android.app.PendingIntent;

import com.example.jeremy.artgenerator.business_logic.data.Sample;

public interface IUploaderService {
    //upload object to db
    void upload(Sample sample , PendingIntent pendingIntent);
}
