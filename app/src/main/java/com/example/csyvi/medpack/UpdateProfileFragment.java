package com.example.csyvi.medpack;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * The type First fragment.
 */
public class UpdateProfileFragment extends Fragment {

    EditText et_nric, et_name, et_address, et_contactNo, et_dob;
    Spinner spinner_race,spinner_citizenship, spinner_maritalStatus, spinner_languages, spinner_chas;
    RadioGroup radio_gender;
    RadioButton radio_male, radio_female;
    Button update_profile;
    ArrayList<Patient> patientList = new ArrayList<Patient>();
    DatePickerDialog datePickerDialog;
    Boolean genderMale;

    String nric, name, address, contactNo, dob, race, citizenship, maritalStatus, gender, spokenLanguage, chas;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_updateprofile, container, false);

        Intent intent = getActivity().getIntent();
        patientList = (ArrayList<Patient>) intent.getSerializableExtra("ListPatient");

        et_nric = view.findViewById(R.id.et_nric);
        et_name = view.findViewById(R.id.et_name);
        et_address = view.findViewById(R.id.et_address);
        et_contactNo = view.findViewById(R.id.et_contactnumber);
        et_dob = view.findViewById(R.id.et_dob);
        spinner_race = view.findViewById(R.id.spinner_race);
        spinner_citizenship = view.findViewById(R.id.spinner_citizenship);
        spinner_maritalStatus = view.findViewById(R.id.spinner_maritalstatus);
        spinner_languages = view.findViewById(R.id.spinner_languages);
        spinner_chas = view.findViewById(R.id.spinner_chas);
        radio_gender = view.findViewById(R.id.radio_gender);
        update_profile = view.findViewById(R.id.submitProfileButton);

        for(Patient patient : patientList)
        {
            et_nric.setText(patient.getNric());
            et_name.setText(patient.getName());
            et_address.setText(patient.getAddress());
            et_contactNo.setText(patient.getContactNo());
            et_dob.setText(patient.getDateOfBirth());
            setProfileAdapters();
            //spinner_race.setSelection(patient.getRace());
            //spinner_citizenship.setText(patient.getNric());
            //spinner_maritalStatus.setText(patient.getNric());
            //spinner_languages.setText(patient.getNric());
            //radio_gender.setText(patient.getNric());
        }

        update_profile.setOnClickListener(new View.OnClickListener() {
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

                if(validateProfile(nric,name,address,contactNo,dob,citizenship,gender,race,spokenLanguage,maritalStatus,chas))
                {
                    if(genderMale)
                    {
                        gender = radio_male.getText().toString();
                    }

                    else
                    {
                        gender = radio_female.getText().toString();
                    }

                    // store the patient info into the arraylist
                    patientList.remove(0);
                    patientList.add(new Patient(nric,name,address,contactNo,dob,citizenship,gender,race,spokenLanguage,maritalStatus,chas));

                    /*for(Patient patient : patientList)
                    {
                        Log.d("errorMsg",patient.toString());
                    }*/

                    Toast.makeText(getActivity(), "Profile updated!", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    Toast.makeText(getActivity(), "Please enter all the details.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    // set the adapters for the race, languages, citizenship and marital status spinners
    public void setProfileAdapters()
    {
        ArrayAdapter<CharSequence> raceAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.race, android.R.layout.simple_spinner_item);
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

        ArrayAdapter<CharSequence>  languagesAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.languages, android.R.layout.simple_spinner_item);
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

        ArrayAdapter<CharSequence>  citizenshipAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.citizenship, android.R.layout.simple_spinner_item);
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

        ArrayAdapter<CharSequence>  maritalStatusAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.maritalstatus, android.R.layout.simple_spinner_item);
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

        ArrayAdapter<CharSequence>  chasAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.chas, android.R.layout.simple_spinner_item);
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

    public Boolean validateProfile(String nric, String name, String address, String contactNo, String dob, String citizenship, String gender, String race, String spokenLanguage, String maritalStatus, String chas)
    {
        Boolean result;

        if(nric.isEmpty() || name.isEmpty() || address.isEmpty() || contactNo.isEmpty() || dob.isEmpty() ||
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
