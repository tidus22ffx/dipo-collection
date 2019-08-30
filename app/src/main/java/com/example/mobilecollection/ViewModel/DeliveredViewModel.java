package com.example.mobilecollection.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobilecollection.Repository.API.ApiService;
import com.example.mobilecollection.Repository.Model.TodoItem;

import java.util.ArrayList;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class DeliveredViewModel extends ViewModel {

    ApiService service = new ApiService();

    MutableLiveData<ArrayList<TodoItem>> todoList = new MutableLiveData<>();
    MutableLiveData<Boolean> loading = new MutableLiveData<>();
    MutableLiveData<Boolean> isError = new MutableLiveData<>();
    MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public MutableLiveData<ArrayList<TodoItem>> getTodoList() {
        return todoList;
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

    public void refreshDeliveredList(){
        fetchTodolist();
    }

    private void fetchTodolist(){
        loading.setValue(true);
        disposable.add(
            service.getDeliveredList()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableSingleObserver<ArrayList<TodoItem>>() {
                @Override
                public void onSuccess(ArrayList<TodoItem> value) {
                    todoList.setValue(value);
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
