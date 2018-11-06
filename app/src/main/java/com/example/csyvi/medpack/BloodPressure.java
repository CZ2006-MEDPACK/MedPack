package com.example.csyvi.medpack;

public class BloodPressure extends VitalSigns {
    private static String bloodPressure;

    public BloodPressure(String bloodPressure){
        this.bloodPressure = bloodPressure;
    }

    /**
     * Gets blood pressure.
     *
     * @return the blood pressure
     */
    public static String getBloodPressure() {
        return bloodPressure;
    }

    /**
     * Sets blood pressure.
     *
     * @param bloodPressure the blood pressure
     */
    public static void setBloodPressure(String bloodPressure) {
        BloodPressure.bloodPressure = bloodPressure;
    }
}
