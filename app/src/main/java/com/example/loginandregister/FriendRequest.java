package com.example.loginandregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FriendRequest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);
    }

    public void Invitation(View view){
        Intent intent =new Intent(this,SendFriendRequest.class);
        startActivity(intent);
    }

    public void BackToFriend(View view) {
        Intent intent =new Intent(this,Friend.class);
        startActivity(intent);
    }

}