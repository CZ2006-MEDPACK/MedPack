package com.example.csyvi.medpack;

import android.content.Intent;
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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    EditText et_nric, et_lastName, et_firstName, et_address, et_contactNo;
    DatePicker dp_dob;
    Spinner spinner_race,spinner_citizenship, spinner_maritalStatus, spinner_languages, spinner_chas;
    RadioGroup radio_gender;
    RadioButton radio_male, radio_female;
    Button submit_profile;
    ArrayList<Patient> patientList;

    String nric, lastName, firstName, address, dob, race, citizenship, maritalStatus, gender, spokenLanguage, chas;
    int contactNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprofile);


        Log.d("errorMsg","test1");
        et_nric = findViewById(R.id.et_nric);
        et_lastName = findViewById(R.id.et_lastname);
        et_firstName = findViewById(R.id.et_firstname);
        et_address = findViewById(R.id.et_address);
        et_contactNo = findViewById(R.id.et_contactnumber);
        dp_dob = findViewById(R.id.dp_dateofbirth);
        spinner_race = findViewById(R.id.spinner_race);
        spinner_citizenship = findViewById(R.id.spinner_citizenship);
        spinner_maritalStatus = findViewById(R.id.spinner_maritalstatus);
        spinner_languages = findViewById(R.id.spinner_languages);
        spinner_chas = findViewById(R.id.spinner_chas);
        radio_gender = findViewById(R.id.radio_gender);
        submit_profile = findViewById(R.id.submitProfileButton);

        Log.d("errorMsg","test2");
        setProfileAdapters();

        Log.d("errorMsg","test3");
        nric = et_nric.getText().toString();
        lastName = et_lastName.getText().toString();
        firstName = et_firstName.getText().toString();
        address = et_address.getText().toString();
        contactNo = Integer.parseInt(et_lastName.getText().toString());
        dob = Integer.toString(dp_dob.getDayOfMonth()) + "/" + Integer.toString(dp_dob.getMonth()) + "/" + Integer.toString(dp_dob.getYear());

        Log.d("errorMsg","test4");
        radio_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch(checkedId)
                {
                    case R.id.radio_male:
                        radio_male = findViewById(checkedId);
                        gender = radio_male.getText().toString();
                        break;

                    case R.id.radio_female:
                        radio_female = findViewById(checkedId);
                        gender = radio_female.getText().toString();
                        break;
                }
            }
        });

        submit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateProfile(nric,lastName,firstName,address,contactNo,dob,citizenship,gender,race,spokenLanguage,maritalStatus,chas))
                {
                    // store the patient info into the arraylist
                    patientList.add(new Patient(nric,lastName,firstName,address,contactNo,dob,citizenship,gender,race,spokenLanguage,maritalStatus,chas));

                    Toast.makeText(ProfileActivity.this, "Profile entered! Redirecting ..", Toast.LENGTH_SHORT).show();

                    // bring patientList arraylist to the home page
                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ListPatient", patientList);
                    intent.putExtras(bundle);
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
        Log.d("errorMsg","adapter1");
        ArrayAdapter<CharSequence>  raceAdapter = ArrayAdapter.createFromResource(this,R.array.race, android.R.layout.simple_spinner_item);
        Log.d("errorMsg","adapter1-1");
        raceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_race.setAdapter(raceAdapter);
        Log.d("errorMsg","adapter1-2");
        spinner_race.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("errorMsg","adapter1-3");
                race = adapterView.getItemAtPosition(position).toString();
            }
        });
        Log.d("errorMsg","adapter2");
        ArrayAdapter<CharSequence>  languagesAdapter = ArrayAdapter.createFromResource(this,R.array.languages, android.R.layout.simple_spinner_item);
        languagesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_languages.setAdapter(languagesAdapter);
        spinner_languages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                spokenLanguage = adapterView.getItemAtPosition(position).toString();
            }
        });

        Log.d("errorMsg","adapter3");
        ArrayAdapter<CharSequence>  citizenshipAdapter = ArrayAdapter.createFromResource(this,R.array.citizenship, android.R.layout.simple_spinner_item);
        citizenshipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_citizenship.setAdapter(citizenshipAdapter);
        spinner_citizenship.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                citizenship = adapterView.getItemAtPosition(position).toString();
            }
        });
        Log.d("errorMsg","adapter4");
        ArrayAdapter<CharSequence>  maritalStatusAdapter = ArrayAdapter.createFromResource(this,R.array.maritalstatus, android.R.layout.simple_spinner_item);
        maritalStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_maritalStatus.setAdapter(maritalStatusAdapter);
        spinner_maritalStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                maritalStatus = adapterView.getItemAtPosition(position).toString();
            }
        });
        Log.d("errorMsg","adapter5");
        ArrayAdapter<CharSequence>  chasAdapter = ArrayAdapter.createFromResource(this,R.array.chas, android.R.layout.simple_spinner_item);
        chasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_chas.setAdapter(chasAdapter);
        spinner_chas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                chas = adapterView.getItemAtPosition(position).toString();
            }
        });
    }

    public Boolean validateProfile(String nric, String lastName, String firstName, String address, int contactNo, String dob, String citizenship, String gender, String race, String spokenLanguage, String maritalStatus, String chas)
    {
        Boolean result;
        String contactString = Integer.toString(contactNo);

        if(nric.isEmpty() || lastName.isEmpty() || firstName.isEmpty() || address.isEmpty() || TextUtils.isEmpty(contactString) || dob.isEmpty() ||
                citizenship.isEmpty() || gender.isEmpty() || race.isEmpty() || spokenLanguage.isEmpty() || maritalStatus.isEmpty() || chas.isEmpty())
        {
            result = false;
        }

        else
        {
            result = true;
        }

        return result;
    }
}
