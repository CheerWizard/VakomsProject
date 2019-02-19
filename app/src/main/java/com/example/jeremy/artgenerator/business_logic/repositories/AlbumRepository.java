package com.example.jeremy.artgenerator.business_logic.repositories;

import android.os.Handler;

import com.example.jeremy.artgenerator.business_logic.cache.AbstractListCache;
import com.example.jeremy.artgenerator.business_logic.cache.AlbumCache;
import com.example.jeremy.artgenerator.business_logic.dao.AlbumDAO;
import com.example.jeremy.artgenerator.business_logic.data.Album;
import com.example.jeremy.artgenerator.business_logic.webservices.AlbumWebService;
import com.example.jeremy.artgenerator.ui.VakomsApplication;
import com.example.jeremy.artgenerator.utils.containers.GarbageContainer;
import com.example.jeremy.artgenerator.utils.resolvers.ObjectResolver;
import com.example.jeremy.artgenerator.wrappers.ExecutorWrapper;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AlbumRepository implements IRepository<Album> {

    //dao
    private AlbumDAO albumDAO;
    //cache
    private AlbumCache albumCache;
    private AbstractListCache<Album> abstractListCache;
    //thread executor
    private ExecutorWrapper executorWrapper;
    //webservice
    private AlbumWebService albumWebService;
    //global vars
    private LiveData<List<Album>> albumListLiveData;
    private LiveData<Album> albumLiveData;
    //:TODO for dagger2
//    public AlbumRepository(AlbumCache albumCache, AbstractListCache<Album> abstractListCache, ExecutorWrapper executorWrapper, AlbumWebService albumWebService, LiveData<List<Album>> albumListLiveData, LiveData<Album> albumLiveData) {
//        this.albumCache = albumCache;
//        this.abstractListCache = abstractListCache;
//        this.executorWrapper = executorWrapper;
//        this.albumWebService = albumWebService;
//        this.albumListLiveData = albumListLiveData;
//        this.albumLiveData = albumLiveData;
//    }
    public AlbumRepository() {
        albumDAO = VakomsApplication.getInstance().getAppDatabaseManager().getAppDatabase().albumDAO();
        albumCache = new AlbumCache();
        abstractListCache = new AbstractListCache<>();
        executorWrapper = new ExecutorWrapper(4);
        albumWebService = new AlbumWebService();
        albumListLiveData = new MutableLiveData<>();
        albumLiveData = new MutableLiveData<>();
    }

    public void setHandler(Handler handler) {
        albumWebService.setHandler(handler);
    }

    @Override
    public LiveData<List<Album>> getAll() {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                if (abstractListCache.getLiveData() == null) {
                    final List<Album> albumList = albumWebService.getAll();
                    //load to db
                    for (Album album : albumList)
                        if (ObjectResolver.isNotNull(album.getTitle() , album.getUserId()))
                            albumDAO.insert(album);
                    //caching data
                    abstractListCache.setLiveData(albumDAO.getAlbums());
                }
                albumListLiveData = abstractListCache.getLiveData();
            }
        });
        return albumListLiveData;
    }

    @Override
    public void insert(final Album album) {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                //insert request
                albumWebService.insert(album);
                //add to db
                albumDAO.insert(album);
                //caching data
                albumCache.add(new MutableLiveData<>(album));
                //add cache to garbage for further cleaning up
                GarbageContainer.add("album_cache" , albumCache);

            }
        });
    }

    @Override
    public void remove(final int id) {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                //update request
                albumWebService.remove(id);
                //update dao
                albumDAO.remove(id);
                //update cache
                albumCache.remove(id);
                //add cache to garbage for further cleaning up
                GarbageContainer.add("album_cache" , albumCache);

            }
        });
    }

    @Override
    public void removeAll() {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                //update request
                albumWebService.removeAll();
                //update dao
                albumDAO.removeAll();
                //update cache
                albumCache.removeAll();
                //add cache to garbage for further cleaning up
                GarbageContainer.add("album_cache" , albumCache);

            }
        });
    }

    @Override
    public void update(final Album album) {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                //update request
                albumWebService.update(album);
                //update dao
                albumDAO.update(album);
                //update cache
                albumCache.remove(album.getId());
                albumCache.add(new MutableLiveData<>(album));
                //add cache to garbage for further cleaning up
                GarbageContainer.add("album_cache" , albumCache);

            }
        });
    }

    @Override
    public void close() {
    }
}
