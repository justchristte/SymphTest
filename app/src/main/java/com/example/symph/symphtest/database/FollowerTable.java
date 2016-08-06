package com.example.symph.symphtest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.symph.symphtest.object.Follower;
import com.example.symph.symphtest.object.User;

import java.util.ArrayList;

/**
 * Created by Kenneth on 8/6/2016.
 */
public class FollowerTable extends DatabaseHandler {

    public static String TABLE_NAME="follower";
    public static String ID="id";
    public static String LOGIN="login";
    public static String FOLLOWERS_LINK="followers_link";
    public static String FOLLOWED_LINK="followed_link";
    public static String REPOS_LINK="repos_link";
    public static String USER_ID="user_id";
    public static String IMAGE="image";
    Table table;

    public FollowerTable(Context context) {
        super(context);
        table=Table.create(TABLE_NAME)
                .addField(ID,Table.TYPE_INTEGER_PRIMARY_KEY)
                .addField(LOGIN,Table.TYPE_TEXT)
                .addField(FOLLOWERS_LINK,Table.TYPE_TEXT)
                .addField(FOLLOWED_LINK,Table.TYPE_TEXT)
                .addField(REPOS_LINK,Table.TYPE_TEXT)
                .addField(USER_ID, Table.TYPE_INTEGER)
                .addField(IMAGE,Table.TYPE_BLOB);
        onCreate(this.getWritableDatabase());
    }

    public long addFollower(String login,String followers,String followed,
                            String repos,int userId,byte[] byteArray){
        ContentValues values=new ContentValues();
        values.put(LOGIN,login);
        values.put(FOLLOWERS_LINK,followers);
        values.put(FOLLOWED_LINK,followed);
        values.put(REPOS_LINK, repos);
        values.put(USER_ID,userId);
        values.put(IMAGE, byteArray);
        return Crud.create(this,TABLE_NAME,values);
    }

    public long addFollower(Follower follower){
        return addFollower(follower.getLogin(), follower.getFollowersLink(),
                follower.getFollowedLink(), follower.getReposLink(),
                follower.getUserId(), follower.getByteArray());
    }

    public ArrayList<Follower> getFollowersFor(int userId){
        ArrayList<Follower>list=new ArrayList<>();
        String sql="select * from "+TABLE_NAME+" where "+USER_ID+"="+userId;
        Cursor cursor=Crud.rawQuery(this,sql);
        int c=0;
        if(cursor.moveToFirst()){
            do{
                c++;
                list.add(new Follower(cursor));
            }while (cursor.moveToNext());
        }
        Log.e("Size",""+c);
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
