package com.example.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Adapter.UserAdapter;
import com.example.loginandregister.R;
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

public class MainFriendFragment extends Fragment {

    private UserAdapter userAdapter;
    private RecyclerView recyclerView;
    private List<User> mUser;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_chat_screen,container,false);
        recyclerView=view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(userAdapter);
        mUser=new ArrayList<>();

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
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    User user = snapshot1.getValue(User.class);
                    assert user != null;
                    if (!user.getUserId().equals(firebaseUser.getUid())){
                        mUser.add(user);
                    }
                }
                userAdapter =new UserAdapter(getContext(),mUser);
                recyclerView.setAdapter(userAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
