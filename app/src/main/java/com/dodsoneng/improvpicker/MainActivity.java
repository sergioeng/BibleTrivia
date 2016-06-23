package com.dodsoneng.improvpicker;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static String _tag = Global.TAG + ".MAINACT";

    private Context                 _context;
    private FloatingActionButton    _fab;
    private static int              _NUM_OF_ITEMS = 9;

    private CheckBox                _checkBox [] = new CheckBox[_NUM_OF_ITEMS] ;
    private TextView                _textView [] = new TextView [_NUM_OF_ITEMS] ;
    private int                     _itemType [] = new int [_NUM_OF_ITEMS];

    private DBAdapter       _db = null;
    private int             _langId = Global.LANGID_ENG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
;
        int i ;
        /// Initialize globals
        _context = this;

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

        for (i = 0; i < _NUM_OF_ITEMS; i++) {
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void updateTextViews () {
            int i;
        for (i = 0; i < _NUM_OF_ITEMS; i++) {
            if (_checkBox [i].isChecked()) {
                _textView [i].setText(_db.getRandomItem(_langId, _itemType [i]));
                _textView [i].setBackgroundColor(_context.getResources().getColor(R.color.lightGrey));
            } else {
                _textView [i].setText(" ");
                _textView [i].setBackgroundColor(_context.getResources().getColor(R.color.white));
            }
        }

    }


    /// -----------------------------------------------------------------------------
    /// LISTENERS
    ///------------------------------------------------------------------------------
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
//                    TextView t = (TextView) findViewById(v.getId()+1);
                    TextView t = (TextView) findViewById((int)(v.getTag()));

                    //is chkIos checked?
                    if (((CheckBox) v).isChecked()) {
//                        Toast.makeText(_context, "Bro, try Android :)", Toast.LENGTH_LONG).show();
//                        t.setText(_db.getRandomItem(_langId, _itemType [_ii]));
                        int itemType = (int) (t.getTag());
Global.logcat(_tag, "_checkBox=" + v.getId() + " textView=" + t.getId() + " ITEM_TYPE=" + itemType);
                        t.setText(_db.getRandomItem(_langId, itemType));
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
