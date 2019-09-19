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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static org.mockito.Mockito.when;

public class DeliveredDetailsViewModelTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    private ApiService service = new ApiService();

    @InjectMocks
    public DeliveredDetailsViewModel deliveredDetailsViewModel= new DeliveredDetailsViewModel();

    private Single<TodoItem> testSingle = null;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
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

    @Test
    public void getPendingDetailSuccess(){
        TodoItem item = new TodoItem(1, "23678219ZDSH", "Zaki", "2018-01-01", "B1234ZX", "Pending");
        testSingle = Single.just(item);

        when(service.getDeliveredDetails(1)).thenReturn(testSingle);

        deliveredDetailsViewModel.fetchDetails(1);

        Assert.assertNotNull(deliveredDetailsViewModel.getTodoDetail().getValue());
        Assert.assertEquals(false, deliveredDetailsViewModel.getIsError().getValue());
        Assert.assertEquals(false, deliveredDetailsViewModel.getLoading().getValue());
        Assert.assertEquals(null, deliveredDetailsViewModel.getErrorMessage().getValue());
    }

    @Test
    public void getPendingDetailFail(){
        testSingle= Single.error(new Throwable());

        when(service.getDeliveredDetails(1)).thenReturn(testSingle);

        deliveredDetailsViewModel.fetchDetails(1);

        Assert.assertEquals(false, deliveredDetailsViewModel.getLoading().getValue());
        Assert.assertEquals(true, deliveredDetailsViewModel.getIsError().getValue());
        Assert.assertNotNull(deliveredDetailsViewModel.getErrorMessage().getValue());
    }
}
