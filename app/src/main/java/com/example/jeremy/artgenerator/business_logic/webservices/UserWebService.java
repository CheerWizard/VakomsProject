package com.example.jeremy.artgenerator.business_logic.webservices;

import android.os.Handler;

import com.example.jeremy.artgenerator.business_logic.api.IJsonServerApi;
import com.example.jeremy.artgenerator.business_logic.data.User;
import com.example.jeremy.artgenerator.utils.deserializers.ConfigDeserializer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserWebService implements IUserWebService {

    //api
    private IJsonServerApi jsonServerApi;
    //global vars
    private List<User> userListResponse;
    private User userResponse;
    //handler
    private Handler handler;

    public UserWebService() {
        initRetrofit();
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private void initRetrofit() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConfigDeserializer.getConfig().getApiUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonServerApi = retrofit.create(IJsonServerApi.class);
    }

    @Override
    public List<User> getAll() {
        jsonServerApi.getAllUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                userListResponse = response.body();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
        return userListResponse;
    }

    @Override
    public void removeAll() {
        jsonServerApi.deleteUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                userListResponse = response.body();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }

    @Override
    public void update(User user) {
        jsonServerApi.patchUser(user.getEmail() , user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                userResponse = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    @Override
    public void insert(User user) {
        jsonServerApi.postUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                userResponse = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    @Override
    public void remove(int position) {
        jsonServerApi.deleteUser(position).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                userResponse = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    @Override
    public void remove(String email) {
        jsonServerApi.deleteUser(email).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                userResponse = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    @Override
    public User get(int position) {
        jsonServerApi.getUser(position).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                userResponse = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        return userResponse;
    }

    @Override
    public User get(String email , String password) {
        jsonServerApi.getUser(email , password).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                userResponse = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        return userResponse;
    }
}
