package com.example.mobilecollection.model;

import com.example.mobilecollection.model.Login;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface Api {
    @GET(value = "login")
    Single<Login> login();
}
