package com.example.csyvi.medpack;

/**
 * The type Appointment.
 */
public class Appointment {
    private int queueNo;
    private String datetime;
    private String serialNo;

    /**
     * Instantiates a new Appointment.
     *
     * @param queueNo     the queue number
     * @param datetime    the date & time of the appointment
     * @param serialNo    the appointment serial number
     */
    public Appointment(int queueNo, String datetime, String serialNo) {
        this.queueNo = queueNo;
        this.datetime = datetime;
        this.serialNo = serialNo;
    }

    /**
     * Gets queue no.
     *
     * @return the queue no
     */
    public int getQueueNo() {
        return queueNo;
    }

    /**
     * Sets queue no.
     *
     * @param queueNo the queue no
     */
    public void setQueueNo(int queueNo) {
        this.queueNo = queueNo;
    }

    /**
     * Gets datetime.
     *
     * @return the datetime
     */
    public String getDatetime() {
        return datetime;
    }

    /**
     * Sets datetime.
     *
     * @param datetime the datetime
     */
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    /**
     * Gets serial number.
     *
     * @return the serial number
     */
    public String getSerialNo() {
        return serialNo;
    }

    /**
     * Sets the serial number.
     *
     * @param serialNo the serial number
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
}