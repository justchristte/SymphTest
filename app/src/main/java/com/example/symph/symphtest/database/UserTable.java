package com.example.symph.symphtest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.symph.symphtest.object.User;

import java.util.ArrayList;

/**
 * Created by Kenneth on 8/5/2016.
 */
public class UserTable extends DatabaseHandler{


    public static String TABLE_NAME="user";
    public static String ID="id";
    public static String LOGIN="login";
    public static String GITHUB_ID="github_id";
    public static String GRAVATAR="gravatar";
    public static String URL="url";
    public static String HTML_URL="html_url";
    public static String TYPE="TYPE";
    public static String SITE_ADMIN="site_admin";
    public static String IMAGE="image";
    Table table;

    public UserTable(Context context) {
        super(context);
        table=Table.create(TABLE_NAME)
                .addField(ID, Table.TYPE_INTEGER_PRIMARY_KEY)
                .addField(LOGIN,Table.TYPE_TEXT)
                .addField(GITHUB_ID,Table.TYPE_TEXT)
                .addField(GRAVATAR,Table.TYPE_TEXT)
                .addField(URL,Table.TYPE_TEXT)
                .addField(HTML_URL,Table.TYPE_TEXT)
                .addField(TYPE,Table.TYPE_TEXT)
                .addField(SITE_ADMIN,Table.TYPE_INTEGER)
                .addField(IMAGE,Table.TYPE_BLOB);
        onCreate(this.getWritableDatabase());
    }

    public long addUser(String login,String githubId,String gravatar,String url,
                        String htmUrl,String type,int siteAdmin,byte[] byteArray){
        ContentValues values=new ContentValues();
        values.put(LOGIN,login);
        values.put(GITHUB_ID,githubId);
        values.put(GRAVATAR,gravatar);
        values.put(URL,url);
        values.put(HTML_URL,htmUrl);
        values.put(TYPE, type);
        values.put(SITE_ADMIN, siteAdmin);
        values.put(IMAGE,byteArray);
        return Crud.create(this,TABLE_NAME,values);
    }

    public long addUser(User user){
        int siteAdmin;
        if(user.isSiteAdmin())
            siteAdmin=1;
        else
            siteAdmin=0;
        ContentValues values=new ContentValues();
        values.put(LOGIN,user.getLogin());
        values.put(GITHUB_ID,user.getGithubId());
        values.put(GRAVATAR,user.getGravatarId());
        values.put(URL,user.getUrl());
        values.put(HTML_URL,user.getHtmlUrl());
        values.put(TYPE, user.getType());
        values.put(SITE_ADMIN,siteAdmin);
        values.put(IMAGE,user.getByteArray());
        return Crud.create(this,TABLE_NAME,values);
    }

    public User getUser(int id){
        String[] projection=table.getProjection();
        String selection=ID+"=?";
        String[] seletionArgs={String.valueOf(id)};
        Cursor cursor= Crud.read(this,projection,TABLE_NAME,selection,seletionArgs,null);
        User user=new User();
        if(cursor!=null){
            cursor.moveToFirst();
            user=new User(cursor);
        }
        return user;
    }

    public ArrayList<User> getAllUsers(){
        ArrayList<User>list=new ArrayList<>();
        String sql="select * from "+TABLE_NAME;
        Cursor cursor=Crud.rawQuery(this,sql);
        if(cursor.moveToFirst()){
            User user;
            do{
                user=new User(cursor);
                list.add(user);
            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean savedUser(User user){
        return savedUser(user.getGithubId());
    }

    public boolean savedUser(String userName){
        String[] projection=table.getProjection();
        String selection=LOGIN+"=?";
        String[] seletionArgs={userName};
        Cursor cursor= Crud.read(this,projection,TABLE_NAME,selection,seletionArgs,null);
        if(cursor!=null)
            return true;
        return false;
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
