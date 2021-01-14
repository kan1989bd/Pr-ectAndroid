package com.example.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Adapter.FriendRequestAdapter;
import com.example.Adapter.UserAdapter;
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
import java.util.List;

public class MainFriendRequestFragment extends Fragment {
    private FriendRequestAdapter friendRequestAdapter;
    private RecyclerView recyclerView;
    private List<User> mUser;
    private ArrayList<String> list=new ArrayList<String>();
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_fragment_friend_request,container,false);
        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(friendRequestAdapter);
        mUser=new ArrayList<>();
        final FirebaseUser fuser=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Friends");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    Friend friend=snapshot1.getValue(Friend.class);
                    if(fuser.getUid().equals(friend.getFriendId())&&friend.getStatus().equals("0")){
           list.add(friend.getMyId());
                    }
                }
                readUsers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        readUsers();
        return view;
    }
    private void readUsers() {
        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    mUser.clear();
                    for (String i:list) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        User user = snapshot1.getValue(User.class);
                        assert user != null;
                        assert firebaseUser != null;
                        if (user.getUserId().equals(i)) {
                            mUser.add(user);
                        }
                    }
                    }
                    friendRequestAdapter = new FriendRequestAdapter(getContext(), mUser);
                    recyclerView.setAdapter(friendRequestAdapter);


                }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
