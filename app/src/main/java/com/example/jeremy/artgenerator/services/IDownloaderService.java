package com.example.jeremy.artgenerator.services;

import android.app.PendingIntent;

public interface IDownloaderService {
    //download T object from db
    void download(int position , PendingIntent pendingIntent);
}
