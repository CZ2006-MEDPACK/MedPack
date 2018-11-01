package com.example.csyvi.medpack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    EditText userName, userEmail, userPassword;
    Button registerButton;
    TextView userLogin;
    LoginManager loginManager;
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

        name = userName.getText().toString();
        password = userPassword.getText().toString();
        email = userEmail.getText().toString();

        progressDialog = new ProgressDialog(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // call validateRegistration method
                registerValid = loginManager.validateRegistration(name,password,email);
                if(registerValid == true)
                {
                    // upload the patient info to the database
                    String registerEmail = email.trim();
                    String registerPassword = password.trim();

                    progressDialog.setMessage("Trying to register now");
                    progressDialog.show();

                    firebaseAuth.createUserWithEmailAndPassword(registerEmail,registerPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                progressDialog.dismiss();
                                Toast.makeText(RegistrationActivity.this,"Registration Successful!",Toast.LENGTH_SHORT).show();
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
