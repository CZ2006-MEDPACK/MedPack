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
public class AppointmentFragment extends Fragment {

    ArrayList<Clinic> clinicList = new ArrayList<>();
    ListView listView;
    CustomAdapter adapter = new CustomAdapter();
    Clinic clinic;
    Context mContext;

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return clinicList.size();
        }

        @Override
        public Object getItem(int position) {
            return clinicList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            View view = View.inflate(getActivity(),R.layout.medicalrecord_customlayout,null);

            TextView textView_queuenumber = view.findViewById(R.id.tv_queueno);
            TextView textView_waitingtime = view.findViewById(R.id.tv_waitingtime);
            TextView textView_datetime = view.findViewById(R.id.tv_datetime);
            TextView textView_clinicname = view.findViewById(R.id.tv_clinicname);
            TextView textView_cliniccontactno = view.findViewById(R.id.tv_cliniccontactno);
            TextView textView_cliniclocation = view.findViewById(R.id.tv_cliniclocation);

            textView_queuenumber.setText(clinicList.get(position).getName());
            textView_waitingtime.setText(clinicList.get(position).getName());
            textView_datetime.setText(clinicList.get(position).getName());
            textView_clinicname.setText(clinicList.get(position).getName());
            textView_cliniccontactno.setText(clinicList.get(position).getName());
            textView_cliniclocation.setText(clinicList.get(position).getName());

            return view;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.locateclinic, container, false);

        BookAppointmentManager bookAppointmentManager = new BookAppointmentManager(mContext);
        bookAppointmentManager.loadAppointment();

        listView = view.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        return view;
    }
}