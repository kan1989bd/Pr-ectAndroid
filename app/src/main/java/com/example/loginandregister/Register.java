package com.example.loginandregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.concurrent.BlockingDeque;
import java.util.regex.Pattern;
public class Register extends AppCompatActivity {
    EditText userName;
    EditText email;
    EditText password;
    EditText passwordCf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userName=findViewById(R.id.txtUsername);
        email=findViewById(R.id.txtEmail);
        password=findViewById(R.id.txtPassword);
        passwordCf=findViewById(R.id.txtConfirmPassword);
    }

    public void BackTomain(View view) {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private Boolean validateUserName(){
        String val=userName.getText().toString();
        if(val.isEmpty()){
            userName.setError("User name cannot be empty");
            return false;
        }
        else if(val.contains(" ")){
            userName.setError("White spaces are not allowed");
            return false;
        }
        else if(val.length()>=20){
            userName.setError("Username must least than 20 character");
            return false;
        }
        else {
            userName.setError(null);
            return true;
        }
    }

    private Boolean validateEmail(){
        String val=email.getText().toString();
        if(val.isEmpty()){
            email.setError("Email cannot be empty");
            return false;
        }
        else if(!val.matches("[a-zA-Z0-9._-]+@gmail+\\.+com+")){
            email.setError("Invalid email address");
            return false;
        }
        else {
            email.setError(null);
            return true;
        }
    }

    private Boolean validatePassword(){
        String val=password.getText().toString();
        if(val.isEmpty()){
            password.setError("Password cannot be empty");
            return false;
        }
        else if(val.length()<6){
            password.setError("Password at least 6 characters");
            return false;
        }
        else if(val.contains(" ")){
            password.setError("White spaces are not allowed");
            return false;
        }
        else {
            password.setError(null);
            return true;
        }
    }

    private Boolean validatePasswordConfirm(){
        String valPassword=password.getText().toString();
        String valPasswordConfirm=passwordCf.getText().toString();
        if(valPasswordConfirm.isEmpty()){
            passwordCf.setError("Password cannot be empty");
            return true;
        }
        else if(valPasswordConfirm!=valPassword){
            passwordCf.setError("Password Confirm must equals Password");
            return false;
        }
        else {
            passwordCf.setError(null);
            return true;
        }
    }
    public void register(View view) {
        if(!validateUserName()|!validateEmail()|!validatePassword()){
            return;
        }
    }
}