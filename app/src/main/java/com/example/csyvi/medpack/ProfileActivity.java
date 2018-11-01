package com.example.csyvi.medpack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class ProfileActivity extends AppCompatActivity {

    EditText et_lastName, et_firstName, et_address, et_contactNo;
    DatePicker dp_dob;
    Spinner spinner_race,spinner_citizenship, spinner_maritalStatus;
    RadioButton radio_male, radio_female;
    CheckBox checkbox_english, checkbox_mandarin, checkbox_malay, checkbox_tamil;
    Button submit_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterprofile);

        et_lastName = findViewById(R.id.et_lastname);
        et_firstName = findViewById(R.id.et_firstname;
        et_address = findViewById(R.id.et_address);
        et_contactNo = findViewById(R.id.et_contactnumber);
        dp_dob = findViewById(R.id.dp_dateofbirth);
        spinner_race = findViewById(R.id.spinner_race);
        spinner_citizenship = findViewById(R.id.spinner_citizenship);
        spinner_maritalStatus = findViewById(R.id.spinner_maritalstatus);
        radio_male = findViewById(R.id.radio_male);
        radio_female = findViewById(R.id.radio_female;
        checkbox_english = findViewById(R.id.checkbox_english);
        checkbox_mandarin = findViewById(R.id.checkbox_mandarin);
        checkbox_malay = findViewById(R.id.checkbox_malay);
        checkbox_tamil = findViewById(R.id.checkbox_tamil);
        submit_profile = findViewById(R.id.submitProfileButton);
    }
}
