package com.example.symph.symphtest.object;

import android.database.Cursor;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    int id;
    String login;
    String githubId;
    String avatarUrl;
    String htmlUrl;
    String followersUrl;
    String followingUrl;
    String reposUrl;
    byte[] byteArray;

    public User(){}

    public User(JSONObject json) throws JSONException {
        login=json.getString("login");
        githubId=json.getString("id");
        avatarUrl=json.getString("avatar_url");
        htmlUrl=json.getString("html_url");
        followersUrl=json.getString("followers_url");
        followingUrl=json.getString("following_url").replace("{/other_user}","");
        reposUrl=json.getString("repos_url");
    }

    public User(Cursor cursor){
        id=cursor.getInt(0);
        login=cursor.getString(1);
        githubId=cursor.getString(2);
        avatarUrl=cursor.getString(3);
        htmlUrl=cursor.getString(4);
        followersUrl=cursor.getString(5);
        followingUrl=cursor.getString(6);
        reposUrl=cursor.getString(7);
        byteArray=cursor.getBlob(8);
    }

    public Bundle toBundle(){
        Bundle bundle=new Bundle();
        bundle.putInt("id", getId());
        bundle.putString("login", getLogin());
        bundle.putString("githubId",getGithubId());
        bundle.putString("avatarUrl",getAvatarUrl());
        bundle.putString("htmlUrl",getHtmlUrl());
        bundle.putString("followersUrl", getFollowersUrl());
        bundle.putString("followingUrl", getFollowingUrl());
        bundle.putString("reposUrl",getReposUrl());
        bundle.putByteArray("byteArray",getByteArray());
        return bundle;
    }

    public User(Bundle bundle){
        id=bundle.getInt("id");
        login=bundle.getString("login");
        githubId=bundle.getString("githubId");
        avatarUrl=bundle.getString("avatarUrl");
        htmlUrl=bundle.getString("htmlUrl");
        followersUrl=bundle.getString("followersUrl");
        followingUrl=bundle.getString("followingUrl");
        reposUrl=bundle.getString("reposUrl");
        byteArray=bundle.getByteArray("byteArray");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGithubId() {
        return githubId;
    }

    public void setGithubId(String githubId) {
        this.githubId = githubId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
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

    public byte[] getByteArray() {
        return byteArray;
    }

    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }

}
