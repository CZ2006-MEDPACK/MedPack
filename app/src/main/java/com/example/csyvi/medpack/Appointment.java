package com.example.csyvi.medpack;

/**
 * The type Appointment.
 */
public class Appointment {
    private String appointmentId;
    private int queueNo;
    private int waitingTime;
    private String datetime;

    /**
     * Instantiates a new Appointment.
     *
     * @param appointmentId     the appointment id
     * @param queueNo     the queue no
     * @param waitingTime the waiting time
     * @param datetime    the datetime
     */
    public Appointment(String appointmentId, int queueNo, int waitingTime, String datetime) {
        this.appointmentId = appointmentId;
        this.queueNo = queueNo;
        this.waitingTime = waitingTime;
        this.datetime = datetime;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
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
     * Gets waiting time.
     *
     * @return the waiting time
     */
    public int getWaitingTime() {
        return waitingTime;
    }

    /**
     * Sets waiting time.
     *
     * @param waitingTime the waiting time
     */
    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
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

    @Override
    public String toString() {
        return "~" + appointmentId +
                "~" + queueNo +
                "~" + waitingTime +
                "~" + datetime +
                "~";
    }
}
