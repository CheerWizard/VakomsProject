package com.example.jeremy.artgenerator.business_logic.repositories;

import android.os.Handler;

import com.example.jeremy.artgenerator.business_logic.cache.AbstractCache;
import com.example.jeremy.artgenerator.business_logic.cache.AbstractListCache;
import com.example.jeremy.artgenerator.business_logic.cache.PostCache;
import com.example.jeremy.artgenerator.business_logic.dao.PostDAO;
import com.example.jeremy.artgenerator.business_logic.data.Post;
import com.example.jeremy.artgenerator.business_logic.webservices.IRetrofitWebService;
import com.example.jeremy.artgenerator.business_logic.webservices.PostWebService;
import com.example.jeremy.artgenerator.ui.VakomsApplication;
import com.example.jeremy.artgenerator.utils.containers.GarbageContainer;
import com.example.jeremy.artgenerator.utils.resolvers.ObjectResolver;
import com.example.jeremy.artgenerator.wrappers.ExecutorWrapper;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class PostRepository implements IRepository<Post> {
    //dao
    private PostDAO postDAO;
    //cache
    private AbstractCache<Integer , Post> postCache;
    private AbstractListCache<Post> abstractListCache;
    //thread executor
    private ExecutorWrapper executorWrapper;
    //webservice
    private IRetrofitWebService<Post> postWebService;
    //global vars
    private LiveData<List<Post>> postListLiveData;
    private LiveData<Post> postLiveData;
    //:TODO for dagger2

    public PostRepository() {
        postDAO = VakomsApplication.getInstance().getAppDatabaseManager().getAppDatabase().postDAO();
        postCache = new PostCache();
        abstractListCache = new AbstractListCache<>();
        executorWrapper = new ExecutorWrapper(4);
        postWebService = new PostWebService();
        postListLiveData = new MutableLiveData<>();
        postLiveData = new MutableLiveData<>();
    }

    public void setHandler(Handler handler) {
        postWebService.setHandler(handler);
    }

    @Override
    public LiveData<List<Post>> getAll() {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                if (abstractListCache.getLiveData() == null) {
                    final List<Post> imageList = postWebService.getAll();
                    //load to db
                    for (Post post : imageList)
                        if (ObjectResolver.isNotNull(post.getBody() , post.getTitle()))
                            postDAO.insert(post);
                    //caching data
                    abstractListCache.setLiveData(postDAO.getPosts());
                }
                postListLiveData = abstractListCache.getLiveData();
            }
        });
        return postListLiveData;
    }

    @Override
    public void insert(final Post post) {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                //insert request
                postWebService.insert(post);
                //add to db
                postDAO.insert(post);
                //caching data
                postCache.add(new MutableLiveData<>(post));
                //add cache to garbage for further cleaning up
                GarbageContainer.add("post_cache" , postCache);
            }
        });
    }

    @Override
    public void remove(final int id) {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                //update request
                postWebService.remove(id);
                //update dao
                postDAO.remove(id);
                //update cache
                postCache.remove(id);
                //add cache to garbage for further cleaning up
                GarbageContainer.add("post_cache" , postCache);

            }
        });
    }

    @Override
    public void removeAll() {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                //update request
                postWebService.removeAll();
                //update dao
                postDAO.removeAll();
                //update cache
                postCache.removeAll();
                //add cache to garbage for further cleaning up
                GarbageContainer.add("post_cache" , postCache);

            }
        });
    }

    @Override
    public void update(final Post post) {
        executorWrapper.execute(new Runnable() {
            @Override
            public void run() {
                //update request
                postWebService.update(post);
                //update dao
                postDAO.update(post);
                //update cache
                postCache.remove(post.getId());
                postCache.add(new MutableLiveData<>(post));
                //add cache to garbage for further cleaning up
                GarbageContainer.add("post_cache" , postCache);

            }
        });
    }

    @Override
    public void close() {
    }
}
