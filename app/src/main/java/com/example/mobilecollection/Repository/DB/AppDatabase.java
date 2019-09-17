package com.example.mobilecollection.Repository.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mobilecollection.Repository.DB.DAO.PendingTodoListDao;
import com.example.mobilecollection.Repository.DB.DAO.SaveDetailDao;
import com.example.mobilecollection.Repository.Model.TodoItem;

import javax.inject.Inject;

@Database(entities = {TodoItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

//    public static AppDatabase getDatabase(Context context){
//        if(instance == null){
//            instance = Room.databaseBuilder(context, AppDatabase.class, "DipoDb").build();
//        }
//        return instance;
//    }

    public abstract PendingTodoListDao pendingTodoListDao();
    public abstract SaveDetailDao saveDetailDao();
}
