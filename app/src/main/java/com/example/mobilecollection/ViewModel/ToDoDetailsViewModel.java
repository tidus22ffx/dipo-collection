package com.example.mobilecollection.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobilecollection.Repository.API.ApiService;
import com.example.mobilecollection.Repository.DB.AppDatabase;
import com.example.mobilecollection.Repository.Model.TodoItem;
import com.example.mobilecollection.di.DaggerApiComponent;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ToDoDetailsViewModel extends ViewModel {
    @Inject
    ApiService service;
    private AppDatabase db;

    MutableLiveData<TodoItem> todoItemDetail = new MutableLiveData<>();
    MutableLiveData<TodoItem> savedTodoItem = new MutableLiveData<>();
    MutableLiveData<Boolean> saveLoading = new MutableLiveData<>();
    MutableLiveData<Boolean> saveError = new MutableLiveData<>();
    MutableLiveData<String> saveErrorMessage = new MutableLiveData<>();
    MutableLiveData<Boolean> loading = new MutableLiveData<>();
    MutableLiveData<Boolean> isError = new MutableLiveData<>();
    MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public ToDoDetailsViewModel() {
        this.service = DaggerApiComponent.builder().build().service();
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
    public MutableLiveData<TodoItem> getToDoDetail() {
        return todoItemDetail;
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

    public void saveToDatabase(){
        saveLoading.setValue(true);
        disposable.add(
                db.todoListDao()
                        .insert(todoItemDetail.getValue())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver(){

                            @Override
                            public void onComplete() {
                                saveLoading.setValue(false);
                                savedTodoItem.setValue(todoItemDetail.getValue());
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
