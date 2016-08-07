package com.example.symph.symphtest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.symph.symphtest.R;
import com.example.symph.symphtest.helper.Helper;
import com.example.symph.symphtest.object.Follower;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentFollowerAdapter extends RecyclerView.Adapter<FragmentFollowerAdapter.ViewHolder>{

    Context context;
    ArrayList<Follower> followers;
    LayoutInflater inflater;

    public FragmentFollowerAdapter(Context context,ArrayList<Follower>followers) {
        this.context=context;
        this.followers=followers;
        inflater= LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public CircleImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            username= (TextView) itemView.findViewById(R.id.user_list_adapter_layout_username);
            image= (CircleImageView) itemView.findViewById(R.id.user_list_adapter_layout_image);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.user_list_adapter_layout,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Follower follower = followers.get(position);
        if(follower!=null){
            holder.username.setText(follower.getLogin());
            holder.image.setImageBitmap(Helper.decodeImage(follower.getByteArray()));
        }
    }

    @Override
    public int getItemCount() {
        return followers.size();
    }

}
