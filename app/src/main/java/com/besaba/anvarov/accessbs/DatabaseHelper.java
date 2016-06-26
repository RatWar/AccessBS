package com.besaba.anvarov.accessbs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	//final String LOG_TAG = "MyLogs";
	public static final String TABLE_NAME = "works";
	public static final String ROWID = "_id";
	public static final String SITE = "site";
	public static final String WORK = "work";
	public static final String START = "start";
	public static final String STOP = "stop";

	public DatabaseHelper(Context context) {
		super(context, "AccessBS", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//Log.d(LOG_TAG, "--- CREATE DataBase ---");
		db.execSQL("create table " + TABLE_NAME + " (" + ROWID
				+ " integer primary key autoincrement, " + SITE + " integer, "
				+ WORK + " integer, " + START + " Integer, " + STOP
				+ " integer);");
		for (int i = 1; i < 13; i++) {
			db.execSQL("insert into " + TABLE_NAME + "(" + ROWID + ", " + SITE
					+ ", " + WORK + ", " + START + ", " + STOP + ") VALUES("
					+ i + ", " + 0 + ", " + 0 + ", " + 0 + ", " + 0 + ")");
			//Log.d(LOG_TAG, "--- INSERT --- row " + i);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
