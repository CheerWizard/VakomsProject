package com.example.jeremy.artgenerator.view_models;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.jeremy.artgenerator.IContract;
import com.example.jeremy.artgenerator.business_logic.data.Todo;
import com.example.jeremy.artgenerator.business_logic.repositories.IRepository;
import com.example.jeremy.artgenerator.business_logic.repositories.TodoRepository;

import java.util.List;

public class TodoViewModel extends ViewModel implements IContract.IViewModel {
    //repository
    private IRepository<Todo> todoIRepository;
    //todo for dagger2
//    public TodoViewModel(IRepository<Todo> postIRepository) {
//        this.todoIRepository = postIRepository;
//    }
    public TodoViewModel() {
        todoIRepository = new TodoRepository();
    }

    public LiveData<List<Todo>> getTodoListLiveData() {
        return todoIRepository.getAll();
    }

    public void onTouchCreateTodoBtn(Todo todo) {
        todoIRepository.insert(todo);
    }

    public void onTouchDeleteTodoBtn(int id) {
        todoIRepository.remove(id);
    }

    @Override
    public void onClose() {
        todoIRepository.close();
    }

    @Override
    public void setHandler(Handler handler) {
        todoIRepository.setHandler(handler);
    }

    @Override
    public void onTouchDeleteAllBtn() {
        todoIRepository.removeAll();
    }
}
