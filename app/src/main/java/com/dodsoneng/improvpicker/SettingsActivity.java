package com.dodsoneng.improvpicker;


import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */

public class SettingsActivity extends PreferenceActivity {


    private static String _tag = Global.TAG + ".SETTACT";

    private Context     _context;
    private String      _prefLanguageKey;
    private String      _prefDonationKey;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Global.logcat (_tag, "onCreate()");

        super.onCreate(savedInstanceState);
        _context = this;

        _prefLanguageKey =  _context.getString (R.string.pref_language_key);
        _prefDonationKey =  _context.getString (R.string.pref_donation_key);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();



        checkValues("onCreate");

    }

    private void checkValues(String where)
    {
        Global.logcat (_tag, "checkValues()");

        /// Substituir for Global methods
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String language = sharedPrefs.getString(_prefLanguageKey, "");
        String donation = sharedPrefs.getString(_prefDonationKey,"");

        String msg = where + " LANG: " + language + " DONATION: " + donation;
        // Toast.makeText(_context, msg, Toast.LENGTH_SHORT).show();
        Global.logcat(_tag, msg);
    }

    /*
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Global.logcat(_tag, "onSharedPreferenceChanged(): " + key.equals("pref_language"));
        if (key.equals("pref_language")) {
            restartActivity();
        }
    }
    private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

*/

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Global.logcat(_tag, "onConfigurationChanged(): detected" );
        // refresh your views here
        super.onConfigurationChanged(newConfig);
    }


    /** -------------------------------------------------------------------------------
     ** This fragment shows the preferences for the first header.
     ** -------------------------------------------------------------------------------
     */
    public static class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

        private String _tag =  Global.TAG + ".SETTACT.SETTINGSFRAG";
        private Context     _context;
        private String      _prefLanguageKey;
        private String      _prefDonationKey;


        @Override
        public void onCreate(Bundle savedInstanceState) {
            Global.logcat (_tag, "onCreate()");

            super.onCreate(savedInstanceState);

            _context = this.getActivity();
            _prefLanguageKey =  _context.getString (R.string.pref_language_key);
            _prefDonationKey =  _context.getString (R.string.pref_donation_key);


            // Make sure default values are applied.  In a real app, you would
            // want this in a shared function that is used to retrieve the
            // SharedPreferences wherever they are needed.

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.fragmented_preferences);

            /// Set summary with current preference for language
            String language = Global.getLanguageSummary(_context);
            Preference pref = findPreference(_prefLanguageKey);
            pref.setSummary(language);

            String msg = "onCreate(): preference language: [" + language + "]";
            //Toast.makeText(_context, msg, Toast.LENGTH_SHORT).show();
            Global.logcat(_tag, msg);

        }
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Global.logcat (_tag, "onSharedPreferenceChanged()");

            if (key.equals(_prefLanguageKey)) {

                String language = Global.getLanguagePreference (_context);
                String summary = Global.getLanguageSummary(_context);

                Global.logcat (_tag, "onSharedPreferenceChanged(): language change detected, new language=["+language+"]");

                Preference pref = findPreference(key);
                pref.setSummary(summary);

                Global.setLocale(this.getActivity(), language);
                restartActivity();
            }

        }
        private void restartActivity() {
            Global.logcat (_tag, "restartActivity()");

            Intent intent = this.getActivity().getIntent();
            this.getActivity().finish();
            startActivity(intent);
        }

        @Override
        public void onResume() {
            Global.logcat (_tag, "onResume()");

            super.onResume();
            getPreferenceScreen()
                    .getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            Global.logcat (_tag, "onPause()");

            super.onPause();
            getPreferenceScreen()
                    .getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(this);
        }

    }

}
