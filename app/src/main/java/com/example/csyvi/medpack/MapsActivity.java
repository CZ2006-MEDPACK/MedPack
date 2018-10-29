package com.example.csyvi.medpack;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * The type Maps activity.
 */
public class MapsActivity extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    /**
     * The Ap manager.
     */
    LocateClinicManager apManager;

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            //view = getLayoutInflater().inflate(R.layout.locateclinic_customlayout,null);

            //TextView textView_clinicname = view.findViewById(R.id.clinicName);
            //TextView textView_clinicaddress = view.findViewById(R.id.clinicAddress);
           // TextView textView_cliniccontactno = view.findViewById(R.id.clinicContactNo);
            //TextView textView_clinicoperatinghours = view.findViewById(R.id.clinicoperatinghours);

           // textView_clinicname.setText(clinicList.get(i).getName());
            //textView_clinicaddress.setText(clinicList.get(i).getName());
            //textView_cliniccontactno.setText(clinicList.get(i).getName());
           // textView_clinicoperatinghours.setText(clinicList.get(i).getOperating_hour());

            return null;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.locateclinic, container, false);

        return view;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        apManager.userLocation();
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        Log.d("TERM", "Check");
    }
}
