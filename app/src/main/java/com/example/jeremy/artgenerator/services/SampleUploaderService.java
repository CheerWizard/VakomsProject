package com.example.jeremy.artgenerator.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.jeremy.artgenerator.R;
import com.example.jeremy.artgenerator.business_logic.data.Sample;
import com.example.jeremy.artgenerator.business_logic.webservices.SampleWebService;

public class SampleUploaderService extends Service implements IUploaderService {

    private static SampleUploaderService instance;
    //webservice
    private SampleWebService sampleWebService;
    //global vars
    private boolean isRunning;

    public void setSampleWebService(SampleWebService sampleWebService) {
        this.sampleWebService = sampleWebService;
    }

    public static SampleUploaderService getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isRunning = true;
        PendingIntent pendingIntent = intent.getParcelableExtra(getResources().getString(R.string.pending_intent));
        final Sample sample = intent.getParcelableExtra(getResources().getString(R.string.sample));
        upload(sample , pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        isRunning = false;
        super.onDestroy();
    }

    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void upload(final Sample sample , final PendingIntent pendingIntent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sampleWebService.insert(sample , pendingIntent);
                } finally {
                    stopSelf();
                }
            }
        }).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
