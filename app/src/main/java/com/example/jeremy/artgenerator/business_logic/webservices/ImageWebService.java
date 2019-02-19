package com.example.jeremy.artgenerator.business_logic.webservices;

import android.os.Handler;

import com.example.jeremy.artgenerator.business_logic.api.IJsonPlaceHolderApi;
import com.example.jeremy.artgenerator.business_logic.data.Image;
import com.example.jeremy.artgenerator.constants.ProcessStates;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageWebService extends RetrofitWebService implements IRetrofitWebService<Image> {
    //api
    private IJsonPlaceHolderApi.Images jsonServerApi;
    //global vars
    private List<Image> imageListResponse;
    private Image imageResponse;
    //handler
    private Handler handler;

    public ImageWebService() {
        super();
        init();
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private void init() {
        jsonServerApi = retrofit.create(IJsonPlaceHolderApi.Images.class);
        imageResponse = new Image();
        imageListResponse = new ArrayList<>();
    }

    @Override
    public List<Image> getAll() {
        jsonServerApi.getAllImages().enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                if (!response.isSuccessful() || response.body() == null) handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Failed.STATUS_DOWNLOAD_FAILED));
                else imageListResponse = response.body();
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
                handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.STATUS_CONNECTION_FAILED));
            }
        });
        return imageListResponse;
    }

    @Override
    public void removeAll() {
        jsonServerApi.deleteAllImages().enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                if (!response.isSuccessful() || response.body() == null) handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Failed.STATUS_DELETE_FAILED));
                else imageListResponse = response.body();
            }

            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
                handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.STATUS_CONNECTION_FAILED));
            }
        });
    }

    @Override
    public void update(Image image) {
        jsonServerApi.patchImage(image.getId() , image).enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {
                if (!response.isSuccessful() || response.body() == null) handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Failed.STATUS_UPDATE_FAILED));
                else imageResponse = response.body();
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {
                handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.STATUS_CONNECTION_FAILED));
            }
        });
    }

    @Override
    public void insert(Image image) {
        jsonServerApi.postImage(image).enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {
                if (!response.isSuccessful() || response.body() == null) handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Failed.STATUS_UPLOAD_FAILED));
                else {
                    imageResponse = response.body();
                    handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Successful.STATUS_UPLOAD_SUCCESSFULLY));
                }
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {
                handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.STATUS_CONNECTION_FAILED));
            }
        });
    }

    @Override
    public void remove(int id) {
        jsonServerApi.deleteImage(id).enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {
                if (!response.isSuccessful() || response.body() == null) handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Failed.STATUS_DELETE_FAILED));
                else {
                    imageResponse = response.body();
                    handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Successful.STATUS_DELETE_SUCCESS));
                }
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {
                handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.STATUS_CONNECTION_FAILED));
            }
        });
    }
}
