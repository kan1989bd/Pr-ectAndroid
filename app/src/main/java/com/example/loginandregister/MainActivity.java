package com.example.loginandregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Object Register;

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