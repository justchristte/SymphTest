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

/**
 * Created by Kenneth on 8/6/2016.
 */
public class FragmentFollowerAdapter extends RecyclerView.Adapter<FragmentFollowerAdapter.ViewHolder>{

    Context context;
    ArrayList<Follower> followers;
    LayoutInflater inflater;
    private final OnItemClickListener listener;

    public FragmentFollowerAdapter(Context context,ArrayList<Follower>followers,OnItemClickListener listener) {
        this.context=context;
        this.followers=followers;
        this.listener = listener;
        inflater= LayoutInflater.from(context);
    }

    public interface OnItemClickListener {
        void onItemClick(Follower follower);
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
            holder.bind(follower,listener);
        }
    }

    @Override
    public int getItemCount() {
        return followers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public CircleImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            username= (TextView) itemView.findViewById(R.id.user_list_adapter_layout_username);
            image= (CircleImageView) itemView.findViewById(R.id.user_list_adapter_layout_image);
        }

        public void bind(final Follower follower, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(follower);
                }
            });
        }
    }
}
