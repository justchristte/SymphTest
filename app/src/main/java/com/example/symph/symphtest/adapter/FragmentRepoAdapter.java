package com.example.symph.symphtest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.symph.symphtest.R;
import com.example.symph.symphtest.object.Repo;

import java.util.ArrayList;

public class FragmentRepoAdapter extends RecyclerView.Adapter<FragmentRepoAdapter.ViewHolder> {

    Context context;
    LayoutInflater inflater;
    ArrayList<Repo>repos;

    public FragmentRepoAdapter(Context context,ArrayList<Repo>repos){
        this.context=context;
        this.repos=repos;
        inflater= LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.user_list_adapter_layout,parent);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Repo repo=repos.get(position);
        if(repo!=null){
            holder.description.setText(repo.getDescription());
            holder.name.setText(repo.getName());
        }
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,description;

        public ViewHolder(View itemView) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.repo_adapter_layout_name);
            description= (TextView) itemView.findViewById(R.id.repo_adapter_layout_description);
        }
    }
}
