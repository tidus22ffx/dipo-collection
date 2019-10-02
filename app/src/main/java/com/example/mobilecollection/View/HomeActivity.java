package com.example.mobilecollection.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.mobilecollection.Adapter.HomeAdapter;
import com.example.mobilecollection.R;
import com.example.mobilecollection.Repository.Model.HomeMenu;
import com.example.mobilecollection.ViewModel.HomeViewModel;
import com.example.mobilecollection.utilities.GPSTrackerService;
import com.example.mobilecollection.utilities.Utilities;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HomeActivity extends AppCompatActivity implements HomeAdapter.Listener {

    GPSTrackerService gpsservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final RecyclerView recyclerView = findViewById(R.id.recyclerview);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//
//        toolbar.setTitle("DIPO");
//        toolbar.setTitleTextColor(Color.WHITE);
//        setSupportActionBar(toolbar);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        gpsservice = new GPSTrackerService(0, 10, TimeUnit.SECONDS, this);
        gpsservice.start();

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
    protected void onStop() {
        super.onStop();
        gpsservice.stop();
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
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.logout_dialog);

        Button yesBtn = dialog.findViewById(R.id.yes_logout);
        Button noBtn = dialog.findViewById(R.id.no_logout);
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void gotoProfile() {
        Intent intent = new Intent(HomeActivity.this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    private void getLocation(){
        double longitude;
        double latitude;
        Location location = Utilities.getCurrentLocation(this);
        longitude = location.getLongitude();
        latitude = location.getLatitude();
//        Toast.makeText(this, "Longitude: " + longitude + ", Latitude: " + latitude, Toast.LENGTH_LONG ).show();
        Log.e("timer", "Longitude: " + longitude + ", Latitude: " + latitude);
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
    }
}
