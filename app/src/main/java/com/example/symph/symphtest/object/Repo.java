package com.example.symph.symphtest.object;

import android.database.Cursor;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

public class Repo {

    int id;
    String githubId;
    String name;
    String htmlUrl;
    String description;
    int userId;

    public Repo(){}

    public Repo(JSONObject json) throws JSONException {
        githubId=json.getString("id");
        name=json.getString("name");
        htmlUrl=json.getString("html_url");
        description=json.getString("description");
    }

    public Repo(Cursor cursor){
        id=cursor.getInt(0);
        githubId=cursor.getString(1);
        name=cursor.getString(2);
        htmlUrl=cursor.getString(3);
        description=cursor.getString(4);
        userId=cursor.getInt(5);
    }

    public Bundle toBundle(){
        Bundle bundle=new Bundle();
        bundle.putInt("id",getId());
        bundle.putString("githubId", getGithubId());
        bundle.putString("htmlUrl", getHtmlUrl());
        bundle.putString("description",getDescription());
        return bundle;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
