package com.example.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.loginandregister.R;
import com.example.model.Friend;
import com.example.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.FriendRequestViewHolder> {
private Context context;
private List<User>mUsers;
private FirebaseUser fuser;
//private FirebaseUser fuser;
    public  FriendRequestAdapter(Context context,List<User>mUsers){
this.context=context;
this.mUsers=mUsers;
    }
    @NonNull
    @Override
    public FriendRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.friend_request_item,parent,false);
        return new FriendRequestAdapter.FriendRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FriendRequestViewHolder holder, int position) {
 final User user=mUsers.get(position);
 fuser= FirebaseAuth.getInstance().getCurrentUser();
holder.txt_username.setText(user.getUserName());
        if (user.getImgUrl().equals("default")){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(context).load(user.getImgUrl()).into(holder.profile_image);
        }
        holder.btn_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Friend friend=new Friend(user.getUserId(),fuser.getUid(),"1");
                FirebaseDatabase.getInstance().getReference().child("Friends").child(user.getUserId()+fuser.getUid()).setValue(friend);
                holder.btn_agree.setVisibility(View.INVISIBLE);
                holder.btn_cancel.setVisibility(View.INVISIBLE);
            }
        });
        holder.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Friends").child(user.getUserId()+fuser.getUid()).removeValue();
                holder.btn_agree.setVisibility(View.INVISIBLE);
                holder.btn_cancel.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class FriendRequestViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_username;
        public ImageView profile_image;
                public Button btn_agree,btn_cancel;
        public FriendRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_username=itemView.findViewById(R.id.user_name);
            profile_image=itemView.findViewById(R.id.profile_image);
            btn_agree=itemView.findViewById(R.id.btn_agree);
            btn_cancel=itemView.findViewById(R.id.btn_cancel);
        }
    }
}
