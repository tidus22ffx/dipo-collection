package com.example.mobilecollection.ViewModel;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.mobilecollection.Repository.API.ApiService;
import com.example.mobilecollection.Repository.DB.DAO.TodoListDao;
import com.example.mobilecollection.Repository.Model.TodoItem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static org.mockito.Mockito.when;

public class DeliveredViewModelTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    private ApiService service =  new ApiService();

    @InjectMocks
    public DeliveredViewModel deliveredViewModel = new DeliveredViewModel();

    private Single<ArrayList<TodoItem>> testSingle = null;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getDeliveredListSuccess(){
        TodoItem item = new TodoItem(1, "23678219ZDSH", "Zaki", "2018-01-01", "B1234ZX", "Active");
        ArrayList<TodoItem> itemList = new ArrayList<>();
        itemList.add(item);
        testSingle = Single.just(itemList);
        when(service.getDeliveredList()).thenReturn(testSingle);
        deliveredViewModel.refreshDeliveredList();

        Assert.assertEquals(1, deliveredViewModel.getTodoList().getValue().size());
        Assert.assertEquals(false, deliveredViewModel.getLoading().getValue());
        Assert.assertEquals(false, deliveredViewModel.getIsError().getValue());
        Assert.assertEquals(null, deliveredViewModel.getErrorMessage().getValue());
    }

    @Test
    public void getDeliveredListFail(){
        testSingle = Single.error(new Throwable());
        when(service.getDeliveredList()).thenReturn(testSingle);
        deliveredViewModel.refreshDeliveredList();

        Assert.assertEquals(true, deliveredViewModel.getIsError().getValue());
        Assert.assertEquals(false, deliveredViewModel.getLoading().getValue());
        Assert.assertNotNull(deliveredViewModel.getErrorMessage().getValue());
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
