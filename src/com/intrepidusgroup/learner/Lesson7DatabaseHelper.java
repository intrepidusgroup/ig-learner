package com.intrepidusgroup.learner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// Most code taken from http://www.vogella.com/articles/AndroidSQLite

public class Lesson7DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "iglearner.db";
	private static final int DATABASE_VERSION = 1;

	public Lesson7DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

  // Method is called during creation of the database
	@Override
  	public void onCreate(SQLiteDatabase database) {
		Lesson7DatabaseTable.onCreate(database);
	}

	//Method is called during an upgrade of the database,
	//e.g. if you increase the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
      int newVersion) {
		Lesson7DatabaseTable.onUpgrade(database, oldVersion, newVersion);
	}
}
