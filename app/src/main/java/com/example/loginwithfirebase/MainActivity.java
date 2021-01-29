package com.example.loginwithfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Button logoutButton;
    private EditText name;
    private Button addValue;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoutButton = findViewById(R.id.logoutButtonId);
        name = findViewById(R.id.nameEditTextId);
        addValue = findViewById(R.id.addValueId);

        listView = findViewById(R.id.listViewId);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logged Out !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, StartActivity.class));
            }
        });

        addValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strName = name.getText().toString();

                if (strName.isEmpty()){
                    Toast.makeText(MainActivity.this, "No name entered !", Toast.LENGTH_SHORT).show();
                }
                else {
                    FirebaseDatabase.getInstance().getReference().child("DataSection").push().child("Name").setValue(strName);
                }
            }
        });

        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.list_item,list);
        listView.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Languages");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    list.add(dataSnapshot.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}