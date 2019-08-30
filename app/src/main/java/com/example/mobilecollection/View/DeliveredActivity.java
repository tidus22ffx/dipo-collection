package com.example.mobilecollection.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mobilecollection.Adapter.DeliveredRecyclerAdapter;
import com.example.mobilecollection.R;
import com.example.mobilecollection.Repository.Model.TodoItem;
import com.example.mobilecollection.ViewModel.DeliveredViewModel;

import java.util.ArrayList;

public class DeliveredActivity extends AppCompatActivity {

    RecyclerView deliveredRecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    DeliveredViewModel deliveredViewModel;
    ProgressBar loading;
    TextView errorMessage;

    private DeliveredRecyclerAdapter adapter = new DeliveredRecyclerAdapter(new ArrayList<TodoItem>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivered);
        deliveredRecyclerView = findViewById(R.id.delivered_list);
        swipeRefreshLayout = findViewById(R.id.deliveredSwipeRefreshLayout);
        loading = findViewById(R.id.delivered_list_loading);
        errorMessage = findViewById(R.id.delivered_list_error);
        deliveredViewModel = ViewModelProviders.of(this).get(DeliveredViewModel.class);
        deliveredViewModel.refreshDeliveredList();

        deliveredRecyclerView.setAdapter(adapter);
        deliveredRecyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                deliveredViewModel.refreshDeliveredList();
            }
        });

        observeViewModel();
    }

    private void observeViewModel(){
        deliveredViewModel.getTodoList().observe(this, new Observer<ArrayList<TodoItem>>() {
            @Override
            public void onChanged(ArrayList<TodoItem> todoItems) {
                if(todoItems != null){
                    adapter.updateList(todoItems);
                    deliveredRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

        deliveredViewModel.getIsError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if(isError){
                    errorMessage.setVisibility(View.VISIBLE);
                }
            }
        });

        deliveredViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                errorMessage.setText(message);
            }
        });

        deliveredViewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if(isLoading){
                    loading.setVisibility(View.VISIBLE);
                    deliveredRecyclerView.setVisibility(View.GONE);
                    errorMessage.setVisibility(View.GONE);
                } else {
                    loading.setVisibility(View.GONE);
                }
            }
        });
    }
}
