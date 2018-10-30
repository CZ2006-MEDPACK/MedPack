package com.example.csyvi.medpack;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * The type Book appointment manager.
 */
public class BookAppointmentManager {

    private ArrayList<Appointment> appointmentList = new ArrayList<>();
    private static final String FILE_NAME = "MedPack.txt";

    EditText SaveAppointment;

    /**
     * This method will insert patient's info into the textfile database
     */
    private void insertAppointment(View view)
    {
        /*SaveAppointment = view.findViewById(R.id.bookAppointment);
        String text = SaveAppointment.getText().toString();
        FileOutputStream fos = null;
        try{
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());
            SaveAppointment.getText().clear();
            Toast.makeText(this, "Appointment Booked!" + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG)
            } catch (IOException e) {
                e.printStackTrace();
            } catch(FileNotFoundException e)
        {
            e.printStackTrace();
        } finally{
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
*/
    }

    /**
     * This method will update patient's info in the textfile database
     */
    private void updateAppointment(View view){
        /*FileInputStream fis = null;
        try{fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while((text = br.readLine()) != null){
                sb.append(text).append("\n");
            }
            SaveAppointment.setText(sb.toString());
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(fis !=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }

    /**
     * This method will delete patient from the textfile database
     */
    private void deleteAppointment()
    {
        PrintWriter delete = null;
        try {
        delete = new PrintWriter("MedPack.txt");
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
        delete.close();
    }
}
