package com.example.loginandregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText userName;
    EditText email;
    EditText password;
    EditText passwordCf;
    Button btnRegister;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userName=findViewById(R.id.txtUsername);
        email=findViewById(R.id.txtEmail);
        password=findViewById(R.id.txtPassword);
        passwordCf=findViewById(R.id.txtConfirmPassword);
        btnRegister=findViewById(R.id.btnConfirm);
        firebaseAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateUserName()|!validateEmail()|!validatePassword()){
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                // register with filebase
                String valEmail=email.getText().toString();
                String valPassword=password.getText().toString();
                final String valUserName=userName.getText().toString();
                firebaseAuth.createUserWithEmailAndPassword(valEmail,valPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                            assert firebaseUser!=null;
                            String userId=firebaseUser.getUid();

                            DatabaseReference database=FirebaseDatabase.getInstance().getReference("User");
                            final User user=new User(userId,valUserName,"default");
                            database.child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Register.this,"User Created",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),MainScreen.class));
                                }
                            });

                        }
                        else {
                            Toast.makeText(Register.this,"Error ! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
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

}