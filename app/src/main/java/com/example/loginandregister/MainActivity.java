package com.example.loginandregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.fragment.MainScreenFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Object Register;

    FirebaseUser firebaseUser;
    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser !=null)
        {
            startActivity(new Intent(MainActivity.this, SplashActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OpenRegister(View view) {
        Intent intentRegister = new Intent(this, Register.class);
        startActivity(intentRegister);
    }

    public void OpenLogin(View view) {
        Intent intentLogin = new Intent(this,Login.class);
        startActivity(intentLogin);
    }
}