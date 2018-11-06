package com.example.csyvi.medpack;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

/**
 * The type First fragment.
 */
public class UpdateProfileFragment extends Fragment {

    EditText et_address, et_contactNo;
    Spinner spinner_citizenship, spinner_maritalStatus, spinner_chasInfo;
    Button update_profile;
    ArrayList<Patient> patientList = new ArrayList<Patient>();
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    String userId;
    String s_nric, s_name, s_address, s_contactNo, s_dob, s_citizenship, s_gender, s_race, s_spokenlanguage, s_maritalstatus, s_chasinfo;
    PatientManager pm = new PatientManager();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_updateprofile, container, false);

        et_address = view.findViewById(R.id.et_address2);
        et_contactNo = view.findViewById(R.id.et_contactnumber2);
        spinner_citizenship = view.findViewById(R.id.spinner_citizenship2);
        spinner_maritalStatus = view.findViewById(R.id.spinner_maritalstatus2);
        spinner_chasInfo = view.findViewById(R.id.spinner_chas2);
        update_profile = view.findViewById(R.id.updateProfileButton);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userId = user.getUid();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("patient").child(userId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        s_nric = dataSnapshot.child("nric").getValue().toString();
                        s_name = dataSnapshot.child("name").getValue().toString();
                        s_address = dataSnapshot.child("address").getValue().toString();
                        s_contactNo = dataSnapshot.child("contactNo").getValue().toString();
                        s_dob = dataSnapshot.child("dateOfBirth").getValue().toString();
                        s_citizenship = dataSnapshot.child("citizenship").getValue().toString();
                        s_gender = dataSnapshot.child("gender").getValue().toString();
                        s_race = dataSnapshot.child("race").getValue().toString();
                        s_maritalstatus = dataSnapshot.child("maritalStatus").getValue().toString();
                        s_spokenlanguage = dataSnapshot.child("spokenLanguage").getValue().toString();
                        s_chasinfo = dataSnapshot.child("chasInfo").getValue().toString();
                    }

                    ArrayAdapter<CharSequence>  citizenshipAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.citizenship, android.R.layout.simple_spinner_item);
                    citizenshipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_citizenship.setAdapter(citizenshipAdapter);
                    spinner_citizenship.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                            s_citizenship = adapterView.getItemAtPosition(position).toString();
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
                            s_maritalstatus = adapterView.getItemAtPosition(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    ArrayAdapter<CharSequence>  chasAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.chas, android.R.layout.simple_spinner_item);
                    chasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_chasInfo.setAdapter(chasAdapter);
                    spinner_chasInfo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                            s_chasinfo = adapterView.getItemAtPosition(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    et_address.setText(s_address);
                    et_contactNo.setText(s_contactNo);
                    spinner_citizenship.setSelection(citizenshipAdapter.getPosition(s_citizenship));
                    spinner_maritalStatus.setSelection(maritalStatusAdapter.getPosition(s_maritalstatus));
                    spinner_chasInfo.setSelection(chasAdapter.getPosition(s_chasinfo));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s_address = et_address.getText().toString();
                s_contactNo = et_contactNo.getText().toString();
                s_citizenship = spinner_citizenship.getSelectedItem().toString();
                s_maritalstatus = spinner_maritalStatus.getSelectedItem().toString();
                s_chasinfo = spinner_chasInfo.getSelectedItem().toString();

                if (pm.validateUpdateProfile(s_address, s_contactNo, s_citizenship, s_maritalstatus, s_chasinfo))
                {
                    // update to the database if the input is not empty
                    Patient patient = new Patient(s_nric,s_name,s_address,s_contactNo,s_dob,s_citizenship,s_gender,s_race,s_spokenlanguage,s_maritalstatus,s_chasinfo);
                    Log.d("testMsg",patient.toString());
                    Map<String, Object> postValues = patient.toMap();
                    databaseReference.updateChildren(postValues);

                    Toast.makeText(getActivity(), "Profile updated!", Toast.LENGTH_SHORT).show();
                }

                else {
                    Toast.makeText(getActivity(), "Please enter all the details.", Toast.LENGTH_SHORT).show();
                }

               }
            });

                return view;
    }

}
