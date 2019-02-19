package com.example.jeremy.artgenerator.business_logic.repositories;

import android.os.Handler;

import com.example.jeremy.artgenerator.business_logic.cache.AbstractCache;
import com.example.jeremy.artgenerator.business_logic.cache.AbstractListCache;
import com.example.jeremy.artgenerator.business_logic.cache.ImageCache;
import com.example.jeremy.artgenerator.business_logic.dao.ImageDAO;
import com.example.jeremy.artgenerator.business_logic.data.Image;
import com.example.jeremy.artgenerator.business_logic.webservices.IRetrofitWebService;
import com.example.jeremy.artgenerator.business_logic.webservices.ImageWebService;
import com.example.jeremy.artgenerator.ui.VakomsApplication;
import com.example.jeremy.artgenerator.utils.containers.GarbageContainer;
import com.example.jeremy.artgenerator.utils.resolvers.ObjectResolver;
import com.example.jeremy.artgenerator.wrappers.ExecutorWrapper;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ImageRepository implements IRepository<Image> {
    //dao
    private ImageDAO imageDAO;
    //cache
    private AbstractCache<Integer , Image> imageCache;
    private AbstractListCache<Image> abstractListCache;
    //thread executor
    private ExecutorWrapper executorWrapper;
    //webservice
    private IRetrofitWebService<Image> imageWebService;
    //global vars
    private LiveData<List<Image>> imageListLiveData;
    private LiveData<Image> imageLiveData;
    //:TODO for dagger2

    public ImageRepository() {
        imageDAO = VakomsApplication.getInstance().getAppDatabaseManager().getAppDatabase().imageDAO();
        imageCache = new ImageCache();
        abstractListCache = new AbstractListCache<>();
        executorWrapper = new ExecutorWrapper(4);
        imageWebService = new ImageWebService();
        imageListLiveData = new MutableLiveData<>();
        imageLiveData = new MutableLiveData<>();
    }

    public void setHandler(Handler handler) {
        imageWebService.setHandler(handler);
    }

    @Override
    public LiveData<List<Image>> getAll() {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                if (abstractListCache.getLiveData() == null) {
                    final List<Image> imageList = imageWebService.getAll();
                    //load to db
                    for (Image image : imageList)
                        if (ObjectResolver.isNotNull(image.getThumbnailUrl() , image.getUrl() , image.getTitle()))
                            imageDAO.insert(image);
                    //caching data
                    abstractListCache.setLiveData(imageDAO.getImages());
                }
                imageListLiveData = abstractListCache.getLiveData();
            }
        });
        return imageListLiveData;
    }

    @Override
    public void insert(final Image image) {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                //insert request
                imageWebService.insert(image);
                //add to db
                imageDAO.insert(image);
                //caching data
                imageCache.add(new MutableLiveData<>(image));
                //add cache to garbage for further cleaning up
                GarbageContainer.add("image_cache" , imageCache);

            }
        });
    }

    @Override
    public void remove(final int id) {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                //update request
                imageWebService.remove(id);
                //update dao
                imageDAO.remove(id);
                //update cache
                imageCache.remove(id);
                //add cache to garbage for further cleaning up
                GarbageContainer.add("image_cache" , imageCache);

            }
        });
    }

    @Override
    public void removeAll() {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                //update request
                imageWebService.removeAll();
                //update dao
                imageDAO.removeAll();
                //update cache
                imageCache.removeAll();
                //add cache to garbage for further cleaning up
                GarbageContainer.add("image_cache" , imageCache);

            }
        });
    }

    @Override
    public void update(final Image image) {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                //update request
                imageWebService.update(image);
                //update dao
                imageDAO.update(image);
                //update cache
                imageCache.remove(image.getId());
                imageCache.add(new MutableLiveData<>(image));
                //add cache to garbage for further cleaning up
                GarbageContainer.add("image_cache" , imageCache);

            }
        });
    }

    @Override
    public void close() {
    }
}
