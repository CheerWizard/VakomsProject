package com.example.jeremy.artgenerator.business_logic.webservices;

import android.os.Handler;

import com.example.jeremy.artgenerator.business_logic.api.IJsonPlaceHolderApi;
import com.example.jeremy.artgenerator.business_logic.data.Todo;
import com.example.jeremy.artgenerator.constants.ProcessStates;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoWebService extends RetrofitWebService implements IRetrofitWebService<Todo> {
    //api
    private IJsonPlaceHolderApi.Todos jsonServerApi;
    //global vars
    private List<Todo> todoListResponse;
    private Todo todoResponse;
    //handler
    private Handler handler;

    public TodoWebService() {
        super();
        init();
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private void init() {
        jsonServerApi = retrofit.create(IJsonPlaceHolderApi.Todos.class);
        todoResponse = new Todo();
        todoListResponse = new ArrayList<>();
    }

    @Override
    public List<Todo> getAll() {
        jsonServerApi.getAllTodos().enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                if (!response.isSuccessful() || response.body() == null) handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Failed.STATUS_DOWNLOAD_FAILED));
                else todoListResponse = response.body();
            }

            @Override
            public void onFailure(Call<List<Todo>> call, Throwable t) {
                handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.STATUS_CONNECTION_FAILED));
            }
        });
        return todoListResponse;
    }

    @Override
    public void removeAll() {
        jsonServerApi.deleteAllTodos().enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                if (!response.isSuccessful() || response.body() == null) handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Failed.STATUS_DELETE_FAILED));
                else todoListResponse = response.body();
            }

            @Override
            public void onFailure(Call<List<Todo>> call, Throwable t) {
                handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.STATUS_CONNECTION_FAILED));
            }
        });
    }

    @Override
    public void update(Todo todo) {
        jsonServerApi.patchTodo(todo.getId() , todo).enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                if (!response.isSuccessful() || response.body() == null) handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Failed.STATUS_UPDATE_FAILED));
                else todoResponse = response.body();
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.STATUS_CONNECTION_FAILED));
            }
        });
    }

    @Override
    public void insert(Todo todo) {
        jsonServerApi.postTodo(todo).enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                if (!response.isSuccessful() || response.body() == null) handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Failed.STATUS_UPLOAD_FAILED));
                else {
                    todoResponse = response.body();
                    handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Successful.STATUS_UPLOAD_SUCCESSFULLY));
                }
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.STATUS_CONNECTION_FAILED));
            }
        });
    }

    @Override
    public void remove(int id) {
        jsonServerApi.deleteTodo(id).enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                if (!response.isSuccessful() || response.body() == null) handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Failed.STATUS_DELETE_FAILED));
                else {
                    todoResponse = response.body();
                    handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.Successful.STATUS_DELETE_SUCCESS));
                }
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                handler.sendMessageAtFrontOfQueue(handler.obtainMessage(ProcessStates.STATUS_CONNECTION_FAILED));
            }
        });
    }
}
