package com.example.mobilecollection.di;

import com.example.mobilecollection.Repository.API.Api;
import com.example.mobilecollection.Repository.API.ApiService;
import com.example.mobilecollection.Repository.DB.AppDatabase;
import com.example.mobilecollection.Repository.DB.DAO.TodoListDao;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiModule.class, DatabaseModule.class, ContextModule.class})
public interface ApiComponent {

//    void inject(ApiService service);


    //    void inject(DeliveredViewModel viewModel);
//
//    void inject(ToDoViewModel viewModel);
//
//    void inject(PendingViewModel viewModel);
//    void inject(DeliveredDetailsViewModel viewModel);

//    Context context();
    ApiService service();
    Api api();
    AppDatabase appDatabase();
    TodoListDao dao();
}
