package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.chatapp.adapters.chatadapter;
import com.example.chatapp.models.modelmessages;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class chatactivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CircleImageView profileimage;
    String name,email,password,recieveruid,status,msg;

    public static String recieverimage,senderimage;
    TextView nameview;
    ImageView send;
    EditText message;
    List<modelmessages> list;
    chatadapter chatadapter;

    String senderRoom,recieverRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatactivity);


        recyclerView=findViewById(R.id.recycler);
        recyclerView.hasFixedSize();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        list=new ArrayList<>();

        send=findViewById(R.id.send);
        message=findViewById(R.id.message);

        name=getIntent().getStringExtra("name");
        email=getIntent().getStringExtra("email");
        password=getIntent().getStringExtra("password");
        recieveruid=getIntent().getStringExtra("uid");
        recieverimage=getIntent().getStringExtra("image");
        status=getIntent().getStringExtra("status");

        profileimage=findViewById(R.id.image);
        nameview=findViewById(R.id.name);

        nameview.setText(name);
        Glide.with(this).load(recieverimage).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(profileimage);

        senderRoom=FirebaseAuth.getInstance().getUid()+recieveruid;
        recieverRoom=recieveruid+FirebaseAuth.getInstance().getUid();


        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                senderimage=snapshot.child("profilepic").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(chatactivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(chatactivity.this, "sender profile pic issue", Toast.LENGTH_SHORT).show();

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                String msg=message.getText().toString();
                if (msg.isEmpty()){
                    Toast.makeText(chatactivity.this, "Enter valid message", Toast.LENGTH_SHORT).show();
                    return;
                }

                Date date=new Date();
                modelmessages modelmessages=new modelmessages(msg, FirebaseAuth.getInstance().getUid(),String.valueOf(date.getTime()));
                FirebaseDatabase.getInstance().getReference().child("chats").child(senderRoom).child("messages").push().setValue(modelmessages).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        FirebaseDatabase.getInstance().getReference().child("chats").child(recieverRoom).child("messages").push().setValue(modelmessages).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {

                            }
                        });
                    }
                });

            }
        });

        FirebaseDatabase.getInstance().getReference().child("chats").child(senderRoom).child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    list.add(snapshot.getValue(modelmessages.class));
                }
                chatadapter=new chatadapter(list,chatactivity.this);
                recyclerView.setAdapter(chatadapter);
                chatadapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(chatactivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });




    }
}