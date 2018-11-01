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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    EditText userName, userEmail, userPassword;
    Button registerButton;
    TextView userLogin;
    LoginManager loginManager = new LoginManager();
    String name, password, email;
    Boolean registerValid;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = findViewById(R.id.etRegisterName);
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
                name = userName.getText().toString();
                password = userPassword.getText().toString();
                email = userEmail.getText().toString();

                // call validateRegistration method to check for null values
                registerValid = loginManager.validateRegistration(name,email,password);

                // if no null values
                if(registerValid)
                {
                    // trim the input to insert into the Firebase database
                    name = name.trim();
                    password = password.trim();
                    email = email.trim();

                    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
                    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(email);

                    if(password.length() < 6)
                    {
                        Toast.makeText(RegistrationActivity.this,"Please enter password of length that is more than 5.",Toast.LENGTH_SHORT).show();
                    }

                    else if(!matcher.matches())
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
                                    Toast.makeText(RegistrationActivity.this,"Registration Successful! Redirecting to Login Page ..",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }

                                else
                                {
                                    Toast.makeText(RegistrationActivity.this,"Registration Failed!",Toast.LENGTH_SHORT).show();
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
            }
        });

    }
}
