package com.example.symph.symphtest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.symph.symphtest.object.Follower;

import java.util.ArrayList;

public class FollowerTable extends DatabaseHandler implements Table.FieldTypes{

    public static String TABLE_NAME="follower";
    public static String ID="id";
    public static String LOGIN="login";
    public static String FOLLOWERS_URL="followers_url";
    public static String FOLLOWED_URL ="following_url";
    public static String REPOS_URL ="repos_url";
    public static String USER_ID="user_id";
    public static String IMAGE="image";
    Table table;

    public FollowerTable(Context context) {
        super(context);
        table=Table.create(TABLE_NAME)
                .addField(ID,TYPE_INTEGER_PRIMARY_KEY)
                .addField(LOGIN,TYPE_TEXT)
                .addField(FOLLOWERS_URL,TYPE_TEXT)
                .addField(FOLLOWED_URL,TYPE_TEXT)
                .addField(REPOS_URL,TYPE_TEXT)
                .addField(USER_ID, TYPE_INTEGER)
                .addField(IMAGE,TYPE_BLOB);
        onCreate(this.getWritableDatabase());
    }

    public long addFollower(String login,String followers,String followed,
                            String repos,int userId,byte[] byteArray){
        ContentValues values=new ContentValues();
        values.put(LOGIN,login);
        values.put(FOLLOWERS_URL,followers);
        values.put(FOLLOWED_URL,followed);
        values.put(REPOS_URL, repos);
        values.put(USER_ID,userId);
        values.put(IMAGE, byteArray);
        return Crud.create(this,TABLE_NAME,values);
    }

    public long addFollower(Follower follower){
        return addFollower(follower.getLogin(), follower.getFollowersUrl(),
                follower.getFollowingUrl(), follower.getReposUrl(),
                follower.getUserId(), follower.getByteArray());
    }

    public void addFollowers(ArrayList<Follower>followers){
        SQLiteDatabase db=this.getWritableDatabase();
        SQLiteStatement statement=db.compileStatement(table.getInsertStatementExcept(new String[]{ID}));

        db.beginTransaction();
        Follower follower;
        for (int c=0;c<followers.size();c++){
            follower=followers.get(c);
            statement.clearBindings();
            statement.bindString(1, follower.getLogin());
            statement.bindString(2,follower.getFollowersUrl());
            statement.bindString(3,follower.getFollowingUrl());
            statement.bindString(4,follower.getReposUrl());
            statement.bindLong(5,follower.getUserId());
            statement.bindBlob(6,follower.getByteArray());
            statement.execute();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public ArrayList<Follower> getFollowersFor(int userId){
        ArrayList<Follower>list=new ArrayList<>();
        String sql="select * from "+TABLE_NAME+" where "+USER_ID+"="+userId;

        Cursor cursor=Crud.rawQuery(this,sql);
        if(cursor.moveToFirst()){
            do{
                list.add(new Follower(cursor));
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
