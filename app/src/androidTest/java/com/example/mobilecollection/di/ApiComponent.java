package com.example.mobilecollection.di;

import com.example.mobilecollection.Repository.API.ApiService;

import dagger.Component;

@Component(modules = ApiModule.class)
public interface ApiComponent {

    void inject(ApiService service);
}
