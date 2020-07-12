package com.example.btlandroid_covid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFirebaseActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button btnLogin, btnSignUp;
    EditText editTextEmail, editTextPassword;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_firebase);
        setTitle("Đăng nhập hoặc đăng ký tài khoản");
        mAuth = FirebaseAuth.getInstance();
        btnLogin = findViewById(R.id.buttonLogin);
        btnSignUp = findViewById(R.id.buttonSignUp);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin.setOnClickListener(v -> {
            if(editTextEmail.getText().toString().isEmpty()||editTextPassword.getText().toString().isEmpty()){
                Toast.makeText(LoginFirebaseActivity.this,"Vui lòng điền đầy đủ thông tin",Toast.LENGTH_SHORT).show();
            }else {
                mAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(LoginFirebaseActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(LoginFirebaseActivity.this,MainActivity.class);
                                    startActivity(i);
                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(LoginFirebaseActivity.this, "Đăng nhập không thành công!",
                                            Toast.LENGTH_SHORT).show();
                                    // ...
                                }
                            }
                        });
            }
        });
        btnSignUp.setOnClickListener(v->{
            if(editTextEmail.getText().toString().isEmpty()||editTextPassword.getText().toString().isEmpty()){
                Toast.makeText(LoginFirebaseActivity.this,"Vui lòng điền đầy đủ thông tin",Toast.LENGTH_SHORT).show();
            }else{
                mAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(LoginFirebaseActivity.this, "Đăng ký tài khoản thành công", Toast.LENGTH_SHORT).show();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginFirebaseActivity.this, "Đăng ký thất bại!",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });
    }
}
