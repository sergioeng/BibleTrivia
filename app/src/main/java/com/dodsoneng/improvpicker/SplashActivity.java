package com.dodsoneng.improvpicker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import java.util.Locale;

public class
SplashActivity extends Activity {


    private static String   _tag = Global.TAG + ".SPLHACT";
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    //public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    protected void onCreate(Bundle savedInstanceState) {

        Global.logcat (_tag, "onCreate():");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            /*
             * Exibindo splash com um timer.
             */
            @Override
            public void run() {
                // Esse método será executado sempre que o timer acabar
                // E inicia a activity principal
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);

                // Fecha esta activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
    // onCreate()

}
