package com.example.symph.symphtest.object;

import android.database.Cursor;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Blob;
import java.util.IdentityHashMap;

/**
 * Created by Kenneth on 8/5/2016.
 */
public class User {
    int id;
    String login;
    String githubId;
    String gravatarId;
    String url;
    String htmlUrl;
    String type;
    String followersLink;
    boolean siteAdmin;
    byte[] byteArray;

    public User(Cursor cursor){
        id=Integer.parseInt(cursor.getString(0));
        login=cursor.getString(1);
        githubId=cursor.getString(2);
        gravatarId=cursor.getString(3);
        url=cursor.getString(4);
        htmlUrl=cursor.getString(5);
        type=cursor.getString(6);
        siteAdmin=getSiteAdmin(cursor.getString(7));
        byteArray=cursor.getBlob(8);
    }

    public User(JSONObject json) throws JSONException {
        login=json.getString("login");
        githubId=json.getString("id");
        gravatarId=json.getString("gravatar_id");
        url=json.getString("url");
        htmlUrl=json.getString("html_url");
        type=json.getString("type");
        siteAdmin=getSiteAdmin(json.getString("site_admin"));
        followersLink=json.getString("followers_url");
    }

    public User(Bundle bundle){
        id=bundle.getInt("id");
        login=bundle.getString("login");
        githubId=bundle.getString("githubId");
        gravatarId=bundle.getString("gravatarId");
        url=bundle.getString("url");
        htmlUrl=bundle.getString("htmlUrl");
        type=bundle.getString("type");
        siteAdmin=bundle.getBoolean("siteAdmin");
        byteArray=bundle.getByteArray("byteArray");
    }

    public User(){ }

    public Bundle toBundle(){
        Bundle bundle=new Bundle();
        bundle.putInt("id", getId());
        bundle.putString("login", getLogin());
        bundle.putString("gravatarId",getGravatarId());
        bundle.putString("url",getUrl());
        bundle.putString("htmlUrl",getHtmlUrl());
        bundle.putString("type", getType());
        bundle.putBoolean("siteAdmin",getSiteAdmin());
        bundle.putByteArray("byteArray",getByteArray());
        return bundle;
    }

    public String getFollowersLink() {
        return followersLink;
    }

    public void setFollowersLink(String followersLink) {
        this.followersLink = followersLink;
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    public boolean getSiteAdmin(String string){
        if(string.equals("false"))
            return false;
        else if(string.equals("true"))
            return true;
        else if(Integer.parseInt(string)==0)
            return false;
        else if(Integer.parseInt(string)==1)
            return true;
        return false;
    }

    public boolean getSiteAdmin(){ return siteAdmin; }

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

    public String getGithubId() {
        return githubId;
    }

    public void setGithubId(String githubId) {
        this.githubId = githubId;
    }

    public String getGravatarId() {
        return gravatarId;
    }

    public void setGravatarId(String gravatarId) {
        this.gravatarId = gravatarId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSiteAdmin() {
        return siteAdmin;
    }

    public void setSiteAdmin(boolean siteAdmin) {
        this.siteAdmin = siteAdmin;
    }
}
