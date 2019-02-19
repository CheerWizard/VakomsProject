package com.example.jeremy.artgenerator.business_logic.repositories;

import android.os.Handler;

import com.example.jeremy.artgenerator.business_logic.cache.AbstractListCache;
import com.example.jeremy.artgenerator.business_logic.cache.UserCache;
import com.example.jeremy.artgenerator.business_logic.dao.UserDAO;
import com.example.jeremy.artgenerator.business_logic.webservices.UserWebService;
import com.example.jeremy.artgenerator.business_logic.data.User;
import com.example.jeremy.artgenerator.constants.ProcessStates;
import com.example.jeremy.artgenerator.ui.VakomsApplication;
import com.example.jeremy.artgenerator.utils.containers.GarbageContainer;
import com.example.jeremy.artgenerator.utils.resolvers.ObjectResolver;
import com.example.jeremy.artgenerator.wrappers.ExecutorWrapper;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserRepository implements IUserRepository {
    //dao
    private UserDAO userDAO;
    //cache
    private UserCache userCache;
    private AbstractListCache<User> abstractListCache;
    //thread executor
    private ExecutorWrapper executorWrapper;
    //webservice
    private UserWebService userWebService;
    //global vars
    protected LiveData<User> userMutableLiveData;
    protected LiveData<List<User>> userListLiveData;
    //handler
    private Handler handler;
    //global vars
    private String password;
    //:TODO for dagger2
//    public UserRepository(AppDatabaseManager appDatabaseManager, UserCache userCache, UserListCache userListCache, ExecutorWrapper executorWrapper, UserWebService userWebService) {
//        this.appDatabaseManager = appDatabaseManager;
//        userDAO = appDatabaseManager.getAppDatabase().userDAO();
//        this.userCache = userCache;
//        this.userListCache = userListCache;
//        this.executorWrapper = executorWrapper;
//        this.userWebService = userWebService;
//    }
    public UserRepository() {
        userDAO = VakomsApplication.getInstance().getAppDatabaseManager().getAppDatabase().userDAO();
        userCache = new UserCache();
        abstractListCache = new AbstractListCache<>();
        executorWrapper = new ExecutorWrapper(4);
        userWebService = new UserWebService();
        userMutableLiveData = new MutableLiveData<>();
        userListLiveData = new MutableLiveData<>();
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
        userWebService.setHandler(handler);
    }

    @Override
    public LiveData<List<User>> getUsers() {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                if (abstractListCache.getLiveData() == null) {
                    final List<User> userList = userWebService.getAll();
                    //caching data
                    abstractListCache.setLiveData(new MutableLiveData<>(userList));
                }
                userListLiveData = abstractListCache.getLiveData();
            }
        });
        return userListLiveData;
    }

    @Override
    public LiveData<User> getUser(final String email) {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                if (!userCache.hasKey(email)) {
                    //get request
                    final User userResponse = userWebService.get(email);
                    //load to db
                    if (ObjectResolver.isNotNull(userResponse) && ObjectResolver.isNotNull(userResponse.getEmail(), userResponse.getPassword())) {
                        userDAO.insert(userResponse);
                        handler.sendEmptyMessage(ProcessStates.Successful.STATUS_LOGIN_SUCCESSFULLY);
                    }
                    //caching data
                    final User user = userDAO.getUser().getValue();
                    if (ObjectResolver.isNotNull(user) && ObjectResolver.isNotNull(user.getEmail())) userCache.add(userDAO.getUser());
                }
                final LiveData<User> userLiveData = userCache.get(email);
                if (userLiveData != null) userMutableLiveData = userLiveData;
                //add cache to garbage for further cleaning up
                GarbageContainer.add("user_cache" , userCache);

            }
        });
        return userMutableLiveData;
    }

    @Override
    public LiveData<User> getUser() {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                userMutableLiveData = userDAO.getUser();
            }
        });
        return userMutableLiveData;
    }

    @Override
    public String getPassword(final String email) {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                //get request
                password = userWebService.get(email).getPassword();
            }
        });
        return password;
    }

    @Override
    public void insert(final User user) {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                //insert request
                userWebService.insert(user);
                //add to db
                userDAO.insert(user);
                //caching data
                userCache.add(new MutableLiveData<>(user));
                //add cache to garbage for further cleaning up
                GarbageContainer.add("user_cache" , userCache);

            }
        });
    }

    @Override
    public void update(final User user) {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                //update request
                userWebService.update(user);
                //update dao
                userDAO.update(user);
                //update cache
                userCache.remove(user.getEmail());
                userCache.add(new MutableLiveData<>(user));
                //add cache to garbage for further cleaning up
                GarbageContainer.add("user_cache" , userCache);

            }
        });
    }

    @Override
    public void close() {
    }
}
