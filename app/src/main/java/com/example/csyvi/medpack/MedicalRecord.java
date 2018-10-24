package com.example.csyvi.medpack;

/**
 * The type Medical record.
 */
public class MedicalRecord {
    private int record_id;
    private String transactionDate;
    private String visitType;
    private String visitConditions;
    private String amountPaid;

    /**
     * Instantiates a new Medical record.
     *
     * @param record_id       the record id
     * @param transactionDate the transaction date
     * @param visitType       the visit type
     * @param visitConditions the visit conditions
     * @param amountPaid      the amount paid
     */
    public MedicalRecord(int record_id, String transactionDate, String visitType, String visitConditions, String amountPaid) {
        this.record_id = record_id;
        this.transactionDate = transactionDate;
        this.visitType = visitType;
        this.visitConditions = visitConditions;
        this.amountPaid = amountPaid;
    }

    /**
     * Gets record id.
     *
     * @return the record id
     */
    public int getRecord_id() {
        return record_id;
    }

    /**
     * Sets record id.
     *
     * @param record_id the record id
     */
    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

    /**
     * Gets transaction date.
     *
     * @return the transaction date
     */
    public String getTransactionDate() {
        return transactionDate;
    }

    /**
     * Sets transaction date.
     *
     * @param transactionDate the transaction date
     */
    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     * Gets visit type.
     *
     * @return the visit type
     */
    public String getVisitType() {
        return visitType;
    }

    /**
     * Sets visit type.
     *
     * @param visitType the visit type
     */
    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    /**
     * Gets visit conditions.
     *
     * @return the visit conditions
     */
    public String getVisitConditions() {
        return visitConditions;
    }

    /**
     * Sets visit conditions.
     *
     * @param visitConditions the visit conditions
     */
    public void setVisitConditions(String visitConditions) {
        this.visitConditions = visitConditions;
    }

    /**
     * Gets amount paid.
     *
     * @return the amount paid
     */
    public String getAmountPaid() {
        return amountPaid;
    }

    /**
     * Sets amount paid.
     *
     * @param amountPaid the amount paid
     */
    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }
}
