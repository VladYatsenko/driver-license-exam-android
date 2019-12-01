package com.android.testdai.util;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.internal.Util;

public class DatabaseUtil extends SQLiteOpenHelper{

    private static String DB_PATH = null;
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "dai_visual.db";
    private SQLiteDatabase mDataBase;
    private Context context;

//    public DatabaseUtil(Context context){
//        super(context, DATABASE_NAME, null, VERSION);
//        this.mContext = context;
//        this.DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
//    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {}

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

//    @Override
//    public void onOpen(SQLiteDatabase db) {
//        super.onOpen(db);
//        db.disableWriteAheadLogging();
//    }
//
    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DATABASE_NAME;
        mDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }
//
//    public void createDataBase() throws IOException {
//        boolean dbExist = checkDataBase();
//        if (!dbExist) {
//            this.getReadableDatabase();
//            try {
//                copyDataBase();
//            } catch (IOException e) {
//                throw new Error("Error copying database");
//            }
//
//            this.close();
//        }
//    }
//
//    private boolean checkDataBase() {
//        SQLiteDatabase checkDB = null;
//        try {
//            String myPath = DB_PATH + DATABASE_NAME;
//            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
//        } catch (SQLiteException ignored) {
//        }
//        if (checkDB != null) {
//            checkDB.close();
//        }
//        return checkDB != null;
//    }
//
    private void copyDataBase() throws IOException {
        InputStream myInput = context.getAssets().open(DATABASE_NAME);
        String outFileName = DB_PATH + DATABASE_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }


    public DatabaseUtil(Context context){
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
        DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/+";;

        if (!checkIfDBExists()){
            try {
                createDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        openDataBase();
    }

    private void createDataBase() throws IOException {
        this.getReadableDatabase();
        this.close();
        try {
            copyDataBase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkIfDBExists() {
        File dbFile = new File(DB_PATH + DATABASE_NAME);
        return dbFile.exists();
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return mDataBase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
    }
}