package com.example.csyvi.medpack.login;

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

import com.example.csyvi.medpack.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    EditText userEmail, userPassword;
    Button registerButton;
    TextView userLogin;
    LoginManager loginManager = new LoginManager();
    String email, password;
    Boolean registerValid, emailValid;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userPassword = findViewById(R.id.etRegisterPass);
        userEmail = findViewById(R.id.etRegisterEmail);
        registerButton = findViewById(R.id.registerButton);
        userLogin = findViewById(R.id.userLogin);

        // instantiate Firebase and progress dialog
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the input from users
                password = userPassword.getText().toString();
                email = userEmail.getText().toString();

                // call validateRegistration method to check for null values
                registerValid = loginManager.validateRegistration(email,password);

                // if no null values
                if(registerValid)
                {
                    // trim the input to insert into the Firebase database
                    password = password.trim();
                    email = email.trim();

                    // call validateEmailFormat method to make sure it is in email format
                    emailValid = loginManager.validateEmailFormat(email);

                    if(password.length() < 6)
                    {
                        Toast.makeText(RegistrationActivity.this,"Please enter password of length that is more than 5.",Toast.LENGTH_SHORT).show();
                    }

                    else if(!emailValid)
                    {
                        Toast.makeText(RegistrationActivity.this,"Please enter your email with the correct format. e.g. username@gmail.com/username@hotmail.com.",Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        // show progress dialog to user
                        progressDialog.setMessage("Trying to register now");
                        progressDialog.show();

                        // upload the patient info to the database
                        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d("errorMsg","test3");
                                if(task.isSuccessful())
                                {
                                    progressDialog.dismiss();
                                    Toast.makeText(RegistrationActivity.this,"Registration Successful! Redirecting to Profile Page ..",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegistrationActivity.this, ProfileActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                                else
                                {
                                    Toast.makeText(RegistrationActivity.this,"Registration Failed! Email is already existed!",Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }

                            }
                        });
                    }
                }

                // if there are null values
                else
                {
                    Toast.makeText(RegistrationActivity.this,"Please enter all the details.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
