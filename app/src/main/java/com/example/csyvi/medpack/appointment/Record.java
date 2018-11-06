package com.example.csyvi.medpack.appointment;

public class Record {
    String appointmentId;
    String appointmentCreatedOn;
    String clinicName;
    String clinicContactNo;
    String clinicLocation;

    public Record(String appointmentId, String appointmentCreatedOn, String clinicName, String clinicContactNo, String clinicLocation) {
        this.appointmentId = appointmentId;
        this.appointmentCreatedOn = appointmentCreatedOn;
        this.clinicName = clinicName;
        this.clinicContactNo = clinicContactNo;
        this.clinicLocation = clinicLocation;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAppointmentDate() {
        return appointmentCreatedOn;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentCreatedOn = appointmentDate;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getClinicContactNo() {
        return clinicContactNo;
    }

    public void setClinicContactNo(String clinicContactNo) {
        this.clinicContactNo = clinicContactNo;
    }

    public String getClinicLocation() {
        return clinicLocation;
    }

    public void setClinicLocation(String clinicLocation) {
        this.clinicLocation = clinicLocation;
    }

    @Override
    public String toString() {
        return "Record{" +
                "appointmentId='" + appointmentId +
                ", appointmentDate='" + appointmentCreatedOn +
                ", clinicName='" + clinicName +
                ", clinicContactNo='" + clinicContactNo +
                ", clinicLocation='" + clinicLocation +
                '}';
    }
}
