package com.example.csyvi.medpack;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class MedicalPrescriptionManager {

    Context mContext;

    public MedicalPrescriptionManager(Context mContext) {
        this.mContext = mContext;
    }

    MedicalPrescription currentPrescription;
    ArrayList<MedicalPrescription> prescriptionList = new ArrayList<>();
    String prescriptionId, name, uses, doses;

    /**
     * This method will insert patient's prescription into the textfile database
     */
    public void insertPrescription(/**Clinic which**/) {
        prescriptionId = "PRESC-001A";
        currentPrescription = new MedicalPrescription(prescriptionId, name, uses, doses);
        Log.d("testMsg", currentPrescription.toString());

        /**StringBuilder data = new StringBuilder();
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
        }**/

    }

    public ArrayList<MedicalPrescription> loadPrescription() {
        /**String[] records, clinic, appointment;
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

        return recordList;**/
        ArrayList<MedicalPrescription> prescriptionList = new ArrayList<>();
        return prescriptionList;
    }
}