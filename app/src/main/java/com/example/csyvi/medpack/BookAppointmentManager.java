package com.example.csyvi.medpack;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.Buffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * The type Book appointment manager.
 */
public class BookAppointmentManager {

    Context mContext;

    public BookAppointmentManager(Context mContext) {
        this.mContext = mContext;
    }

    Appointment currentAppointment;
    String test;
    String[] record;
    ArrayList<Record> recordList = new ArrayList<Record>();
    String appointmentId, appointmentCreatedOn,clinicName, clinicLocation, clinicPostal, clinicAddress, clinicContactNo;

    /**
     * This method will insert patient's info into the textfile database
     */
    public void insertAppointment(Clinic which) {
        appointmentCreatedOn = generateAppointmentDateTime();
        appointmentId = generateAppointmentID(which, appointmentCreatedOn);
        currentAppointment = new Appointment(appointmentId, appointmentCreatedOn);
        Log.d("testMsg", currentAppointment.toString());

        StringBuilder data = new StringBuilder();
        //data.append(patient.toString()).append("chasSupport{chas=\"" + chasSupport + "\"}").append(which.toString());
        data.append("PatientInfo{" + CurrentPatient.getNric())
                .append("~" + CurrentPatient.getName())
                .append("~" + CurrentPatient.getContactNo())
                .append("~" + CurrentPatient.getSpokenLanguage())
                .append("~" + CurrentPatient.getChasInfo() + "};Hello")
                .append(which.toString() + ";Hello")
                .append(currentAppointment.toString() + ";Hello")
                .append("VitalSignsInfo{" + VitalSigns.getPulseRate())
                .append("~" + VitalSigns.getOxygenSaturation())
                .append("~" + VitalSigns.getBloodPressure())
                .append("~" + VitalSigns.getRespiratoryRate())
                .append("~" + VitalSigns.getBodyTemperature())
                .append("~" + VitalSigns.getPainScale()  + "}");

        Log.d("testMsg", data.toString());
        BufferedWriter bufferedWriter = null;

        try {
            FileOutputStream fos = mContext.openFileOutput("appointment.txt", mContext.MODE_APPEND);
            Log.d("APPOINTMENT", "PATH: " + mContext.getFilesDir());
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos));
            bufferedWriter.write(data.toString());
            bufferedWriter.write("\n");
            bufferedWriter.close();
            fos.close();
            //fos.write(data.toString().getBytes());
            //fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Record> loadAppointment() {
        String[] records, clinic, appointment;
        FileInputStream fis = null;
        try {
            fis = mContext.openFileInput("appointment.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            //StringBuilder sb = new StringBuilder();
            String text = br.readLine();
            while (text != null) {
                Log.d("testMsg", text);
                records = text.split(";Hello");
                Log.d("testMsg", "first: " + records[0]);
                Log.d("testMsg", "first: " + records[1]);
                Log.d("testMsg", "first: " + records[2]);

                records[1] = records[1].replace("Clinic{", "");
                records[1] = records[1].replace("}", "");
                clinic = records[1].split("~");

                records[2] = records[2].replace("AppointmentInfo{", "");
                records[2] = records[2].replace("}", "");
                appointment = records[2].split("~");

                clinicName = clinic[0];
                clinicAddress = clinic[1];
                clinicPostal = clinic[2];
                clinicContactNo = clinic[3];
                clinicLocation = clinicAddress + " " + clinicPostal;
                appointmentId = appointment[0];
                appointmentCreatedOn = appointment[1];

                Log.d("testMsg","test1");
                Log.d("testMsg",clinicName);
                Log.d("testMsg",clinicAddress);
                Log.d("testMsg",clinicPostal);
                Log.d("testMsg",clinicContactNo);
                Log.d("testMsg",clinicLocation);
                Log.d("testMsg",appointmentId);
                Log.d("testMsg",appointmentCreatedOn);

                recordList.add(new Record(appointmentId,appointmentCreatedOn,clinicName,clinicContactNo,clinicLocation));
                Log.d("testMsg","test2");
                //sb.append(text).append("\n");
                text = br.readLine();
            }

            br.close();
            Log.d("APPOINTMENT", "LOAD");
            //Toast.makeText(mContext, sb.toString(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return recordList;
    }

    public String generateAppointmentID(Clinic clinic, String date) {
        StringBuilder appointmentId = new StringBuilder();
        String text = clinic.getName();
        String uniqueA = text.substring(0,1);
        String unqiueB = text.substring(3,4);
        String unqiueC = text.substring(1,4);
        text = clinic.getAddress();
        String uniqueD = text.substring(0,2);
        String uniqueE = text.substring(3,4);
        text = date;
        String unqiueF = text.substring(0,2);
        String unqiueG = text.substring(3,5);

        appointmentId.append(unqiueF).append(unqiueB).append(uniqueD).append(unqiueG).append(uniqueA)
                .append(unqiueC).append(uniqueE);

        return appointmentId.toString();
    }

    public String generateAppointmentDateTime() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

}
