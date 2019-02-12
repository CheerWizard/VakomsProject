package com.example.jeremy.artgenerator.view_models;

import com.example.jeremy.artgenerator.IContract;
import com.example.jeremy.artgenerator.business_logic.data.User;
import com.example.jeremy.artgenerator.business_logic.repositories.UserRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel implements IContract.IViewModel {

    //repository
    private UserRepository userRepository;

    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void onTouchUpdateProfileBtn(User user) {
        userRepository.update(user);
    }

    public LiveData<User> getUserLiveData(String email , String password) {
        return userRepository.getUser(email, password);
    }

    public LiveData<List<User>> getUserListLiveData() {
        return userRepository.getUsers();
    }

    public void onTouchRegisterBtn(User user) {
        userRepository.insert(user);
    }
    @Override
    public void onClose() {
        userRepository.close();
    }
}
