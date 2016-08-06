package com.example.symph.symphtest.object;

import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

public class Follower {
    int id;
    String login;
    String followersUrl;
    String followingUrl;
    String reposUrl;
    int userId;
    byte[] byteArray;

    public Follower(){}

    public Follower(User user){
        login=user.getLogin();
        followersUrl=user.followersUrl;
        followingUrl=user.getFollowingUrl();
        reposUrl=user.getReposUrl();
        userId=user.getId();
        byteArray=user.getByteArray();
    }

    public Follower(Cursor cursor){
        id=cursor.getInt(0);
        login=cursor.getString(1);
        followersUrl =cursor.getString(2);
        followingUrl =cursor.getString(3);
        reposUrl =cursor.getString(4);
        userId=cursor.getInt(5);
        byteArray=cursor.getBlob(6);
    }

    public Follower(JSONObject json) throws JSONException {
        login=json.getString("login");
        followersUrl =json.getString("followers_url");
        followingUrl =json.getString("following_url");
        reposUrl =json.getString("repos_url");
    }

    public Follower(JSONObject json,int userId) throws JSONException {
        this(json);
        this.userId=userId;
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFollowersUrl() {
        return followersUrl;
    }

    public void setFollowersUrl(String followersUrl) {
        this.followersUrl = followersUrl;
    }

    public String getFollowingUrl() {
        return followingUrl;
    }

    public void setFollowingUrl(String followingUrl) {
        this.followingUrl = followingUrl;
    }

    public String getReposUrl() {
        return reposUrl;
    }

    public void setReposUrl(String reposUrl) {
        this.reposUrl = reposUrl;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
