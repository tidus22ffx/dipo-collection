package com.example.mobilecollection.Repository.API;

import com.example.mobilecollection.Repository.Model.Login;
import com.example.mobilecollection.Repository.Model.TodoItem;
import com.example.mobilecollection.Repository.Model.UserLocation;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @POST("todoList/add")
    @FormUrlEncoded
    Call<UserLocation> postLocation(@Field("longitude") double longitude, @Field("latitude") double latitude);

}
