package com.example.mobilecollection.utilities;


import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.example.mobilecollection.Repository.API.ApiService;
import com.example.mobilecollection.Repository.Model.UserLocation;
import com.example.mobilecollection.di.DaggerApiComponent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class GPSTrackerService {

    int initialDelay;
    int period;
    TimeUnit unit;
    Context context;
    CompositeDisposable disposable = new CompositeDisposable();
    static ScheduledExecutorService scheduler;

    @Inject
    ApiService service;

    @Inject
    public GPSTrackerService(int initialDelay, int period, TimeUnit unit, Context context) {
        this.initialDelay = initialDelay;
        this.period = period;
        this.unit = unit;
        this.context = context;
        service = DaggerApiComponent.builder().build().service();
    }

    public void start(){
        Log.d("GPSTracking", "Starting Service...");
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> getLocation(),initialDelay, period, unit);
        Log.d("GPSTracking", "Service started.");
    }

    public void stop(){
            Log.d("GPSTracking", "Stopping service...");
            scheduler.shutdown();
            if(scheduler.isShutdown()){
                Log.d("GPSTracking", "Service stopped.");
            }
    }

    private void getLocation(){
        double longitude;
        double latitude;
        Location location = Utilities.getCurrentLocation(context);
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        Log.e("timer", "Longitude: " + longitude + ", Latitude: " + latitude);
        sendLocation(longitude, latitude);
//        Toast.makeText(this, "Longitude: " + longitude + ", Latitude: " + latitude, Toast.LENGTH_LONG ).show();
//        disposable.add(
//            service.postUserLocation(longitude, latitude)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.from(scheduler))
//                .subscribeWith(new DisposableCompletableObserver() {
//
//                    @Override
//                    public void onComplete() {
//                        Log.d("Location Track", "Data sent.");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("Location Track", "Fail sending data error: \n" + e.toString() + "\n");
//                    }
//                })
//        );

    }

    private void sendLocation(double longitude, double latitude){
        service.postUserLocation(longitude, latitude).enqueue(new Callback<UserLocation>() {
            @Override
            public void onResponse(Call<UserLocation> call, Response<UserLocation> response) {
                Log.i("Location Track", "Data sent");
            }

            @Override
            public void onFailure(Call<UserLocation> call, Throwable e) {
                Log.i("Location Track", "Fail sending data error: \n" + e.toString() + "\n");
            }
        });
    }

}
