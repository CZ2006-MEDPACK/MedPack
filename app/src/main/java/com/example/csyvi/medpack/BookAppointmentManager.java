package com.example.csyvi.medpack;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * The type Book appointment manager.
 */
public class BookAppointmentManager {

    Context mContext;

    public BookAppointmentManager(Context mContext){
        this.mContext = mContext;
    }

    private ArrayList<Appointment> appointmentList = new ArrayList<>();

    /**
     * This method will insert patient's info into the textfile database
     */
    private void insertAppointment(Patient patient, boolean chasSupport, Clinic which)
    {
        StringBuilder data = new StringBuilder();
        data.append(patient.toString()).append("chasSupport=\"" +chasSupport + "\"").append(which.toString());
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(mContext.openFileOutput("appointment.txt", Context.MODE_PRIVATE));
            Log.d("APPOINTMENT", data.toString());
            outputStreamWriter.write(data.toString());
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * This method will update patient's info in the textfile database
     */
    private void updateAppointment()
    {
    }

    /**
     * This method will delete patient from the textfile database
     */
    private void deleteAppointment()
    {
    }
}
