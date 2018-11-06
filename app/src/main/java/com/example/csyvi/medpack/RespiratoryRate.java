package com.example.csyvi.medpack;

public class RespiratoryRate extends VitalSigns {
    private static int respiratoryRate;

    public RespiratoryRate(int respiratoryRate){
        this.respiratoryRate = respiratoryRate;
    }


    public static int getRespiratoryRate() {
        return respiratoryRate;
    }

    /**
     * Sets respiratory rate.
     *
     * @param respiratoryRate the respiratory rate
     */
    public static void setRespiratoryRate(int respiratoryRate) {
        RespiratoryRate.respiratoryRate = respiratoryRate;
    }
}
