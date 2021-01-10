package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.loginandregister.EditProfile;
import com.example.loginandregister.FriendRequest;
import com.example.loginandregister.R;

public class MainFriendFragment extends Fragment {
    Button btnFriend;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_main_screen,container,false);
        btnFriend = view.findViewById(R.id.btnFriend2);
        btnFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),FriendRequest.class));
            }
        });
        return view;
    }
}
