package com.example.jeremy.artgenerator.business_logic.webservices;

import android.os.Handler;

import com.example.jeremy.artgenerator.business_logic.api.IJsonPlaceHolderApi;
import com.example.jeremy.artgenerator.business_logic.data.User;
import com.example.jeremy.artgenerator.constants.ProcessStates;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserWebService extends RetrofitWebService implements IUserWebService {
    //api
    private IJsonPlaceHolderApi.Users jsonServerApi;
    //global vars
    private List<User> userListResponse;
    private User userResponse;
    //handler
    private Handler handler;

    public UserWebService() {
        super();
        init();
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private void init() {
        jsonServerApi = retrofit.create(IJsonPlaceHolderApi.Users.class);
        userResponse = new User();
        userListResponse = new ArrayList<>();
    }

    @Override
    public List<User> getAll() {
        jsonServerApi.getAllUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful() || response.body() == null) handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Failed.STATUS_DOWNLOAD_FAILED));
                else userListResponse = response.body();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.STATUS_CONNECTION_FAILED));
            }
        });
        return userListResponse;
    }

    @Override
    public void removeAll() {
        jsonServerApi.deleteUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful() || response.body() == null) handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Failed.STATUS_DELETE_FAILED));
                else userListResponse = response.body();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.STATUS_CONNECTION_FAILED));
            }
        });
    }

    @Override
    public void update(User user) {
        jsonServerApi.patchUser(user.getId() , user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful() || response.body() == null) handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Failed.STATUS_UPDATE_FAILED));
                else userResponse = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.STATUS_CONNECTION_FAILED));
            }
        });
    }

    @Override
    public void insert(User user) {
        jsonServerApi.postUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful() || response.body() == null) handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Failed.STATUS_REGISTER_POST_RESPONSE_FAILED));
                else {
                    userResponse = response.body();
                    handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Successful.STATUS_REGISTER_SUCCESSFULLY));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.STATUS_CONNECTION_FAILED));
            }
        });
    }

    @Override
    public void remove(int id) {
        jsonServerApi.deleteUser(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful() || response.body() == null) handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Failed.STATUS_DELETE_FAILED));
                else userResponse = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.STATUS_CONNECTION_FAILED));
            }
        });
    }

    public void remove(String email) {
        jsonServerApi.deleteUser(email).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful() || response.body() == null) handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Failed.STATUS_DELETE_FAILED));
                else userResponse = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.STATUS_CONNECTION_FAILED));
            }
        });
    }

    public User get(String email) {
        jsonServerApi.getUser(email).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful() || response.body() == null) handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Failed.STATUS_LOGIN_GET_RESPONSE_FAILED));
                else userResponse = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.STATUS_CONNECTION_FAILED));
            }
        });
        return userResponse;
    }
}
