package com.example.symph.symphtest.fragment;

import android.app.Activity;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.symph.symphtest.R;
import com.example.symph.symphtest.adapter.FragmentFollowerAdapter;
import com.example.symph.symphtest.database.FollowerTable;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_followers_layout, container, false);
        listView= (RecyclerView) rootView.findViewById(R.id.fragment_followers_layout_list);
        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);

        list=new ArrayList<>();
        adapter=new FragmentFollowerAdapter(getContext(),list,this);
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
    public void onItemClick(Follower follower) {

    }
}
