package com.example.csyvi.medpack;

import android.content.Context;
import android.content.ContextWrapper;
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
import java.util.ArrayList;

/**
 * The type Book appointment manager.
 */
public class BookAppointmentManager {

    Context mContext;

    public BookAppointmentManager(Context mContext) {
        this.mContext = mContext;
    }

    private ArrayList<Appointment> appointmentList = new ArrayList<>();

    /**
     * This method will insert patient's info into the textfile database
     */
    public void insertAppointment(Patient patient, boolean chasSupport, Clinic which) {
        StringBuilder data = new StringBuilder();
        data.append(patient.toString()).append("chasSupport{chas=\"" + chasSupport + "\"}").append(which.toString());

        FileOutputStream fos = null;

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

    public void loadAppointment(){
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
    }

    /**
     * This method will update patient's info in the textfile database
     */
    public void updateAppointment() {
    }

    /**
     * This method will delete patient from the textfile database
     */
    public void deleteAppointment() {
    }
}
