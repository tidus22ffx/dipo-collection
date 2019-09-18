package com.example.mobilecollection.ViewModel;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.mobilecollection.Repository.API.ApiService;
import com.example.mobilecollection.Repository.DB.AppDatabase;
import com.example.mobilecollection.Repository.DB.AppDatabase_Impl;
import com.example.mobilecollection.Repository.DB.DAO.TodoListDao;
import com.example.mobilecollection.Repository.Model.TodoItem;
import com.example.mobilecollection.ViewModel.PendingViewModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

public class PendingViewModelTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private TodoListDao dao = Mockito.mock(TodoListDao.class);
    private Application app =  Mockito.mock(Application.class);

    @InjectMocks
    public PendingViewModel pendingViewModel = new PendingViewModel(app);

    private Maybe<List<TodoItem>> testMaybe = null;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getPendingListSuccess(){
        TodoItem item = new TodoItem(1, "23678219ZDSH", "Zaki", "2018-01-01", "B1234ZX", "Pending");
        List<TodoItem> itemList = new ArrayList<>();
        itemList.add(item);
        testMaybe = Maybe.just(itemList);
        when(dao.getListTodoItemByStatus("Pending")).thenReturn(testMaybe);
        pendingViewModel.refreshPendingList();

        Assert.assertEquals(1, pendingViewModel.getPendingList().getValue().size());
        Assert.assertEquals(false, pendingViewModel.getLoading().getValue());
        Assert.assertEquals(false, pendingViewModel.getIsError().getValue());
        Assert.assertEquals(null, pendingViewModel.getErrorMessage().getValue());
    }

    @Test
    public void getPendingListFail(){
        testMaybe = Maybe.error(new Throwable());
        when(dao.getListTodoItemByStatus("Pending")).thenReturn(testMaybe);
        pendingViewModel.refreshPendingList();

        Assert.assertEquals(true, pendingViewModel.getIsError().getValue());
        Assert.assertEquals(false, pendingViewModel.getLoading().getValue());
        Assert.assertNotNull(pendingViewModel.getErrorMessage().getValue());
    }

    @Before
    public void setupRxSchedulers(){
        Scheduler immediate = new Scheduler() {

            @Override
            public Disposable scheduleDirect(Runnable run, long delay, TimeUnit unit) {
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(new Executor() {
                    @Override
                    public void execute(Runnable command) {
                        command.run();
                    }
                }, false);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(c -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(c -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(c -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(c -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(c -> immediate);
    }
}
