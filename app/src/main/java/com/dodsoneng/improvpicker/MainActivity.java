package com.dodsoneng.improvpicker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class
MainActivity extends Activity {


    private static String   _tag = Global.TAG + ".MAINACT";
    private Context         _context;

    @Override
    //public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    protected void onCreate(Bundle savedInstanceState) {
            String language;

        Global.logcat (_tag, "onCreate(): begin");

        //super.onCreate(savedInstanceState, persistentState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /// Initialize globals
        _context = this;

        /// Check whether there is a language already configured in SharedPreferences.
        /// NO  --> get the device current locale and take the device language configuration
        /// YES --> use the Shared Language configuration
        String prefLanguage = Global.getLanguagePreference(_context);

        /// If it is the first time running app
        if (prefLanguage.isEmpty()) {
            Global.logcat (_tag, "onCreate(): there is no shared preference");

            language = getDeviceLanguage ();
            Global.logcat (_tag, "onCreate(): initializing shared preference to current device language ["+language + "]");
            Global.setLanguagePreference (_context, language);

            Global.logcat (_tag, "onCreate(): creating database");
            new StartGame().execute();
        }
        else {
            language = Global.getLanguagePreference(_context);

            if (language.equals(Locale.getDefault().getLanguage()) == false) {
                Global.logcat(_tag, "onCreate(): preference language is different from locale, setting locale ");
                Global.setLocale(this, language);
                Global.logcat (_tag, "onCreate(): end doing nothing so far. 'onResume()' will trigger the recriation");
///eng                return;
            }
            try {
                Intent intent = new Intent(_context, PickerActivity.class);
                _context.startActivity(intent);
                finish();
            }
            catch (Exception ex)
            {
                Global.logcat (_tag, "Something failed onCreate(), see bellow...");
                Global.logcat (_tag, ex.toString());
            }

        }

        Global.logcat(_tag, "onCreate(): language: pref=" +language+" locale="+ Locale.getDefault().getLanguage() +" are matching");
        Global.logcat(_tag, "onCreate(): end");

    }
    // onCreate()

    @Override
    protected void onResume() {
        Global.logcat (_tag, "onResume(): begin");
        super.onResume();

        String language = Global.getLanguagePreference(_context);

        Global.logcat(_tag, "onResume(): language: pref=" +language+" locale="+ Locale.getDefault().getLanguage());

        if (language.equals(Locale.getDefault().getLanguage()) == false) {
            Global.logcat(_tag, "onResume(): preference language is different from locale, setting locale ");
            Global.setLocale(this, language);

        }
/*
        Global.logcat(_tag, "onResume(): Shared Preference lang: " + language);
        Global.logcat(_tag, "            Default locale lang   : " + Locale.getDefault().getLanguage());
        Global.logcat(_tag, "            Config locale lang    : " + getResources().getConfiguration().locale.getLanguage());
*/
        if (Global.hasLocaleChanged(_context)) {
            Global.logcat(_tag, "onResume(): locale has changed, restart views");
            Global.resetHasLocaleChanged(_context);
            //restartActivity();
            //setContentView(R.layout.activity_main);
            recreate();
        }

    }
    // onResume()

    @Override
    protected void onPause() {
        Global.logcat(_tag, "onPause(): ");
        super.onPause();
    }
    // onPause()

    @Override
    protected void onDestroy() {
        Global.logcat(_tag, "onDestroy(): ");
        super.onDestroy();
    }

    /// --------------------------------------------------------------------------------------------
    /// METHODS
    /// --------------------------------------------------------------------------------------------

    /// This method gets the device configuration and returns the current language configured.
    /// (English, Spanish, Portuguese, etc
    ///
    private String getDeviceLanguage () {
        Locale current = getResources().getConfiguration().locale;
        String  lang = current.getLanguage();

        Global.logcat (_tag, "getDeviceLanguage(): DisplayLanguage()=" + current.getDisplayLanguage() + " Language()=" + current.getLanguage() + " Country()=" + current.getCountry());
        return lang;

    }

    private class StartGame extends AsyncTask<Void, Void, Void>
    {
        private ProgressDialog progress = null;
        BuildDatabase buildDatabase = new BuildDatabase(_context);

        @Override
        protected void onPreExecute() {

            progress = ProgressDialog.show(
                    _context, null, "Loading Data...");

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            buildDatabase.insertData();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                Intent intent = new Intent(_context, PickerActivity.class);
                _context.startActivity(intent);
                finish();
            }
            catch (Exception ex)
            {
                Global.logcat (_tag, "Something failed onPostExecute(), see bellow...");
                Global.logcat (_tag, ex.toString());
            }
            progress.dismiss();
            super.onPostExecute(result);
        }

    }


}
