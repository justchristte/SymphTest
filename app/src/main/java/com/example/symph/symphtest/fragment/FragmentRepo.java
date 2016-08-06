package com.example.symph.symphtest.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.symph.symphtest.R;
import com.example.symph.symphtest.adapter.FragmentRepoAdapter;
import com.example.symph.symphtest.database.RepoTable;
import com.example.symph.symphtest.helper.DividerItemDecoration;
import com.example.symph.symphtest.helper.Helper;
import com.example.symph.symphtest.helper.RecyclerTouchListener;
import com.example.symph.symphtest.object.Repo;

import java.util.ArrayList;

public class FragmentRepo extends Fragment {

    RecyclerView listView;
    ArrayList<Repo>list;
    FragmentRepoAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_followers_layout, container, false);
        listView= (RecyclerView) rootView.findViewById(R.id.fragment_followers_layout_list);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);

        list=new ArrayList<>();
        adapter=new FragmentRepoAdapter(getContext(),list);
        listView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        listView.setAdapter(adapter);
        listViewTouchListener();

        try{
            Bundle bundle=getArguments();
            int userId=bundle.getInt("userId");
            RepoTable repoTable=new RepoTable(getContext());
            ArrayList<Repo>repos=repoTable.getReposFor(userId);
            for(int c=0;c<repos.size();c++)
                list.add(repos.get(c));
            adapter.notifyDataSetChanged();
        }catch (SQLiteException e){
            e.printStackTrace();
        }

        return rootView;
    }

    private void listViewTouchListener() {
        listView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), listView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Repo repo = list.get(position);
                DialogInterface.OnClickListener listener = getBuilderClickListener(repo);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("View " + repo.getName() + " on github?").setPositiveButton("Yes", listener)
                        .setNegativeButton("No", listener).show();
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

    public DialogInterface.OnClickListener getBuilderClickListener(final Repo repo){
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Helper.viewOnBrowser(getContext(), repo.getHtmlUrl());
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };
    }

}
