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
		_db.insertRecord(	1	,	typeId,	Global.LANGID_ENG,	"horny"	);
		_db.insertRecord(	2	,	typeId,	Global.LANGID_ENG,	"hate"	);
		_db.insertRecord(	3	,	typeId,	Global.LANGID_ENG,	"passion"	);
		_db.insertRecord(	4	,	typeId,	Global.LANGID_ENG,	"cuteness"	);
		_db.insertRecord(	5	,	typeId,	Global.LANGID_ENG,	"scorn"	);

		_db.insertRecord(	1	,	typeId,	Global.LANGID_POR,	"tesão"	);
		_db.insertRecord(	2	,	typeId,	Global.LANGID_POR,	"ódio"	);
		_db.insertRecord(	3	,	typeId,	Global.LANGID_POR,	"paixão"	);
		_db.insertRecord(	4	,	typeId,	Global.LANGID_POR,	"fofura"	);
		_db.insertRecord(	5	,	typeId,	Global.LANGID_POR,	"desprezo"	);

		_db.insertRecord(	1	,	typeId,	Global.LANGID_SPA,	"caliente"	);
		_db.insertRecord(	2	,	typeId,	Global.LANGID_SPA,	"odio"	);
		_db.insertRecord(	3	,	typeId,	Global.LANGID_SPA,	"pasión"	);
		_db.insertRecord(	4	,	typeId,	Global.LANGID_SPA,	"monería"	);
		_db.insertRecord(	5	,	typeId,	Global.LANGID_SPA,	"desdén"	);
	}
	private void insertGenres (int typeId) {
		_db.insertRecord(	1	,	typeId,	Global.LANGID_ENG,	"western"	);
		_db.insertRecord(	2	,	typeId,	Global.LANGID_ENG,	"terror"	);
		_db.insertRecord(	3	,	typeId,	Global.LANGID_ENG,	"musical"	);
		_db.insertRecord(	4	,	typeId,	Global.LANGID_ENG,	"soap opera"	);
		_db.insertRecord(	5	,	typeId,	Global.LANGID_ENG,	"cartoon"	);

		_db.insertRecord(	1	,	typeId,	Global.LANGID_POR,	"faroeste"	);
		_db.insertRecord(	2	,	typeId,	Global.LANGID_POR,	"terror"	);
		_db.insertRecord(	3	,	typeId,	Global.LANGID_POR,	"musical"	);
		_db.insertRecord(	4	,	typeId,	Global.LANGID_POR,	"novela"	);
		_db.insertRecord(	5	,	typeId,	Global.LANGID_POR,	"desenho animado"	);

		_db.insertRecord(	1	,	typeId,	Global.LANGID_SPA,	"wild west"	);
		_db.insertRecord(	2	,	typeId,	Global.LANGID_SPA,	"odio"	);
		_db.insertRecord(	3	,	typeId,	Global.LANGID_SPA,	"pasión"	);
		_db.insertRecord(	4	,	typeId,	Global.LANGID_SPA,	"telenovela"	);
		_db.insertRecord(	5	,	typeId,	Global.LANGID_SPA,	"dibujos animados"	);
	}
	private void insertCharacters (int typeId) {
		_db.insertRecord(	1	,	typeId,	Global.LANGID_ENG,	"translator"	);
		_db.insertRecord(	2	,	typeId,	Global.LANGID_ENG,	"driver"	);
		_db.insertRecord(	3	,	typeId,	Global.LANGID_ENG,	"soldier"	);
		_db.insertRecord(	4	,	typeId,	Global.LANGID_ENG,	"cook"	);
		_db.insertRecord(	5	,	typeId,	Global.LANGID_ENG,	"car seller"	);

		_db.insertRecord(	1	,	typeId,	Global.LANGID_POR,	"tradutor"	);
		_db.insertRecord(	2	,	typeId,	Global.LANGID_POR,	"motorista"	);
		_db.insertRecord(	3	,	typeId,	Global.LANGID_POR,	"soldado"	);
		_db.insertRecord(	4	,	typeId,	Global.LANGID_POR,	"cozinheiro"	);
		_db.insertRecord(	5	,	typeId,	Global.LANGID_POR,	"vendedor de carros"	);

		_db.insertRecord(	1	,	typeId,	Global.LANGID_SPA,	"traductor"	);
		_db.insertRecord(	2	,	typeId,	Global.LANGID_SPA,	"conductor"	);
		_db.insertRecord(	3	,	typeId,	Global.LANGID_SPA,	"soldado"	);
		_db.insertRecord(	4	,	typeId,	Global.LANGID_SPA,	"cocinero"	);
		_db.insertRecord(	5	,	typeId,	Global.LANGID_SPA,	"vendedor de coches"	);
	}
	private void insertPlaces (int typeId) {
		_db.insertRecord(	1	,	typeId,	Global.LANGID_ENG,	"beach"	);
		_db.insertRecord(	2	,	typeId,	Global.LANGID_ENG,	"office"	);
		_db.insertRecord(	3	,	typeId,	Global.LANGID_ENG,	"kitchen"	);
		_db.insertRecord(	4	,	typeId,	Global.LANGID_ENG,	"supermarket"	);
		_db.insertRecord(	5	,	typeId,	Global.LANGID_ENG,	"bus"	);

		_db.insertRecord(	1	,	typeId,	Global.LANGID_POR,	"praia"	);
		_db.insertRecord(	2	,	typeId,	Global.LANGID_POR,	"escritório"	);
		_db.insertRecord(	3	,	typeId,	Global.LANGID_POR,	"cozinha"	);
		_db.insertRecord(	4	,	typeId,	Global.LANGID_POR,	"supermercado"	);
		_db.insertRecord(	5	,	typeId,	Global.LANGID_POR,	"onibus"	);

		_db.insertRecord(	1	,	typeId,	Global.LANGID_SPA,	"playa"	);
		_db.insertRecord(	2	,	typeId,	Global.LANGID_SPA,	"oficina"	);
		_db.insertRecord(	3	,	typeId,	Global.LANGID_SPA,	"cocina"	);
		_db.insertRecord(	4	,	typeId,	Global.LANGID_SPA,	"supermercado"	);
		_db.insertRecord(	5	,	typeId,	Global.LANGID_SPA,	"autobus"	);


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
