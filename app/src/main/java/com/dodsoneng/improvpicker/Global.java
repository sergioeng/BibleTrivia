package com.dodsoneng.improvpicker;

import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Global {
    
    public static String Version = "1";

	/* Debug Code */
	public static boolean debug_onoff = false;
	
	public static String TAG = "IMPROPICK";

	public static int TYPEID_EMOTION   = 1;
	public static int TYPEID_GENRE     = 2;
	public static int TYPEID_CHARACTER = 3;
	public static int TYPEID_PLACE     = 4;
	public static int TYPEID_ENVIRON   = 5;
	public static int TYPEID_EVENT     = 6;
	public static int TYPEID_ADJECTIVE = 7;
	public static int TYPEID_ACTION    = 8;
	public static int TYPEID_MOMENT    = 9;

	// variables for ads
	public static String KEYWORD1 = "geico";
	public static String KEYWORD2 = "auto insurance";
	public static String KEYWORD3 = "christianity";
	public static String KEYWORD4 = "church";
	public static String KEYWORD5 = "catholic";
	public static String KEYWORD6 = "religion";


	public static void alert (Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	public static void problem (Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

	public static void logcat (String tag, Exception ex) 
	{
		StackTraceElement [] elems = ex.getStackTrace();
    	for (int n = elems.length; n > 0; n --) 
    	   Log.d (tag,elems[n-1].toString());
    	Log.d (tag,ex.toString());
	}

	public static void logcat (String tag, String text) 
	{
        Log.d (tag, text);
	}
    public static void logcat (String tag, String text, boolean print)
    {
        if (print) logcat (tag, text);
    }
	public static void logcat (String tag, int id)
	{
		Log.d (tag, "ID="+ id);
	}


    private static String getSharedPreference (Context context, String key) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return (prefs.getString(key, ""));
	}

    private static Boolean getSharedPreferenceBoolean (Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return (prefs.getBoolean(key, false));
    }


    private static void setSharedPrefence (Context context, String key, String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(key, value);
		editor.commit();
	}

    private static void setSharedPrefence (Context context, String key, Boolean value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }


    /// This method look at the shared preferences and gets the current configured language
    /// and then returns the index of the language according to what is defined in
    /// strings/menu_language_entries
    /// (English, Spanish, Portuguese, etc
    ///
    public static int getLanguageId (Context context) {

        String key =  context.getString (R.string.pref_language_key);
        String langValues  [] = context.getResources().getStringArray(R.array.menu_language_values);

        String language = getSharedPreference(context, key);

        /// Set the language ID based on Preference Settings

        for (int i=0; i < langValues.length ; i ++ ) {
            if (langValues [i].equals(language)) {
                return i+1;
            }
        }

        return -1;
    }

    public static String getLanguagePreference (Context context) {
        String key = context.getString (R.string.pref_language_key);
        String language = getSharedPreference (context, key);
        return language;
    }


    public static String getLanguageSummary (Context context) {
        String prefLanguageKey =  context.getString (R.string.pref_language_key);
        String langEntries [] = context.getResources().getStringArray(R.array.menu_language_entries);
        String langValues  [] = context.getResources().getStringArray(R.array.menu_language_values);

        String language = getSharedPreference(context, prefLanguageKey);

        /// Set the language ID based on Preference Settings

        for (int i=0; i < langValues.length ; i ++ ) {
            if (langValues [i].equals(language)) {
                return langEntries [i];
            }
        }

        return null;
    }

    public static void setLanguagePreference (Context context, String value) {
        String key = context.getString (R.string.pref_language_key);
        setSharedPrefence (context, key, value);
    }


    public static void setLocale (Activity activity, String language) {
        String key = activity.getBaseContext().getString (R.string.locale_has_changed_key);
        Locale locale = new Locale (language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        activity.getBaseContext().getResources().updateConfiguration(config,
                activity.getBaseContext().getResources().getDisplayMetrics());

        setSharedPrefence(activity.getBaseContext(), key, true);

    }

    public static Boolean hasLocaleChanged(Context context) {
        String key = context.getString (R.string.locale_has_changed_key);
        Boolean ret = getSharedPreferenceBoolean(context, key);
        return ret;
    }
    public static void resetHasLocaleChanged(Context context) {
        String key = context.getString (R.string.locale_has_changed_key);
        setSharedPrefence(context, key, false);
    }


} // end of Class Global
