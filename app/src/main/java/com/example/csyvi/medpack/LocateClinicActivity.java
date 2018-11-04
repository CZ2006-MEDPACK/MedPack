package com.example.csyvi.medpack;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        /*clinic_array = new Clinic[clinicList.size()];
        Log.d("chasClinic", "testMaps");
        clinic_array = clinicList.toArray(clinic_array);
        Log.d("chasClinic", "testMaps");
        String[] name = new String[clinic_array.length];
        int i = 0;
        for (Clinic a : clinic_array){
            name[i] = a.getName();
            i++;
        }
        Log.d("chasClinic", "testMaps");
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,name);*/

        //testing code
//        BookAppointmentManager bookam = new BookAppointmentManager(this.getActivity());
//        bookam.insertAppointment(new Patient("testNRIC", "testLAST", "testFIRST"
//        , "testADDRESS", 12345678, "testBIRTH", "testCITIZEN"
//        , "testGENDER", "testRACE", "testLANGUAGES", "testMARITAL"
//        , "testALLERGY"), true, clinicList.get(0));
//
//
//        bookam.loadAppointment();

        listView = view.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (bookingCount < 1) {
                    clinic = clinicList.get(position);
                    Log.d("testMsg", clinic.toString());

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Book Appointment").setMessage("Do you want to book appointment on this clinic?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    BookAppointmentManager bookAppointmentManager = new BookAppointmentManager(mContext);
                                    bookAppointmentManager.insertAppointment(clinic);
                                    Toast.makeText(getActivity(), "You have booked your Appointment successfully! Check out Your Records!", Toast.LENGTH_LONG).show();
                                    bookingCount++;
                                    //bookAppointmentManager.loadAppointment();
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
