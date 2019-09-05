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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.mobilecollection.Adapter.DeliveredRecyclerAdapter;
import com.example.mobilecollection.Adapter.ToDoRecyclerAdapter;
import com.example.mobilecollection.R;
import com.example.mobilecollection.Repository.Model.TodoItem;
import com.example.mobilecollection.ViewModel.DeliveredViewModel;
import com.example.mobilecollection.ViewModel.ToDoViewModel;

import java.util.ArrayList;

public class ToDoListActivity extends AppCompatActivity implements  ToDoRecyclerAdapter.Listener {

    RecyclerView toDoRecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    ToDoViewModel toDoViewModel;
    ProgressBar loading;
    TextView errorMessage;
    SearchView searchView;

    private ToDoRecyclerAdapter adapter =
            new ToDoRecyclerAdapter(new ArrayList<TodoItem>(), ToDoListActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView textView = findViewById(R.id.toolbar_text);
        searchView = findViewById(R.id.to_do_search);

        textView.setText("To Do List");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        toDoRecyclerView = findViewById(R.id.to_do_list_recycler);
        swipeRefreshLayout = findViewById(R.id.to_do_swipe_refresh_layout);
        loading = findViewById(R.id.to_do_list_loading);
        errorMessage = findViewById(R.id.to_do_list_error);
        toDoViewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);
        toDoViewModel.refreshToDoList();

        toDoRecyclerView.setAdapter(adapter);
        toDoRecyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                toDoViewModel.refreshToDoList();
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        observeViewModel();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                toDoViewModel.filterList(newText);
                return false;
            }
        });
    }

    private void observeViewModel() {
        toDoViewModel.getTodoList().observe(this, new Observer<ArrayList<TodoItem>>() {
            @Override
            public void onChanged(ArrayList<TodoItem> todoItems) {
                if(todoItems != null){
                    adapter.updateList(todoItems);
                    toDoRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

        toDoViewModel.getIsError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if(isError){
                    errorMessage.setVisibility(View.VISIBLE);
                }
            }
        });

        toDoViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                errorMessage.setText(message);
            }
        });

        toDoViewModel.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if(isLoading){
                    loading.setVisibility(View.VISIBLE);
                    toDoRecyclerView.setVisibility(View.GONE);
                    errorMessage.setVisibility(View.GONE);
                } else {
                    loading.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(int position) {

    }
}
