package com.example.btlandroid_covid19.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DBName="covid";
    private static final int VERSION=1;

    private static final String TABLE_NAME = "News";
    private static final String TITLE = "title";
    private static final String LINK="_id";
    private static final String TIME="time";
    private static final String SOURCE = "source";

    private SQLiteDatabase myDB;

    public DBHelper(Context context){
        super(context, DBName, null, VERSION);
    }

    public static String getDBName() {
        return DBName;
    }

    public static String getTITLE() {
        return TITLE;
    }

    public static String getLINK() {
        return LINK;
    }

    public static String getTIME() {
        return TIME;
    }

    public static String getSOURCE() {
        return SOURCE;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String queryTable = "CREATE TABLE "+ TABLE_NAME+
                "( "+
                TITLE + " TEXT, "+
                LINK + " TEXT PRIMARY KEY, "+
                TIME +" TEXT, "+
                SOURCE + " TEXT"+
                ")";
        db.execSQL(queryTable);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, int i, int i1){

    }
    public void openDB(){
        myDB=getWritableDatabase();
    }
    public void closeDB(){
        if(myDB!=null && myDB.isOpen()){
            myDB.close();
        }
    }
    public long Insert( String title, String link, String time, String source){
        ContentValues values=new ContentValues();
        values.put(TITLE,title);
        values.put(LINK,link);
        values.put(TIME,time);
        values.put(SOURCE, source);
        return myDB.insert(TABLE_NAME, null, values);
    }
    public long Update(String title, String link, String time, String source){
        ContentValues values = new ContentValues();
        values.put(SOURCE,source);
        values.put(LINK, link);
        values.put(TIME,time);
        String where = LINK + "=" +link;
        return myDB.update(TABLE_NAME, values, where, null);
    }
    public long Delete(String link){
        String where = LINK+ " = ?";
        return myDB.delete(TABLE_NAME,where,new String[]{link});

    }
    public Cursor getAllRecord(){
        String query = "SELECT * FROM " + TABLE_NAME;
        return myDB.rawQuery(query, null);
    }
}
