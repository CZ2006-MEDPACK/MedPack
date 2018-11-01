package com.example.csyvi.medpack;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.MapFragment;

/**
 * The type First fragment.
 */
public class VitalSignsFragment extends Fragment {

    EditText PulseRate, OxygenSaturation, Temperature;
    EditText BloodPressureSystolic, BloodPressureDiastolic, RespiratoryRate;
    MeasureVitalSignsManager vs = new MeasureVitalSignsManager();
    LocateClinicManager clinicManager;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        clinicManager = new LocateClinicManager(this.getActivity(), this.getFragmentManager(), new MapsActivity());

        View view = inflater.inflate(R.layout.measurevitalsigns_inputvitalsigns, container, false);
        PulseRate = (EditText) view.findViewById(R.id.editTextPulseRate);
        OxygenSaturation = (EditText) view.findViewById(R.id.editTextOxygenSaturation);
        BloodPressureSystolic = (EditText) view.findViewById(R.id.editTextBPSystolic);
        BloodPressureDiastolic = (EditText) view.findViewById(R.id.editTextBPDiastolic);
        Temperature = (EditText) view.findViewById(R.id.editTextEnterTemperature);
        RespiratoryRate = (EditText) view.findViewById(R.id.editTextRespiratoryRate);
        progressDialog = new ProgressDialog(getActivity());

        PulseRate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

            }
        });

        OxygenSaturation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

            }
        });

        BloodPressureSystolic.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

            }
        });

        BloodPressureDiastolic.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

            }
        });

        Temperature.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

            }
        });

        RespiratoryRate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

            }
        });

        final Button submitButton = view.findViewById(R.id.button);
        progressDialog = new ProgressDialog(getActivity());

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("storeDATA", "entering user location");
                Log.d("timeCheck", "timeStart");
                progressDialog.setMessage("Searching for nearby clinics. Please wait.");
                progressDialog.show();
                Log.d("chasClinic", "entering user location");
                clinicManager.userLocation();
                //submitButton.setVisibility(View.INVISIBLE);
            }
        });

        return view;
    }

}
