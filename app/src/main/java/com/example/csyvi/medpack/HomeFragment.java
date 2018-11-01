package com.example.csyvi.medpack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * The type Home fragment.
 */
public class HomeFragment extends Fragment {

    TextView lastName, firstName, address, contactNo, dateOfBirth, citizenship, gender, race, spokenLanguages, maritalStatus, allergyInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bookappointment_homepage, container, false);

        lastName = view.findViewById(R.id.text_lastname);
        firstName = view.findViewById(R.id.text_firstname);
        address = view.findViewById(R.id.text_address);
        contactNo = view.findViewById(R.id.text_contactNo);
        dateOfBirth = view.findViewById(R.id.text_dob);
        citizenship = view.findViewById(R.id.text_citizenship);
        gender = view.findViewById(R.id.text_gender);
        race = view.findViewById(R.id.text_race);
        maritalStatus = view.findViewById(R.id.text_maritalstatus);
        spokenLanguages = view.findViewById(R.id.text_spokenlanguages);
        allergyInfo = view.findViewById(R.id.text_allergy);

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