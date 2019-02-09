package com.example.jeremyjohn.dingdong;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

public class DBHelper extends SQLiteOpenHelper {

    String DB_PATH = null;
    private static String DB_NAME = "BusServices.db";
    // private static String DB_NAME = "BusRoute.db";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    private static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "DinDong.db";
    public static final String TABLE_FAV = "fav";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DESNAME = "desname";
    public static final String COLUMN_DESNO = "desno";
    public static final String COLUMN_CURNAME = "curname";
    public static final String COLUMN_CURNO = "curno";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        this.myContext = context;
        this.DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[10];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_FAV
                + " (" + COLUMN_ID + " INTEGER PRIMARY KEY, "
                + COLUMN_DESNAME + " TEXT, "
                + COLUMN_DESNO + " TEXT, "
                + COLUMN_CURNAME + " TEXT, "
                + COLUMN_CURNO + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAV);
        onCreate(db);
        /*if (newVersion > oldVersion)
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();

            }*/
    }

    public void addFav(String a,String b,String c,String d) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESNAME, a);
        values.put(COLUMN_DESNO, b);
        values.put(COLUMN_CURNAME, c);
        values.put(COLUMN_CURNO, d);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_FAV, null, values);
        db.close();
    }

    public boolean deleteFav(String desname, String curname) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_FAV, COLUMN_DESNAME  + " = ? AND " + COLUMN_CURNAME + " = ?" , new String[]{desname,curname} ) != 0;
    }
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_FAV;
        Cursor data = db.rawQuery(query, null);

        return data;
    }
    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return myDataBase.query("bus", null, null, null, null, null, null);
    }
    public Cursor getBusStop(){
        SQLiteDatabase db = this.myDataBase;
        String query = "SELECT * FROM bus" ;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public Cursor getBusRoute(){
        SQLiteDatabase db = this.myDataBase;
        String query = "SELECT * FROM busroute" ;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

}
