package com.example.csyvi.medpack.patient;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Patient.
 */
public class Patient {
    private String nric;
    private String name;
    private String address;
    private String contactNo;
    private String dateOfBirth;
    private String citizenship;
    private String gender;
    private String race;
    private String spokenLanguage;
    private String maritalStatus;
    private String chasInfo;

    /**
     * Instantiates a new Patient.
     *
     * @param nric            the nric
     * @param name        the name
     * @param address         the address
     * @param contactNo       the contact no
     * @param dateOfBirth     the date of birth
     * @param citizenship     the citizenship
     * @param gender          the gender
     * @param race            the race
     * @param spokenLanguage the spoken language
     * @param maritalStatus   the marital status
     * @param chasInfo     the chas info
     */
    public Patient(String nric, String name, String address, String contactNo, String dateOfBirth, String citizenship, String gender, String race, String spokenLanguage, String maritalStatus, String chasInfo) {
        this.nric = nric;
        this.name= name;
        this.address = address;
        this.contactNo = contactNo;
        this.dateOfBirth = dateOfBirth;
        this.citizenship = citizenship;
        this.gender = gender;
        this.race = race;
        this.spokenLanguage = spokenLanguage;
        this.maritalStatus = maritalStatus;
        this.chasInfo = chasInfo;
    }

    /**
     * Gets nric.
     *
     * @return the nric
     */
    public String getNric() {
        return nric;
    }

    /**
     * Sets nric.
     *
     * @param nric the nric
     */
    public void setNric(String nric) {
        this.nric = nric;
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
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets contact no.
     *
     * @return the contact no
     */
    public String getContactNo() {
        return contactNo;
    }

    /**
     * Sets contact no.
     *
     * @param contactNo the contact no
     */
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    /**
     * Gets date of birth.
     *
     * @return the date of birth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets date of birth.
     *
     * @param dateOfBirth the date of birth
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Gets citizenship.
     *
     * @return the citizenship
     */
    public String getCitizenship() {
        return citizenship;
    }

    /**
     * Sets citizenship.
     *
     * @param citizenship the citizenship
     */
    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    /**
     * Gets gender.
     *
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets gender.
     *
     * @param gender the gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets race.
     *
     * @return the race
     */
    public String getRace() {
        return race;
    }

    /**
     * Sets race.
     *
     * @param race the race
     */
    public void setRace(String race) {
        this.race = race;
    }

    /**
     * Gets spoken language.
     *
     * @return the spoken language
     */
    public String getSpokenLanguage() {
        return spokenLanguage;
    }

    /**
     * Sets spoken languages.
     *
     * @param spokenLanguage the spoken language
     */
    public void setSpokenLanguage(String spokenLanguage) {
        this.spokenLanguage = spokenLanguage;
    }

    /**
     * Gets marital status.
     *
     * @return the marital status
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Sets marital status.
     *
     * @param maritalStatus the marital status
     */
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     * Gets chas info.
     *
     * @return the chas info
     */
    public String getChasInfo() {
        return chasInfo;
    }

    /**
     * Sets chas info.
     *
     * @param chasInfo the chas info
     */
    public void setChasInfo(String chasInfo) {
        this.chasInfo = chasInfo;
    }

    @Override
    public String toString() {
        return nric +
                "~" + name +
                "~" + address +
                "~" + contactNo +
                "~" + dateOfBirth +
                "~" + citizenship +
                "~" + gender +
                "~" + race +
                "~" + spokenLanguage +
                "~" + maritalStatus +
                "~" + chasInfo +
                "~";
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("nric", nric);
        result.put("name", name);
        result.put("address", address);
        result.put("contactNo", contactNo);
        result.put("dateOfBirth", dateOfBirth);
        result.put("citizenship", citizenship);
        result.put("gender", gender);
        result.put("race", race);
        result.put("spokenLanguage", spokenLanguage);
        result.put("maritalStatus", maritalStatus);
        result.put("chasInfo", chasInfo);

        return result;
    }
}
