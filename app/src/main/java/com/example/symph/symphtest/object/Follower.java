package com.example.symph.symphtest.object;

import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kenneth on 8/6/2016.
 */
public class Follower {
    int id;
    String login;
    String followersLink;
    String followedLink;
    String reposLink;
    int userId;
    byte[] byteArray;

    public Follower(){}

    public Follower(Cursor cursor){
        id=cursor.getInt(0);
        login=cursor.getString(1);
        followersLink=cursor.getString(2);
        followedLink=cursor.getString(3);
        reposLink=cursor.getString(4);
        userId=cursor.getInt(5);
        byteArray=cursor.getBlob(6);
    }

    public Follower(JSONObject json) throws JSONException {
        login=json.getString("login");
        followersLink=json.getString("followers_url");
        followedLink=json.getString("following_url");
        reposLink=json.getString("repos_url");
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

    public String getFollowersLink() {
        return followersLink;
    }

    public void setFollowersLink(String followersLink) {
        this.followersLink = followersLink;
    }

    public String getFollowedLink() {
        return followedLink;
    }

    public void setFollowedLink(String followedLink) {
        this.followedLink = followedLink;
    }

    public String getReposLink() {
        return reposLink;
    }

    public void setReposLink(String reposLink) {
        this.reposLink = reposLink;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
