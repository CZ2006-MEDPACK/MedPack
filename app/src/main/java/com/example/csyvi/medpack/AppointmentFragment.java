package com.example.csyvi.medpack;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * The type Second fragment.
 */
public class AppointmentFragment extends Fragment {

    ListView listView;
    Context mContext;
    CustomAdapter adapter = new CustomAdapter();
    String[] test;
    ArrayList<Record> recordList;
    TextView textView_appointmentid, textView_appointmentdate, textView_datetime, textView_clinicname, textView_cliniccontactno, textView_cliniclocation;

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return recordList.size();
        }

        @Override
        public Object getItem(int position) {
            return recordList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            View view = View.inflate(getActivity(), R.layout.medicalrecord_customlayout,null);

            textView_appointmentid = view.findViewById(R.id.tv_appointmentid);
            textView_appointmentdate = view.findViewById(R.id.tv_appointmentdate);
            textView_clinicname = view.findViewById(R.id.tv_clinicname);
            textView_cliniccontactno = view.findViewById(R.id.tv_cliniccontactno);
            textView_cliniclocation = view.findViewById(R.id.tv_cliniclocation);

            textView_appointmentid.setText(recordList.get(position).getAppointmentId());
            textView_appointmentdate.setText(recordList.get(position).getAppointmentDate());
            textView_clinicname.setText(recordList.get(position).getClinicName());
            textView_cliniccontactno.setText(recordList.get(position).getClinicContactNo());
            textView_cliniclocation.setText(recordList.get(position).getClinicLocation());

            return view;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = this.getActivity();
        BookAppointmentManager bookAppointmentManager = new BookAppointmentManager(mContext);

        //test = bookAppointmentManager.loadAppointment();
        recordList = bookAppointmentManager.loadAppointment();
        //arrayList = Arrays.asList(test);

        Log.d("testMsg",recordList.toString());
        View view = inflater.inflate(R.layout.medicalrecord, container, false);

        listView = view.findViewById(R.id.listViewRecord);
        listView.setAdapter(adapter);

        return view;
    }
}