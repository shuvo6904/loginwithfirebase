package com.example.loginwithfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button register;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEditText = findViewById(R.id.emailEditTextId);
        passwordEditText = findViewById(R.id.passwordEditTextId);
        register = findViewById(R.id.registerButtonId);

        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){

                    Toast.makeText(RegisterActivity.this, "Empty Credentials !",Toast.LENGTH_SHORT).show();

                }

                else if (password.length() < 6){

                    Toast.makeText(RegisterActivity.this, "Password is too short !",Toast.LENGTH_SHORT).show();

                }

                else {
                    registerUser(email, password);
                }
            }
        });


    }

    private void registerUser(String strEmail, String strPassword) {

        mAuth.createUserWithEmailAndPassword(strEmail, strPassword).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Register is Successfull !",Toast.LENGTH_SHORT).show();
                }

                else {
                    Toast.makeText(RegisterActivity.this, "Register Failed !",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}