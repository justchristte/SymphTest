package com.example.symph.symphtest.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.symph.symphtest.DrawerActivity;
import com.example.symph.symphtest.R;
import com.example.symph.symphtest.adapter.FragmentFollowerAdapter;
import com.example.symph.symphtest.database.FollowerTable;
import com.example.symph.symphtest.database.FollowingTable;
import com.example.symph.symphtest.database.UserTable;
import com.example.symph.symphtest.helper.DataWrapper;
import com.example.symph.symphtest.helper.DividerItemDecoration;
import com.example.symph.symphtest.helper.Helper;
import com.example.symph.symphtest.helper.RecyclerTouchListener;
import com.example.symph.symphtest.object.Follower;
import com.example.symph.symphtest.object.User;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class FragmentFollowers extends Fragment{

    RecyclerView listView;
    ArrayList<Follower>list;
    FragmentFollowerAdapter adapter;
    FragmentActivity context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_followers_layout, container, false);
        listView= (RecyclerView) rootView.findViewById(R.id.fragment_followers_layout_list);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);
        context=getActivity();

        list=new ArrayList<>();
        adapter=new FragmentFollowerAdapter(getActivity(),list);
        listView.addItemDecoration(new DividerItemDecoration(context, LinearLayout.VERTICAL));
        listView.setAdapter(adapter);
        listViewTouchListener();

        Bundle bundle=getArguments();
        int userId=bundle.getInt("userId");
        if(bundle.getBoolean("isFollower"))
            getFollowers(userId);
        else
            getFollowing(userId);

        return rootView;
    }

    public void getFollowers(int userId){
        try{
            FollowerTable followerTable=new FollowerTable(getContext());
            ArrayList<Follower>followers=followerTable.getFollowersFor(userId);
            for(int c=0;c<followers.size();c++)
                list.add(followers.get(c));
            adapter.notifyDataSetChanged();
        }catch (SQLiteException e){
            e.printStackTrace();
        }
    }

    public void getFollowing(int userId){
        try{
            FollowingTable followingTable=new FollowingTable(getContext());
            ArrayList<Follower>following=followingTable.getFollowingFor(userId);
            for(int c=0;c<following.size();c++)
                list.add(following.get(c));
            adapter.notifyDataSetChanged();
        }catch (SQLiteException e){
            e.printStackTrace();
        }
    }

    public void listViewTouchListener(){
        listView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), listView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String username = list.get(position).getLogin();
                        DataWrapper wrapper = new DataWrapper(getActivity());
                        try {
                            UserTable userTable = new UserTable(getContext());
                            User user = userTable.getUser(username);
                            if (user == null)
                                user = wrapper.fetchUserData(username, true);
                            Intent intent = new Intent(getActivity(), DrawerActivity.class);
                            intent.putExtras(user.toBundle());
                            startActivity(intent);
                        } catch (IOException e) {
                            Helper.displayMessage(e.getMessage(), getActivity().findViewById(R.id.drawer_layout));
                            wrapper.dismissDialog();
                            e.printStackTrace();
                        } catch (JSONException e) {
                            Helper.displayMessage("a problem occured while fetching data", getActivity().findViewById(R.id.drawer_layout));
                            wrapper.dismissDialog();
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
