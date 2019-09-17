package com.example.mobilecollection.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.mobilecollection.Repository.API.ApiService;
import com.example.mobilecollection.Repository.DB.AppDatabase;
import com.example.mobilecollection.Repository.DB.DAO.TodoListDao;
import com.example.mobilecollection.Repository.Model.TodoItem;
import com.example.mobilecollection.di.ApiComponent;
import com.example.mobilecollection.di.DaggerApiComponent;
import com.example.mobilecollection.di.DatabaseModule;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class DeliveredDetailsViewModel extends AndroidViewModel {

    @Inject
    ApiService service;

    @Inject
    TodoListDao dao;

    MutableLiveData<TodoItem> todoDetail = new MutableLiveData<>();
    MutableLiveData<Boolean> loading = new MutableLiveData<>();
    MutableLiveData<Boolean> isError = new MutableLiveData<>();
    MutableLiveData<String> errorMessage = new MutableLiveData<>();
    MutableLiveData<TodoItem> savedTodoItem = new MutableLiveData<>();
    MutableLiveData<Boolean> saveLoading = new MutableLiveData<>();
    MutableLiveData<Boolean> saveError = new MutableLiveData<>();
    MutableLiveData<String> saveErrorMessage = new MutableLiveData<>();

    public DeliveredDetailsViewModel(@NonNull Application application) {
        super(application);
//        db = AppDatabase.getDatabase(application);
        ApiComponent component = DaggerApiComponent.builder()
                .databaseModule(new DatabaseModule(application)).build();
        this.service = component.service();
        dao = component.dao();
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

    public MutableLiveData<TodoItem> getSavedTodoItem() {
        return savedTodoItem;
    }

    public MutableLiveData<Boolean> getSaveLoading() {
        return saveLoading;
    }

    public MutableLiveData<Boolean> getSaveError() {
        return saveError;
    }

    public MutableLiveData<String> getSaveErrorMessage() {
        return saveErrorMessage;
    }

    CompositeDisposable disposable = new CompositeDisposable();

    public MutableLiveData<TodoItem> getTodoDetail() {
        return todoDetail;
    }

    public void fetchDetails(int id){
        loading.setValue(true);
        disposable.add(
            service.getDeliveredDetails(id)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableSingleObserver<TodoItem>(){

                @Override
                public void onSuccess(TodoItem value) {
                    todoDetail.setValue(value);
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

    public void saveToDatabase(){
        saveLoading.setValue(true);

        TodoItem data = todoDetail.getValue();
        data.setTodoStatus("Pending");

        disposable.add(
            dao
            .insert(data)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableCompletableObserver(){

                @Override
                public void onComplete() {
                    saveLoading.setValue(false);
                    savedTodoItem.setValue(todoDetail.getValue());
                }

                @Override
                public void onError(Throwable e) {
                    saveLoading.setValue(false);
                    saveError.setValue(true);
                    saveErrorMessage.setValue(e.toString());
                }
            })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
