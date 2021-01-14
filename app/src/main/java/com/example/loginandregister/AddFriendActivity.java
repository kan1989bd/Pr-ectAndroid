package com.example.loginandregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.Friend;
import com.example.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AddFriendActivity extends AppCompatActivity {
    TextView txtUserName;
    TextView txtInfoName;
    TextView txtInfoEmail;
    ImageView imageAvatar;
    Button btn_unfriend,btn_make_friend;
    private FirebaseUser fuser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        String t=getIntent().getStringExtra("idUser");
        txtUserName=findViewById(R.id.txt_profile_user_name);
        txtInfoName=findViewById(R.id.txt_profile_infor_nam);
        imageAvatar=findViewById(R.id.img_profile_avatar);
        btn_make_friend=findViewById(R.id.btn_make_friend);
        btn_unfriend=findViewById(R.id.btn_unfriend);
String d=getIntent().getStringExtra("status");
        if(d.equals("0")){
            btn_make_friend.setVisibility(View.INVISIBLE);
            btn_unfriend.setVisibility(View.INVISIBLE);
        }
        if(d.equals("1")){
            btn_make_friend.setVisibility(View.INVISIBLE);
            btn_unfriend.setVisibility(View.VISIBLE);
        }

        fuser= FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference("User").child(t);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                assert user!=null;
                txtUserName.setText(user.getUserName());
                txtInfoName.setText(user.getUserName());
                String imgUrl=user.getImgUrl();

                if(imgUrl!="default"){
                    Picasso.get().load(imgUrl).into(imageAvatar);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

btn_make_friend.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String t=getIntent().getStringExtra("idUser");
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        Friend friend=new Friend(fuser.getUid(),t,"0");
        FirebaseDatabase.getInstance().getReference().child("Friends").child(fuser.getUid()+t).setValue(friend);
        btn_make_friend.setVisibility(View.INVISIBLE);
        btn_unfriend.setVisibility(View.VISIBLE);

    }
});


btn_unfriend.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
                String t=getIntent().getStringExtra("idUser");
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        Friend friend=new Friend(fuser.getUid(),t,"0");
        FirebaseDatabase.getInstance().getReference().child("Friends").child(fuser.getUid()+t).removeValue();
        FirebaseDatabase.getInstance().getReference().child("Friends").child(t+fuser.getUid()).removeValue();
        btn_make_friend.setVisibility(View.VISIBLE);
        btn_unfriend.setVisibility(View.INVISIBLE);
    }
});

    }
}