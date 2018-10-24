package com.example.csyvi.medpack;

/**
 * The type Vital signs.
 */
public class VitalSigns {
    private String vitals_Id;
    private float bodyTemperature;
    private int pulseRate;
    private int respiratoryRate;
    private String bloodPressure;
    private int oxygenSaturation;
    private int painScale;

    /**
     * Instantiates a new Vital signs.
     *
     * @param vitals_Id        the vitals id
     * @param bodyTemperature  the body temperature
     * @param pulseRate        the pulse rate
     * @param respiratoryRate  the respiratory rate
     * @param bloodPressure    the blood pressure
     * @param oxygenSaturation the oxygen saturation
     * @param painScale        the pain scale
     */
    public VitalSigns(String vitals_Id, float bodyTemperature, int pulseRate, int respiratoryRate, String bloodPressure, int oxygenSaturation, int painScale) {
        this.vitals_Id = vitals_Id;
        this.bodyTemperature = bodyTemperature;
        this.pulseRate = pulseRate;
        this.respiratoryRate = respiratoryRate;
        this.bloodPressure = bloodPressure;
        this.oxygenSaturation = oxygenSaturation;
        this.painScale = painScale;
    }

    /**
     * Gets vitals id.
     *
     * @return the vitals id
     */
    public String getVitals_Id() {
        return vitals_Id;
    }

    /**
     * Sets vitals id.
     *
     * @param vitals_Id the vitals id
     */
    public void setVitals_Id(String vitals_Id) {
        this.vitals_Id = vitals_Id;
    }

    /**
     * Gets body temperature.
     *
     * @return the body temperature
     */
    public float getBodyTemperature() {
        return bodyTemperature;
    }

    /**
     * Sets body temperature.
     *
     * @param bodyTemperature the body temperature
     */
    public void setBodyTemperature(float bodyTemperature) {
        this.bodyTemperature = bodyTemperature;
    }

    /**
     * Gets pulse rate.
     *
     * @return the pulse rate
     */
    public int getPulseRate() {
        return pulseRate;
    }

    /**
     * Sets pulse rate.
     *
     * @param pulseRate the pulse rate
     */
    public void setPulseRate(int pulseRate) {
        this.pulseRate = pulseRate;
    }

    /**
     * Gets respiratory rate.
     *
     * @return the respiratory rate
     */
    public int getRespiratoryRate() {
        return respiratoryRate;
    }

    /**
     * Sets respiratory rate.
     *
     * @param respiratoryRate the respiratory rate
     */
    public void setRespiratoryRate(int respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }

    /**
     * Gets blood pressure.
     *
     * @return the blood pressure
     */
    public String getBloodPressure() {
        return bloodPressure;
    }

    /**
     * Sets blood pressure.
     *
     * @param bloodPressure the blood pressure
     */
    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    /**
     * Gets oxygen saturation.
     *
     * @return the oxygen saturation
     */
    public int getOxygenSaturation() {
        return oxygenSaturation;
    }

    /**
     * Sets oxygen saturation.
     *
     * @param oxygenSaturation the oxygen saturation
     */
    public void setOxygenSaturation(int oxygenSaturation) {
        this.oxygenSaturation = oxygenSaturation;
    }

    /**
     * Gets pain scale.
     *
     * @return the pain scale
     */
    public int getPainScale() {
        return painScale;
    }

    /**
     * Sets pain scale.
     *
     * @param painScale the pain scale
     */
    public void setPainScale(int painScale) {
        this.painScale = painScale;
    }
}
