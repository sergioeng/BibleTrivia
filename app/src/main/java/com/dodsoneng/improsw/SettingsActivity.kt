package com.dodsoneng.improsw

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceActivity
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.util.Log

/**
 * A [PreferenceActivity] that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 *
 *
 * See [
 * Android Design: Settings](http://developer.android.com/design/patterns/settings.html) for design guidelines and the [Settings
 * API Guide](http://developer.android.com/guide/topics/ui/settings.html) for more information on developing a Settings UI.
 */

class SettingsActivity : PreferenceActivity() {

    private var _context: Context? = null
    private var _prefLanguageKey: String? = null
    private var _prefDonationKey: String? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate()")

        super.onCreate(savedInstanceState)
        _context = this

        _prefLanguageKey = _context!!.getString(R.string.pref_language_key)
        _prefDonationKey = _context!!.getString(R.string.pref_donation_key)

        // Display the fragment as the main content.
        fragmentManager.beginTransaction()
            .replace(android.R.id.content, SettingsFragment())
            .commit()

        checkValues("onCreate")

    }

    private fun checkValues(where: String) {
        Log.d(TAG, "checkValues()")

        /// Substituir for Global methods
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(baseContext)
        val language = sharedPrefs.getString(_prefLanguageKey, "")
        val donation = sharedPrefs.getString(_prefDonationKey, "")

        val msg = "$where LANG: $language DONATION: $donation"
        // Toast.makeText(_context, msg, Toast.LENGTH_SHORT).show();
        Log.d(TAG, msg)
    }

    /*
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(TAG, "onSharedPreferenceChanged(): " + key.equals("pref_language"));
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

    override fun onConfigurationChanged(newConfig: Configuration) {
        Log.d(TAG, "onConfigurationChanged(): detected")
        // refresh your views here
        super.onConfigurationChanged(newConfig)
    }


    /** -------------------------------------------------------------------------------
     * This fragment shows the preferences for the first header.
     * -------------------------------------------------------------------------------
     */
    class SettingsFragment : PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener {

        private val TAG = "IMPROZE.STTNGSFRG"
        private var _context: Context? = null
        private var _prefLanguageKey: String? = null
        private var _prefDonationKey: String? = null


        override fun onCreate(savedInstanceState: Bundle?) {
            Log.d(TAG, "onCreate()")

            super.onCreate(savedInstanceState)

            _context = this.activity
            _prefLanguageKey = _context!!.getString(R.string.pref_language_key)
            _prefDonationKey = _context!!.getString(R.string.pref_donation_key)


            // Make sure default values are applied.  In a real app, you would
            // want this in a shared function that is used to retrieve the
            // SharedPreferences wherever they are needed.

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.fragmented_preferences)

            /// Set summary with current preference for language
            val language = Global.getLanguageSummary(_context!!)
            val pref = findPreference(_prefLanguageKey)
            pref.summary = language

            val msg = "onCreate(): preference language: [$language]"
            //Toast.makeText(_context, msg, Toast.LENGTH_SHORT).show();
            Log.d(TAG, msg)

        }

        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
            Log.d(TAG, "onSharedPreferenceChanged()")

            if (key == _prefLanguageKey) {

                val language = Global.getLanguagePreference(_context!!)
                val summary = Global.getLanguageSummary(_context!!)

                Log.d(TAG, "onSharedPreferenceChanged(): language change detected, new language=[$language]")

                val pref = findPreference(key)
                pref.summary = summary

                Global.setLocale(this.activity, language!!)
                restartActivity()
            }

        }

        private fun restartActivity() {
            Log.d(TAG, "restartActivity()")

            val intent = this.activity.intent
            this.activity.finish()
            startActivity(intent)
        }

        override fun onResume() {
            Log.d(TAG, "onResume()")

            super.onResume()
            preferenceScreen
                .sharedPreferences
                .registerOnSharedPreferenceChangeListener(this)
        }

        override fun onPause() {
            Log.d(TAG, "onPause()")

            super.onPause()
            preferenceScreen
                .sharedPreferences
                .unregisterOnSharedPreferenceChangeListener(this)
        }

    }

    companion object {
        private val TAG = "IMPROZE.SETTACT"
    }

}