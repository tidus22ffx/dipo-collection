package com.example.mobilecollection.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mobilecollection.R;
import com.example.mobilecollection.utilities.Utilities;

public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView textView = findViewById(R.id.toolbar_text);

        Drawable backButton = getDrawable(R.drawable.ico_back);
        Drawable resizedBackButton = Utilities.resizeDrawable(backButton, this, 20, 20);

        toolbar.setNavigationIcon(resizedBackButton);
        textView.setText("CHANGE PASSWORD");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }
}
