package com.dodsoneng.improsw


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log


class SplashActivity : AppCompatActivity() {

    override//public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    fun onCreate(savedInstanceState: Bundle?) {

        Log.d(TAG, "onCreate():")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            // Esse método será executado sempre que o timer acabar
            // E inicia a activity principal
            val i = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(i)

            // Fecha esta activity
            finish()
        }, SPLASH_TIME_OUT.toLong())
    }

    companion object {
        private val TAG = "IMPROZE.SPLHACT"
        private val SPLASH_TIME_OUT = 1000
    }
    // onCreate()

}