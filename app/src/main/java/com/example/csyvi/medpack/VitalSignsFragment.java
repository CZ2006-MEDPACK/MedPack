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

import java.time.temporal.TemporalAccessor;

/**
 * The type First fragment.
 */
public class VitalSignsFragment extends Fragment {

    EditText PulseRate, OxygenSaturation, Temperature;
    EditText BloodPressureSystolic, BloodPressureDiastolic, RespiratoryRate;
    RadioButton radio_1, radio_2, radio_3, radio_4, radio_5;
    RadioGroup radioPainScale;
    MeasureVitalSignsManager vs = new MeasureVitalSignsManager();
    LocateClinicManager clinicManager;
    ProgressDialog progressDialog;
    String painScale;
    int pain;
    View view = null;
    RadioButton radioButton;

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
        radioPainScale = (RadioGroup) view.findViewById(R.id.radioGroup);

        /*
        radioPainScale.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                switch(checkedId)
                {
                    case R.id.radio_one:
                        radio_1 = view.findViewById(checkedId);
                        painScale = radio_1.getText().toString();
                        pain = 1;
                        break;

                    case R.id.radio_two:
                        radio_2 = view.findViewById(checkedId);
                        painScale = radio_2.getText().toString();
                        pain = 2;
                        break;

                    case R.id.radio_three:
                        radio_3 = view.findViewById(checkedId);
                        painScale = radio_3.getText().toString();
                        pain = 3;
                        break;

                    case R.id.radio_four:
                        radio_4 = view.findViewById(checkedId);
                        painScale = radio_4.getText().toString();
                        pain = 4;
                        break;

                    case R.id.radio_five:
                        radio_5 = view.findViewById(checkedId);
                        painScale = radio_5.getText().toString();
                        pain = 5;
                        break;

                }
            }
        });*/

        radioPainScale.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {



                // get selected radio button from radioGroup
                int selectedId = radioPainScale.getCheckedRadioButtonId();
                Log.d("tag","selectId" + selectedId);

                // find the radiobutton by returned id
                radioButton = (RadioButton) view.findViewById(selectedId);

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
                    /*if(pain == 1)
                    {
                        painScale = radio_1.getText().toString();
                    }

                    if(pain == 2)
                    {
                        painScale = radio_2.getText().toString();
                    }

                    if(pain == 3)
                    {
                        painScale = radio_3.getText().toString();
                    }

                    if(pain == 4)
                    {
                        painScale = radio_4.getText().toString();
                    }

                    if(pain == 5)
                    {
                        painScale = radio_5.getText().toString();
                    }*/

                    int pulse = 68;
                    double oxygen = 100;
                    //Log.d("testMsg", Integer.toString(pulse));
                    /*int bloodPressureSystolic = Integer.parseInt(BloodPressureSystolic.getText().toString());
                    int bloodPressureDiastolic = Integer.parseInt(BloodPressureDiastolic.getText().toString());
                    StringBuilder bloodPressure = new StringBuilder();
                    bloodPressure.append(bloodPressureSystolic).append("/").append(bloodPressureDiastolic);
                    float temperature = Float.valueOf(Temperature.getText().toString());
                    int respiratoryRate = Integer.parseInt(RespiratoryRate.getText().toString());*/

                    //Log.d("testMsg", bloodPressure.toString());
                    //Log.d("testMsg", Float.toString(temperature));
                    //Log.d("testMsg", Integer.toString(respiratoryRate));

                    //int pain = Integer.parseInt(radioButton.getText().toString());

                    //Log.d("testMsg", painScale);
                    //int painScale1 = Integer.parseInt(painScale);

                    //new VitalSigns(temperature, pulse, respiratoryRate, bloodPressure.toString(), oxygen, pain);

                    //Log.d("testMsg", Integer.toString(VitalSigns.getPainScale()));

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
