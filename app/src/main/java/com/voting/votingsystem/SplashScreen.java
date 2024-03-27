package com.voting.votingsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.voting.votingsystem.activities.LoginActivity;

public class SplashScreen extends AppCompatActivity {

    public static final String PREFERENCES = "prefkey";
    SharedPreferences sharedPreferences;
    public static final String IsLogin = "islogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreen.this, LoginActivity.class));
            finish();
        }, 3000); // Delay for 3000 milliseconds (3 seconds)


    }
}