package com.example.loginandregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.fragment.MainFriendFragment;
import com.example.fragment.MainFriendRequestFragment;
import com.example.fragment.MainInvitationFragment;
import com.example.fragment.MainProfileFragment;
import com.example.fragment.MainScreenFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FriendRequest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);
        Fragment FragmentIndex = new MainFriendRequestFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_friend_container,FragmentIndex).commit();
        BottomNavigationView bottomNav = findViewById(R.id.top_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.Fragment_Friend_Request:
                            selectedFragment = new MainFriendRequestFragment();
                            break;
                        case R.id.Fragment_Invitation:
                            selectedFragment = new MainInvitationFragment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_friend_container,selectedFragment).commit();
                    return true;
                }
            };
    public void BackToFriend(View view) {
        Intent intent=new Intent(this,MainScreen.class);
        startActivity(intent);
    }

}