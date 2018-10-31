package com.example.csyvi.medpack;

/**
 * The type Patient.
 */
public class Patient {
    private String nric;
    private String lastName;
    private String firstName;
    private String address;
    private int contactNo;
    private String dateOfBirth;
    private String citizenship;
    private String gender;
    private String race;
    private String spokenLanguages;
    private String maritalStatus;
    private String allergyInfo;

    /**
     * Instantiates a new Patient.
     *
     * @param nric            the nric
     * @param lastName        the last name
     * @param firstName       the first name
     * @param address         the address
     * @param contactNo       the contact no
     * @param dateOfBirth     the date of birth
     * @param citizenship     the citizenship
     * @param gender          the gender
     * @param race            the race
     * @param spokenLanguages the spoken languages
     * @param maritalStatus   the marital status
     * @param allergyInfo     the allergy info
     */
    public Patient(String nric, String lastName, String firstName, String address, int contactNo, String dateOfBirth, String citizenship, String gender, String race, String spokenLanguages, String maritalStatus, String allergyInfo) {
        this.nric = nric;
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.contactNo = contactNo;
        this.dateOfBirth = dateOfBirth;
        this.citizenship = citizenship;
        this.gender = gender;
        this.race = race;
        this.spokenLanguages = spokenLanguages;
        this.maritalStatus = maritalStatus;
        this.allergyInfo = allergyInfo;
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
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
    public int getContactNo() {
        return contactNo;
    }

    /**
     * Sets contact no.
     *
     * @param contactNo the contact no
     */
    public void setContactNo(int contactNo) {
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
     * Gets spoken languages.
     *
     * @return the spoken languages
     */
    public String getSpokenLanguages() {
        return spokenLanguages;
    }

    /**
     * Sets spoken languages.
     *
     * @param spokenLanguages the spoken languages
     */
    public void setSpokenLanguages(String spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
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
     * Gets allergy info.
     *
     * @return the allergy info
     */
    public String getAllergyInfo() {
        return allergyInfo;
    }

    /**
     * Sets allergy info.
     *
     * @param allergyInfo the allergy info
     */
    public void setAllergyInfo(String allergyInfo) {
        this.allergyInfo = allergyInfo;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "nric='" + nric + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", address='" + address + '\'' +
                ", contactNo=" + contactNo +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", citizenship='" + citizenship + '\'' +
                ", gender='" + gender + '\'' +
                ", race='" + race + '\'' +
                ", spokenLanguages='" + spokenLanguages + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", allergyInfo='" + allergyInfo + '\'' +
                '}';
    }
}
