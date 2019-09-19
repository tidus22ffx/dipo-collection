package com.example.mobilecollection.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.mobilecollection.Repository.DB.AppDatabase;
import com.example.mobilecollection.Repository.DB.DAO.TodoListDao;
import com.example.mobilecollection.Repository.Model.TodoItem;
import com.example.mobilecollection.di.DaggerApiComponent;
import com.example.mobilecollection.di.DatabaseModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;

public class PendingViewModel extends AndroidViewModel {

    @Inject
    TodoListDao dao;

    public PendingViewModel(@NonNull Application application) {
        super(application);
        dao = DaggerApiComponent.builder().databaseModule(new DatabaseModule(application))
                .build().dao();
//        db = AppDatabase.getDatabase(application);
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
            dao
            .getListTodoItemByStatus("Pending")
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableMaybeObserver<List<TodoItem>>() {

                @Override
                public void onSuccess(List<TodoItem> todoItems) {
                    loading.setValue(false);
                    ArrayList list = new ArrayList<TodoItem>();
                    list.addAll(todoItems);
                    isError.setValue(false);
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
