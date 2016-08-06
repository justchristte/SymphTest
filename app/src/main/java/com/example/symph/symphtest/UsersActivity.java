package com.example.symph.symphtest;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.RelativeLayout;

import com.example.symph.symphtest.adapter.UsersActivityAdapter;
import com.example.symph.symphtest.database.UserTable;
import com.example.symph.symphtest.helper.Helper;
import com.example.symph.symphtest.object.User;

import java.util.ArrayList;

/**
 * Created by Kenneth on 8/5/2016.
 */
public class UsersActivity extends AppCompatActivity implements UsersActivityAdapter.OnItemClickListener{

    RecyclerView listView;
    private StaggeredGridLayoutManager layoutManager;
    ArrayList<User>list;
    UsersActivityAdapter adapter;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        listView= (RecyclerView) findViewById(R.id.activity_user_list_users);
        relativeLayout= (RelativeLayout) findViewById(R.id.activity_user_list_layout);

        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);

        list=new ArrayList<>();
        adapter=new UsersActivityAdapter(getApplicationContext(),list,this);
        listView.setAdapter(adapter);

        try{
            UserTable userTable=new UserTable(this);
            ArrayList<User>users=userTable.getAllUsers();
            for(int c=0;c<users.size();c++)
                list.add(users.get(c));
            adapter.notifyDataSetChanged();
        }
        catch (SQLiteException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(User item) {
        Intent intent=new Intent(UsersActivity.this,DrawerActivity.class);
        intent.putExtras(item.toBundle());
        startActivity(intent);
    }
}
