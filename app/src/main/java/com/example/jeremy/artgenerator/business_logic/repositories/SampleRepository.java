package com.example.jeremy.artgenerator.business_logic.repositories;

import android.app.PendingIntent;
import android.os.Handler;

import com.example.jeremy.artgenerator.business_logic.cache.SampleCache;
import com.example.jeremy.artgenerator.business_logic.cache.SampleListCache;
import com.example.jeremy.artgenerator.business_logic.dao.SampleDAO;
import com.example.jeremy.artgenerator.business_logic.databases.AppDatabaseManager;
import com.example.jeremy.artgenerator.business_logic.webservices.SampleWebService;
import com.example.jeremy.artgenerator.business_logic.data.Sample;
import com.example.jeremy.artgenerator.ui.GlobalPadApplication;
import com.example.jeremy.artgenerator.wrappers.ExecutorWrapper;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SampleRepository implements ISampleRepository {

    //dao
    private SampleDAO sampleDAO;
    //db manager
    private AppDatabaseManager appDatabaseManager;
    //cache
    private SampleCache sampleCache;
    private SampleListCache sampleListCache;
    //thread executor
    private ExecutorWrapper executorWrapper;
    //webservice
    private SampleWebService sampleWebService;
    //global vars
    private MutableLiveData<List<Sample>> sampleListLiveData;
    private MutableLiveData<Sample> sampleLiveData;

    //for dagger2
//    public SampleRepository(AppDatabaseManager appDatabaseManager, SampleCache sampleCache, SampleListCache sampleListCache, ExecutorWrapper executorWrapper, SampleWebService sampleWebService) {
//        this.appDatabaseManager = appDatabaseManager;
//        sampleDAO = appDatabaseManager.getAppDatabase().sampleDAO();
//        this.sampleCache = sampleCache;
//        this.sampleListCache = sampleListCache;
//        this.executorWrapper = executorWrapper;
//        this.sampleWebService = sampleWebService;
//    }

    public void setHandler(Handler handler) {
        sampleWebService.setHandler(handler);
    }

    public void setPendingIntent(PendingIntent pendingIntent) {
        sampleWebService.setPendingIntent(pendingIntent);
    }

    public SampleRepository() {
        sampleDAO = GlobalPadApplication.getInstance().getAppDatabaseManager().getAppDatabase().sampleDAO();
        sampleCache = new SampleCache();
        sampleListCache = new SampleListCache();
        executorWrapper = new ExecutorWrapper(6);
        sampleWebService = new SampleWebService();
    }

    @Override
    public void removeAll() {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                //delete request
                sampleWebService.removeAll();
                //clear table in db
                sampleDAO.removeAll();
                //clear cache
                sampleCache.removeAll();
            }
        });
    }

    @Override
    public LiveData<List<Sample>> getAllSamples() {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                //get request
                final List<Sample> sampleList = sampleWebService.getAll();
                //load to db
                for (Sample sample : sampleList) {
                    if (sample != null) sampleDAO.insert(sample);
                }
                //caching data
                sampleListCache.setUserListLiveData(sampleDAO.getSamples());
                //put in live data
                sampleListLiveData = (MutableLiveData<List<Sample>>) sampleListCache.getSampleListLiveData();
            }
        });
        return sampleListLiveData;
    }

    @Override
    public void remove(final int id) {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                //delete request
                sampleWebService.remove(id);
                //delete from db
                sampleDAO.remove(id);
                //caching data
                /*need condition , as if we will just try to remove value for appropriate key
                that might not exist , method should throw OutOfBoundsException*/
                if (sampleCache.hasKey(id)) sampleCache.remove(id);
            }
        });
    }

    @Override
    public LiveData<Sample> getSample(final int id) {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                //get request
                final Sample sample = sampleWebService.get(id);
                //load to db
                if (sample != null) sampleDAO.insert(sample);
                //caching data
                sampleCache.add(sampleDAO.getSample(id));
                //put in live data
                sampleLiveData = (MutableLiveData<Sample>) sampleCache.get(id);
            }
        });
        return sampleLiveData;
    }

    @Override
    public void insert(final Sample sample) {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                //insert request
                sampleWebService.insert(sample);
                //add to db
                sampleDAO.insert(sample);
                //caching data
                sampleLiveData.setValue(sample);
                sampleCache.add(sampleLiveData);
            }
        });
    }

    @Override
    public void update(final Sample sample) {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                //update request
                sampleWebService.update(sample);
                //update dao
                sampleDAO.update(sample);
                //update cache
                sampleCache.remove(sample.getId());
                sampleLiveData.setValue(sample);
                sampleCache.add(sampleLiveData);
            }
        });
    }

    @Override
    public void close() {
        //close db connection
        appDatabaseManager.close();
        //clear cache
        sampleCache.removeAll();
    }
}
