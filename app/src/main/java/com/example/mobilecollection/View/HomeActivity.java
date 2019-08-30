package com.example.mobilecollection.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mobilecollection.Adapter.HomeAdapter;
import com.example.mobilecollection.R;
import com.example.mobilecollection.Repository.Model.HomeMenu;
import com.example.mobilecollection.ViewModel.HomeViewModel;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeAdapter.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final RecyclerView recyclerView = findViewById(R.id.recyclerview);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("DIPO");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        HomeViewModel model = ViewModelProviders.of(this).get(HomeViewModel.class);
        model.getmHomeMenu().observe(this, new Observer<List<HomeMenu>>() {
            @Override
            public void onChanged(List<HomeMenu> homeMenus) {
                HomeAdapter adapter =
                        new HomeAdapter(homeMenus, HomeActivity.this, HomeActivity.this);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Membaca file menu dan menambahkan isinya ke action bar jika ada.
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnProfile:
                gotoProfile();
                return true;
            case R.id.btnLogout:
                gotoLogout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void gotoLogout() {
        Toast.makeText(this, "Logout Clicked", Toast.LENGTH_SHORT).show();
    }

    private void gotoProfile() {
        Toast.makeText(this, "Profile Clicked ", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(HomeActivity.this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(int position) {
        if (position == 0) {
            Intent intent = new Intent(HomeActivity.this, ToDoListActivity.class);
            startActivity(intent);
        } else if (position == 1) {
            Intent intent = new Intent(HomeActivity.this, PendingActivity.class);
            startActivity(intent);
        } else if (position == 2) {
            Intent intent = new Intent(HomeActivity.this, DeliveredActivity.class);
            startActivity(intent);
        } else if (position == 3) {
            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "position "+position, Toast.LENGTH_SHORT).show();
        }
//        Intent intent = new Intent(this, Amphibi_Deskripsi.class);
//        Bundle bundle = new Bundle();
//        bundle.putInt("position", position);
//        intent.putExtras(bundle);
//        startActivity(intent);
    }
}
