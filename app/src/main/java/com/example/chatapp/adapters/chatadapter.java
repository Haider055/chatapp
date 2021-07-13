package com.example.chatapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatapp.R;
import com.example.chatapp.models.modelmessages;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.chatapp.chatactivity.recieverimage;
import static com.example.chatapp.chatactivity.senderimage;

public class chatadapter extends RecyclerView.Adapter {

    List<modelmessages> list;
    Context context;

    public chatadapter(List<modelmessages> list, Context context) {
        this.list = list;
        this.context = context;
    }
    int reciever=1;
    int sender=2;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        if (viewType==reciever){
            return new recieverholder(LayoutInflater.from(context).inflate(R.layout.reciever_layoutchatmessages,parent,false));
        }
        else {
            return new senderholder(LayoutInflater.from(context).inflate(R.layout.sender_layoutchatmessages,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder.getClass()==senderholder.class){
            ((senderholder) holder).sendertext.setText(list.get(position).getMessage());
            Glide.with(context).load(senderimage).into(((senderholder) holder).senderimage);
        }
        else {
            ((recieverholder) holder).recievertext.setText(list.get(position).getMessage());
            Glide.with(context).load(recieverimage).into(((recieverholder) holder).recieverimage);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ((senderholder)holder).sendertext.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        if (FirebaseAuth.getInstance().getUid().equals(list.get(position).getSenderid()))
            return sender;
        else
        return reciever;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class senderholder extends RecyclerView.ViewHolder{

        CircleImageView senderimage;
        TextView sendertext;

        public senderholder(View itemView) {
            super(itemView);

            senderimage=itemView.findViewById(R.id.image);
            sendertext=itemView.findViewById(R.id.message);

        }
    }

    public class recieverholder extends RecyclerView.ViewHolder{

        CircleImageView recieverimage;
        TextView recievertext;
        public recieverholder(View itemView) {
            super(itemView);

            recieverimage=itemView.findViewById(R.id.image);
            recievertext=itemView.findViewById(R.id.message);

        }
    }
}
