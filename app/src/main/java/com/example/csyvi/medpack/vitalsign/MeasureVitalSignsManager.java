package com.example.csyvi.medpack.vitalsign;


import android.widget.EditText;

/**
 * The type Measure vital signs manager.
 */
public class MeasureVitalSignsManager {


    /**
     * This method will measure the temperature of the patient
     */
    public boolean checkTemperature(EditText Temperature) {
        if (Temperature.getText().toString().trim().length() == 0 || Integer.parseInt(Temperature.getText().toString()) < 10 || Integer.parseInt(Temperature.getText().toString()) > 70) {
            Temperature.setError("Please enter a valid input from the range of 10 to 70");
            return true;
        }
        return false;
    }

    public boolean checkBloodPressureSystolic(EditText BloodPressureSystolic) {
        if (BloodPressureSystolic.getText().toString().trim().length() == 0 || Integer.parseInt((BloodPressureSystolic.getText().toString())) < 10 || Integer.parseInt(BloodPressureSystolic.getText().toString()) > 250) {
            BloodPressureSystolic.setError("Please enter a valid input from the range of 10 to 250");
            return true;
        }
        return false;
    }

    public boolean checkBloodPressureDiastolic(EditText BloodPressureDiastolic){
        if (BloodPressureDiastolic.getText().toString().trim().length() == 0 || Integer.parseInt(BloodPressureDiastolic.getText().toString()) < 0 || Integer.parseInt(BloodPressureDiastolic.getText().toString()) > 200)
        {
            BloodPressureDiastolic.setError("Please enter a valid input from the range of 0 to 200");
            return true;
        }
        return false;
    }

    /**
     * This method will measure the respiratory rate of the patient
     */
    public boolean checkRespiratoryRate(EditText RespiratoryRate) {
        if (RespiratoryRate.getText().toString().trim().length() == 0 || Integer.parseInt(RespiratoryRate.getText().toString()) < 10 || Integer.parseInt(RespiratoryRate.getText().toString()) > 25)
        {
            RespiratoryRate.setError("Please enter a valid input from range of 10 to 25");
            return true;
        }
        return false;
    }


}
