package com.example.mobilecollection.di;
import android.app.Application;
import androidx.room.Room;
import com.example.mobilecollection.Repository.DB.AppDatabase;
import com.example.mobilecollection.Repository.DB.DAO.PendingTodoListDao;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    private static AppDatabase database;

    public DatabaseModule(Application application) {
        database = Room.databaseBuilder(application, AppDatabase.class, "DipoDb").build();
    }

    @Singleton
    @Provides
    static AppDatabase provideDatabase(){
        return database;
    }

    @Singleton
    @Provides
    PendingTodoListDao providePendingDao(AppDatabase database){
        return database.pendingTodoListDao();
    }
}
