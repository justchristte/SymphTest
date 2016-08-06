package com.example.symph.symphtest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.symph.symphtest.object.Follower;

import java.util.ArrayList;

public class FollowingTable extends DatabaseHandler implements Table.FieldTypes{

    public static String TABLE_NAME="following";
    public static String ID="id";
    public static String LOGIN="login";
    public static String FOLLOWERS_URL="followers_url";
    public static String FOLLOWED_URL="following_url";
    public static String REPOS_URL="repos_url";
    public static String USER_ID="user_id";
    public static String IMAGE="image";
    Table table;

    public FollowingTable(Context context) {
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

    public long addFollowing(String login,String followers,String followed,
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

    public long addFollowing(Follower follower){
        return addFollowing(follower.getLogin(), follower.getFollowersUrl(),
                follower.getFollowingUrl(), follower.getReposUrl(),
                follower.getUserId(), follower.getByteArray());
    }

    public ArrayList<Follower> getFollowingFor(int userId){
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
