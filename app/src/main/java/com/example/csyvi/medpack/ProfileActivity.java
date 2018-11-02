package com.example.csyvi.medpack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    EditText et_lastName, et_firstName, et_address, et_contactNo, et_allergyInfo;
    DatePicker dp_dob;
    Spinner spinner_race,spinner_citizenship, spinner_maritalStatus;
    RadioButton radio_male, radio_female;
    CheckBox checkbox_english, checkbox_mandarin, checkbox_malay, checkbox_tamil;
    Button submit_profile;
    ArrayList<Patient> patient = new ArrayList<Patient>();

    String lastName, firstName, address, dob, race, citizenship, maritalStatus, gender, spokenLanguages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprofile);

        et_lastName = findViewById(R.id.et_lastname);
        et_firstName = findViewById(R.id.et_firstname);
        et_address = findViewById(R.id.et_address);
        et_contactNo = findViewById(R.id.et_contactnumber);
        dp_dob = findViewById(R.id.dp_dateofbirth);
        spinner_race = findViewById(R.id.spinner_race);
        spinner_citizenship = findViewById(R.id.spinner_citizenship);
        spinner_maritalStatus = findViewById(R.id.spinner_maritalstatus);
        radio_male = findViewById(R.id.radio_male);
        radio_female = findViewById(R.id.radio_female);
        checkbox_english = findViewById(R.id.checkbox_english);
        checkbox_mandarin = findViewById(R.id.checkbox_mandarin);
        checkbox_malay = findViewById(R.id.checkbox_malay);
        checkbox_tamil = findViewById(R.id.checkbox_tamil);
        et_allergyInfo = findViewById(R.id.et_allergy);
        submit_profile = findViewById(R.id.submitProfileButton);

        setProfileAdapters();



    }

    public void setProfileAdapters()
    {
        ArrayAdapter<CharSequence>  raceAdapter = ArrayAdapter.createFromResource(this,R.array.race, android.R.layout.simple_spinner_item);
        raceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_race.setAdapter(raceAdapter);
        spinner_race.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                race = adapterView.getItemAtPosition(position).toString();
            }
        });


        ArrayAdapter<CharSequence>  citizenshipAdapter = ArrayAdapter.createFromResource(this,R.array.citizenship, android.R.layout.simple_spinner_item);
        citizenshipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_citizenship.setAdapter(citizenshipAdapter);
        spinner_citizenship.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                citizenship = adapterView.getItemAtPosition(position).toString();
            }
        });

        ArrayAdapter<CharSequence>  maritalStatusAdapter = ArrayAdapter.createFromResource(this,R.array.maritalstatus, android.R.layout.simple_spinner_item);
        maritalStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_maritalStatus.setAdapter(maritalStatusAdapter);
        spinner_maritalStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                maritalStatus = adapterView.getItemAtPosition(position).toString();
            }
        });
    }

}
