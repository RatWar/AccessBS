package com.besaba.anvarov.accessbs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DbAdapter {
	private final Context mCtx;
	private DatabaseHelper mDBHelper;
	private SQLiteDatabase mDB;

	public DbAdapter(Context context) {
		this.mCtx = context;
	}

	// открыть подключение
	public DbAdapter open() {
		mDBHelper = new DatabaseHelper(mCtx);
		mDB = mDBHelper.getWritableDatabase();
		return this;
	}

	// закрыть подключение
	public void close() {
		if (mDBHelper != null)
			mDBHelper.close();
	}

	public void updateWork(int rowId, DataWork nWork) {
		ContentValues updateValues = new ContentValues();
		updateValues.put(DatabaseHelper.SITE, nWork.SITE);
		updateValues.put(DatabaseHelper.WORK, nWork.WORK);
		updateValues.put(DatabaseHelper.START, nWork.START);
		updateValues.put(DatabaseHelper.STOP, nWork.STOP);
		mDB.update(DatabaseHelper.TABLE_NAME, updateValues,
				DatabaseHelper.ROWID + "=" + rowId, null);
	}

	// возвращает курсор, спозиционированный на указанной записи
	public Cursor getWork(int rowId) {
		Cursor mCursor = mDB.query(DatabaseHelper.TABLE_NAME, null,
				DatabaseHelper.ROWID + "=" + rowId, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
}
