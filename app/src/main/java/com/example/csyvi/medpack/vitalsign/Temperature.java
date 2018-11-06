package com.example.csyvi.medpack.vitalsign;

public class Temperature extends VitalSigns {
    private static float bodyTemperature;

    public Temperature(float bodyTemperature){
        this.bodyTemperature = bodyTemperature;
    }

    /**
     * Gets body temperature.
     *
     * @return the body temperature
     */
    public static float getBodyTemperature() {
        return bodyTemperature;
    }

    /**
     * Sets body temperature.
     *
     * @param bodyTemperature the body temperature
     */
    public static void setBodyTemperature(float bodyTemperature) {
        Temperature.bodyTemperature = bodyTemperature;
    }


}
