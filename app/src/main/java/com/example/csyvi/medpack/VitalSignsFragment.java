package com.example.csyvi.medpack;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
        clinicManager = new LocateClinicManager(this.getActivity(), this.getFragmentManager(), new LocateClinicActivity(), progressDialog);

        View view = inflater.inflate(R.layout.measurevitalsigns_inputvitalsigns, container, false);
        PulseRate = (EditText) view.findViewById(R.id.editTextPulseRate);
        OxygenSaturation = (EditText) view.findViewById(R.id.editTextOxygenSaturation);
        BloodPressureSystolic = (EditText) view.findViewById(R.id.editTextBPSystolic);
        BloodPressureDiastolic = (EditText) view.findViewById(R.id.editTextBPDiastolic);
        Temperature = (EditText) view.findViewById(R.id.editTextEnterTemperature);
        RespiratoryRate = (EditText) view.findViewById(R.id.editTextRespiratoryRate);
        progressDialog = new ProgressDialog(getActivity());

        final Button submitButton = view.findViewById(R.id.button);
        progressDialog = new ProgressDialog(getActivity());

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean failFlag = false;
                if (PulseRate.getText().toString().trim().length() == 0 || Integer.parseInt((PulseRate.getText().toString())) < 30 || Integer.parseInt((PulseRate.getText().toString())) >150)
                {
                    failFlag = true;
                    PulseRate.setError("Please enter a valid input from the range of 30 to 150");
                }
                if (OxygenSaturation.getText().toString().trim().length() == 0 || Integer.parseInt((OxygenSaturation.getText().toString())) < 70 || Integer.parseInt((OxygenSaturation.getText().toString())) > 100)
                {
                    failFlag = true;
                    OxygenSaturation.setError("Please enter a valid input from the range of 70% to 100%");
                }
                if (BloodPressureSystolic.getText().toString().trim().length() == 0 || Integer.parseInt((BloodPressureSystolic.getText().toString())) < 0 || Integer.parseInt(BloodPressureSystolic.getText().toString()) > 250)
                {
                    failFlag = true;
                    BloodPressureSystolic.setError("Please enter a valid input from the range of 0 to 250");
                }
                if (BloodPressureDiastolic.getText().toString().trim().length() == 0 || Integer.parseInt(BloodPressureDiastolic.getText().toString()) < 0 || Integer.parseInt(BloodPressureDiastolic.getText().toString()) > 200)
                {
                    failFlag = true;
                    BloodPressureDiastolic.setError("Please enter a valid input from the range of 0 to 200");
                }
                if (Temperature.getText().toString().trim().length() == 0 || Integer.parseInt(Temperature.getText().toString()) < 10 || Integer.parseInt(Temperature.getText().toString()) > 70)
                {
                    failFlag = true;
                    Temperature.setError("Please enter a valid input from range of 24°C to 50°C");
                }
                if (RespiratoryRate.getText().toString().trim().length() == 0 || Integer.parseInt(RespiratoryRate.getText().toString()) < 10 || Integer.parseInt(RespiratoryRate.getText().toString()) > 25)
                {
                    failFlag = true;
                    RespiratoryRate.setError("Please enter a valid input from range of 10 to 25");
                }
                if (failFlag == false) {
                    Log.d("storeDATA", "entering user location");
                    Log.d("timeCheck", "timeStart");
                    progressDialog.setMessage("Searching for nearby clinics. Please wait.");
                    progressDialog.show();
                    Log.d("chasClinic", "entering user location");
                    clinicManager.userLocation();
                    //submitButton.setVisibility(View.INVISIBLE);
                }
            }
        });

        return view;
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (progressDialog != null) progressDialog.dismiss();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, new IntentFilter("com.blah.DISMISS_DIALOG"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
    }
}
