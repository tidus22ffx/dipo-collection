package com.example.mobilecollection.Repository.DB.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mobilecollection.Repository.Model.TodoItem;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface SaveDetailDao {

    @Query("SELECT * FROM TodoItem WHERE id = :id")
    Single<TodoItem> getSaveDetail(int id);

    @Insert
    Completable insert(TodoItem... items);

    @Update
    Completable update(TodoItem... items);

    @Delete
    Completable delete(TodoItem item);
}
