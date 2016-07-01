package com.dodsoneng.improvpicker;

//import com.dodsoneng.support.DodsonEng;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

public class BuildDatabase {
	Context 	_context;
//	DodsonEng de = null;
	DBAdapter 	_db = null;
	int 		_numOfEntries = 0;
	private static String _tag = Global.TAG + ".BUILDDB";
	
	
	public BuildDatabase (Context context) {
		_context = context;
		_db = new DBAdapter(_context);
//		de = new DodsonEng(mContext, Global.debug_onoff);
	}
	
	/***
     * inserData will insert the questions into the database.
     * Only inserts the questions if they haven't previously 
     * been inserted.
     */
	public void insertData ( )
    {
		Global.logcat(_tag, "insertData()");

		try {
			_db.open();
			_numOfEntries = _db.getNumOfRecords();
			Global.logcat(_tag, "_numOfEntries="+_numOfEntries);

			if (_numOfEntries == 0) {
				insertEmotions (Global.TYPEID_EMOTION);
				insertGenres (Global.TYPEID_GENRE);
				insertCharacters (Global.TYPEID_CHARACTER);
				insertPlaces (Global.TYPEID_PLACE);
				insertEnvironments (Global.TYPEID_ENVIRON);
				insertEvents (Global.TYPEID_EVENT);
				insertAdjectives (Global.TYPEID_ADJECTIVE);
				insertActions (Global.TYPEID_ACTION);
				insertMoments (Global.TYPEID_MOMENT);
			}
		}
		catch (Exception e) {
			Global.problem(_context, _context.getString(R.string.msgerror1));
			Global.logcat (_tag, e);
		}

    } // End of insertData ()


	private void insertEmotions (int typeId) {
		Global.logcat(_tag, "insertEmotions()");
		_db.insertRecord(	1	,	typeId,	1,	"horny"	);
		_db.insertRecord(	2	,	typeId,	1,	"hate"	);
		_db.insertRecord(	3	,	typeId,	1,	"passion"	);
		_db.insertRecord(	4	,	typeId,	1,	"cuteness"	);
		_db.insertRecord(	5	,	typeId,	1,	"scorn"	);

		_db.insertRecord(	1	,	typeId,	2,	"caliente"	);
		_db.insertRecord(	2	,	typeId,	2,	"odio"	);
		_db.insertRecord(	3	,	typeId,	2,	"pasión"	);
		_db.insertRecord(	4	,	typeId,	2,	"monería"	);
		_db.insertRecord(	5	,	typeId,	2,	"desdén"	);

		_db.insertRecord(	1	,	typeId,	3,	"tesão"	);
		_db.insertRecord(	2	,	typeId,	3,	"ódio"	);
		_db.insertRecord(	3	,	typeId,	3,	"paixão"	);
		_db.insertRecord(	4	,	typeId,	3,	"fofura"	);
		_db.insertRecord(	5	,	typeId,	3,	"desprezo"	);

	}
	private void insertGenres (int typeId) {
		_db.insertRecord(	1	,	typeId,	1,	"western"	);
		_db.insertRecord(	2	,	typeId,	1,	"terror"	);
		_db.insertRecord(	3	,	typeId,	1,	"musical"	);
		_db.insertRecord(	4	,	typeId,	1,	"soap opera"	);
		_db.insertRecord(	5	,	typeId,	1,	"cartoon"	);

		_db.insertRecord(	1	,	typeId,	3,	"faroeste"	);
		_db.insertRecord(	2	,	typeId,	3,	"terror"	);
		_db.insertRecord(	3	,	typeId,	3,	"musical"	);
		_db.insertRecord(	4	,	typeId,	3,	"novela"	);
		_db.insertRecord(	5	,	typeId,	3,	"desenho animado"	);

		_db.insertRecord(	1	,	typeId,	2,	"wild west"	);
		_db.insertRecord(	2	,	typeId,	2,	"odio"	);
		_db.insertRecord(	3	,	typeId,	2,	"pasión"	);
		_db.insertRecord(	4	,	typeId,	2,	"telenovela"	);
		_db.insertRecord(	5	,	typeId,	2,	"dibujos animados"	);
	}
	private void insertCharacters (int typeId) {
		_db.insertRecord(	1	,	typeId,	1,	"translator"	);
		_db.insertRecord(	2	,	typeId,	1,	"driver"	);
		_db.insertRecord(	3	,	typeId,	1,	"soldier"	);
		_db.insertRecord(	4	,	typeId,	1,	"cook"	);
		_db.insertRecord(	5	,	typeId,	1,	"car seller"	);

		_db.insertRecord(	1	,	typeId,	3,	"tradutor"	);
		_db.insertRecord(	2	,	typeId,	3,	"motorista"	);
		_db.insertRecord(	3	,	typeId,	3,	"soldado"	);
		_db.insertRecord(	4	,	typeId,	3,	"cozinheiro"	);
		_db.insertRecord(	5	,	typeId,	3,	"vendedor de carros"	);

		_db.insertRecord(	1	,	typeId,	2,	"traductor"	);
		_db.insertRecord(	2	,	typeId,	2,	"conductor"	);
		_db.insertRecord(	3	,	typeId,	2,	"soldado"	);
		_db.insertRecord(	4	,	typeId,	2,	"cocinero"	);
		_db.insertRecord(	5	,	typeId,	2,	"vendedor de coches"	);
	}
	private void insertPlaces (int typeId) {
		_db.insertRecord(	1	,	typeId,	1,	"beach"	);
		_db.insertRecord(	2	,	typeId,	1,	"office"	);
		_db.insertRecord(	3	,	typeId,	1,	"kitchen"	);
		_db.insertRecord(	4	,	typeId,	1,	"supermarket"	);
		_db.insertRecord(	5	,	typeId,	1,	"bus"	);

		_db.insertRecord(	1	,	typeId,	3,	"praia"	);
		_db.insertRecord(	2	,	typeId,	3,	"escritório"	);
		_db.insertRecord(	3	,	typeId,	3,	"cozinha"	);
		_db.insertRecord(	4	,	typeId,	3,	"supermercado"	);
		_db.insertRecord(	5	,	typeId,	3,	"onibus"	);

		_db.insertRecord(	1	,	typeId,	2,	"playa"	);
		_db.insertRecord(	2	,	typeId,	2,	"oficina"	);
		_db.insertRecord(	3	,	typeId,	2,	"cocina"	);
		_db.insertRecord(	4	,	typeId,	2,	"supermercado"	);
		_db.insertRecord(	5	,	typeId,	2,	"autobus"	);


	}
	private void insertEnvironments (int typeId) {


	}
	private void insertEvents (int typeId) {


	}
	private void insertAdjectives (int typeId) {


	}
	private void insertActions (int typeId) {


	}
	private void insertMoments (int typeId) {


	}

}
