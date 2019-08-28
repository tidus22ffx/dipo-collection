package com.example.mobilecollection.viewmodel;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.mobilecollection.model.ApiService;
import com.example.mobilecollection.model.Login;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {

    ApiService service = new ApiService();

    MutableLiveData<Login> loginData = new MutableLiveData<>();
    MutableLiveData<Boolean> loginError = new MutableLiveData<>();
    MutableLiveData<String> errorMessage = new MutableLiveData<>();
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public MutableLiveData<Login> getLoginData() {
        return loginData;
    }

    public MutableLiveData<Boolean> getLoginError() {
        return loginError;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    private CompositeDisposable disposable = new CompositeDisposable();

    public void login(final String username, final String password) {
        isLoading.setValue(true);
        disposable.add(
            service.getLogin()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableSingleObserver<Login>() {
                @Override
                public void onSuccess(Login value) {
                    boolean valid = checkCredential(username, password, value);
                    if(valid){
                        loginData.setValue(value);
                        loginError.setValue(false);
                        isLoading.setValue(false);
                        errorMessage.setValue(null);
                    } else {
                        loginError.setValue(true);
                        isLoading.setValue(false);
                        errorMessage.setValue("Username atau password salah!");
                    }
                }

                @Override
                public void onError(Throwable e) {
                    loginError.setValue(true);
                    isLoading.setValue(false);
                    errorMessage.setValue("Koneksi gagal!");
                }
            })
        );
    }

    private boolean checkCredential(String username, String password, Login loginData) {
        String userInput = loginData.getUsername();
        String pw = loginData.getPassword();
        if (username.equals(userInput) && password.equals(pw)) {
            return true;
        }
        return false;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
