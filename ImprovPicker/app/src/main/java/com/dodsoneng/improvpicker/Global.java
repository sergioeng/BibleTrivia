package com.dodsoneng.improvpicker;

import java.util.List;

import android.content.Context;
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


	public static int LANGID_ENG = 1;
	public static int LANGID_SPA = 2;
	public static int LANGID_POR = 3;

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
	public static void logcat (String tag, int id)
	{
		Log.d (tag, "ID="+ id);
	}

} // end of Class Global
