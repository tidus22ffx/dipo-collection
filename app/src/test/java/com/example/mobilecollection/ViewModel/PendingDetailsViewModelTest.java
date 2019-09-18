package com.example.mobilecollection.ViewModel;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.mobilecollection.Repository.DB.DAO.TodoListDao;
import com.example.mobilecollection.Repository.Model.TodoItem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

public class PendingDetailsViewModelTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private TodoListDao dao = Mockito.mock(TodoListDao.class);
    private Application app =  Mockito.mock(Application.class);

    @InjectMocks
    public PendingDetailsViewModel pendingDetailsViewModel = new PendingDetailsViewModel(app);

    private Single<TodoItem> testMaybe = null;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getPendingDetailSuccess(){
        TodoItem item = new TodoItem(1, "23678219ZDSH", "Zaki", "2018-01-01", "B1234ZX", "Pending");
        testMaybe = Single.just(item);

        when(dao.getPendingDetail(1)).thenReturn(testMaybe);

        pendingDetailsViewModel.fetchDetails(1);

        Assert.assertNotNull(pendingDetailsViewModel.getTodoDetail().getValue());
        Assert.assertEquals(false, pendingDetailsViewModel.getIsError().getValue());
        Assert.assertEquals(false, pendingDetailsViewModel.getLoading().getValue());
        Assert.assertEquals(null, pendingDetailsViewModel.getErrorMessage().getValue());
    }

    @Test
    public void getPendingDetailFail(){
        testMaybe = Single.error(new Throwable());

        when(dao.getPendingDetail(1)).thenReturn(testMaybe);

        pendingDetailsViewModel.fetchDetails(1);

        Assert.assertEquals(false, pendingDetailsViewModel.getLoading().getValue());
        Assert.assertEquals(true, pendingDetailsViewModel.getIsError().getValue());
        Assert.assertNotNull(pendingDetailsViewModel.getErrorMessage().getValue());
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
