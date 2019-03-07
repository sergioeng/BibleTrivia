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
import android.widget.ToggleButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class PickerActivityOOLD extends AppCompatActivity {


    private static String _tag = Global.TAG + ".PICKACT";

    private Context _context;
    private FloatingActionButton _fab;
    private static int _NUM_OF_ITEMS = 9;

    private ToggleButton _toggleButton[] = new ToggleButton[_NUM_OF_ITEMS];
    private TextView _textView[] = new TextView[_NUM_OF_ITEMS];
    private int _itemType[] = new int[_NUM_OF_ITEMS];
    private String _text[] = new String[_NUM_OF_ITEMS];

    private DBAdapter _db = null;

    @Override
    //public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    protected void onCreate(Bundle savedInstanceState) {
        String language;

        Global.logcat(_tag, "onCreate(): begin");

        //super.onCreate(savedInstanceState, persistentState);
        super.onCreate(savedInstanceState);

        /// Initialize globals
        _context = this;

        /// Check whether there is a language already configured in SharedPreferences.
        /// NO  --> get the device current locale and take the device language configuration
        /// YES --> use the Shared Language configuration
        String prefLanguage = Global.getLanguagePreference(_context);
        language = getDeviceLanguage();

        Global.logcat(_tag, "onCreate(): language: pref=" + language + " locale=" + Locale.getDefault().getLanguage() + " are matching");

        setContentView(R.layout.activity_picker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        _fab = (FloatingActionButton) findViewById(R.id.fab);
        assert _fab != null;
/*
 <string name="cbox_text1">"CHARACTER"</string>
    <string name="cbox_text2">"EMOTION"</string>
    <string name="cbox_text3">"GENRE"</string>
    <string name="cbox_text4">"PLACE"</string>
    <string name="cbox_text5">"OBJECT"</string>
    <string name="cbox_text6">"ADJECTIVE"</string>
    <string name="cbox_text7">"EVENT"</string>
    <string name="cbox_text8">"ACTION"</string>
    <string name="cbox_text9">"WHEN"</string>

 */
        _itemType[0] = Global.TYPEID_CHARACTER;
        _itemType[1] = Global.TYPEID_EMOTION;
        _itemType[2] = Global.TYPEID_GENRE;
        _itemType[3] = Global.TYPEID_PLACE;
        _itemType[4] = Global.TYPEID_OBJECT;
        _itemType[5] = Global.TYPEID_ADJECTIVE;
        _itemType[6] = Global.TYPEID_EVENT;
        _itemType[7] = Global.TYPEID_ACTION;
        _itemType[8] = Global.TYPEID_MOMENT;

        _toggleButton[0] = (ToggleButton) findViewById(R.id.toggleButton1);
        _toggleButton[1] = (ToggleButton) findViewById(R.id.toggleButton2);
        _toggleButton[2] = (ToggleButton) findViewById(R.id.toggleButton3);
        _toggleButton[3] = (ToggleButton) findViewById(R.id.toggleButton4);
        _toggleButton[4] = (ToggleButton) findViewById(R.id.toggleButton5);
        _toggleButton[5] = (ToggleButton) findViewById(R.id.toggleButton6);
        _toggleButton[6] = (ToggleButton) findViewById(R.id.toggleButton7);
        _toggleButton[7] = (ToggleButton) findViewById(R.id.toggleButton8);
        _toggleButton[8] = (ToggleButton) findViewById(R.id.toggleButton9);

        _textView[0] = (TextView) findViewById(R.id.textView1);
        _textView[1] = (TextView) findViewById(R.id.textView2);
        _textView[2] = (TextView) findViewById(R.id.textView3);
        _textView[3] = (TextView) findViewById(R.id.textView4);
        _textView[4] = (TextView) findViewById(R.id.textView5);
        _textView[5] = (TextView) findViewById(R.id.textView6);
        _textView[6] = (TextView) findViewById(R.id.textView7);
        _textView[7] = (TextView) findViewById(R.id.textView8);
        _textView[8] = (TextView) findViewById(R.id.textView9);

        for (int i = 0; i < _NUM_OF_ITEMS; i++) {
            _toggleButton[i].setTag(_textView[i].getId());    // save the textView ID correspondent
            _textView[i].setTag(i);    // save the item type correspondent
            _text[i] = "";
        }


        _db = new DBAdapter(_context);
        _db.open();


        /// Add listener to check boxes
        addListenerOnFab();
        addListenerOnToggleButton();

        Global.logcat(_tag, "onCreate(): end");

    }
    // onCreate()

    @Override
    protected void onResume() {
        Global.logcat(_tag, "onResume(): begin");
        super.onResume();

        String language = Global.getLanguagePreference(_context);

        Global.logcat(_tag, "onResume(): language: pref=" + language + " locale=" + Locale.getDefault().getLanguage());

        if (language.equals(Locale.getDefault().getLanguage()) == false) {
            Global.logcat(_tag, "onResume(): preference language is different from locale, setting locale ");
            Global.setLocale(this, language);

        }
/*
        Global.logcat(_tag, "onResume(): Shared Preference lang: " + language);
        Global.logcat(_tag, "            Default locale lang   : " + Locale.getDefault().getLanguage());
        Global.logcat(_tag, "            Config locale lang    : " + getResources().getConfiguration().locale.getLanguage());
*/
        /// Update the screen contents
        updateTextViews();

        if (Global.hasLocaleChanged(_context)) {
            Global.logcat(_tag, "onResume(): locale has changed, restart views");

            if (_db != null) _db.close();
            Global.resetHasLocaleChanged(_context);
            //restartActivity();
            //setContentView(R.layout.activity_main);
            Global.logcat(_tag, "onResume(): calling recreate ...");
            recreate();

        }

    }
    // onResume()
    //

    @Override
    protected void onDestroy() {
        Global.logcat(_tag, "onDestroy(): ");
        super.onDestroy();
        if (_db != null) _db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Global.logcat(_tag, "onCreateOptionsMenu(): begin");
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Global.logcat(_tag, "onOptionsItemSelected(): begin");

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent();
            intent.setClassName(this, "com.dodsoneng.improvpicker.SettingsActivity");
            startActivity(intent);
            Global.logcat(_tag, "onOptionsItemSelected(): end 1");
            return true;
        }

        Global.logcat(_tag, "onOptionsItemSelected(): end 2");
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Global.logcat(_tag, "onSaveInstanceState()");
//        Testing
//        logButtonsStatus();

        // Save the user's current game state
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        Global.logcat(_tag, "onRestoreInstanceState()");
// Testing
//        logButtonsStatus();

        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
    }


    /// --------------------------------------------------------------------------------------------
    /// METHODS
    /// --------------------------------------------------------------------------------------------

    /// This method gets the device configuration and returns the current language configured.
    /// (English, Spanish, Portuguese, etc
    ///
    private String getDeviceLanguage() {
        Locale current = getResources().getConfiguration().locale;
        String lang = current.getLanguage();

        Global.logcat(_tag, "getDeviceLanguage(): DisplayLanguage()=" + current.getDisplayLanguage() + " Language()=" + current.getLanguage() + " Country()=" + current.getCountry());
        return lang;

    }

    /// This method updates the main screen with new value takne randomly from database.
    /// Only for the item that has the check box setted.
    ///
    private void updateTextViews() {
        int i;
        int langId = Global.getLanguageId(_context);

        Global.logcat(_tag, "updateTextViews()");

        for (i = 0; i < _NUM_OF_ITEMS; i++) {
            if (_toggleButton[i].isChecked()) {
                _text[i] = _db.getRandomItem(langId, _itemType[i]);
                _textView[i].setText(_text[i]);
                _textView[i].setTextColor(_context.getResources().getColor(android.R.color.holo_blue_bright));
            } else {

                if (_text[i].equals("")) _text[i] = _db.getRandomItem(langId, _itemType[i]);
                _textView[i].setText(_text[i]);
                _textView[i].setTextColor(_context.getResources().getColor(android.R.color.holo_orange_light));
            }

        }
        logButtonsStatus();

    }


    /// --------------------------------------------------------------------------------------------
    /// LISTENERS
    ///---------------------------------------------------------------------------------------------
    public void addListenerOnFab() {
        if (_fab != null) {
            _fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
/*
                    Snackbar.make(view, _context.getString(R.string.msgalert1), Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                     */

                    /// Check which of the checkbuttons are checked, and then get data for them
                    updateTextViews();
                }
            });
        }
    }

    public void addListenerOnToggleButton() {

        for (int i = 0; i < _NUM_OF_ITEMS; i++) {
            _toggleButton[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    TextView t = (TextView) findViewById((int) (v.getTag()));
                    int langId = Global.getLanguageId(_context);
//                    int itemType = (int) (t.getTag());
//                    int i = itemType - 1;
                    int i = (int) (t.getTag());

                    //is chkIos checked?
                    if (((ToggleButton) v).isChecked()) {
                        Global.logcat(_tag, "_toggleButton=" + v.getId() + " textView=" + t.getId() + " i=" + i);
//                        ((ToggleButton) v).setTextColor(_context.getResources().getColor(R.color.black));
                        _textView[i].setText(_text[i]);
                        _textView[i].setTextColor(_context.getResources().getColor(android.R.color.holo_blue_bright));
                    } else {
                        _textView[i].setText(_text[i]);
                        _textView[i].setTextColor(_context.getResources().getColor(android.R.color.holo_orange_light));
                        //((ToggleButton) v).setTextColor(_context.getResources().getColor(android.R.color.holo_orange_light));
                    }
                }

            });

        }
    }

    private void logButtonsStatus() {
        int i;
        for (i = 0; i < _NUM_OF_ITEMS; i++) {

            if (_toggleButton[i].isChecked()) {
                Global.logcat(_tag, "CHECKED   " +
                        " isFocused=" + _toggleButton[i].isFocused() +
                        " isSelected=" + _toggleButton[i].isSelected() +
                        " button=[" + _text[i] + "]");
            } else {
                Global.logcat(_tag, "UNCHECKED " +
                        " isFocused=" + _toggleButton[i].isFocused() +
                        " isSelected=" + _toggleButton[i].isSelected() +
                        " button=[" + _text[i] + "]");
            }
        }
    }


    private class StartGame extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;
        BuildDatabase buildDatabase = new BuildDatabase(_context);

        @Override
        protected void onPreExecute() {

            progress = ProgressDialog.show(_context, null, _context.getString(R.string.msgalert2));

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            buildDatabase.insertData();
            return null;
        }
/*
        @Override
        protected void onPostExecute(Void result) {
            try {
                Intent intent = new Intent(_context, TriviaActivity.class);
                _context.startActivity(intent);
                finish();
            }
            catch (Exception ex)
            {
                de.printff (ex.toString());
            }
            progress.dismiss();
            super.onPostExecute(result);
        }
*/
    }


}
