package com.example.mobilecollection.Repository.API;

import com.example.mobilecollection.Repository.Model.Login;
import com.example.mobilecollection.Repository.Model.TodoItem;
import com.example.mobilecollection.di.DaggerApiComponent;

import java.util.ArrayList;
import javax.inject.Inject;
import io.reactivex.Single;

public class ApiService {

    @Inject
    public Api api;

    public ApiService(){
        api = DaggerApiComponent.builder().build().api();
    }

    public Single<Login> getLogin() {
        return api.login();
    }
    public Single<ArrayList<TodoItem>> getDeliveredList() {
        return api.getDeliveredList();
    }
    public Single<ArrayList<TodoItem>> getPendingList() { return api.getPendingList(); }
    public Single<ArrayList<TodoItem>> getToDoList() { return api.getToDoList(); }
    public Single<TodoItem> getToDoDetails(int id) { return api.getTodoDetails(id); }
    public Single<TodoItem> getDeliveredDetails(int id) { return api.getDeliveredDetails(id); }

}
