package com.my.booze.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.my.booze.R;
import com.my.booze.utilities.Utilities;

import java.nio.MappedByteBuffer;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;
    ImageView image;
    String login_Status;
    String location_Added;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        image = (ImageView) findViewById(R.id.logo);
        login_Status = Utilities.getString(getApplicationContext(), "login_Status");
        location_Added = Utilities.getString(getApplicationContext(), "location_Added");

        if (login_Status.equals("yes"))
        {
            if(location_Added.equals("yes")) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }
                }, SPLASH_TIME_OUT);
            } else {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent mainIntent = new Intent(SplashScreenActivity.this, DeliveryAddressActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }
                }, SPLASH_TIME_OUT);
            }
        }
        else {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mainIntent = new Intent(SplashScreenActivity.this, QuestionActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }
}