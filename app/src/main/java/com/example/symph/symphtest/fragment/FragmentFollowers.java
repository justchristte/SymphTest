package com.example.symph.symphtest.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.symph.symphtest.R;
import com.example.symph.symphtest.adapter.FragmentFollowerAdapter;
import com.example.symph.symphtest.database.FollowerTable;
import com.example.symph.symphtest.helper.DividerItemDecoration;
import com.example.symph.symphtest.helper.Helper;
import com.example.symph.symphtest.object.Follower;

import java.util.ArrayList;

/**
 * Created by Kenneth on 8/6/2016.
 */
public class FragmentFollowers extends Fragment implements FragmentFollowerAdapter.OnItemClickListener{

    RecyclerView listView;
    ArrayList<Follower>list;
    private StaggeredGridLayoutManager layoutManager;
    FragmentFollowerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_followers_layout, container, false);
        listView= (RecyclerView) rootView.findViewById(R.id.fragment_followers_layout_list);
        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);

        list=new ArrayList<>();
        adapter=new FragmentFollowerAdapter(getContext(),list,this);
        listView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        listView.setAdapter(adapter);

        try{
            Bundle bundle=getArguments();
            int userId=bundle.getInt("userId");
            FollowerTable followerTable=new FollowerTable(getContext());
            ArrayList<Follower> followers=followerTable.getFollowersFor(userId);
            for(int c=0;c<followers.size();c++)
                list.add(followers.get(c));
            adapter.notifyDataSetChanged();
        } catch (SQLiteException e){
            e.printStackTrace();
        }


        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemClick(final Follower follower) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Helper.viewOnBrowser(getContext(),"http://github.com/"+follower.getLogin());
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("View "+follower.getLogin()+" on github?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
}
