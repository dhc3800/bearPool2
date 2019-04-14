package com.dhc3800.bearpool5;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText email;
    EditText password;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.submit).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        findViewById(R.id.textView3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                if (true) {
                    signUp(email.getText().toString(), password.getText().toString());
                } else {
                    Toast.makeText(MainActivity.this, "Please use a certified Berkeley email", Toast.LENGTH_LONG).show();

                }
                break;
            case R.id.textView3:
                startActivity(new Intent(MainActivity.this, Login.class));
        }
    }
    private boolean verify() {
        return email.getText().toString().endsWith("@berkeley.edu");
    }

    public void signUp(String email, String pass) {
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mAuth.getCurrentUser()
                            .sendEmailVerification()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    startActivity(new Intent(MainActivity.this, Intermediate.class));
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.i("tag", "msg");
                                }
                            });
                } else {
                    Toast.makeText(MainActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void sendSignInLink(String email, ActionCodeSettings actioncodeSettings) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Toast.makeText(this, "Its lit", Toast.LENGTH_LONG).show();

        auth.sendSignInLinkToEmail(email, actioncodeSettings)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Its lit", Toast.LENGTH_LONG).show();

                            startActivity(new Intent(MainActivity.this, Intermediate.class));
                        }
                    }
                });
    }

    public ActionCodeSettings buildActionCodeSettings() {
        // [START auth_build_action_code_settings]
        ActionCodeSettings actionCodeSettings =
                ActionCodeSettings.newBuilder()
                        // URL you want to redirect back to. The domain (www.example.com) for this
                        // URL must be whitelisted in the Firebase Console.
                        .setUrl("https://bearpool5.page.link/finishSignUp?cartId=1234")
                        // This must be true
                        .setHandleCodeInApp(true)
                        .setAndroidPackageName(
                                "com.dhc3800.bearpool5",
                                true, /* installIfNotAvailable */
                                "12"    /* minimumVersion */)
                        .build();
        // [END auth_build_action_code_settings]
        return actionCodeSettings;
    }
}
