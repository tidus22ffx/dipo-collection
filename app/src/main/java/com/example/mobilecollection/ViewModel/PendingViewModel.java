package com.example.mobilecollection.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.mobilecollection.Repository.API.ApiService;
import com.example.mobilecollection.Repository.DB.AppDatabase;
import com.example.mobilecollection.Repository.Model.TodoItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;

public class PendingViewModel extends AndroidViewModel {

    ApiService service = new ApiService();
    private AppDatabase db;

    public PendingViewModel(@NonNull Application application) {
        super(application);
        db = AppDatabase.getDatabase(application);
    }

    MutableLiveData<ArrayList<TodoItem>> pendingList = new MutableLiveData<>();
    MutableLiveData<Boolean> loading = new MutableLiveData<>();
    MutableLiveData<Boolean> isError = new MutableLiveData<>();
    MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public MutableLiveData<ArrayList<TodoItem>> getPendingList() {
        return pendingList;
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

    public void refreshPendingList(){
        fetchPendinglist();
    }

    private void fetchPendinglist(){
        loading.setValue(true);
        disposable.add(
            db.pendingTodoListDao()
            .getPendingList()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableMaybeObserver<List<TodoItem>>() {

                @Override
                public void onSuccess(List<TodoItem> todoItems) {
                    loading.setValue(false);
                    ArrayList list = new ArrayList<TodoItem>();
                    list.addAll(todoItems);
                    pendingList.setValue(list);
                }

                @Override
                public void onError(Throwable e) {
                    loading.setValue(false);
                    isError.setValue(true);
                    errorMessage.setValue(e.toString());
                }

                @Override
                public void onComplete() {
                    loading.setValue(false);
                    isError.setValue(false);
                }
            })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
