package com.example.bglresidentprofiling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupAdminActivity extends AppCompatActivity {
    EditText signupName, signupUsername, signupEmail, signupPassword;
    TextView loginRedirectText;
    Button signupButton, button2;
    FirebaseDatabase database;
    DatabaseReference reference;
    private ProgressDialog loading;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_admin);
        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupUsername = findViewById(R.id.signup_username);
        signupPassword = findViewById(R.id.signup_password);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        loading = new ProgressDialog(this);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        signupButton = findViewById(R.id.signup_button);
        button2 = findViewById(R.id.button2);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");
                String name = signupName.getText().toString();
                String email = signupEmail.getText().toString();
                String username = signupUsername.getText().toString();
                String password = signupPassword.getText().toString();
                HelperClass helperClass = new HelperClass(name, email, username, password);
                reference.child(username).setValue(helperClass);
                loading.setTitle("Signing in...");
                loading.setMessage("Please wait.");
                loading.setCanceledOnTouchOutside(false);
                loading.show();
                Intent intent = new Intent(SignupAdminActivity.this, LoginActivity.class);
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            user=auth.getCurrentUser();
                            user.sendEmailVerification();
                            Toast.makeText(SignupAdminActivity.this, "You have signup successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        } else if (!task.isSuccessful()) {
                            Toast.makeText(SignupAdminActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                        }
                        loading.dismiss();
                    }
                });

            }
        });
        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupAdminActivity.this, LoginAdminActivity.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupAdminActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}