package com.claudiusmbemba.irisdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class splashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread mythread = new Thread()
        {
            @Override
            public void run() {
                try {
                    sleep(1000);
                    startActivity(new Intent(getApplicationContext(),login.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        mythread.start();
    }
}


