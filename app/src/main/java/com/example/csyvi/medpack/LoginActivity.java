package com.example.csyvi.medpack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    TextView userRegistration;
    EditText loginEmail;
    EditText loginPassword;
    Button loginButton;
    String email, password;
    Boolean loginValid, emailValid;
    FirebaseAuth fireBaseAuth;
    ProgressDialog progressDialog;
    LoginManager loginManager = new LoginManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail = findViewById(R.id.etEmail);
        loginPassword = findViewById(R.id.etPassword);
        loginButton = findViewById(R.id.loginButton);
        userRegistration = findViewById(R.id.registerView);

        fireBaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fireBaseAuth.getCurrentUser();
        progressDialog = new ProgressDialog(this);

        if (user != null) {
            finish();
            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        }

        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the input from users
                email = loginEmail.getText().toString();
                password = loginPassword.getText().toString();

                // call validateRegistration method to check for null values
                loginValid = loginManager.validateLogin(email,password);

                // if no null values
                if(loginValid)
                {
                    // trim the input to insert into the Firebase database
                    email = email.trim();
                    password = password.trim();

                    // call validateEmailFormat method to make sure it is in email format
                    emailValid = loginManager.validateEmailFormat(email);

                    if (password.length() < 6)
                    {
                        Toast.makeText(LoginActivity.this, "Please enter password of length that is more than 5.", Toast.LENGTH_SHORT).show();
                    }

                    else if (!emailValid)
                    {
                        Toast.makeText(LoginActivity.this, "Please enter your email with the correct format. e.g. username@gmail.com/username@hotmail.com.", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        // show progress dialog to user
                        progressDialog.setMessage("Trying to login now");
                        progressDialog.show();

                        fireBaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "Login Successful! Redirecting ..", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Login Failed! Account is not valid!", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        });
                    }
                }

                // if there are null values
                else
                {
                    Toast.makeText(LoginActivity.this,"Please enter all the details.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
