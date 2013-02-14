package com.intrepidusgroup.learner;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

//Most code taken from http://www.vogella.com/articles/AndroidSQLite

public class Lesson7DatabaseTable {

  // Database table
  public static final String TABLE_USERS = "users";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_USER = "user";
  public static final String COLUMN_PASSWORD = "password";
  public static final String COLUMN_DESCRIPTION = "description";

  // Database creation SQL statement
  private static final String DATABASE_CREATE = "create table " 
      + TABLE_USERS
      + "(" 
      + COLUMN_ID + " integer primary key autoincrement, " 
      + COLUMN_USER + " text not null, " 
      + COLUMN_PASSWORD + " text not null," 
      + COLUMN_DESCRIPTION
      + " text not null" 
      + ");";

  public static void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
  }

  public static void onUpgrade(SQLiteDatabase database, int oldVersion,
      int newVersion) {
    Log.d("LEARNER", "Upgrading database from version "
        + oldVersion + " to " + newVersion
        + ", which will destroy all old data");
    database.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
    onCreate(database);
  }
} 