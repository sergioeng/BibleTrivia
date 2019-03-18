package com.dodsoneng.improsw

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.media.AudioManager
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
import com.dodsoneng.improsw.Global.TYPEDID_ARTIGO
import com.dodsoneng.improsw.Global.TYPEID_ACTION
import com.dodsoneng.improsw.Global.TYPEID_ADJECTIVE
import com.dodsoneng.improsw.Global.TYPEID_CHARACTER
import com.dodsoneng.improsw.Global.TYPEID_EMOTION
import com.dodsoneng.improsw.Global.TYPEID_EVENT
import com.dodsoneng.improsw.Global.TYPEID_GENRE
import com.dodsoneng.improsw.Global.TYPEID_MOMENT
import com.dodsoneng.improsw.Global.TYPEID_OBJECT
import com.dodsoneng.improsw.Global.TYPEID_PLACE
import java.util.*
import android.os.CountDownTimer
import android.view.SoundEffectConstants
import android.media.ToneGenerator




//import com.sun.org.apache.xerces.internal.dom.DOMMessageFormatter.setLocale

class PickerActivity : AppCompatActivity() {

    private var mContext: Context = this@PickerActivity
    private var mDBAdapter: DBAdapter? = null
    private val timeDef : Long = 18
    private var timerOn = false
    var mTimer: CountDownTimer? = null


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

        val cvTimer = findViewById<CardView>(R.id.cvTimer)
        cvTimer.setOnClickListener (clickListenerTimer)

        val tvContent = findViewById<TextView>(R.id.tvTimerHeader)
        tvContent.text = "${getTimeStr(timeDef * 1000)}"

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
        //var humanB = mDBAdapter!!.getRandomItem(langId, TYPEID_CHARACTER)
        var action = mDBAdapter!!.getRandomItem(langId, TYPEID_ACTION)
        var place = mDBAdapter!!.getRandomItem(langId, TYPEID_PLACE)
        var event = mDBAdapter!!.getRandomItem(langId, TYPEID_EVENT)

        val tvContent = findViewById<TextView>(R.id.tvTopCardContent)

        val random = Random()
        if (random.nextInt(3) == 3) {
            when (langId) {
            1 -> tvContent.text = "${humanA.capitalize()} $action at $place during $event."
            2 -> tvContent.text = "${humanA.capitalize()} $action en $place durante $event."
            3 -> {
                var id = mDBAdapter!!.getRandomItemID (langId, TYPEID_PLACE)
                place = mDBAdapter!!.getItem(langId, id, TYPEID_PLACE)
                var artigo = mDBAdapter!!.getItem(langId, id, TYPEDID_ARTIGO)
                tvContent.text = "${humanA.capitalize()} $action $artigo $place durante $event."
                 }
            }
        }
        else {
            when (langId) {
                1 -> tvContent.text = "${humanA.capitalize()} at $place."
                2 -> tvContent.text = "${humanA.capitalize()} en $place."
                3 -> {
                    var id = mDBAdapter!!.getRandomItemID (langId, TYPEID_PLACE)
                    place = mDBAdapter!!.getItem(langId, id, TYPEID_PLACE)
                    var artigo = mDBAdapter!!.getItem(langId, id, TYPEDID_ARTIGO)
                    tvContent.text = "${humanA.capitalize()} $artigo $place."
                }
            }
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

   private val clickListenerTimer = View.OnClickListener {view ->
        Log.d(TAG, "clickListenerTimer(): card pressed")

       val tvContent = findViewById<TextView>(R.id.tvTimerHeader)
        if (timerOn) {
            mTimer?.cancel()
            timerOn = false
            tvContent.text = "${getTimeStr(timeDef * 1000)}"
        }
        else {
            mTimer = timer (timeDef * 1000, 1000)
            timerOn = true
            mTimer?.start()
        }


    }


    /*
    ** ==============================
    ** END clickListeners SECTION
    ** ==============================
     */

    private fun timer (millisInFuture:Long,countDownInterval:Long):CountDownTimer{
        val tvContent = findViewById<TextView>(R.id.tvTimerHeader)
  //      val audioManager: AudioManager = getSystemService(Context.AUDIO_SERVICE)
        val toneG = ToneGenerator(AudioManager.STREAM_ALARM, 100)
        var duration = 100
        return object: CountDownTimer(millisInFuture,countDownInterval){
                override fun onTick(millisUntilFinished: Long) {
                    tvContent.text = "${getTimeStr(millisUntilFinished)}"
                    if (millisUntilFinished < 15000) {
                        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, duration)
//                      toneG.startTone(ToneGenerator.TONE_, duration)
                      duration += 50
//                    audioManager.playSoundEffect(SoundEffectConstants.CLICK, 0.7F)
                    }
                }

                override fun onFinish() {
                    tvContent.text = "${getTimeStr(timeDef)}"
                    toneG.stopTone()
                }
        }
    }

    private fun formatNumber(value: Long): String{
        if(value < 10)
            return "0$value"
        return "$value"
    }

    private fun getTimeStr(timeMilli: Long ):String {
        var seconds = timeMilli / 1000
        var minutes = seconds / 60
        val hours = minutes / 60

        if (minutes > 0)
            seconds = seconds % 60
        if (hours > 0)
            minutes = minutes % 60
        val time:String = formatNumber(minutes) + ":" + formatNumber(seconds)
       return time
    }
    /// --------------------------------------------------------------------------------------------
    /// LISTENERS
    ///---------------------------------------------------------------------------------------------

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
    }

    companion object {
        private const val TAG = "IMPROZE.PICKACT"
    }

}