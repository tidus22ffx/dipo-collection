package com.example.mobilecollection.di;

import com.example.mobilecollection.Repository.API.ApiService;
import com.example.mobilecollection.ViewModel.DeliveredViewModel;
import com.example.mobilecollection.ViewModel.PendingViewModel;
import com.example.mobilecollection.ViewModel.ToDoViewModel;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface ApiComponent {

    void inject(ApiService service);

    void inject(DeliveredViewModel viewModel);

    void inject(ToDoViewModel viewModel);
}
