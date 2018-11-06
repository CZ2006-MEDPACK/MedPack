package com.example.csyvi.medpack;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
            View view = View.inflate(getActivity(),R.layout.medicalprescription_customlayout,null);
            Log.d("medprescription","new test");
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

        //prescriptionList = MedicalPrescriptionManager.loadPrescription();

        //Log.d("testMsg",recordList.toString());
        View view = inflater.inflate(R.layout.activity_medicationscheduler, container, false);

        listView = view.findViewById(R.id.listViewPrescriptions);
        listView.setAdapter(adapter);

        return view;
    }
}