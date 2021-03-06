package com.example.loginandregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SendFriendRequest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_friend_request);
    }

    public void OpenFriend(View view) {
        Intent intent = new Intent(this,FriendRequest.class);
        startActivity(intent);
    }


    public void BackTomain(View view) {
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void BackToFriend(View view) {
        Intent intent =new Intent(this,Friend.class);
        startActivity(intent);
    }
}