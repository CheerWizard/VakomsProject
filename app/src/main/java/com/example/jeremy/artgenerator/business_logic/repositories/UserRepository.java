package com.example.jeremy.artgenerator.business_logic.repositories;

import android.os.Handler;

import com.example.jeremy.artgenerator.business_logic.cache.UserCache;
import com.example.jeremy.artgenerator.business_logic.cache.UserListCache;
import com.example.jeremy.artgenerator.business_logic.dao.UserDAO;
import com.example.jeremy.artgenerator.business_logic.databases.AppDatabaseManager;
import com.example.jeremy.artgenerator.business_logic.webservices.UserWebService;
import com.example.jeremy.artgenerator.business_logic.data.User;
import com.example.jeremy.artgenerator.ui.GlobalPadApplication;
import com.example.jeremy.artgenerator.wrappers.ExecutorWrapper;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserRepository implements IUserRepository {

    //dao
    private UserDAO userDAO;
    //db manager
    private AppDatabaseManager appDatabaseManager;
    //cache
    private UserCache userCache;
    private UserListCache userListCache;
    //thread executor
    private ExecutorWrapper executorWrapper;
    //webservice
    private UserWebService userWebService;
    //global vars
    private MutableLiveData<List<User>> userListMutableLiveData;
    private MutableLiveData<User> userMutableLiveData;
    //for dagger2
//    public UserRepository(AppDatabaseManager appDatabaseManager, UserCache userCache, UserListCache userListCache, ExecutorWrapper executorWrapper, UserWebService userWebService) {
//        this.appDatabaseManager = appDatabaseManager;
//        userDAO = appDatabaseManager.getAppDatabase().userDAO();
//        this.userCache = userCache;
//        this.userListCache = userListCache;
//        this.executorWrapper = executorWrapper;
//        this.userWebService = userWebService;
//    }

    public UserRepository() {
        userDAO = GlobalPadApplication.getInstance().getAppDatabaseManager().getAppDatabase().userDAO();
        userCache = new UserCache();
        userListCache = new UserListCache();
        executorWrapper = new ExecutorWrapper(4);
        userWebService = new UserWebService();
    }

    public void setHandler(Handler handler) {
        userWebService.setHandler(handler);
    }

    @Override
    public LiveData<List<User>> getUsers() {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                final List<User> userList = userWebService.getAll();
                //load to db
                for (User user : userList) {
                    if (user != null) userDAO.insert(user);
                }
                //caching data
                userListCache.setUserListLiveData(userDAO.getUsers());
                //put in live data
                userListMutableLiveData = (MutableLiveData<List<User>>) userListCache.getUserListLiveData();
            }
        });
        return userListMutableLiveData;
    }

    @Override
    public LiveData<User> getUser(final String email , final String password) {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                //get request
                final User user = userWebService.get(email, password);
                //load to db
                if (user != null) userDAO.insert(user);
                //caching data
                userCache.add(userDAO.getUser(email));
                //put in live data
                userMutableLiveData = (MutableLiveData<User>) userCache.get(email);
            }
        });
        return userMutableLiveData;
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
                userMutableLiveData.setValue(user);
                userCache.add(userMutableLiveData);
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
                userMutableLiveData.setValue(user);
                userCache.add(userMutableLiveData);
            }
        });
    }

    @Override
    public void close() {
        //close db connection
        appDatabaseManager.close();
        //clear cache
        userCache.removeAll();
    }
}
