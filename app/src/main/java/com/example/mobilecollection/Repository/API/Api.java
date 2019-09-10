package com.example.mobilecollection.Repository.API;

import com.example.mobilecollection.Repository.Model.Login;
import com.example.mobilecollection.Repository.Model.TodoItem;

import java.util.ArrayList;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {
    @GET(value = "login")
    Single<Login> login();

    @GET(value = "todolist")
    Single<ArrayList<TodoItem>> getDeliveredList();

    @GET(value = "todolist")
    Single<ArrayList<TodoItem>> getPendingList();

    @GET(value = "todolist")
    Single<ArrayList<TodoItem>> getToDoList();

    @GET(value = "todolist/{idDetail}")
    Single<TodoItem> getTodoDetails(@Path("idDetail") int id);

    @GET(value = "todolist/{idDetail}")
    Single<TodoItem> getDeliveredDetails(@Path("idDetail") int id);

}
