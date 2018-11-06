package com.example.csyvi.medpack;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * The type Second fragment.
 */
public class PrescriptionFragment extends Fragment {

    ListView listView;
    Context mContext;
    CustomAdapter adapter = new CustomAdapter();
    ArrayList<MedicalPrescription> prescriptionList = new ArrayList<>();
    TextView textView_prescriptionId, textView_medName, textView_medUse, textView_medDosage;

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int position) {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return 1;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            View view = View.inflate(getActivity(), R.layout.medicalprescription_customlayout, null);
            Log.d("medprescription", "new test");
            textView_prescriptionId = view.findViewById(R.id.md_prescription);
            textView_medName = view.findViewById(R.id.md_medname);
            textView_medUse = view.findViewById(R.id.md_uses);
            textView_medDosage = view.findViewById(R.id.md_dosage);

            textView_prescriptionId.setText("PRESC-001A");
            textView_medName.setText("Paracetomol (acetaminophen)");
            textView_medUse.setText("For treating mild pain and fever");
            textView_medDosage.setText("2 x 500mg tablets, 3 times daily");

            return view;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = this.getActivity();
        MedicalPrescriptionManager medicalPrescriptionManager = new MedicalPrescriptionManager(mContext);

        View view = inflater.inflate(R.layout.activity_medicationscheduler, container, false);

        listView = view.findViewById(R.id.listViewPrescriptions);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Book Appointment").setMessage("Do you want to schedule your medication?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
                                Intent intent = new Intent(getActivity(), OnAlarmReceive.class);
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        getActivity(), 0, intent,
                                        PendingIntent.FLAG_UPDATE_CURRENT);


                                // Getting current time and add the seconds in it
                                Calendar cal = Calendar.getInstance();
                                cal.add(Calendar.SECOND, 5);

                                alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });

                builder.create();
                builder.show();

            }
        });
        listView.setAdapter(adapter);
        return view;
    }
}