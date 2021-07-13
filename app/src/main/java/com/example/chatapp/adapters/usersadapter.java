package com.example.chatapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.chatapp.R;
import com.example.chatapp.chatactivity;
import com.example.chatapp.dashboard;
import com.example.chatapp.models.modelusers;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class usersadapter extends RecyclerView.Adapter<usersadapter.holder> {

    List<modelusers> list;
    Context context;
    private usersadapter.holder holder;
    private int position;

    public usersadapter(List<modelusers> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new usersadapter.holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_recycler_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(usersadapter.holder holder, int position) {
        Glide.with(context).load(list.get(position).getProfilepic()).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(holder.image);
        holder.name.setText(list.get(position).getName());
        holder.status.setText(list.get(position).getStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, chatactivity.class);
                intent.putExtra("name",list.get(position).getName());
                intent.putExtra("email",list.get(position).getEmail());
                intent.putExtra("password",list.get(position).getPassword());
                intent.putExtra("image",list.get(position).getProfilepic());
                intent.putExtra("uid",list.get(position).getUid());
                intent.putExtra("status",list.get(position).getStatus());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class holder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView name,status;

        public holder(View itemView) {
            super(itemView);


            image=itemView.findViewById(R.id.circleImageView);
            name=itemView.findViewById(R.id.name);
            status=itemView.findViewById(R.id.status);

        }

    }
}
