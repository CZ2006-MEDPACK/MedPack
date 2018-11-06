package com.example.csyvi.medpack.vitalsign;

/**
 * The type Vital signs.
 */
public class VitalSigns {
    private static float bodyTemperature;
    private static int pulseRate;
    private static int respiratoryRate;
    private static String bloodPressure;
    private static double oxygenSaturation;
    private static int painScale;

    /**
     * Instantiates a new Vital signs.
     *
     * @param bodyTemperature  the body temperature
     * @param pulseRate        the pulse rate
     * @param respiratoryRate  the respiratory rate
     * @param bloodPressure    the blood pressure
     * @param oxygenSaturation the oxygen saturation
     * @param painScale        the pain scale
     */
    public VitalSigns( float bodyTemperature, int pulseRate, int respiratoryRate, String bloodPressure, double oxygenSaturation, int painScale) {
        VitalSigns.bodyTemperature = bodyTemperature;
        VitalSigns.pulseRate = pulseRate;
        VitalSigns.respiratoryRate = respiratoryRate;
        VitalSigns.bloodPressure = bloodPressure;
        VitalSigns.oxygenSaturation = oxygenSaturation;
        VitalSigns.painScale = painScale;
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
        VitalSigns.bodyTemperature = bodyTemperature;
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
        VitalSigns.pulseRate = pulseRate;
    }

    /**
     * Gets respiratory rate.
     *
     * @return the respiratory rate
     */
    public static int getRespiratoryRate() {
        return respiratoryRate;
    }

    /**
     * Sets respiratory rate.
     *
     * @param respiratoryRate the respiratory rate
     */
    public static void setRespiratoryRate(int respiratoryRate) {
        VitalSigns.respiratoryRate = respiratoryRate;
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
        VitalSigns.bloodPressure = bloodPressure;
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
        VitalSigns.oxygenSaturation = oxygenSaturation;
    }

    /**
     * Gets pain scale.
     *
     * @return the pain scale
     */
    public static int getPainScale() {
        return painScale;
    }

    /**
     * Sets pain scale.
     *
     * @param painScale the pain scale
     */
    public static void setPainScale(int painScale) {
        VitalSigns.painScale = painScale;
    }


}
