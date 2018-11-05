package com.example.csyvi.medpack;

/**
 * The type Appointment.
 */
public class Appointment {
    private String appointmentId;
    private String appointmentCreatedOn;

    public Appointment(String appointmentId, String appointmentCreatedOn) {
        this.appointmentId = appointmentId;
        this.appointmentCreatedOn = appointmentCreatedOn;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAppointmentCreatedOn() {
        return appointmentCreatedOn;
    }

    public void setAppointmentCreatedOn(String appointmentCreatedOn) {
        this.appointmentCreatedOn = appointmentCreatedOn;
    }

    @Override
    public String toString() {
        return "AppointmentInfo{" + appointmentId +
                "~" + appointmentCreatedOn +
                "}";
    }
}
