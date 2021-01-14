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
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> mUser=new ArrayList<>();
     ArrayList<String> list=new ArrayList<String>();
     ArrayList<String> listFriend=new ArrayList<String>();
    FirebaseUser fuser;
    DatabaseReference reference;
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.activity_send_friend_request, container, false);
            txtUserName=view.findViewById(R.id.txtUserName);
    btnSendFirendRequest=view.findViewById(R.id.btnSendFriendRequest);
         //   mUser=new ArrayList<>();

    btnSendFirendRequest.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            reaUser();

        }
    });
            return view;
        }
private void reaUser(){
    mUser=new ArrayList<>();
    list=new ArrayList<String>();
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
            //Kiem tra user ton tai
            if (list.size() != 0) {
                reference = FirebaseDatabase.getInstance().getReference("Friends");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listFriend.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            Friend friend = snapshot1.getValue(Friend.class);
                            if (friend.getMyId().equals(fuser.getUid())) {
                                listFriend.add(friend.getFriendId());
                            }
                            if (friend.getFriendId().equals(fuser.getUid())) {
                                listFriend.add(friend.getMyId());
                            }
                        }
                        for (String i : listFriend) {
                            if (list.get(0).toString().equals(i)) {
                                Intent intent = new Intent(getActivity(), AddFriendActivity.class);
                                intent.putExtra("idUser", list.get(0).toString());
                                intent.putExtra("status", "1");
                                startActivity(intent);
                                getActivity().finish();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                if (list.get(0).toString().equals(fuser.getUid())) {
                    Intent intent = new Intent(getActivity(), AddFriendActivity.class);
                    intent.putExtra("idUser", list.get(0).toString());
                    intent.putExtra("status", "0");
                    startActivity(intent);
                    getActivity().finish();
                }else {
                    Intent intent = new Intent(getActivity(), AddFriendActivity.class);
                    intent.putExtra("idUser", list.get(0).toString());
                    intent.putExtra("status", "2");
                    startActivity(intent);
                getActivity().finish();
                }

            }
            else {
                Toast.makeText(getActivity(),"Username does not exits",Toast.LENGTH_SHORT).show();
            }
    }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
}

}


