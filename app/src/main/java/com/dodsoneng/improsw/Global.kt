package com.dodsoneng.improsw

import java.util.Locale

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.preference.PreferenceManager
import android.widget.Toast

object Global {

    const val TYPEID_EMOTION = 1
    const val TYPEID_GENRE = 2
    const val TYPEID_CHARACTER = 3
    const val TYPEID_PLACE = 4
    const val TYPEID_OBJECT = 5
    const val TYPEID_EVENT = 6
    const val TYPEID_ADJECTIVE = 7
    const val TYPEID_ACTION = 8
    const val TYPEID_MOMENT = 9
    const val TYPEDID_ARTIGO = 100

    fun problem(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    private fun getSharedPreference(context: Context, key: String): String {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(key, "")
    }

    private fun getSharedPreferenceBoolean(context: Context, key: String): Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getBoolean(key, false)
    }


    private fun setSharedPrefence(context: Context, key: String, value: String) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.commit()
    }

    private fun setSharedPrefence(context: Context, key: String, value: Boolean?) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        editor.putBoolean(key, value!!)
        editor.commit()

    }


    /// This method look at the shared preferences and gets the current configured language
    /// and then returns the index of the language according to what is defined in
    /// strings/menu_language_entries
    /// (English, Spanish, Portuguese, etc
    ///
    fun getLanguageId(context: Context): Int {

        val key = context.getString(R.string.pref_language_key)
        val langValues = context.resources.getStringArray(R.array.menu_language_values)

        val language = getSharedPreference(context, key)

        /// Set the language ID based on Preference Settings

        for (i in langValues.indices) {
            if (langValues[i] == language) {
                return i + 1
            }
        }

        return -1
    }

    fun getLanguagePreference(context: Context): String {
        val key = context.getString(R.string.pref_language_key)
        return getSharedPreference(context, key)
    }


    fun getLanguageSummary(context: Context): String? {
        val prefLanguageKey = context.getString(R.string.pref_language_key)
        val langEntries = context.resources!!.getStringArray(R.array.menu_language_entries)
        val langValues = context.resources!!.getStringArray(R.array.menu_language_values)

        val language = getSharedPreference(context, prefLanguageKey!!)

        /// Set the language ID based on Preference Settings

        for (i in langValues.indices) {
            if (langValues[i] == language) {
                return langEntries[i]
            }
        }

        return null
    }

    fun setLanguagePreference(context: Context, value: String) {
        val key = context.getString(R.string.pref_language_key)
        setSharedPrefence(context, key!!, value)
    }


    fun setLocale(activity: Activity, language: String) {
        val key = activity.baseContext.getString(R.string.locale_has_changed_key)
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        activity.baseContext.resources.updateConfiguration(
            config,
            activity.baseContext.resources.displayMetrics
        )

        setSharedPrefence(activity.baseContext, key, true)

    }

    fun hasLocaleChanged(context: Context): Boolean {
        val key = context.getString(R.string.locale_has_changed_key)
        return this.getSharedPreferenceBoolean(context, key!!)
    }

    fun resetHasLocaleChanged(context: Context) {
        val key = context.getString(R.string.locale_has_changed_key)
        setSharedPrefence(context, key!!, false)
    }


} // end of Class Global