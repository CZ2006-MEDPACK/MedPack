package com.example.csyvi.medpack;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.Buffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * The type Book appointment manager.
 */
public class BookAppointmentManager {

    Context mContext;

    public BookAppointmentManager(Context mContext) {
        this.mContext = mContext;
    }

    private ArrayList<Appointment> appointmentList = new ArrayList<>();
    String appointmentId, datetime;
    int queueNo, waitingTime;
    VitalSigns patientVS;
    String appointment;

    /**
     * This method will insert patient's info into the textfile database
     */
    public void insertAppointment(Clinic which) {
        Appointment currentAppointment;
        queueNo = generateQueueNo();
        appointmentId = generateAppointmentID(queueNo);
        datetime = generateAppointmentDateTime();
        waitingTime = calculateWaitingTime(datetime);

        currentAppointment = new Appointment(appointmentId,queueNo,waitingTime,datetime);
        Log.d("testMsg",currentAppointment.toString());
        appointmentList.add(currentAppointment);

        StringBuilder data = new StringBuilder();
        //data.append(patient.toString()).append("chasSupport{chas=\"" + chasSupport + "\"}").append(which.toString());
        data.append("chasSupport{nric=\"" + CurrentPatient.getNric() + "\"")
                .append("name=\"" + CurrentPatient.getName() + "\"")
                .append("contactNo=\"" + CurrentPatient.getContactNo() + "\"")
                .append("spokenLanguage=\"" + CurrentPatient.getSpokenLanguage() + "\"")
                .append("chas=\"" + CurrentPatient.getChasInfo() + "\"}")
                .append(which.toString())
                .append(appointmentList.toString());
                //.append(patientVS.toString());

        FileOutputStream fos = null;
        Log.d("testMsg",data.toString());

        try {
            fos = mContext.openFileOutput("appointment.txt", mContext.MODE_PRIVATE);
            Log.d("APPOINTMENT", "PATH: " + mContext.getFilesDir());
            fos.write(data.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String loadAppointment(){
        FileInputStream fis = null;
        try{
            fis = mContext.openFileInput("appointment.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null)
            {
                sb.append(text).append("\n");
            }
            appointment = sb.toString();
            Log.d("APPOINTMENT", "LOAD");
            Toast.makeText(mContext, sb.toString(), Toast.LENGTH_LONG).show();
        }catch (IOException e )
        {
            e.printStackTrace();
        }finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return appointment;
    }

    // used to save the current date
    public void saveCurrentDate()
    {
        Log.d("testMsg","testfunctions1");
        //SharedPreferences sharedPrefs = mContext.getSharedPreferences("savedDate",0);
        Log.d("testMsg","testfunctions2");
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        Log.d("testMsg","testfunctions3");
        //sharedPrefs.edit().putInt("lastRecordedDay", currentDay).commit();
        Log.d("testMsg","testfunctions4");
    }

    // increment queue number from 1 and reset to 1 for the next day
    public int generateQueueNo()
    {
        int startingQueueNo = 1; // start queue number from 001 everyday

        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        Log.d("testMsg","testfunctions4-1");
        //sharedPrefs.edit().putInt("savedDate", currentDay).commit();
        Log.d("testMsg","testfunctions5");
        //int lastRecordedDay = sharedPrefs.getInt("lastRecordedDay", 0);
        Log.d("testMsg","testfunctions6");
        //if (currentDay != lastRecordedDay) {
            startingQueueNo = 0;
            saveCurrentDate();
            Log.d("testMsg","testfunctions7");
        //}

        //else
        //{
            Log.d("testMsg","testfunctions8");
            startingQueueNo++;
        //}

        return startingQueueNo;
    }

    public String generateAppointmentID(int queueNo)
    {
        Log.d("testMsg","testfunctions9");
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        Log.d("testMsg","testfunctions10");
        String appointmentId = currentDay + "-" + Integer.toString(queueNo);

        return appointmentId;
    }

    public String generateAppointmentDateTime()
    {
        Log.d("testMsg","testfunctions11");
        Calendar calendar = Calendar.getInstance();

        // increment hour by default of 3
        calendar.add(Calendar.HOUR, 3);

        Log.d("testMsg","testfunctions12");
        String dateTime = Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
        Log.d("testMsg","testfunctions13");

        return dateTime;
    }

    public int calculateWaitingTime(String datetime)
    {
        int waitingTime = 0;

        Log.d("testMsg","testfunctions14");
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        Log.d("testMsg","testfunctions15");
        //SharedPreferences sharedPrefs = mContext.getSharedPreferences("savedHour",0);
        Log.d("testMsg","testfunctions16");
        //int lastRecordedHour = sharedPrefs.getInt("lastRecordedHour", 0);
        Log.d("testMsg","testfunctions17");
        //if (currentHour != lastRecordedHour) {
         //   waitingTime = lastRecordedHour - currentHour;
            saveCurrentTime();
            Log.d("testMsg","testfunctions18");
        //}

        //else
        {
            waitingTime = 0;
          //  Log.d("testMsg","testfunctions19");
        }

        return waitingTime;
    }

    public void saveCurrentTime()
    {
        Log.d("testMsg","testfunctions20");
        //SharedPreferences sharedPrefs = mContext.getSharedPreferences("savedTime",0);
        Log.d("testMsg","testfunctions21");
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);// increment hour by default of 3
        Log.d("testMsg","testfunctions22");
        Calendar.getInstance().add(Calendar.HOUR, 3);
        Log.d("testMsg","testfunctions23");
        //sharedPrefs.edit().putInt("lastRecordedHour", currentHour).commit();
        Log.d("testMsg","testfunctions24");
    }
}
