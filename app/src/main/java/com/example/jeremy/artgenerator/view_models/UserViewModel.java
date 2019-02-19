package com.example.jeremy.artgenerator.view_models;

import android.os.Handler;

import com.example.jeremy.artgenerator.IContract;
import com.example.jeremy.artgenerator.business_logic.data.User;
import com.example.jeremy.artgenerator.logic.IUserAuthenticationLogic;
import com.example.jeremy.artgenerator.logic.UserAuthenticationLogic;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel implements IContract.IViewModel {
    //repository
    private IUserAuthenticationLogic userAuthenticationLogic;
    //:TODO for dagger2
//    public UserViewModel(UserAuthenticationLogic userAuthenticationLogic) {
//        this.userAuthenticationLogic = userAuthenticationLogic;
//    }
    public UserViewModel() {
        userAuthenticationLogic = new UserAuthenticationLogic();
    }

    public void setHandler(Handler handler) {
        userAuthenticationLogic.setHandler(handler);
    }

    @Override
    public void onTouchDeleteAllBtn() {
        //empty method
    }

    public void onTouchUpdateProfileBtn(User user) {
        userAuthenticationLogic.update(user);
    }

    public LiveData<User> getUserLiveData(String email) {
        return userAuthenticationLogic.getUser(email);
    }

    public LiveData<User> getUserLiveData() {
        return userAuthenticationLogic.getUser();
    }

    public void onTouchLoginBtn(String email) {
        userAuthenticationLogic.login(email);
    }

    public void onTouchRestoreBtn(String email) {
        userAuthenticationLogic.restorePassword(email);
    }

    public LiveData<List<User>> getUserListLiveData() {
        return userAuthenticationLogic.getUsers();
    }

    public void onTouchRegisterBtn(User user) {
        userAuthenticationLogic.register(user);
    }
    @Override
    public void onClose() {
        userAuthenticationLogic.close();
    }
}
