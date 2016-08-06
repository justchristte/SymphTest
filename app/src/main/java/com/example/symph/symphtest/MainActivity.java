package com.example.symph.symphtest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.symph.symphtest.database.FollowerTable;
import com.example.symph.symphtest.database.UserTable;
import com.example.symph.symphtest.helper.Helper;
import com.example.symph.symphtest.object.Follower;
import com.example.symph.symphtest.object.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    EditText userName;
    Button add,view;
    ProgressDialog progressDialog;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName= (EditText) findViewById(R.id.activity_main_username);
        add= (Button) findViewById(R.id.activity_main_add_user);
        view= (Button) findViewById(R.id.activity_main_add_view);
        relativeLayout= (RelativeLayout) findViewById(R.id.activity_main_layout);

        addClickListener();
        viewClickListener();
    }

    public void addClickListener(){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasError()) {
                    showProgressDialog();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String string = userName.getText().toString();
                                setDialogMessage(string + " avatar");
                                User user = Helper.getUser(string);
                                long userId = saveUser(user);
                                if (userId > 0) {
                                    setDialogTitle("Downloading follower data");
                                    saveFollowers(user.getFollowersLink(), userId);
                                    gotoUsersActivity();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Helper.displayMessage("Problem occured when adding user", relativeLayout);
                            }
                            progressDialog.dismiss();
                        }
                    }).start();
                }
            }
        });
    }

    public void saveFollowers(String link,long userId){
        try {
            String response=Helper.getJsonResponse(link);
            JSONArray array=new JSONArray(response);
            JSONObject json;
            Follower follower;
            int id=(int)userId;
            for(int c=0;c<array.length();c++){
                json=array.getJSONObject(c);
                follower=new Follower(json,id);
                setDialogMessage(follower.getLogin()+" avatar");
                Bitmap bitmap=Helper.getImage(json.getString("avatar_url"));
                if(bitmap!=null)
                    follower.setByteArray(Helper.toByteArray(bitmap));
                saveFollower(follower);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public long saveFollower(Follower follower){
        FollowerTable followerTable=new FollowerTable(this);
        if(follower!=null)
            return followerTable.addFollower(follower);
        return 0;
    }

    public long saveUser(User user){
        UserTable userTable=new UserTable(this);
        if(user!=null)
            return userTable.addUser(user);
        return 0;
    }

    public void viewClickListener(){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUsersActivity();
            }
        });
    }

    public boolean hasError(){
        String string=userName.getText().toString();
        if(string.equals("")){
            Helper.displayMessage("Please enter a username", relativeLayout);
            return true;
        }
        return false;
    }

    public void gotoUsersActivity(){
        startActivity(new Intent(MainActivity.this, UsersActivity.class));
    }

    public void setDialogMessage(final String string){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.setMessage(string);
            }
        });
    }

    public void setDialogTitle(final String string){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.setTitle(string);
            }
        });
    }

    public void showProgressDialog(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog = ProgressDialog.show(MainActivity.this, "Downloading user data", "Please wait...", true);
            }
        });
    }

}
