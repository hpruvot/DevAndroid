package com.example.megamovies.app.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{

	// URI
	public static final Uri CONTENT_URI = Uri.parse("content://com.example.megamovies.app");
	// Nom de la base
	public static final String BD_NAME = "database.db";
	// Version
	public static final int VERSION = 1;
	// Mine du content provider
	public static final String CONTENT_MINE = "vnd.android.cursor.item/vnd.example.megamovie";

	
	public DatabaseHelper(Context context) {
		super(context, BD_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DB.Film.TABLE_CREATE);
		Log.i("DatabaseHelper","DatabaseHelper.onCreate()");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DB.Film.TABLE_DROP);
		Log.i("DatabaseHelper","DatabaseHelper.onUpgrade()");
		onCreate(db);
	}
}
	
