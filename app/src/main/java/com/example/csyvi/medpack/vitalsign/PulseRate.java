package com.example.csyvi.medpack.vitalsign;

public class PulseRate extends VitalSigns {
    private static int pulseRate;

    public PulseRate(int pulseRate){
        this.pulseRate = pulseRate;
    }

    /**
     * Gets pulse rate.
     *
     * @return the pulse rate
     */
    public static int getPulseRate() {
        return pulseRate;
    }

    /**
     * Sets pulse rate.
     *
     * @param pulseRate the pulse rate
     */
    public static void setPulseRate(int pulseRate) {
        PulseRate.pulseRate = pulseRate;
    }

    /**
     * Gets respiratory rate.
     *
     * @return the respiratory rate
     */
}
