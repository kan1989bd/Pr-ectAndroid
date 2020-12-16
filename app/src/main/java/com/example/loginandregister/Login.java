package com.example.loginandregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText txtPasword;
    EditText txtEmail;
    Button btnLogin;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtEmail=findViewById(R.id.txtEmailLogin);
        txtPasword=findViewById(R.id.txtPassWord);
        btnLogin=findViewById(R.id.btnLogin);
        progressBar=findViewById(R.id.progressBarLogin);
        firebaseAuth=FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateEmail()|!validatePassword()){
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(txtEmail.getText().toString(),txtPasword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this,"User Created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainScreen.class));
                        }
                        else {
                            Toast.makeText(Login.this,"Error ! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private Boolean validateEmail(){
        String val=txtEmail.getText().toString();
        if(val.isEmpty()){
            txtEmail.setError("Email cannot be empty");
            return false;
        }
        else if(!val.matches("[a-zA-Z0-9._-]+@gmail+\\.+com+")){
            txtEmail.setError("Invalid email address");
            return false;
        }
        else {
            txtEmail.setError(null);
            return true;
        }
    }

    private Boolean validatePassword(){
        String val=txtPasword.getText().toString();
        if(val.isEmpty()){
            txtPasword.setError("Password cannot be empty");
            return false;
        }
        else if(val.length()<6){
            txtPasword.setError("Password at least 6 characters");
            return false;
        }
        else if(val.contains(" ")){
            txtPasword.setError("White spaces are not allowed");
            return false;
        }
        else {
            txtPasword.setError(null);
            return true;
        }
    }

    public void BackTomain(View view) {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}