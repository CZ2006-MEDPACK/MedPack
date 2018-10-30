package com.example.csyvi.medpack;

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

    LocateClinicManager clinicManager;
    ListView listView;
    ArrayAdapter<String> adapter;
    Clinic[] clinic_array;

//    class CustomAdapter extends BaseAdapter {
//        @Override
//        public int getCount() {
//            return 0;
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return 0;
//        }

//        @Override
//        public View getView(int position, View view, ViewGroup viewGroup) {
//            Log.d("chasClinic", "testACtivity4");
//            view = getLayoutInflater().inflate(R.layout.locateclinic_customlayout,null);
//
//            Clinic currentClinic = clinicManager.getClinicList().get(position);
//
//            TextView textView_clinicname = view.findViewById(R.id.clinicName);
//            TextView textView_clinicaddress = view.findViewById(R.id.clinicAddress);
//            TextView textView_cliniccontactno = view.findViewById(R.id.clinicContactNo);
//            TextView textView_clinicoperatinghours = view.findViewById(R.id.clinicoperatinghours);
//
//            textView_clinicname.setText(currentClinic.getName());
//            textView_clinicaddress.setText(currentClinic.getAddress());
//            textView_cliniccontactno.setText(currentClinic.getPhone_number());
//            textView_clinicoperatinghours.setText(currentClinic.getOperating_hour());
//
//            return view;
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        clinicManager = (LocateClinicManager) args.getSerializable("ListClinic");
        View view = inflater.inflate(R.layout.locateclinic, container, false);

        /*ArrayList<String> testArray = new ArrayList<String>();
        testArray.add("clinic1");
        testArray.add("clinic2");
        testArray.add("clinic3");
        testArray.add("clinic4");
        testArray.add("clinic5");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,testArray);*/


        //CustomAdapter adapter = new CustomAdapter();

        clinic_array = new Clinic[clinicManager.getClinicList().size()];
        clinicManager.getClinicList().toArray(clinic_array);
        String[] name = new String[clinic_array.length];
        int i = 0;
        for (Clinic a : clinic_array){
            name[i] = a.getName();
            i++;
        }
        Log.d("chasClinic", "testACtivity5");
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,name);
        listView = view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        return view;
    }
}
