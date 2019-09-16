package com.example.mobilecollection.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private Context app;

    public ContextModule(Context context){
        this.app = context;
    }

    @Singleton
    @Provides
    public Context provideContext(){
        return app;
    }
}
