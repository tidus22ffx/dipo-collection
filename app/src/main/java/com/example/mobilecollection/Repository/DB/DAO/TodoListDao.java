package com.example.mobilecollection.Repository.DB.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mobilecollection.Repository.Model.TodoItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface TodoListDao {

    @Query("SELECT * FROM TodoItem")
    Maybe<List<TodoItem>> getAllTodoItem();

    @Query("SELECT * FROM TodoItem WHERE id = :id")
    Single<TodoItem> getPendingDetail(int id);

    @Query("SELECT * FROM TodoItem WHERE todoStatus = :status")
    Maybe<List<TodoItem>> getListTodoItemByStatus(String status);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(TodoItem... items);

    @Update
    Completable update(TodoItem... items);

    @Delete
    Completable delete(TodoItem item);
}
