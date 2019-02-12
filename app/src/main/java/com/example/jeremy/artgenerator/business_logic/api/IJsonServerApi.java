package com.example.jeremy.artgenerator.business_logic.api;

import com.example.jeremy.artgenerator.business_logic.data.Sample;
import com.example.jeremy.artgenerator.business_logic.data.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IJsonServerApi {
    @GET("samples")
    Call<List<Sample>> getAllSamples();
    //get list of samples from db for recent email
    @GET("samples")
    Call<List<Sample>> getSamples(@Query("userEmail") String userEmail);
    //get single sample from db
    @GET("samples")
    Call<Sample> getSample(@Query("userEmail") String userEmail , @Query("id") int id);
    //post single sample to db
    @POST("samples")
    Call<Sample> postSample(@Path("userEmail") String email , @Body Sample sample);
    //update - put sample in db
    @PUT("samples/{userEmail}")
    Call<Sample> putSample(@Path("userEmail") String email , @Body Sample sample);
    //update - put sample data as fields (null ignores)
    @PATCH("samples/{userEmail}")
    Call<Sample> patchSample(@Path("userEmail") String email , @Body Sample sample);
    //delete all samples of recent user from db
    @DELETE("samples/{userEmail}")
    Call<List<Sample>> deleteSamples(@Path("userEmail") String userEmail);
    //delete appropriate sample of recent user from db
    @DELETE("samples/{userEmail}")
    Call<Sample> deleteSample(@Path("userEmail") String userEmail , @Path("id") int id);
    //update - put user in db
    @PUT("users/{email}")
    Call<User> putUser(@Path("email") String email , @Body User user);
    //update - put user data as fields (null ignores)
    @PATCH("users/{email}")
    Call<User> patchUser(@Path("email") String email , @Body User user);
    //get singular user by email and password
    @GET("users")
    Call<User> getUser(@Query("email") String email , @Query("password") String password);
    @GET("users")
    Call<User> getUser(@Query("userId") int userId);
    //post singular user to db
    @POST("users")
    Call<User> postUser(@Body User user);
    @DELETE("users/{userEmail}")
    Call<User> deleteUser(@Path("userEmail") String userEmail);
    @DELETE("users/{userId}")
    Call<User> deleteUser(@Path("userId") int userId);
    @GET("users")
    Call<List<User>> getAllUsers();
    @DELETE("users")
    Call<List<User>> deleteUsers();
}
