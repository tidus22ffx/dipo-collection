package com.example.mobilecollection.ViewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobilecollection.Repository.API.ApiService;
import com.example.mobilecollection.Repository.Model.TodoItem;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ToDoViewModel extends ViewModel {
    ApiService service = new ApiService();

    MutableLiveData<ArrayList<TodoItem>> todoList = new MutableLiveData<>();
    MutableLiveData<TodoItem> todoItemDetail = new MutableLiveData<>();
    MutableLiveData<Boolean> loading = new MutableLiveData<>();
    MutableLiveData<Boolean> isError = new MutableLiveData<>();
    MutableLiveData<String> errorMessage = new MutableLiveData<>();
    ArrayList<TodoItem> todoItems;

    public MutableLiveData<ArrayList<TodoItem>> getTodoList() {
        todoList.setValue(todoItems);
        return todoList;
    }

    public MutableLiveData<ArrayList<TodoItem>> filterList(String param) {
        ArrayList<TodoItem> todoItemsFiltered = new ArrayList<>();

        Log.d("ITEMS", "TODOITEMS: "+todoItems);
        if (param.isEmpty() || param.equalsIgnoreCase("")) {
            todoList.setValue(todoItems);
            return todoList;
        } else {
            String filterPattern = param.toLowerCase().trim();

            for (TodoItem item : todoItems) {
                if (item.getContractNo().toLowerCase().contains(filterPattern)
                        || item.getPlat().toLowerCase().contains(filterPattern)){
                    todoItemsFiltered.add(item);
                }
            }
            todoList.setValue(todoItemsFiltered);
            return todoList;
        }
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<Boolean> getIsError() {
        return isError;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    CompositeDisposable disposable = new CompositeDisposable();

    public void refreshToDoList(){
        fetchTodolist();
    }

    private void fetchTodolist(){
        loading.setValue(true);
        disposable.add(
                service.getToDoList()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<ArrayList<TodoItem>>() {
                            @Override
                            public void onSuccess(ArrayList<TodoItem> value) {
                                Log.d("Value", "VAL: "+value);
                                todoItems = new ArrayList<>(value);
                                todoList.setValue(value);
                                loading.setValue(false);
                                isError.setValue(false);
                                errorMessage.setValue(null);
                            }

                            @Override
                            public void onError(Throwable e) {
                                loading.setValue(false);
                                isError.setValue(true);
                                errorMessage.setValue(e.toString());
                            }
                        })
        );
    }

    public void fetchDetail(int id) {
        loading.setValue(true);
        disposable.add(
                service.getToDoDetails(id)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<TodoItem>() {
                            @Override
                            public void onSuccess(TodoItem value) {
                                todoItemDetail.setValue(value);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                loading.setValue(false);
                                isError.setValue(true);
                                errorMessage.setValue(e.toString());
                            }
                        })
        );
    }

    public MutableLiveData<TodoItem> getToDoDetail() {
        return todoItemDetail;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
