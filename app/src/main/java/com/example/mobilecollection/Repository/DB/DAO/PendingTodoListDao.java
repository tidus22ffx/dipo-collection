package com.example.mobilecollection.Repository.DB.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mobilecollection.Repository.Model.TodoItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;

@Dao
public interface PendingTodoListDao {

    @Query("SELECT * FROM TodoItem")
    Maybe<List<TodoItem>> getPendingList();

    @Query("SELECT * FROM TodoItem WHERE id = :id")
    Maybe<TodoItem> getPendingDetail(int id);

    @Insert
    Completable insert(TodoItem... items);

    @Update
    Completable update(TodoItem... items);

    @Delete
    Completable delete(TodoItem item);
}
