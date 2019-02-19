package com.example.jeremy.artgenerator.logic;

import android.os.Handler;

import com.example.jeremy.artgenerator.business_logic.data.User;
import com.example.jeremy.artgenerator.business_logic.repositories.IUserRepository;
import com.example.jeremy.artgenerator.business_logic.repositories.UserRepository;
import com.example.jeremy.artgenerator.constants.ProcessStates;
import com.example.jeremy.artgenerator.utils.containers.PasswordContainer;
import com.example.jeremy.artgenerator.utils.resolvers.ObjectResolver;

import java.util.List;

import androidx.lifecycle.LiveData;

public class UserAuthenticationLogic extends UserRepository implements IUserAuthenticationLogic {
    //repository
    private IUserRepository userRepository;
    //handler
    private Handler handler;
    //:TODO for dagger2
//    public UserAuthenticationLogic(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
    public UserAuthenticationLogic() {
        userRepository = new UserRepository();
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
        userRepository.setHandler(handler);
    }

    @Override
    public void login(String email) {
        if (ObjectResolver.isNotEmpty(email)) userMutableLiveData = userRepository.getUser(email);
        else handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Failed.STATUS_LOGIN_FIELDS_EMPTY));
    }

    @Override
    public void register(User user) {
        if (ObjectResolver.isNotEmpty(user.getLogin() , user.getEmail() , user.getPassword())) {
            final User userResponse = userRepository.getUser(user.getEmail()).getValue();
            if (!ObjectResolver.isNotNull(userResponse)) userRepository.insert(user);
            else handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Failed.STATUS_REGISTER_FIELDS_NOT_VALID));
        }
        else handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Failed.STATUS_REGISTER_FIELDS_EMPTY));
    }

    @Override
    public void restorePassword(String email) {
        if (ObjectResolver.isNotEmpty(email)) {
            final String password = userRepository.getPassword(email);
            if (ObjectResolver.isNotNull(password)) {
                PasswordContainer.setPassword(password);
                handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Successful.STATUS_RESTORE_PASSWORD_SUCCESS));
            }
            else handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Failed.STATUS_RESTORE_PASSWORD_FAILED));
        }
        else handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Failed.STATUS_RESTORE_PASSWORD_EMPTY_FIELD));
    }

    @Override
    public LiveData<List<User>> getUsers() {
        return userListLiveData;
    }

    @Override
    public LiveData<User> getUser() {
        return userMutableLiveData;
    }

    @Override
    public void update(User user) {
        userRepository.update(user);
    }

    @Override
    public void close() {
        userRepository.close();
    }
}
