package com.example.csyvi.medpack;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * The type Maps activity.
 */
public class MapsActivity extends Fragment {

    ArrayList<Clinic> clinicList = new ArrayList<>();
    ListView listView;
    ArrayAdapter<String> adapter;
    //Clinic[] clinic_array;
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
<<<<<<< HEAD
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
=======
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = this.getActivity();
>>>>>>> dc584c4698f005e13a0525207a413355071c5f1c
        Log.d("chasClinic", "testMaps");
        Bundle args = getArguments();
        clinicList = (ArrayList<Clinic>) args.getSerializable("ListClinic");
        View view = inflater.inflate(R.layout.locateclinic, container, false);
        Log.d("storeDATA", "testMaps");

        /*ArrayList<String> testArray = new ArrayList<String>();
        testArray.add("clinic1");
        testArray.add("clinic2");
        testArray.add("clinic3");
        testArray.add("clinic4");
        testArray.add("clinic5");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,testArray);*/

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



        CustomAdapter adapter = new CustomAdapter();
        listView = view.findViewById(R.id.listView);
        Log.d("storeDATA", "testMaps");
        listView.setAdapter(adapter);
        Log.d("timeCheck", "timeStart");
        return view;
    }
}
