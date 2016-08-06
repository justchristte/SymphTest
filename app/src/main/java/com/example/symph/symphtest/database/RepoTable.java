package com.example.symph.symphtest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.symph.symphtest.object.Repo;

import java.util.ArrayList;

public class RepoTable extends DatabaseHandler implements Table.FieldTypes{

    public static String TABLE_NAME="repo";
    public static String ID="id";
    public static String GITHUB_ID="github_id";
    public static String NAME="name";
    public static String HTML_URL="html_url";
    public static String DESCRIPTION="description";
    public static String USER_ID="user_id";

    Table table;

    public RepoTable(Context context) {
        super(context);
        table=Table.create(TABLE_NAME)
                .addField(ID,TYPE_INTEGER_PRIMARY_KEY)
                .addField(GITHUB_ID,TYPE_TEXT)
                .addField(NAME,TYPE_TEXT)
                .addField(HTML_URL, TYPE_TEXT)
                .addField(DESCRIPTION,TYPE_TEXT)
                .addField(USER_ID,TYPE_INTEGER);
        onCreate(this.getWritableDatabase());
    }

    public long addRepo(Repo repo){
        ContentValues values=new ContentValues();
        values.put(GITHUB_ID, repo.getGithubId());
        values.put(NAME,repo.getName());
        values.put(HTML_URL,repo.getHtmlUrl());
        values.put(DESCRIPTION, repo.getDescription());
        values.put(USER_ID,repo.getUserId());
        return Crud.create(this,TABLE_NAME,values);
    }

    public ArrayList<Repo> getReposFor(int userId){
        ArrayList<Repo>list=new ArrayList<>();
        String sql="select * from "+TABLE_NAME+" where "+USER_ID+"="+userId;
        Cursor cursor=Crud.rawQuery(this,sql);
        if(cursor.moveToFirst()){
            do{
                list.add(new Repo(cursor));
            }while (cursor.moveToNext());
        }
        return list;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(table.getCreateQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(table.getDeleteQuery());
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
