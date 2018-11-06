package com.example.csyvi.medpack.others;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.csyvi.medpack.R;
import com.example.csyvi.medpack.patient.CurrentPatient;
import com.example.csyvi.medpack.patient.Patient;
import com.example.csyvi.medpack.vitalsign.ScanVitalSignsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * The type Home fragment.
 */
public class HomeFragment extends Fragment {

    TextView name, address, contactNo, dateOfBirth, citizenship, gender, race, spokenLanguage, maritalStatus, chasInfo;
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    Patient patient;
    String userId;
    String s_nric, s_name, s_address, s_contactNo, s_dob, s_citizenship, s_gender, s_race, s_spokenlanguage, s_maritalstatus, s_chasinfo;

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

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userId = user.getUid();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("patient").child(userId);

        Log.d("errorMsg", databaseReference.toString());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
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

                    name.setText(s_name);
                    address.setText(s_address);
                    contactNo.setText(s_contactNo);
                    dateOfBirth.setText(s_dob);
                    citizenship.setText(s_citizenship);
                    gender.setText(s_gender);
                    race.setText(s_race);
                    maritalStatus.setText(s_maritalstatus);
                    spokenLanguage.setText(s_spokenlanguage);
                    chasInfo.setText(s_chasinfo);

                    //Note: use to share Current Patient Info across activities
                    CurrentPatient.setNric(s_nric);
                    CurrentPatient.setName(s_name);
                    CurrentPatient.setContactNo(s_contactNo);
                    CurrentPatient.setSpokenLanguage(s_spokenlanguage);
                    CurrentPatient.setChasInfo(s_chasinfo);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //Intent intent = getActivity().getIntent();
        //patientList = (ArrayList<Patient>) intent.getSerializableExtra("ListPatient");
        //patientList = (ArrayList<Patient>) getArguments().getSerializable("ListPatient");
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