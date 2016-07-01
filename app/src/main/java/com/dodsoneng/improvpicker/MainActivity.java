package com.dodsoneng.improvpicker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity  {


    private static String _tag = Global.TAG + ".MAINACT";

    private Context                 _context;
    private FloatingActionButton    _fab;
    private static int              _NUM_OF_ITEMS = 9;

    private CheckBox                _checkBox [] = new CheckBox[_NUM_OF_ITEMS] ;
    private TextView                _textView [] = new TextView [_NUM_OF_ITEMS] ;
    private int                     _itemType [] = new int [_NUM_OF_ITEMS];

    private DBAdapter       _db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            String language;

        super.onCreate(savedInstanceState);

        /// Initialize globals
        _context = this;

        /// Check whether there is a language already configured in SharedPreferences.
        /// NO  --> get the device current locale and take the device language configuration
        /// YES --> use the Shared Language configuration
        String prefLanguage = Global.getLanguagePreference(_context);

        if (prefLanguage.isEmpty()) {
            Global.logcat (_tag, "onCreate(): there is no shared preference");

            language = getDeviceLanguage ();
            Global.logcat (_tag, "onCreate(): initializing shared preference to current device language ["+language + "]");
            Global.setLanguagePreference (_context, language);
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
;
        _fab = (FloatingActionButton) findViewById(R.id.fab); assert _fab != null;

        _itemType [0] = Global.TYPEID_EMOTION  ;
        _itemType [1] = Global.TYPEID_GENRE    ;
        _itemType [2] = Global.TYPEID_CHARACTER;
        _itemType [3] = Global.TYPEID_PLACE    ;
        _itemType [4] = Global.TYPEID_ENVIRON  ;
        _itemType [5] = Global.TYPEID_EVENT    ;
        _itemType [6] = Global.TYPEID_ADJECTIVE;
        _itemType [7] = Global.TYPEID_ACTION   ;
        _itemType [8] = Global.TYPEID_MOMENT   ;

        _checkBox [0] = (CheckBox) findViewById(R.id.checkBox1);
        _checkBox [1] = (CheckBox) findViewById(R.id.checkBox2);
        _checkBox [2] = (CheckBox) findViewById(R.id.checkBox3);
        _checkBox [3] = (CheckBox) findViewById(R.id.checkBox4);
        _checkBox [4] = (CheckBox) findViewById(R.id.checkBox5);
        _checkBox [5] = (CheckBox) findViewById(R.id.checkBox6);
        _checkBox [6] = (CheckBox) findViewById(R.id.checkBox7);
        _checkBox [7] = (CheckBox) findViewById(R.id.checkBox8);
        _checkBox [8] = (CheckBox) findViewById(R.id.checkBox9);

        _textView [0] = (TextView) findViewById(R.id.textView1);
        _textView [1] = (TextView) findViewById(R.id.textView2);
        _textView [2] = (TextView) findViewById(R.id.textView3);
        _textView [3] = (TextView) findViewById(R.id.textView4);
        _textView [4] = (TextView) findViewById(R.id.textView5);
        _textView [5] = (TextView) findViewById(R.id.textView6);
        _textView [6] = (TextView) findViewById(R.id.textView7);
        _textView [7] = (TextView) findViewById(R.id.textView8);
        _textView [8] = (TextView) findViewById(R.id.textView9);

        for (int i = 0; i < _NUM_OF_ITEMS; i++) {
            _checkBox [i].setTag(_textView [i].getId());    // save the textView ID correspondent
            _textView [i].setTag(_itemType [i]);    // save the item type correspondent
        }
        /// Insert Data in the Database
        BuildDatabase bd = new BuildDatabase(_context);
        bd.insertData();

        _db = new DBAdapter(_context);
        _db.open();

        /// Update the screen contents
        updateTextViews ();

        /// Add listener to check boxes
        addListenerOnFab ();
        addListenerOnChkBox();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent();
            intent.setClassName(this, "com.dodsoneng.improvpicker.SettingsActivity");
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String language = Global.getLanguagePreference(_context);

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
            setContentView(R.layout.activity_main);
            recreate();
        }

    }

    private void restartActivity() {
        Global.logcat(_tag, "restartActivity(): ");
        Intent intent = getIntent();
        finish();
        startActivity(intent);
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

        /** TO BE DELETED
        for (int i=0; i < _langValues.length ; i ++ ) {
            if (_langValues [i].equals(lang)) {
                return _langEntries [i];
            }
        }

        Global.logcat (_tag, "("+lang+") not found in _langValues");

        return null;
         **/
    }


/*** TO BE DELETED
    /// This method look at the shared preferences and gets the current configured language
    /// and then returns the index of the language according to what is defined in
    /// strings/menu_language_entries
    /// (English, Spanish, Portuguese, etc
    ///
    private int getLanguageId () {

        String language = Global.getSharedPreference(_context, _prefLanguageKey);

        /// Set the language ID based on Preference Settings

        for (int i=0; i < _langEntries.length ; i ++ ) {
            if (_langEntries [i].equals(language)) {
                Global.logcat (_tag, "getLanguageId(): entry=[" + language + "] id=" + i+1 );
                return i+1;
            }
        }

        Global.logcat (_tag, "("+language+") not found in _langArray");

        return -1;
    }
***/


    /// This method updates the main screen with new value takne randomly from database.
    /// Only for the item that has the check box setted.
    ///
    private void updateTextViews () {
            int i;
            int langId = Global.getLanguageId(_context);

        for (i = 0; i < _NUM_OF_ITEMS; i++) {
            if (_checkBox [i].isChecked()) {
                _textView [i].setText(_db.getRandomItem(langId, _itemType [i]));
                _textView [i].setBackgroundColor(_context.getResources().getColor(R.color.lightGrey));
            } else {
                _textView [i].setText(" ");
                _textView [i].setBackgroundColor(_context.getResources().getColor(R.color.white));
            }
        }

    }


    /// --------------------------------------------------------------------------------------------
    /// LISTENERS
    ///---------------------------------------------------------------------------------------------
    public void addListenerOnFab () {
        if (_fab != null) {
            _fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, _context.getString(R.string.msgalert1), Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();

                    /// Check which of the checkbuttons are checked, and then get data for them
                    updateTextViews();
                }
            });
        }
    }

    public void addListenerOnChkBox() {

        for (int i = 0; i < _NUM_OF_ITEMS; i++) {
            _checkBox[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    TextView t = (TextView) findViewById((int)(v.getTag()));
                    int langId = Global.getLanguageId(_context);

                    //is chkIos checked?
                    if (((CheckBox) v).isChecked()) {

                        int itemType = (int) (t.getTag());
Global.logcat(_tag, "_checkBox=" + v.getId() + " textView=" + t.getId() + " ITEM_TYPE=" + itemType);
                        t.setText(_db.getRandomItem(langId, itemType));
                        t.setBackgroundColor(_context.getResources().getColor(R.color.lightGrey));
                    } else {
                        t.setText(" ");
                        t.setBackgroundColor(_context.getResources().getColor(R.color.white));
                    }

                }

            });

        }
    }

}
