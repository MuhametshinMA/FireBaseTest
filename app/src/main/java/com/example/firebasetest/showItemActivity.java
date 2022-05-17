package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class showItemActivity extends AppCompatActivity {

    private TextView tvName, tvLastName, tvEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item);
        init();
        getIntentMain();
    }
    private void init() {
        tvName = findViewById(R.id.tvName);
        tvLastName = findViewById(R.id.tvLastName);
        tvEmail = findViewById(R.id.tvEmail);
    }
    private void getIntentMain() {
        Intent intent = getIntent();
        if (intent != null) {
            tvName.setText(intent.getStringExtra(Constants.USER_NAME));
            tvLastName.setText(intent.getStringExtra(Constants.USER_LAST_NAME));
            tvEmail.setText(intent.getStringExtra(Constants.USER_EMAIL));
        }
    }
}