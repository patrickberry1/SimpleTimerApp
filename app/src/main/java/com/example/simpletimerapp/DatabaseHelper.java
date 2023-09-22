package com.example.simpletimerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "TimerDB.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "timers";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "timer_name";
    private static final String COLUMN_XML = "timer_structure";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_XML + " TEXT);";
        db.execSQL((query));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertTimer(String name, String structure){
        //TODO: don't allow insert of duplicate names? Or should I rework everything to include timer ids?
        
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("timer_name", name);
        cv.put("timer_structure", structure);
        long result = DB.insert("timers", null, cv);
        if (result == -1){
            return false;
        } else {
            return true;
        }
    }
    public boolean updateTimer(int id, String structure){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("timer_structure", structure);
        Cursor cursor = DB.rawQuery("Select * from timers where id = ?", new String[] {Integer.toString(id)});

        if(cursor.getCount() > 0){
            long result = DB.update("timers", cv, "id=?", new String[] {Integer.toString(id)});
            if (result == -1){
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean deleteTimer(int id){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from timers where id = ?", new String[] {Integer.toString(id)});

        if(cursor.getCount() > 0){
            long result = DB.delete("timers", "id=?", new String[] {Integer.toString(id)});
            if (result == -1){
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from timers", null);
        return cursor;
    }

}
