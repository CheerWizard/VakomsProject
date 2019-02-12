package com.example.jeremy.artgenerator.business_logic.webservices;


import android.app.PendingIntent;
import android.os.Handler;

import com.example.jeremy.artgenerator.business_logic.api.IJsonServerApi;
import com.example.jeremy.artgenerator.business_logic.data.Sample;
import com.example.jeremy.artgenerator.constants.ProcessStates;
import com.example.jeremy.artgenerator.utils.senders.NotificationSender;
import com.example.jeremy.artgenerator.utils.cache.EmailCache;
import com.example.jeremy.artgenerator.utils.deserializers.ConfigDeserializer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SampleWebService implements ISampleWebService {
    //api
    private IJsonServerApi jsonServerApi;
    //global vars
    private List<Sample> sampleListResponse;
    private Sample sampleResponse;
    //handler
    private Handler handler;
    //pending intent
    private PendingIntent pendingIntent;

    public SampleWebService() {
        initRetrofit();
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setPendingIntent(PendingIntent pendingIntent) {
        this.pendingIntent = pendingIntent;
    }

    private void initRetrofit() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConfigDeserializer.getConfig().getApiUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonServerApi = retrofit.create(IJsonServerApi.class);
    }

    @Override
    public List<Sample> getAllSampleOfUsers() {
        jsonServerApi.getAllSamples().enqueue(new Callback<List<Sample>>() {
            @Override
            public void onResponse(Call<List<Sample>> call, Response<List<Sample>> response) {
                sampleListResponse = response.body();
            }

            @Override
            public void onFailure(Call<List<Sample>> call, Throwable t) {

            }
        });
        return sampleListResponse;
    }

    @Override
    public List<Sample> getAll() {
        jsonServerApi.getSamples(EmailCache.getEmail()).enqueue(new Callback<List<Sample>>() {
            @Override
            public void onResponse(Call<List<Sample>> call, Response<List<Sample>> response) {
                sampleListResponse = response.body();
            }

            @Override
            public void onFailure(Call<List<Sample>> call, Throwable t) {

            }
        });
        return sampleListResponse;
    }

    @Override
    public void removeAll() {
        jsonServerApi.deleteSamples(EmailCache.getEmail()).enqueue(new Callback<List<Sample>>() {
            @Override
            public void onResponse(Call<List<Sample>> call, Response<List<Sample>> response) {
                sampleListResponse = response.body();
            }

            @Override
            public void onFailure(Call<List<Sample>> call, Throwable t) {

            }
        });
    }

    @Override
    public void update(Sample sample) {
        jsonServerApi.patchSample(sample.getUserEmail() , sample).enqueue(new Callback<Sample>() {
            @Override
            public void onResponse(Call<Sample> call, Response<Sample> response) {
                sampleResponse = response.body();
            }

            @Override
            public void onFailure(Call<Sample> call, Throwable t) {

            }
        });
    }

    @Override
    public void insert(Sample sample) {
        jsonServerApi.postSample(sample.getUserEmail() , sample).enqueue(new Callback<Sample>() {
            @Override
            public void onResponse(Call<Sample> call, Response<Sample> response) {
                sampleResponse = response.body();
            }

            @Override
            public void onFailure(Call<Sample> call, Throwable t) {

            }
        });
    }

    @Override
    public void insert(Sample sample, final PendingIntent pendingIntent) {
        jsonServerApi.postSample(sample.getUserEmail() , sample).enqueue(new Callback<Sample>() {
            @Override
            public void onResponse(Call<Sample> call, Response<Sample> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    try {
                        NotificationSender.Upload.sendError(pendingIntent , ProcessStates.Failed.STATUS_UPLOAD_FAILED);
                    } catch (PendingIntent.CanceledException e) {
                        handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Failed.STATUS_PENDING_INTENT_CANCELED));
                    }
                }
                else {
                    sampleResponse = response.body();
                    try {
                        NotificationSender.Upload.sendNotification(pendingIntent , sampleResponse);
                    } catch (PendingIntent.CanceledException e) {
                        handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Failed.STATUS_PENDING_INTENT_CANCELED));
                    }
                }
            }

            @Override
            public void onFailure(Call<Sample> call, Throwable t) {
                try {
                    NotificationSender.Upload.sendError(pendingIntent , ProcessStates.STATUS_CONNECTION_FAILED);
                } catch (PendingIntent.CanceledException e) {
                    handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Failed.STATUS_PENDING_INTENT_CANCELED));
                }
            }
        });
    }

    @Override
    public void remove(int position) {
        jsonServerApi.deleteSample(EmailCache.getEmail() , position).enqueue(new Callback<Sample>() {
            @Override
            public void onResponse(Call<Sample> call, Response<Sample> response) {
                sampleResponse = response.body();
            }

            @Override
            public void onFailure(Call<Sample> call, Throwable t) {

            }
        });
    }

    @Override
    public Sample get(int position) {
        jsonServerApi.getSample(EmailCache.getEmail() , position).enqueue(new Callback<Sample>() {
            @Override
            public void onResponse(Call<Sample> call, Response<Sample> response) {
                sampleResponse = response.body();
            }

            @Override
            public void onFailure(Call<Sample> call, Throwable t) {

            }
        });
        return sampleResponse;
    }

    @Override
    public Sample get(int position , final PendingIntent pendingIntent) {
        jsonServerApi.getSample(EmailCache.getEmail() , position).enqueue(new Callback<Sample>() {
            @Override
            public void onResponse(Call<Sample> call, Response<Sample> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    try {
                        NotificationSender.Download.sendError(pendingIntent , ProcessStates.Failed.STATUS_DOWNLOAD_FAILED);
                    } catch (PendingIntent.CanceledException e) {
                        handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Failed.STATUS_PENDING_INTENT_CANCELED));
                    }
                }
                else {
                    sampleResponse = response.body();
                    try {
                        NotificationSender.Download.sendNotification(pendingIntent , sampleResponse);
                    } catch (PendingIntent.CanceledException e) {
                        handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Failed.STATUS_PENDING_INTENT_CANCELED));
                    }
                }
            }

            @Override
            public void onFailure(Call<Sample> call, Throwable t) {
                try {
                    NotificationSender.Download.sendError(pendingIntent , ProcessStates.STATUS_CONNECTION_FAILED);
                } catch (PendingIntent.CanceledException e) {
                    handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Failed.STATUS_PENDING_INTENT_CANCELED));
                }
            }
        });
        return sampleResponse;
    }

}
