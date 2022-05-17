package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText tvName, tvLastName, tvEmail;
    private DatabaseReference mDataBase;
    private String USER_KEY = "User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        tvName = findViewById(R.id.tpName);
        tvLastName = findViewById(R.id.tpLastName);
        tvEmail = findViewById(R.id.tpEmail);
        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
    }

    public void onClickSave(View view) {
        String id = mDataBase.getKey();
        String name = tvName.getText().toString();
        String lastName = tvLastName.getText().toString();
        String email = tvEmail.getText().toString();
        User newUser = new User(id, name, lastName, email);

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(lastName)) {
            mDataBase.push().setValue(newUser);
            Toast.makeText(this, "saved", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "empty field", Toast.LENGTH_LONG).show();
        }
    }
    public void onClickRead(View view) {
        Intent intent = new Intent(this, ReadActivity.class);
        startActivity(intent);
    }
}