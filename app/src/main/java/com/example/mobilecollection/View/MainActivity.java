package com.example.mobilecollection.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilecollection.R;
import com.example.mobilecollection.Repository.Model.Login;
import com.example.mobilecollection.ViewModel.LoginViewModel;

public class MainActivity extends AppCompatActivity {

    Button loginButton;
    EditText username;
    EditText password;
    TextView errorText;
    LoginViewModel viewModel;
    ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username_field);
        password = findViewById(R.id.password_field);
        loginButton = findViewById(R.id.signin_button);
        loader = findViewById(R.id.activity_indicator);
        loader.setVisibility(View.GONE);
        errorText = findViewById(R.id.error_text);
        errorText.setVisibility(View.INVISIBLE);
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pwd = password.getText().toString();
                viewModel.login(user, pwd);
            }
        });

        observeViewModel();
    }

    private void observeViewModel(){
        viewModel.getLoginData().observe(this, new Observer<Login>() {
            @Override
            public void onChanged(Login login) {
                if(login != null){
                    Toast.makeText(getApplicationContext(), login.getName(), Toast.LENGTH_LONG).show();
                }
            }
        });

        viewModel.getLoginError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if(isError){
                    errorText.setVisibility(View.VISIBLE);
                } else {
                    errorText.setVisibility(View.INVISIBLE);
                }
            }
        });

        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                errorText.setText(s);
            }
        });

        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if(isLoading){
                    loader.setVisibility(View.VISIBLE);
                    errorText.setVisibility(View.INVISIBLE);
                    loginButton.setVisibility(View.GONE);
                } else {
                    loader.setVisibility(View.GONE);
                    loginButton.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
