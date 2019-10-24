package com.roger.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static DataBaseHelper mInstance = null;

    private static final String DATABASE_NAME = "BDCajaTrujillo";
    private static final int DATABASE_VERSION = 1;
    private Context mCtx;


    public static DataBaseHelper getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new DataBaseHelper(ctx);
            SQLiteDatabase db1 = mInstance.getWritableDatabase();
            if (db1 == null){
                mInstance.onCreate(db1);
            }

        }
        return mInstance;
    }
    private DataBaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.mCtx = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists "+VisitClientTable.TableName + " ( "
                + VisitClientTable.cClient + " TEXT, "
                + VisitClientTable.cDocument + " TEXT, "
                + VisitClientTable.nLatitude + " FLOAT, "
                + VisitClientTable.nLength + " FLOAT) "
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public String getDatabaseName() {
        return super.getDatabaseName();
    }
}
