package com.dodsoneng.improsw

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.dodsoneng.improsw.Global.TYPEID_ACTION
import com.dodsoneng.improsw.Global.TYPEID_ADJECTIVE
import com.dodsoneng.improsw.Global.TYPEID_CHARACTER
import com.dodsoneng.improsw.Global.TYPEID_EMOTION
import com.dodsoneng.improsw.Global.TYPEID_EVENT
import com.dodsoneng.improsw.Global.TYPEID_GENRE
import com.dodsoneng.improsw.Global.TYPEID_MOMENT
import com.dodsoneng.improsw.Global.TYPEID_OBJECT
import com.dodsoneng.improsw.Global.TYPEID_PLACE
import java.util.Locale

//import com.sun.org.apache.xerces.internal.dom.DOMMessageFormatter.setLocale

class PickerActivity : AppCompatActivity() {

    private var mContext: Context = this@PickerActivity
    private var mDBAdapter: DBAdapter? = null

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

        /// Initialize globals
        // mContext = this
        mDBAdapter = DBAdapter(mContext)
        mDBAdapter!!.open()

        /// Check whether there is a language already configured in SharedPreferences.
        /// NO  --> get the device current locale and take the device language configuration
        /// YES --> use the Shared Language configuration
        val prefLanguage = Global.getLanguagePreference(mContext)
        language = deviceLanguage

        Log.d(
            TAG,
            "onCreate(): language: pref=" + language + " locale=" + Locale.getDefault().language + " are matching"
        )

        setContentView(R.layout.activity_picker)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val cvSceneTitle = findViewById<CardView>(R.id.cvTopCardHeader)
        cvSceneTitle.setOnClickListener (clickListenerSceneTitle)
        val cv1 = findViewById<CardView>(R.id.cv1CardImage)
        cv1.setOnClickListener (clickListener1)
        val cv2 = findViewById<CardView>(R.id.cv2CardImage)
        cv2.setOnClickListener (clickListener2)
        val cv3 = findViewById<CardView>(R.id.cv3CardImage)
        cv3.setOnClickListener (clickListener3)
        val cv4 = findViewById<CardView>(R.id.cv4CardImage)
        cv4.setOnClickListener (clickListener4)
        val cv5 = findViewById<CardView>(R.id.cv5CardImage)
        cv5.setOnClickListener (clickListener5)
        val cv6 = findViewById<CardView>(R.id.cv6CardImage)
        cv6.setOnClickListener (clickListener6)
        val cv7 = findViewById<CardView>(R.id.cv7CardImage)
        cv7.setOnClickListener (clickListener7)
        val cv8 = findViewById<CardView>(R.id.cv8CardImage)
        cv8.setOnClickListener (clickListener8)
        val cv9 = findViewById<CardView>(R.id.cv9CardImage)
        cv9.setOnClickListener (clickListener9)

        Log.d(TAG, "onCreate(): end")

    }
    // onCreate()

    override fun onResume() {
        Log.d(TAG, "onResume(): begin")
        super.onResume()

        val language = Global.getLanguagePreference(mContext)

        Log.d(TAG, "onResume(): language: pref=" + language + " locale=" + Locale.getDefault().language)

        if (language != Locale.getDefault().language) {
            Log.d(TAG, "onResume(): preference language is different from locale, setting locale ")
            Global.setLocale(this, language)
        }
        /*
        Log.d(TAG, "onResume(): Shared Preference lang: " + language);
        Log.d(TAG, "            Default locale lang   : " + Locale.getDefault().getLanguage());
        Log.d(TAG, "            Config locale lang    : " + getResources().getConfiguration().locale.getLanguage());
*/
        /// Update the screen contents
//        updateTextViews()

        if (Global.hasLocaleChanged(mContext)) {
            Log.d(TAG, "onResume(): locale has changed, restart views")

            if (mDBAdapter != null) mDBAdapter!!.close()
            Global.resetHasLocaleChanged(mContext)
            //restartActivity();
            //setContentView(R.layout.activity_main);
            Log.d(TAG, "onResume(): calling recreate ...")
            recreate()

        }

    }
    // onResume()
    //

    override fun onDestroy() {
        Log.d(TAG, "onDestroy(): ")
        super.onDestroy()
        if (mDBAdapter != null) mDBAdapter!!.close()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG, "onCreateOptionsMenu(): begin")
        // Inflate the menu; this adds items to the action bar if it is present.
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected(): begin")

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            val intent = Intent()
            intent.setClassName(this, "com.dodsoneng.improsw.SettingsActivity")
            startActivity(intent)
            Log.d(TAG, "onOptionsItemSelected(): end 1")
            return true
        }

        Log.d(TAG, "onOptionsItemSelected(): end 2")
        return super.onOptionsItemSelected(item)
    }

    public override fun onSaveInstanceState(savedInstanceState: Bundle) {
        Log.d(TAG, "onSaveInstanceState()")
        //        Testing
        //        logButtonsStatus();

        // Save the user's current game state
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState)
    }

    public override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.d(TAG, "onRestoreInstanceState()")
        // Testing
        //        logButtonsStatus();

        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState)

        // Restore state members from saved instance
    }

    /*
    ** ==============================
    ** BEGIN clickListeners SECTION
    ** ==============================
     */
    private val clickListenerSceneTitle = View.OnClickListener {view ->
        Log.d(TAG, "clkListenerSceneTitle(): card pressed")

        val langId = Global.getLanguageId(mContext)

//        Toast.makeText(this, "Card pressed", Toast.LENGTH_LONG).show()

        var humanA = mDBAdapter!!.getRandomItem(langId, TYPEID_CHARACTER)
        var humanB = mDBAdapter!!.getRandomItem(langId, TYPEID_CHARACTER)
        var action = mDBAdapter!!.getRandomItem(langId, TYPEID_ACTION)
        var place = mDBAdapter!!.getRandomItem(langId, TYPEID_PLACE)
        var event = mDBAdapter!!.getRandomItem(langId, TYPEID_EVENT)

        val tvContent = findViewById<TextView>(R.id.tvTopCardContent)

        when (langId) {
            1 -> tvContent.text = "${humanA.capitalize()} $action at $place during $event"
            2 -> tvContent.text = "${humanA.capitalize()} $action en $place durante $event"
            3 -> tvContent.text = "${humanA.capitalize()} $action no(a) $place durante $event"
        }
    }

    private val clickListener1 = View.OnClickListener {view ->
        Log.d(TAG, "clickListenerCharacter(): card pressed")
        val langId = Global.getLanguageId(mContext)
        var result = mDBAdapter!!.getRandomItem(langId, TYPEID_CHARACTER)
        val tvContent = findViewById<TextView>(R.id.tv1CardContent)
        tvContent.text = "$result"
    }

    private val clickListener2 = View.OnClickListener {view ->
        Log.d(TAG, "clickListenerAction(): card pressed")
        val langId = Global.getLanguageId(mContext)
        var result = mDBAdapter!!.getRandomItem(langId, TYPEID_ACTION)
        val tvContent = findViewById<TextView>(R.id.tv2CardContent)
        tvContent.text = "$result"
    }

    private val clickListener3 = View.OnClickListener {view ->
        Log.d(TAG, "clickListenerEmotion(): card pressed")
        val langId = Global.getLanguageId(mContext)
        var result = mDBAdapter!!.getRandomItem(langId, TYPEID_EMOTION)
        val tvContent = findViewById<TextView>(R.id.tv3CardContent)
        tvContent.text = "$result"
    }

    private val clickListener4 = View.OnClickListener {view ->
        Log.d(TAG, "clickListenerPlace(): card pressed")
        val langId = Global.getLanguageId(mContext)
        var result = mDBAdapter!!.getRandomItem(langId, TYPEID_PLACE)
        val tvContent = findViewById<TextView>(R.id.tv4CardContent)
        tvContent.text = "$result"
    }

    private val clickListener5 = View.OnClickListener {view ->
        Log.d(TAG, "clickListenerAction(): card pressed")
        val langId = Global.getLanguageId(mContext)
        var result = mDBAdapter!!.getRandomItem(langId, TYPEID_ADJECTIVE)
        val tvContent = findViewById<TextView>(R.id.tv5CardContent)
        tvContent.text = "$result"
    }

    private val clickListener6 = View.OnClickListener {view ->
        Log.d(TAG, "clickListenerEmotion(): card pressed")
        val langId = Global.getLanguageId(mContext)
        var result = mDBAdapter!!.getRandomItem(langId, TYPEID_GENRE)
        val tvContent = findViewById<TextView>(R.id.tv6CardContent)
        tvContent.text = "$result"
    }

    private val clickListener7 = View.OnClickListener {view ->
        Log.d(TAG, "clickListenerCharacter(): card pressed")
        val langId = Global.getLanguageId(mContext)
        var result = mDBAdapter!!.getRandomItem(langId, TYPEID_OBJECT)
        val tvContent = findViewById<TextView>(R.id.tv7CardContent)
        tvContent.text = "$result"
    }

    private val clickListener8 = View.OnClickListener {view ->
        Log.d(TAG, "clickListenerAction(): card pressed")
        val langId = Global.getLanguageId(mContext)
        var result = mDBAdapter!!.getRandomItem(langId, TYPEID_EVENT)
        val tvContent = findViewById<TextView>(R.id.tv8CardContent)
        tvContent.text = "$result"
    }

    private val clickListener9 = View.OnClickListener {view ->
        Log.d(TAG, "clickListenerEmotion(): card pressed")
        val langId = Global.getLanguageId(mContext)
        var result = mDBAdapter!!.getRandomItem(langId, TYPEID_MOMENT)
        val tvContent = findViewById<TextView>(R.id.tv9CardContent)
        tvContent.text = "$result"
    }


    /*
    ** ==============================
    ** END clickListeners SECTION
    ** ==============================
     */


    /// This method updates the main screen with new value takne randomly from database.
    /// Only for the item that has the check box setted.
    ///
    private fun updateTextViews() {

        var i = 0
        val langId = Global.getLanguageId(mContext)

        Log.d(TAG, "updateTextViews()")
/*
        while (i < _NUM_OF_ITEMS) {
            if (_toggleButton[i].isChecked()) {
                _text[i] = mDBAdapter!!.getRandomItem(langId, _itemType[i])
                _textView[i].setText(_text[i])
                _textView[i].setTextColor(mContext.resources.getColor(android.R.color.holo_blue_bright))
            } else {

                if (_text[i] == "") _text[i] = mDBAdapter!!.getRandomItem(langId, _itemType[i])
                _textView[i].setText(_text[i])
                _textView[i].setTextColor(mContext.resources.getColor(android.R.color.holo_orange_light))
            }
            i++

        }
        logButtonsStatus()
*/
    }


    /// --------------------------------------------------------------------------------------------
    /// LISTENERS
    ///---------------------------------------------------------------------------------------------

    fun addListenerOnToggleButton() {
/*
        for (i in 0 until _NUM_OF_ITEMS) {
            _toggleButton[i].setOnClickListener(View.OnClickListener { v ->
                val t = findViewById<View>(v.tag as Int) as TextView
                val langId = Global.getLanguageId(mContext)
                //                    int itemType = (int) (t.getTag());
                //                    int i = itemType - 1;
                val i = t.tag as Int

                //is chkIos checked?
                if ((v as ToggleButton).isChecked) {
                    Log.d(TAG, "_toggleButton=" + v.getId() + " textView=" + t.id + " i=" + i)
                    //                        ((ToggleButton) v).setTextColor(mContext.getResources().getColor(R.color.black));
                    _textView[i].setText(_text[i])
                    _textView[i].setTextColor(mContext!!.resources.getColor(android.R.color.holo_blue_bright))
                } else {
                    _textView[i].setText(_text[i])
                    _textView[i].setTextColor(mContext!!.resources.getColor(android.R.color.holo_orange_light))
                    //((ToggleButton) v).setTextColor(mContext.getResources().getColor(android.R.color.holo_orange_light));
                }
            })

        }
*/
    }

    private fun logButtonsStatus() {
    /*
        var i: Int
        i = 0
        while (i < _NUM_OF_ITEMS) {

            if (_toggleButton[i].isChecked()) {
                Log.d(
                    TAG, "CHECKED   " +
                            " isFocused=" + _toggleButton[i].isFocused() +
                            " isSelected=" + _toggleButton[i].isSelected() +
                            " button=[" + _text[i] + "]"
                )
            } else {
                Log.d(
                    TAG, "UNCHECKED " +
                            " isFocused=" + _toggleButton[i].isFocused() +
                            " isSelected=" + _toggleButton[i].isSelected() +
                            " button=[" + _text[i] + "]"
                )
            }
            i++
        }
    */
    }


    private inner class StartGame : AsyncTask<Void, Void, Void>() {
        private var progress: ProgressDialog? = null
        internal var buildDatabase = BuildDatabase(mContext)

        override fun onPreExecute() {

            progress = ProgressDialog.show(mContext, null, mContext!!.getString(R.string.msgalert2))

            super.onPreExecute()
        }

        override fun doInBackground(vararg params: Void): Void? {
            buildDatabase.insertData()
            return null
        }
        /*
        @Override
        protected void onPostExecute(Void result) {
            try {
                Intent intent = new Intent(mContext, TriviaActivity.class);
                mContext.startActivity(intent);
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

    companion object {


        private val TAG = "IMPROZE.PICKACT"
        private val _NUM_OF_ITEMS = 9
    }


}