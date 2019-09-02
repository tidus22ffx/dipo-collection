package com.example.mobilecollection.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mobilecollection.Adapter.PendingRecyclerAdapter;
import com.example.mobilecollection.R;
import com.example.mobilecollection.Repository.Model.TodoItem;
import com.example.mobilecollection.ViewModel.PendingViewModel;

import java.util.ArrayList;

public class PendingActivity extends AppCompatActivity {

    RecyclerView pendingRecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    PendingViewModel pendingViewModel;
    ProgressBar loading;
    TextView errorMessage;

    private PendingRecyclerAdapter adapter = new PendingRecyclerAdapter(new ArrayList<TodoItem>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);

        Toolbar toolbar = findViewById(R.id.pending_toolbar);
        toolbar.setTitle("DATA PENDING");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        pendingRecyclerView = findViewById(R.id.pending_list);
        swipeRefreshLayout = findViewById(R.id.pending_swipe_refresh_layout);
        loading = findViewById(R.id.pending_list_loading);
        errorMessage = findViewById(R.id.pending_list_error);

        pendingViewModel = ViewModelProviders.of(this).get(PendingViewModel.class);
        pendingViewModel.refreshPendingList();

        pendingRecyclerView.setAdapter(adapter);
        pendingRecyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                pendingViewModel.refreshPendingList();
            }
        });

        observeViewModel();
    }

    private void observeViewModel(){
        pendingViewModel.getPendingList().observe(this, new Observer<ArrayList<TodoItem>>() {
            @Override
            public void onChanged(ArrayList<TodoItem> pendingList) {
                if(pendingList != null){
                    adapter.updateList(pendingList);
                    pendingRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

        pendingViewModel.getIsError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if(isError){
                    errorMessage.setVisibility(View.VISIBLE);
                }
            }
        });

        pendingViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                errorMessage.setText(message);
            }
        });

        pendingViewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if(isLoading){
                    loading.setVisibility(View.VISIBLE);
                    pendingRecyclerView.setVisibility(View.GONE);
                    errorMessage.setVisibility(View.GONE);
                } else {
                    loading.setVisibility(View.GONE);
                }
            }
        });
    }
}
