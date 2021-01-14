package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Adapter.UserAdapter;
import com.example.loginandregister.AddFriendActivity;
import com.example.loginandregister.R;
import com.example.model.Friend;
import com.example.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainInvitationFragment extends Fragment {
    Button btnSendFirendRequest;
    EditText txtUserName;
//    private RecyclerView recyclerView;
//    private UserAdapter userAdapter;
    private List<User> mUser=new ArrayList<>();
   private   ArrayList<String> list;
    private ArrayList<String> listFriend;
    FirebaseUser fuser;
    DatabaseReference reference;
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.activity_send_friend_request, container, false);
            txtUserName=view.findViewById(R.id.txtUserName);
    btnSendFirendRequest=view.findViewById(R.id.btnSendFriendRequest);
            mUser=new ArrayList<>();
            list=new ArrayList<>();
            listFriend=new ArrayList<>();
            readFriend();
    btnSendFirendRequest.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            readUser();
        }
    });
            return view;
        }
private void readUser(){
    final FirebaseUser fuser= FirebaseAuth.getInstance().getCurrentUser();
    reference=FirebaseDatabase.getInstance().getReference("User");
    reference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            mUser.clear();
            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                User user = snapshot1.getValue(User.class);
                if (user.getUserName().equals(txtUserName.getText().toString())) {
                    list.add(user.getUserId());
                }
            }

            if(list.size()!=0) {
                String td="";
                for (String i : listFriend) {
                    if (list.get(0).equals(i)) {
                        td = i;
                    }
                }
                Intent intent = new Intent(getActivity(), AddFriendActivity.class);
                intent.putExtra("idUser", list.get(0).toString());
                if(fuser.getUid().equals(list.get(0).toString())) {
                    intent.putExtra("status", "0");
                }else if(!td.equals("")){
                    intent.putExtra("status", "1");
                }else {
                    intent.putExtra("status", "");
                }
                startActivity(intent);
                getActivity().finish();
            }else{
                Toast.makeText(getActivity(),"UserName does not exits",Toast.LENGTH_SHORT).show();
            }
    }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });


}
private void readFriend()
{
    final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference1= FirebaseDatabase.getInstance().getReference("Friends");
    reference1.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                Friend friend=snapshot.getValue(Friend.class);
                if(friend.getMyId().equals(firebaseUser.getUid())){
                    listFriend.add(friend.getFriendId());
                }
                if(friend.getFriendId().equals(firebaseUser.getUid())){
                    listFriend.add(friend.getMyId());
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
}
}


