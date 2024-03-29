package com.example.mobilecollection.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.mobilecollection.Adapter.DeliveredRecyclerAdapter;
import com.example.mobilecollection.R;
import com.example.mobilecollection.Repository.Model.TodoItem;
import com.example.mobilecollection.ViewModel.DeliveredViewModel;
import com.example.mobilecollection.utilities.Utilities;

import java.util.ArrayList;

public class DeliveredActivity extends AppCompatActivity implements DeliveredRecyclerAdapter.ClickListener {

    RecyclerView deliveredRecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    DeliveredViewModel deliveredViewModel;
    ProgressBar loading;
    TextView errorMessage;
    SearchView searchView;

    private DeliveredRecyclerAdapter adapter = new DeliveredRecyclerAdapter(new ArrayList<TodoItem>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivered);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView textView = findViewById(R.id.toolbar_text);
        searchView = findViewById(R.id.delivered_search);

        Drawable backButton = getDrawable(R.drawable.ico_back);
        Drawable resizedBackButton = Utilities.resizeDrawable(backButton, this, 20, 20);

        textView.setText("DATA DELIVERED");
        toolbar.setNavigationIcon(resizedBackButton);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

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

        searchView.setOnClickListener(v -> searchView.setIconified(false));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                deliveredViewModel.filterList(newText);
                return false;
            }
        });

        observeViewModel();
    }

    private void observeViewModel(){
        deliveredViewModel.getTodoList().observe(this, new Observer<ArrayList<TodoItem>>() {
            @Override
            public void onChanged(ArrayList<TodoItem> todoItems) {
                if(todoItems != null){
                    adapter.updateList(todoItems, DeliveredActivity.this);
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

    @Override
    public void onClick(int id) {
        Intent intent = new Intent(this, DeliveredDetailsActivity.class);
        intent.putExtra("detailId", id);
        startActivity(intent);
    }
}
