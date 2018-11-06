package com.example.csyvi.medpack;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity{

    EditText et_nric, et_name, et_address, et_contactNo, et_dob;
    Spinner spinner_race,spinner_citizenship, spinner_maritalStatus, spinner_languages, spinner_chas;
    RadioGroup radio_gender;
    RadioButton radio_male, radio_female;
    Button submit_profile;
    ArrayList<Patient> patientList = new ArrayList<Patient>();
    DatePickerDialog datePickerDialog;
    Boolean genderMale;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    String userId;
    PatientManager pm = new PatientManager();
    DatabaseReference databaseRef;

    String nric, name, address, contactNo, dob, race, citizenship, maritalStatus, gender, spokenLanguage, chas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprofile);

        et_nric = findViewById(R.id.et_nric);
        et_name = findViewById(R.id.et_name);
        et_address = findViewById(R.id.et_address);
        et_contactNo = findViewById(R.id.et_contactnumber);
        et_dob = findViewById(R.id.et_dob);
        spinner_race = findViewById(R.id.spinner_race);
        spinner_citizenship = findViewById(R.id.spinner_citizenship);
        spinner_maritalStatus = findViewById(R.id.spinner_maritalstatus);
        spinner_languages = findViewById(R.id.spinner_languages);
        spinner_chas = findViewById(R.id.spinner_chas);
        radio_gender = findViewById(R.id.radio_gender);
        submit_profile = findViewById(R.id.submitProfileButton);

        setProfileAdapters();

        et_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(ProfileActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                et_dob.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });



        radio_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                switch(checkedId)
                {
                    case R.id.radio_male:
                        radio_male = findViewById(checkedId);
                        gender = radio_male.getText().toString();
                        genderMale = true;
                        break;

                    case R.id.radio_female:
                        radio_female = findViewById(checkedId);
                        gender = radio_female.getText().toString();
                        genderMale = false;
                        break;
                }
            }
        });

        submit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nric = et_nric.getText().toString();
                name = et_name.getText().toString();
                address = et_address.getText().toString();
                contactNo = et_contactNo.getText().toString();
                dob = et_dob.getText().toString();
                citizenship = spinner_citizenship.getSelectedItem().toString();
                race = spinner_race.getSelectedItem().toString();
                spokenLanguage = spinner_languages.getSelectedItem().toString();
                maritalStatus = spinner_maritalStatus.getSelectedItem().toString();
                chas = spinner_chas.getSelectedItem().toString();
                if(pm.validateInsertProfile(nric,name,address,contactNo,dob,citizenship,gender,race,spokenLanguage,maritalStatus,chas))
                {
                    if(genderMale)
                    {
                        gender = radio_male.getText().toString();
                    }

                    else
                    {
                        gender = radio_female.getText().toString();
                    }

                    database = FirebaseDatabase.getInstance();
                    mAuth = FirebaseAuth.getInstance();
                    databaseRef = database.getReference("patient");
                    FirebaseUser user = mAuth.getCurrentUser();
                    userId = user.getUid();
                    Log.d("errorMsg", userId);

                    //String id = databaseRef.push().getKey();
                    // store patient info into the Firebase database
                    Patient patient = new Patient(nric,name,address,contactNo,dob,citizenship,gender,race,spokenLanguage,maritalStatus,chas);
                    databaseRef.child(userId).setValue(patient);
                    // store the patient info into the arraylist
                    //patientList.add(new Patient(nric,name,address,contactNo,dob,citizenship,gender,race,spokenLanguage,maritalStatus,chas));

                    /*for(Patient patient : patientList)
                    {
                        Log.d("errorMsg",patient.toString());
                    }*/

                    Toast.makeText(ProfileActivity.this, "Profile entered! Redirecting ..", Toast.LENGTH_SHORT).show();
                    // bring patientList arraylist to the home page
                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                    // implemented serializable on Patient entity class instead
                    //intent.putExtra("ListPatient", patientList);
                    startActivity(intent);
                }

                else
                {
                    Toast.makeText(ProfileActivity.this, "Please enter all the details.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    // set the adapters for the race, languages, citizenship and marital status spinners
    public void setProfileAdapters()
    {
        ArrayAdapter<CharSequence>  raceAdapter = ArrayAdapter.createFromResource(this,R.array.race, android.R.layout.simple_spinner_item);
        raceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_race.setAdapter(raceAdapter);
        spinner_race.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                race = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence>  languagesAdapter = ArrayAdapter.createFromResource(this,R.array.languages, android.R.layout.simple_spinner_item);
        languagesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_languages.setAdapter(languagesAdapter);
        spinner_languages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                spokenLanguage = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence>  citizenshipAdapter = ArrayAdapter.createFromResource(this,R.array.citizenship, android.R.layout.simple_spinner_item);
        citizenshipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_citizenship.setAdapter(citizenshipAdapter);
        spinner_citizenship.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                citizenship = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence>  maritalStatusAdapter = ArrayAdapter.createFromResource(this,R.array.maritalstatus, android.R.layout.simple_spinner_item);
        maritalStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_maritalStatus.setAdapter(maritalStatusAdapter);
        spinner_maritalStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                maritalStatus = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence>  chasAdapter = ArrayAdapter.createFromResource(this,R.array.chas, android.R.layout.simple_spinner_item);
        chasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_chas.setAdapter(chasAdapter);
        spinner_chas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                chas = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
