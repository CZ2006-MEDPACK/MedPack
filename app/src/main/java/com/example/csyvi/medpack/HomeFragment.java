package com.example.csyvi.medpack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * The type Home fragment.
 */
public class HomeFragment extends Fragment {

    TextView name, address, contactNo, dateOfBirth, citizenship, gender, race, spokenLanguage, maritalStatus, chasInfo;
    ArrayList<Patient> patientList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bookappointment_homepage, container, false);

        name = view.findViewById(R.id.text_name);
        address = view.findViewById(R.id.text_address);
        contactNo = view.findViewById(R.id.text_contactNo);
        dateOfBirth = view.findViewById(R.id.text_dob);
        citizenship = view.findViewById(R.id.text_citizenship);
        gender = view.findViewById(R.id.text_gender);
        race = view.findViewById(R.id.text_race);
        maritalStatus = view.findViewById(R.id.text_maritalstatus);
        spokenLanguage = view.findViewById(R.id.text_spokenlanguage);
        chasInfo = view.findViewById(R.id.text_chas);

        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();

        patientList = (ArrayList<Patient>) bundle.getSerializable("ListPatient");
        for(Patient patient : patientList)
        {
            name.setText(patient.getName());
            address.setText(patient.getAddress());
            contactNo.setText(patient.getContactNo());
            dateOfBirth.setText(patient.getDateOfBirth());
            citizenship.setText(patient.getCitizenship());
            gender.setText(patient.getGender());
            race.setText(patient.getRace());
            maritalStatus.setText(patient.getMaritalStatus());
            spokenLanguage.setText(patient.getSpokenLanguage());
            chasInfo.setText(patient.getChasInfo());
        }


        Button button = view.findViewById(R.id.bookAppointment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ScanVitalSignsActivity.class);
                //Log.d("debugMEDPACK", "tesT");
                getActivity().startActivity(intent);
            }
        });

        return view;
    }
}