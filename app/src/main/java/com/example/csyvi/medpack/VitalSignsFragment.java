package com.example.csyvi.medpack;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * The type First fragment.
 */
public class VitalSignsFragment extends Fragment {

    EditText PulseRate, OxygenSaturation, Temperature;
    EditText BloodPressureSystolic, BloodPressureDiastolic, RespiratoryRate;
    RadioGroup radioGroup;
    RadioButton radioButton;
    MeasureVitalSignsManager vs = new MeasureVitalSignsManager();
    LocateClinicManager clinicManager;
    ProgressDialog progressDialog;
    int pain = 1;

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
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId){
                if (checkedId == R.id.painScale1)
                {
                    pain = 1;
                }
                else if (checkedId == R.id.painScale2)
                {
                    pain = 2;
                }
                else if (checkedId == R.id.painScale3)
                {
                    pain = 3;
                }
                else if (checkedId == R.id.painScale4)
                {
                    pain = 4;
                }
                else if (checkedId == R.id.painScale5)
                {
                    pain = 5;
                }
                Log.d("ReturnResult", "pain: " + pain);
            }
        });

        progressDialog = new ProgressDialog(getActivity());

        final Button submitButton = view.findViewById(R.id.button);
        progressDialog = new ProgressDialog(getActivity());

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean failFlag = false;
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
                if (Temperature.getText().toString().trim().length() == 0 || Float.valueOf(Temperature.getText().toString()) < 10.0 || Float.valueOf(Temperature.getText().toString()) > 70.0)
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
                    int pulse = 68;
                    double oxygen = 100;
                    int bloodPressureSystolic = Integer.parseInt(BloodPressureSystolic.getText().toString());
                    int bloodPressureDiastolic = Integer.parseInt(BloodPressureDiastolic.getText().toString());
                    StringBuilder bloodPressure = new StringBuilder();
                    bloodPressure.append(bloodPressureSystolic).append("/").append(bloodPressureDiastolic);
                    float temperature = Float.valueOf(Temperature.getText().toString());
                    int respiratoryRate = Integer.parseInt(RespiratoryRate.getText().toString());

                    new VitalSigns(temperature, pulse, respiratoryRate, bloodPressure.toString(), oxygen, pain);

                    Log.d("ReturnResult", "vitalSign value: " + VitalSigns.getBodyTemperature() + " | " + VitalSigns.getPulseRate()
                    + " | " + VitalSigns.getRespiratoryRate() + " | " + VitalSigns.getBloodPressure() + " | " + VitalSigns.getOxygenSaturation()
                    + " | " + VitalSigns.getPainScale());

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
