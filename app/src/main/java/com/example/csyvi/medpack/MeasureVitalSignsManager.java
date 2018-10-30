package com.example.csyvi.medpack;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Measure vital signs manager.
 */
public class MeasureVitalSignsManager {
    /**
     * The Vs.
     */
    VitalSigns vs;

    /**
     * This method will check the pulse rate of the patient
     */
    public static Boolean checkPulseRate(String sn)
    {
        Boolean check = false;
        String no = "\\d+";
        CharSequence inputStr = sn;
        Pattern pte = Pattern.compile(no, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pte.matcher(inputStr);

        if(matcher.matches())
        {
            check = true;
        }
        return check;

    }

    /**
     * This method will check the oxygen saturation of the patient
     */
    private void checkOxygenSaturation()
    {
    }

    /**
     * This method will check the temperature of the patient
     */
    private void checkTemperature()
    {
    }

    /**
     * This method will check the blood pressure of the patient
     */
    private void checkBloodPressure()
    {

    }

    /**
     * This method will check the respiratory rate of the patient
     */
    private void checkRespiratoryRate()
    {
    }

    /**
     * This method will check the pain scale of the patient
     */
    private void checkPainScale()
    {
    }
}
