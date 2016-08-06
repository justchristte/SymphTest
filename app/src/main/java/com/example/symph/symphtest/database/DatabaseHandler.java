package com.example.symph.symphtest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import com.example.symph.symphtest.helper.Helper;

import java.util.Map;

/**
 * Created by Kenneth on 8/5/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    static int DATABASE_VERSION=1;
    static final String DATABASE_NAME="local_db";

    public DatabaseHandler(Context context) {
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
