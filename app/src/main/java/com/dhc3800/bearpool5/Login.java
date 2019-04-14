package com.dhc3800.bearpool5;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText email;
    EditText pass;
    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.editText3);
        pass = findViewById(R.id.editText4);
        login = findViewById(R.id.button3);
        findViewById(R.id.textView2).setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button3:
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(Login.this, MainPage.class));
                                } else {
                                    Toast.makeText(Login.this, "Login failed", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                break;
            case R.id.textView2:
                startActivity(new Intent(Login.this, MainActivity.class));
                finish();
                break;

        }
    }
}
