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
import java.util.Arrays;

/**
 * The type Second fragment.
 */
public class AppointmentFragment extends Fragment {
    
    ListView listView;
    CustomAdapter adapter = new CustomAdapter();
    Context mContext;
    String[] test;
    ArrayList<String> arrayList = new ArrayList<>();

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
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

            textView_queuenumber.setText("nigga");
            textView_waitingtime.setText(arrayList.get(position));
            textView_datetime.setText(arrayList.get(position));
            textView_clinicname.setText(arrayList.get(position));
            textView_cliniccontactno.setText(arrayList.get(position));
            String location = arrayList.get(position) + arrayList.get(position);
            textView_cliniclocation.setText(location);

            /*textView_queuenumber.setText(test[14]);
            textView_waitingtime.setText(test[15]);
            textView_datetime.setText(test[16]);
            textView_clinicname.setText(test[5]);
            textView_cliniccontactno.setText(test[8]);
            String location = test[6] + test[7];
            textView_cliniclocation.setText(location);*/

            return view;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = this.getActivity();

        BookAppointmentManager bookAppointmentManager = new BookAppointmentManager(mContext);
        test = bookAppointmentManager.loadAppointment();
        Log.d("testMsg", test[5]);

        arrayList = new ArrayList<String>(Arrays.asList(test));
        Log.d("testMsg",arrayList.toString());
        View view = inflater.inflate(R.layout.medicalrecord, container, false);

        listView = view.findViewById(R.id.listViewRecord);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        listView.setAdapter(adapter);

        return view;
    }
}