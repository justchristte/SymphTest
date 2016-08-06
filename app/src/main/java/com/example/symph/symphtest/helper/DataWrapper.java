package com.example.symph.symphtest.helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;

import com.example.symph.symphtest.database.FollowerTable;
import com.example.symph.symphtest.database.FollowingTable;
import com.example.symph.symphtest.database.RepoTable;
import com.example.symph.symphtest.database.UserTable;
import com.example.symph.symphtest.object.Follower;
import com.example.symph.symphtest.object.Repo;
import com.example.symph.symphtest.object.User;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class DataWrapper {
    private Activity activity;
    private ProgressDialog progressDialog;

    public DataWrapper(Activity activity){
        this.activity=activity;
    }

    public User fetchUserData(String username,boolean showProgressDialog) throws IOException, JSONException {
        if(showProgressDialog)
            showProgressDialog("Downloading Content",username+" data");
        User user=getUser(username);
        ArrayList<User>followers=getUserList(user.getFollowersUrl());
        ArrayList<User>following=getUserList(user.getFollowingUrl());
        ArrayList<Repo>repos=getRepos(user.getReposUrl());

        UserTable userTable=new UserTable(activity);
        FollowerTable followerTable=new FollowerTable(activity);
        FollowingTable followingTable=new FollowingTable(activity);
        RepoTable repoTable=new RepoTable(activity);

        Repo repo;
        Follower follower;

        int userId=(int)userTable.addUser(user);

        for(int c=0;c<followers.size();c++){
            follower=new Follower(followers.get(c));
            follower.setId(userId);
            followerTable.addFollower(follower);
        }
        for(int c=0;c<following.size();c++) {
            follower=new Follower(following.get(c));
            follower.setId(userId);
            followingTable.addFollowing(follower);
        }
        for(int c=0;c<repos.size();c++) {
            repo=repos.get(c);
            repo.setId(userId);
            repoTable.addRepo(repo);
        }
        dismissDialog();
        return user;
    }

    public ArrayList<Repo> getRepos(String url) throws JSONException {
        ArrayList<Repo>list=new ArrayList<>();
        updateProgressMessage("repos");
        JSONArray array=new JSONArray(Helper.getJsonResponse(url));
        Repo repo;
        for(int c=0;c<array.length();c++){
            repo=new Repo(array.getJSONObject(c));
            list.add(repo);
        }
        return list;
    }

    public ArrayList<User> getUserList(String url) throws JSONException, IOException {
        ArrayList<User>list=new ArrayList<>();
        updateProgressMessage("follower datas");
        JSONArray array=new JSONArray(Helper.getJsonResponse(url));
        User user;
        for(int c=0;c<array.length();c++){
            user=new User(array.getJSONObject(c));
            updateProgressMessage(user.getLogin()+" avatar");
            Bitmap bitmap=Helper.getAvatar(user.getAvatarUrl());
            if(bitmap!=null)
                user.setByteArray(Helper.toByteArray(bitmap));
            else
                throw new IOException("Unable to download image from "+user.getAvatarUrl());
            list.add(user);
        }
        return list;
    }

    public User getUser(String username) throws JSONException, IOException {
        User user=new User(Helper.getJSON(username));
        updateProgressMessage(username+" avatar");
        Bitmap bitmap=Helper.getAvatar(user.getAvatarUrl());
        if(bitmap!=null)
            user.setByteArray(Helper.toByteArray(bitmap));
        else
            throw new IOException("Unable to download image from "+user.getAvatarUrl());
        return user;
    }

    public void updateProgressMessage(final String message){
        if(progressDialog!=null)
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.setMessage(message);
                }
            });
    }

    public void dismissDialog(){
        if(progressDialog!=null)
            progressDialog.dismiss();
    }

    public void showProgressDialog(final String title, final String message){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog=ProgressDialog.show(activity,title,message,true);
            }
        });
    }
}
