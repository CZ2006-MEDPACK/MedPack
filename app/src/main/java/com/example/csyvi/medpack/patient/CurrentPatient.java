package com.example.csyvi.medpack.patient;

public class CurrentPatient {
    private static String nric;
    private static String name;
    private static String contactNo;
    private static String spokenLanguage;
    private static String chasInfo;

    public static String getNric() {
        return nric;
    }

    public static void setNric(String nric) {
        CurrentPatient.nric = nric;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        CurrentPatient.name = name;
    }

    public static String getContactNo() {
        return contactNo;
    }

    public static void setContactNo(String contactNo) {
        CurrentPatient.contactNo = contactNo;
    }

    public static String getSpokenLanguage() {
        return spokenLanguage;
    }

    public static void setSpokenLanguage(String spokenLanguage) {
        CurrentPatient.spokenLanguage = spokenLanguage;
    }

    public static String getChasInfo() {return chasInfo;}

    public static void setChasInfo(String chasInfo) {
        CurrentPatient.chasInfo = chasInfo;}
}
