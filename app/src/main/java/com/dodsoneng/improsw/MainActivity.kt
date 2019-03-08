package com.dodsoneng.improsw

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

import java.util.Locale

//import com.sun.org.apache.xerces.internal.dom.DOMMessageFormatter.setLocale

class MainActivity : AppCompatActivity() {

    private var mContext: Context = this@MainActivity

    /// --------------------------------------------------------------------------------------------
    /// METHODS
    /// --------------------------------------------------------------------------------------------

    /// This method gets the device configuration and returns the current language configured.
    /// (English, Spanish, Portuguese, etc
    ///
    private val deviceLanguage: String
        get() {
            val current = resources.configuration.locale
            val lang = current.language
            Log.d(
                TAG,
                "getDeviceLanguage(): DisplayLanguage()=" + current.displayLanguage + " Language()=" + current.language + " Country()=" + current.country
            )
            return lang

        }

    override//public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    fun onCreate(savedInstanceState: Bundle?) {
        val language: String

        Log.d(TAG, "onCreate(): begin")

        //super.onCreate(savedInstanceState, persistentState);
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        /// Initialize globals
        // mContext = this.baseContext

        /// Check whether there is a language already configured in SharedPreferences.
        /// NO  --> get the device current locale and take the device language configuration
        /// YES --> use the Shared Language configuration
        val prefLanguage = Global.getLanguagePreference(mContext)

        /// If it is the first time running app
        if (prefLanguage!!.isEmpty()) {
            Log.d(TAG, "onCreate(): there is no shared preference")

            language = deviceLanguage
            Log.d(TAG, "onCreate(): initializing shared preference to current device language [$language]")
            Global.setLanguagePreference(mContext, language)

            Log.d(TAG, "onCreate(): creating database")
            StartGame().execute()
        } else {
            language = Global.getLanguagePreference(mContext)!!

            if (language != Locale.getDefault().language) {
                Log.d(TAG, "onCreate(): preference language is different from locale, setting locale ")
                Global.setLocale(this, language)
                Log.d(TAG, "onCreate(): end doing nothing so far. 'onResume()' will trigger the recriation")
                ///eng                return;
            }
            try {
                val intent = Intent(this.mContext, PickerActivity::class.java)
                this.mContext.startActivity(intent)
                finish()
            } catch (ex: Exception) {
                Log.d(TAG, "Something failed onCreate(), see bellow...")
                Log.d(TAG, ex.toString())
            }

        }

        Log.d(
            TAG,
            "onCreate(): language: pref=" + language + " locale=" + Locale.getDefault().language + " are matching"
        )
        Log.d(TAG, "onCreate(): end")

    }
    // onCreate()

    override fun onResume() {
        Log.d(TAG, "onResume(): begin")
        super.onResume()

        val language = Global.getLanguagePreference(mContext)

        Log.d(TAG, "onResume(): language: pref=" + language + " locale=" + Locale.getDefault().language)

        if (language == Locale.getDefault().language == false) {
            Log.d(TAG, "onResume(): preference language is different from locale, setting locale ")
            Global.setLocale(this, language!!)

        }
        /*
        Log.d(TAG, "onResume(): Shared Preference lang: " + language);
        Log.d(TAG, "            Default locale lang   : " + Locale.getDefault().getLanguage());
        Log.d(TAG, "            Config locale lang    : " + getResources().getConfiguration().locale.getLanguage());
*/
        if (Global.hasLocaleChanged(mContext)) {
            Log.d(TAG, "onResume(): locale has changed, restart views")
            Global.resetHasLocaleChanged(mContext)
            //restartActivity();
            //setContentView(R.layout.activity_main);
            recreate()
        }

    }
    // onResume()

    override fun onPause() {
        Log.d(TAG, "onPause(): ")
        super.onPause()
    }
    // onPause()

    override fun onDestroy() {
        Log.d(TAG, "onDestroy(): ")
        super.onDestroy()
    }

    private inner class StartGame : AsyncTask<Void, Void, String>() {
        private var progress: ProgressDialog? = null
        internal var buildDatabase = BuildDatabase(mContext)

        override fun onPreExecute() {
            super.onPreExecute()

            progress = ProgressDialog.show(
                mContext, null, "Loading Data..."
            )

        }

        override fun doInBackground(vararg params: Void): String? {
            buildDatabase.insertData()
            return "success"
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            try {
                val intent = Intent(mContext, PickerActivity::class.java)
                mContext.startActivity(intent)
                finish()
            } catch (ex: Exception) {
                Log.d(TAG, "Something failed onPostExecute(), see bellow...")
                Log.d(TAG, ex.toString())
            }

            progress!!.dismiss()
        }

    }

    companion object {

        private const val TAG = "IMPROZE.MAINACT"
    }


}