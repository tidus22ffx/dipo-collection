package com.example.mobilecollection.Repository.API;

import com.example.mobilecollection.Repository.Model.Login;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface Api {
    @GET(value = "login")
    Single<Login> login();
}
