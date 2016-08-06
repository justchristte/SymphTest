package com.example.symph.symphtest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.symph.symphtest.R;
import com.example.symph.symphtest.helper.Helper;
import com.example.symph.symphtest.object.User;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersActivityAdapter extends RecyclerView.Adapter<UsersActivityAdapter.ViewHolder> {

    Context context;
    ArrayList<User>users;
    LayoutInflater inflater;

    public UsersActivityAdapter(Context context, ArrayList<User> users){
        this.context=context;
        this.users=users;
        inflater= LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.user_list_adapter_layout,parent);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = users.get(position);
        if(user!=null){
            holder.username.setText(user.getLogin());
            holder.image.setImageBitmap(Helper.decodeImage(user.getByteArray()));
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView username;

        public ViewHolder(View itemView) {
            super(itemView);
            username= (TextView) itemView.findViewById(R.id.user_list_adapter_layout_username);
            image= (CircleImageView) itemView.findViewById(R.id.user_list_adapter_layout_image);
        }
    }
}
