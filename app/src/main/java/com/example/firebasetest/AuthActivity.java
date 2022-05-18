package com.example.firebasetest;

import static android.os.Build.VERSION_CODES.O;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnRegistration, btnLogIn, btnToMainActivity, btnSignOut;
    private TextView tvLogInAs;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        init();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            showSigned();
            String SignInAs = "You sign in as: " + currentUser.getEmail();
            tvLogInAs.setText(SignInAs);
        } else {
            showUnSigned();

            Toast.makeText(this, "User null", Toast.LENGTH_LONG).show();
        }
    }

    private void init() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPass);
        btnRegistration = findViewById(R.id.btnRegistration);
        btnLogIn = findViewById(R.id.btnLogIn);
        btnToMainActivity = findViewById(R.id.btnToMainActivity);
        tvLogInAs = findViewById(R.id.tvLogInAs);
        btnSignOut = findViewById(R.id.btnSignOut);
        mAuth = FirebaseAuth.getInstance();
    }
    public void onClickSignUp(View view) {
        if (!TextUtils.isEmpty(etEmail.getText().toString())
                && !TextUtils.isEmpty(etPassword.getText().toString())) {
            mAuth.createUserWithEmailAndPassword(etEmail.getText().toString(),
                    etPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendEmailVerify();
                                Toast.makeText(getApplicationContext(), "Registration success",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Registration failed",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "input Email and Password", Toast.LENGTH_LONG).show();
        }
    }
    public void onClickSignIn(View view) {
        if (!TextUtils.isEmpty(etEmail.getText().toString())
                && !TextUtils.isEmpty(etPassword.getText().toString())) {
            mAuth.signInWithEmailAndPassword(etEmail.getText().toString(),
                    etPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        showSigned();
                        Toast.makeText(getApplicationContext(), "log in success",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "log in failed",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public void toSignOut(View view) {
        FirebaseAuth.getInstance().signOut();
        showUnSigned();
    }

    public void toMainActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void sendEmailVerify() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        assert currentUser != null;
        currentUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Check email to verify", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Email not found", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void showSigned() {
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        if (user.isEmailVerified()) {
            String SignInAs = "You sign in as: " + user.getEmail();
            tvLogInAs.setText(SignInAs);
            btnToMainActivity.setVisibility(View.VISIBLE);
            tvLogInAs.setVisibility(View.VISIBLE);
            btnSignOut.setVisibility(View.VISIBLE);
            etEmail.setVisibility(View.GONE);
            etPassword.setVisibility(View.GONE);
            btnRegistration.setVisibility(View.GONE);
            btnLogIn.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "email is verified", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getApplicationContext(), "Check email to verify", Toast.LENGTH_LONG).show();
        }
    }

    public void showUnSigned() {
        btnToMainActivity.setVisibility(View.GONE);
        tvLogInAs.setVisibility(View.GONE);
        btnSignOut.setVisibility(View.GONE);
        etEmail.setVisibility(View.VISIBLE);
        etPassword.setVisibility(View.VISIBLE);
        btnRegistration.setVisibility(View.VISIBLE);
        btnLogIn.setVisibility(View.VISIBLE);
    }
}