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
    LocateClinicManager clinicManager;
    ProgressDialog progressDialog;
    int pain = 1;
    View view = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        clinicManager = new LocateClinicManager(this.getActivity(), this.getFragmentManager(), new LocateClinicActivity(), progressDialog);

        view = inflater.inflate(R.layout.measurevitalsigns_inputvitalsigns, container, false);
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
                failFlag = vs.checkBloodPressureDiastolic(BloodPressureDiastolic);
                failFlag = vs.checkBloodPressureSystolic(BloodPressureSystolic);
                failFlag = vs.checkTemperature(Temperature);
                failFlag = vs.checkRespiratoryRate(RespiratoryRate);
                if (failFlag == false) {
                    int pulse = 68;
                    double oxygen = 100;
                    int bloodPressureSystolic = Integer.parseInt(BloodPressureSystolic.getText().toString());
                    int bloodPressureDiastolic = Integer.parseInt(BloodPressureDiastolic.getText().toString());
                    StringBuilder bloodPressure = new StringBuilder();
                    bloodPressure.append(bloodPressureSystolic).append("/").append(bloodPressureDiastolic);
                    float temperature = Float.valueOf(Temperature.getText().toString());
                    int respiratoryRate = Integer.parseInt(RespiratoryRate.getText().toString());

                    new Temperature(temperature);
                    new PulseRate(pulse);
                    new RespiratoryRate(respiratoryRate);
                    new BloodPressure(bloodPressure.toString());
                    new OxygenSaturation(oxygen);
                    new PainScale(pain);

                    progressDialog.setMessage("Searching for nearby clinics. Please wait.");
                    progressDialog.show();

                    Log.d("chasClinic", "entering user location");
                    clinicManager.userLocation();
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
