package com.example.mobilecollection.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mobilecollection.R;
import com.example.mobilecollection.utilities.Utilities;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView textView = findViewById(R.id.toolbar_text);

        Drawable backButton = getDrawable(R.drawable.ico_back);
        Drawable resizedBackButton = Utilities.resizeDrawable(backButton, this, 20, 20);

        textView.setText("SETTING");
        toolbar.setNavigationIcon(resizedBackButton);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }
}
