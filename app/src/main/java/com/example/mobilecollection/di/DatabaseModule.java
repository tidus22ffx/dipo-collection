package com.example.mobilecollection.di;
import android.app.Application;
import androidx.room.Room;
import com.example.mobilecollection.Repository.DB.AppDatabase;
import com.example.mobilecollection.Repository.DB.DAO.TodoListDao;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    private static AppDatabase database;

    public DatabaseModule(Application application) {
        database = Room.databaseBuilder(application, AppDatabase.class, "DipoDb")
                .addMigrations(AppDatabase.MIGRATION_1_2)
                .build();
    }

    @Singleton
    @Provides
    static AppDatabase provideDatabase(){
        return database;
    }

    @Singleton
    @Provides
    static TodoListDao providePendingDao(AppDatabase database){
        return database.todoListDao();
    }
}
