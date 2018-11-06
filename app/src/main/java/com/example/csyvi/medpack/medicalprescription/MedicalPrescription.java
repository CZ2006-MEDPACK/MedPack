package com.example.csyvi.medpack.medicalprescription;

/**
 * The type Medical prescription.
 */
public class MedicalPrescription {
    private String prescription_Id;
    private String name;
    private String uses;
    private String doses;

    /**
     * Instantiates a new Medical prescription.
     *
     * @param prescription_id the prescription id
     * @param name            the name
     * @param uses            the uses
     * @param doses           the doses
     */
    public MedicalPrescription(String prescription_id, String name, String uses, String doses) {
        this.prescription_Id = prescription_id;
        this.name = name;
        this.uses = uses;
        this.doses = doses;
    }

    /**
     * Gets prescription id.
     *
     * @return the prescription id
     */
    public String getPrescription_Id() {
        return prescription_Id;
    }

    /**
     * Sets prescription id.
     *
     * @param prescription_Id the prescription id
     */
    public void setPrescription_Id(String prescription_Id) {
        this.prescription_Id = prescription_Id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets uses.
     *
     * @return the uses
     */
    public String getUses() {
        return uses;
    }

    /**
     * Sets uses.
     *
     * @param uses the uses
     */
    public void setUses(String uses) {
        this.uses = uses;
    }

    /**
     * Gets doses.
     *
     * @return the doses
     */
    public String getDoses() {
        return doses;
    }

    /**
     * Sets doses.
     *
     * @param doses the doses
     */
    public void setDoses(String doses) {
        this.doses = doses;
    }

    @Override
    public String toString() {
        return "MedicalPrescription{" +
                "prescriptionId='" + prescription_Id +
                ", name='" + name +
                ", doses='" + doses +
                '}';
    }
}
