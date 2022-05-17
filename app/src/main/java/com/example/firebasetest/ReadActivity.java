package com.example.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReadActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> listData;
    private List<User> listTmp;
    private DatabaseReference mDataBase;
    private String USER_KEY = "User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_layout);
        init();
        getDataFromBd();
        setOnClickItem();
    }
    private void init() {
        listView = findViewById(R.id.listView);
        listData = new ArrayList<>();
        listTmp = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);
        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
    }
    private void getDataFromBd() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (listData.size() > 0) {
                    listData.clear();
                }
                if (listTmp.size() > 0) {
                    listTmp.clear();
                }
                for (DataSnapshot ds: snapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    assert user != null;
                    listData.add(user.name);
                    listTmp.add(user);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDataBase.addValueEventListener(valueEventListener);
    }
    private void setOnClickItem() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user = listTmp.get(i);
                Intent intent = new Intent(ReadActivity.this, showItemActivity.class);
                intent.putExtra(Constants.USER_NAME, user.name);
                intent.putExtra(Constants.USER_LAST_NAME, user.lastName);
                intent.putExtra(Constants.USER_EMAIL, user.email);
                startActivity(intent);
            }
        });
    }
}
