package com.example.csyvi.medpack;

public class PatientManager {

    public Boolean validateUpdateProfile(String address, String contactNo, String citizenship, String maritalStatus, String chas)
    {
        Boolean result;

        if(address.isEmpty() || contactNo.isEmpty() || citizenship.isEmpty() ||  maritalStatus.isEmpty() || chas.isEmpty())
        {
            result = false;
        }

        else
        {
            result = true;
        }

        return result;
    }

    public Boolean validateInsertProfile(String nric, String name, String address, String contactNo, String dob, String citizenship, String gender, String race, String spokenLanguage, String maritalStatus, String chas)
    {
        Boolean result;

        if(nric.isEmpty() || name.isEmpty() || address.isEmpty() || contactNo.isEmpty() || dob.isEmpty() ||
                citizenship.isEmpty() || gender.isEmpty() || race.isEmpty() || spokenLanguage.isEmpty() || maritalStatus.isEmpty() || chas.isEmpty())
        {
            result = false;
        }

        else
        {
            result = true;
        }

        return result;
    }
}
