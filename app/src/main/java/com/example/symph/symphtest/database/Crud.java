package com.example.symph.symphtest.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kenneth on 8/5/2016.
 */
public class Crud {

    public static long create(SQLiteOpenHelper database, String tableName, ContentValues values){
        SQLiteDatabase db = database.getWritableDatabase();

        long newRowId;
        try{
            newRowId = db.insertOrThrow(
                    tableName,
                    null,
                    values);
        }catch (SQLiteConstraintException e){
            newRowId = -1;
        };

        return newRowId;
    }

    private static long delete(SQLiteOpenHelper database, String tableName,String comparedField, String id){
        SQLiteDatabase db = database.getReadableDatabase();

        // Define 'where' part of query.
        String selection = comparedField + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { String.valueOf(id) };
        // Issue SQL statement.
        return db.delete(tableName, selection, selectionArgs);
    }

    public static Cursor read(SQLiteOpenHelper database, String[] projection, String tableName, String selection, String[] selectionArgs, String sortOrder){

        SQLiteDatabase db = database.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.


        // How you want the results sorted in the resulting Cursor

        Cursor c = db.query(
                tableName,                                // The table to query
                projection,                               // The columns to return
                selection,                                     // The columns for the WHERE clause
                selectionArgs,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        return c;
    }

    public static Cursor rawQuery(SQLiteOpenHelper database,String sql){
        SQLiteDatabase db = database.getWritableDatabase();
        return db.rawQuery(sql, null);
    }

}
