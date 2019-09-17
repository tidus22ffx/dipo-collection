package com.example.mobilecollection.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.mobilecollection.Repository.DB.AppDatabase;
import com.example.mobilecollection.Repository.Model.TodoItem;
import com.example.mobilecollection.di.DaggerApiComponent;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class PendingDetailsViewModel extends AndroidViewModel {

    @Inject
    AppDatabase db;

    public PendingDetailsViewModel(@NonNull Application application) {
        super(application);
//        db = AppDatabase.getDatabase(application);
        db = DaggerApiComponent.builder().build().appDatabase();
    }

    MutableLiveData<TodoItem> todoDetail = new MutableLiveData<>();
    MutableLiveData<Boolean> loading = new MutableLiveData<>();
    MutableLiveData<Boolean> isError = new MutableLiveData<>();
    MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public MutableLiveData<TodoItem> getTodoDetail() {
        return todoDetail;
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

    CompositeDisposable disposable = new CompositeDisposable();

    public void fetchDetails(int id) {
        disposable.add(
            db.todoListDao()
            .getPendingDetail(id)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableSingleObserver<TodoItem>() {
                @Override
                public void onSuccess(TodoItem todoItem) {
                    todoDetail.setValue(todoItem);
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

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}


