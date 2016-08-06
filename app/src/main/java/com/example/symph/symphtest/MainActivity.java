package com.example.symph.symphtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.symph.symphtest.database.UserTable;
import com.example.symph.symphtest.helper.DataWrapper;
import com.example.symph.symphtest.helper.Helper;

import org.json.JSONException;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    EditText userName;
    Button add,view;
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
                if(!hasError()){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            DataWrapper wrapper=new DataWrapper(MainActivity.this);
                            try {
                                wrapper.fetchUserData(userName.getText().toString(), true);
                                gotoUsersActivity();
                            } catch (IOException e) {
                                Helper.displayMessage(e.getMessage(),relativeLayout);
                                wrapper.dismissDialog();
                                e.printStackTrace();
                            } catch (JSONException e) {
                                Helper.displayMessage("a problem occured while fetching data",relativeLayout);
                                wrapper.dismissDialog();
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
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
        UserTable userTable=new UserTable(this);
        if(string.equals("")){
            Helper.displayMessage("Please enter a username", relativeLayout);
            return true;
        }
        else if(userTable.getUser(string)!=null){
            Helper.displayMessage("User already added",relativeLayout);
            return true;
        }
        return false;
    }

    public void gotoUsersActivity(){
        startActivity(new Intent(MainActivity.this, UsersActivity.class));
    }

}
