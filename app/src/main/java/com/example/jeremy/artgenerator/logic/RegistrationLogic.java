package com.example.jeremy.artgenerator.logic;

import android.os.Handler;

import com.example.jeremy.artgenerator.business_logic.api.IJsonServerApi;
import com.example.jeremy.artgenerator.constants.ProcessStates;
import com.example.jeremy.artgenerator.datasources.UserDataSource;
import com.example.jeremy.artgenerator.business_logic.data.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public class RegistrationLogic implements IRegistrationLogic {

    private IJsonServerApi jsonServerApi;
    private UserDataSource userDataSource;
    private Handler handler;
    private boolean success;

    public RegistrationLogic(UserDataSource userDataSource , Handler handler) {
        this.userDataSource = userDataSource;
        this.handler = handler;
    }

    @Override
    public void runRegistration(final String name , final String email, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runRetrofit();
                if (isAllValid(name , email , password)) {
                    if (isInServer(email , password)) handler.sendEmptyMessage(ProcessStates.Failed.STATUS_REGISTER_FIELDS_NOT_VALID);
                    else post(name , email , password);
                }
                else handler.sendEmptyMessage(ProcessStates.Failed.STATUS_REGISTER_FIELDS_EMPTY);
            }
        }).start();
    }

    @Override
    public void clear() {
        userDataSource.close();
    }

    private void runRetrofit() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebConstants.JSON_SERVER_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonServerApi = retrofit.create(IJsonServerApi.class);
    }

    private void post(String name , String email , String password) {
        jsonServerApi.postUser(new User(name, password, email)).enqueue(new Callback<User>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) handler.sendMessage(handler.obtainMessage(ProcessStates.Failed.STATUS_REGISTER_POST_RESPONSE_FAILED , response.code()));
                else userDataSource.add(response.body());
            }
            @EverythingIsNonNull
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                handler.sendMessage(handler.obtainMessage(ProcessStates.STATUS_CONNECTION_FAILED , t.getMessage()));
            }
        });
    }

    private void get(String email , String password) {
        jsonServerApi.getUser(email , password).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    success = false;
                    handler.sendMessage(handler.obtainMessage(ProcessStates.Failed.STATUS_REGISTER_GET_RESPONSE_FAILED , response.code()));
                }
                else success = response.body() != null;
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                success = false;
                handler.sendMessage(handler.obtainMessage(ProcessStates.STATUS_CONNECTION_FAILED , t.getMessage()));
            }
        });
    }

    private boolean isAllValid(final String name , final String email, final String password) {
        return ((email != null && email !="") && (password != null && password !="") && (name != null && name !=""));
    }

    private boolean isInServer(String email , String password) {
        get(email , password);
        return success;
    }

}
