package com.example.loginandregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Friend extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
    }

    public void OpenFriend(View view) {
        Intent intent=new Intent(this,Friend.class);
        startActivity(intent);
    }

    public void ToProfile(View view) {
        Intent intent=new Intent(this,ProfileActivity.class);
        startActivity(intent);
    }

    public void OpenMainScreen(View view) {
        Intent inten = new Intent(this,MainScreen.class);
        startActivity(inten);
    }

    public void OpenfriendRequest(View view) {
        Intent inten = new Intent(this,FriendRequest.class);
        startActivity(inten);
    }
}