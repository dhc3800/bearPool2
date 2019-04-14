package com.dhc3800.bearpool5;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Intermediate extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intermediate);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                FirebaseAuth.getInstance().getCurrentUser()
                        .sendEmailVerification()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                            }
                        });
                break;
            case R.id.button2:
                FirebaseAuth.getInstance().getCurrentUser().reload();
                Toast.makeText(Intermediate.this, FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();
//                Toast.makeText(Intermediate.this, String.valueOf(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()), Toast.LENGTH_LONG).show();
                if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                    startActivity(new Intent(Intermediate.this, MainPage.class));
                } else {
//                    Toast.makeText(Intermediate.this, "Please verify email", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

//        String emailLink = intent.getData().toString();

// Confirm the link is a sign-in with email link.
//        if (auth.isSignInWithEmailLink(emailLink)) {
//            // Retrieve this from wherever you stored it
//            String email = "someemail@domain.com";
//
//            // The client SDK will parse the code from the link for you.
//            auth.signInWithEmailLink(email, emailLink)
//                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                AuthResult result = task.getResult();
//                                // You can access the new user via result.getUser()
//                                // Additional user info profile *not* available via:
//                                // result.getAdditionalUserInfo().getProfile() == null
//                                // You can check if the user is new or existing:
//                                // result.getAdditionalUserInfo().isNewUser()
//                                startActivity(new Intent(Intermediate.this, MainPage.class));
//
//                            } else {
//                            }
//                        }
//                    });
//        }




}
