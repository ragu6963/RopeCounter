package com.example.activitycounter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DbOpenHelper {
    private static final String DATABASE_NAME = "RopeCount.db";
    private static final int DATABASE_VERSION = 2;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(RopeDB.CreateDB._CREATE0);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            db.execSQL("DROP TABLE IF EXISTS " + RopeDB.CreateDB._TABLENAME0);
//            onCreate(db);

            if (oldVersion < 2) {
                try {
                    db.beginTransaction();
                    db.execSQL("ALTER TABLE " + RopeDB.CreateDB._TABLENAME0 + " ADD COLUMN " + RopeDB.CreateDB.WEIGHT + "Integer DEFAULT 75");
                    db.setTransactionSuccessful();
                } catch (IllegalStateException e) {
                    System.out.println(e);
                } finally {
                    db.endTransaction();
                }
            }

        }
    }

    public DbOpenHelper(Context context) {
        this.mCtx = context;
    }

    public DbOpenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create() {
        mDBHelper.onCreate(mDB);
    }

    public void close() {
        mDB.close();
    }

    public long insertColumn(String date, long count, int weight) {
        ContentValues values = new ContentValues();
        values.put(RopeDB.CreateDB.DATE, date);
        values.put(RopeDB.CreateDB.COUNT, count);
        values.put(RopeDB.CreateDB.WEIGHT, weight);
        return mDB.insert(RopeDB.CreateDB._TABLENAME0, null, values);
    }

    public Cursor selectColumns() {
        return mDB.query(RopeDB.CreateDB._TABLENAME0, null, null, null, null, null, "_id desc");
    }

    public void deleteAllColumns() {
        mDB.delete(RopeDB.CreateDB._TABLENAME0, null, null);
    }
}
