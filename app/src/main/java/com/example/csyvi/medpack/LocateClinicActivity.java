package com.example.csyvi.medpack;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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
 * The type Maps activity.
 */
public class LocateClinicActivity extends Fragment {

    ArrayList<Clinic> clinicList = new ArrayList<>();
    ListView listView;
    CustomAdapter adapter = new CustomAdapter();
    //ArrayAdapter<String> adapter;
    Clinic clinic;
    Context mContext;
    int bookingCount = 0;

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
            Log.d("chasClinic", "testACtivity4");
            View view = View.inflate(getActivity(),R.layout.locateclinic_customlayout,null);

            TextView textView_clinicname = view.findViewById(R.id.clinicName);
            TextView textView_clinicaddress = view.findViewById(R.id.clinicAddress);
            TextView textView_cliniccontactno = view.findViewById(R.id.clinicContactNo);
            TextView textView_clinicoperatinghours = view.findViewById(R.id.clinicoperatinghours);
            TextView textView_clinicdistance = view.findViewById(R.id.clinicDistance);

            textView_clinicname.setText(clinicList.get(position).getName());
            textView_clinicaddress.setText(clinicList.get(position).getAddress());
            textView_cliniccontactno.setText(clinicList.get(position).getPhone_number());
            textView_clinicoperatinghours.setText(clinicList.get(position).getOperating_hour());
            Double distance = clinicList.get(position).getDistance();
            distance = distance / 1000;
            String getDistance = Double.toString(distance);
            textView_clinicdistance.setText(getDistance + "KM");

            return view;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = this.getActivity();
        Log.d("chasClinic", "testMaps");
        Bundle args = getArguments();
        clinicList = (ArrayList<Clinic>) args.getSerializable("ListClinic");
        View view = inflater.inflate(R.layout.locateclinic, container, false);
        Log.d("storeDATA", "testMaps");

        listView = view.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (bookingCount < 1) {
                    clinic = clinicList.get(position);
                    Log.d("testMsg", clinic.toString());

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Book Appointment").setMessage("Do you want to book appointment on this clinic for today?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    BookAppointmentManager bookAppointmentManager = new BookAppointmentManager(mContext);
                                    bookAppointmentManager.insertAppointment(clinic);
                                    Toast.makeText(getActivity(), "You have booked your Appointment successfully! Check out Your Records!", Toast.LENGTH_LONG).show();
                                    bookingCount++;
                                    //ArrayList<String> test = bookAppointmentManager.loadAppointment();
                                    //Intent intent = new Intent(getActivity(), MainActivity.class);
                                    //startActivity(intent);
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

                else
                {
                    Toast.makeText(getActivity(), "You can only book appointment once.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listView.setAdapter(adapter);
        Log.d("timeCheck", "timeStart");

        return view;
    }
}
