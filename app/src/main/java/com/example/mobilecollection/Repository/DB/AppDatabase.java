package com.example.mobilecollection.Repository.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mobilecollection.Repository.DB.DAO.SaveDetailDao;
import com.example.mobilecollection.Repository.DB.DAO.TodoListDao;
import com.example.mobilecollection.Repository.Model.TodoItem;

@Database(entities = {TodoItem.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

//    public static AppDatabase getDatabase(Context context){
//        if(instance == null){
//            instance = Room.databaseBuilder(context, AppDatabase.class, "DipoDb").build();
//        }
//        return instance;
//    }

    public abstract SaveDetailDao saveDetailDao();
    public abstract TodoListDao todoListDao();

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE `TodoItem` "
                    + "ADD COLUMN todoStatus VARCHAR(20)");
            database.execSQL("UPDATE `TodoItem` "
                    + "SET todoStatus = `Pending`");
        }
    };
}
