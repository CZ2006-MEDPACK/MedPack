package com.example.csyvi.medpack.vitalsign;

public class OxygenSaturation extends VitalSigns {
    private static double oxygenSaturation;

    public OxygenSaturation(double oxygenSaturation){
        this.oxygenSaturation = oxygenSaturation;
    }

    /**
     * Gets oxygen saturation.
     *
     * @return the oxygen saturation
     */
    public static double getOxygenSaturation() {
        return oxygenSaturation;
    }

    /**
     * Sets oxygen saturation.
     *
     * @param oxygenSaturation the oxygen saturation
     */
    public static void setOxygenSaturation(double oxygenSaturation) {
        OxygenSaturation.oxygenSaturation = oxygenSaturation;
    }
}
