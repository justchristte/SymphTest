package com.example.symph.symphtest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.symph.symphtest.object.User;

import java.util.ArrayList;

public class UserTable extends DatabaseHandler implements Table.FieldTypes{

    public static String TABLE_NAME="user";
    public static String ID="id";
    public static String LOGIN="login";
    public static String GITHUB_ID="github_id";
    public static String AVATAR_URL="avatar_url";
    public static String HTML_URL="html_url";
    public static String FOLLOWERS_URL="followers_url";
    public static String FOLLOWING_URL="following_url";
    public static String REPOS_URL="repos_url";
    public static String IMAGE="image";

    Table table;
    public UserTable(Context context) {
        super(context);
        table=Table.create(TABLE_NAME)
                .addField(ID,TYPE_INTEGER_PRIMARY_KEY)
                .addField(LOGIN,TYPE_TEXT)
                .addField(GITHUB_ID,TYPE_TEXT)
                .addField(AVATAR_URL,TYPE_TEXT)
                .addField(HTML_URL,TYPE_TEXT)
                .addField(FOLLOWERS_URL,TYPE_TEXT)
                .addField(FOLLOWING_URL,TYPE_TEXT)
                .addField(REPOS_URL,TYPE_TEXT)
                .addField(IMAGE, TYPE_BLOB);
        onCreate(this.getWritableDatabase());
    }

    public long addUser(User user){
        ContentValues values=new ContentValues();
        values.put(LOGIN,user.getLogin());
        values.put(GITHUB_ID,user.getGithubId());
        values.put(AVATAR_URL,user.getAvatarUrl());
        values.put(HTML_URL,user.getHtmlUrl());
        values.put(FOLLOWERS_URL, user.getFollowersUrl());
        values.put(FOLLOWING_URL, user.getFollowingUrl());
        values.put(REPOS_URL,user.getReposUrl());
        values.put(IMAGE, user.getByteArray());
        return Crud.create(this,TABLE_NAME,values);
    }

    public User getUser(String username){
        String[] projection=table.getProjection();
        String selection=LOGIN+"=?";
        String[] seletionArgs={username};
        Cursor cursor= Crud.read(this, projection, TABLE_NAME, selection, seletionArgs, null);
        if(cursor!=null){
            cursor.moveToFirst();
            if(cursor.getCount()>0)
                return new User(cursor);
        }
        return null;
    }

    public ArrayList<User> getAllUsers(){
        ArrayList<User>list=new ArrayList<>();
        String sql="select * from "+TABLE_NAME;
        Cursor cursor=Crud.rawQuery(this, sql);
        if(cursor.moveToFirst()){
            User user;
            do{
                user=new User(cursor);
                list.add(user);
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
