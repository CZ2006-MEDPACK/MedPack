package com.example.csyvi.medpack;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

/**
 * The type First fragment.
 */

public class VitalSignsFragment extends Fragment{

    EditText PulseRate, OxygenSaturation, Temperature;
    EditText BloodPressureSystolic, BloodPressureDiastolic, RespiratoryRate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.measurevitalsigns_inputvitalsigns, container, false);
        PulseRate = (EditText)view.findViewById(R.id.editTextPulseRate);
        OxygenSaturation = (EditText)view.findViewById(R.id.editTextOxygenSaturation);
        BloodPressureSystolic = (EditText)view.findViewById(R.id.editTextBPSystolic);
        BloodPressureDiastolic = (EditText)view.findViewById(R.id.editTextBPDiastolic);
        Temperature = (EditText)view.findViewById(R.id.editTextEnterTemperature);
        RespiratoryRate = (EditText)view.findViewById(R.id.editTextRespiratoryRate);

        MeasureVitalSignsManager vs = new MeasureVitalSignsManager();

        PulseRate.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(MeasureVitalSignsManager.checkPulseRate(PulseRate.getText().toString()))
                {
                    Toast.makeText(getContext(), "Abnormal values detected! Please re-enter values or see a doctor immediately!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getContext(), "Values Recorded!", Toast.LENGTH_SHORT).show();
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

        Button submitButton = view.findViewById(R.id.button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MapsActivity()).commit();
            }
        });

        return view;
    }

}
