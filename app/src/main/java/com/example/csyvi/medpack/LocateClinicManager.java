package com.example.csyvi.medpack;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

/**
 * The type Locate clinic manager.
 */
public class LocateClinicManager {
    private Context mContext;
    private LatLng user_LatLng;
    private LocationManager locationManager;
    private ArrayList<String> direction = new ArrayList<>();
    private ArrayList<String> locationAL = new ArrayList<>();
    private ArrayList<String> storage = new ArrayList<>();
    private ArrayList<Clinic> clinicList = new ArrayList<>();
    private Geocoder geocoder;

    /**
     * This method will instantiate Context and Geocoder objects
     * @param mContext
     */
    private LocateClinicManager(Context mContext) {
        this.mContext = mContext;
        geocoder = new Geocoder(mContext);

        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Call the other methods to retrieve locations' details
     */
    private void userLocation() {
        final LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                user_LatLng = new LatLng(location.getLatitude(), location.getLongitude());
                calculatingDirection();
                locatingName();
                siteRetrieve();
                latLngChecker();
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
            @Override
            public void onProviderEnabled(String provider) {
            }
            @Override
            public void onProviderDisabled(String provider) {
            }
        };
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);
    }

    /**
     *
     */
    private void calculatingDirection(){
        int counter = 0;
        if (user_LatLng.longitude < 103.819839){
            direction.add("West");
        }
        else if (user_LatLng.longitude > 103.819839){
            direction.add("East");
        }
        if (user_LatLng.latitude > (1.3521+0.05))
        {
            direction.add("North");
        }
        else
        {
            direction.add("Central");
        }
        for(String s: direction)
        {
            if (s.equals("North") || s.equals("East"))
                counter++;
        }
        if (counter == 2)
            direction.add("Northeast");
    }

    /**
     * This method will read the file and locate the name
     */
    private void locatingName (){
        for (String s: direction) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(
                        new InputStreamReader(mContext.getAssets().open(s + ".txt")));

                // do reading, usually loop until end of file reading
                String mLine;
                while ((mLine = reader.readLine()) != null) {
                    //process line
                    locationAL.add(mLine);
                }
            } catch (IOException e) {
                //log the exception
                Log.d("Exception", e.getMessage());
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        //log the exception
                        Log.d("Exception", e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * This method will retrieve clinic details (excluding longitude & latitude) from SingHealth and insert into arraylist
     */
    private void siteRetrieve() {

        ArrayList<String> clinicName = new ArrayList<>();

        Log.d("USERss", "--------------------------------------------------------------");
        for (String s : locationAL) {
            String keyword = s;
            Document doc = null;
            try {
                int counter = 0;
                doc = Jsoup.connect("https://www.singhealth.com.sg/PatientCare/GP/Pages/" + keyword + ".aspx").get();
                String title = doc.title();
                Elements rows = doc.select("tbody > tr > td");
                for (Element row : rows) {
                    if (!row.text().equals(" ") && !row.text().equals("")) {
                        if ((row.text()).equals(keyword))
                        counter = 1;
                        if (counter == 1 && ((row.text()).contains("Tel No: ") || (row.text()).contains("Tel: "))) {
                            clinicName.add((row.select("strong")).text());
                            storage.add(row.text());
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int i = 0;
        String name, postalCode, address, phoneNumber, operating_hour;
        for (String a: storage) {
            if (!a.equals(clinicName.get(i))) {
                a = a.replace(clinicName.get(i), "");
                a = a.trim();
                address = a.substring(0, a.indexOf(" Singapore "));
                a = a.replace(address, "");
                a = a.trim();
                postalCode = a.substring((a.indexOf("Singapore ")), (a.indexOf("Singapore ") + 16));
                a = a.replace(postalCode, "");
                a = a.trim();
                if (a.contains("Tel: ")) {
                    phoneNumber = a.substring((a.indexOf("Tel: ") + 5), (a.indexOf("Tel: ") + 14));
                    a = a.replace("Tel: ", "");
                } else {
                    phoneNumber = a.substring((a.indexOf("Tel No: ") + 8), (a.indexOf("Tel No: ") + 16));
                    a = a.replace("Tel No: ", "");
                }
                a = a.replace(phoneNumber, "");
                a = a.trim();
                operating_hour = a;
                clinicList.add(new Clinic(clinicName.get(i), address, postalCode, phoneNumber, operating_hour));
            }
            else
            {
                operating_hour = a.substring(a.indexOf("Operating Hours: "), a.length());
                a = a.replace(operating_hour, "");
                a = a.trim();
                if (a.contains("Tel: ")){
                    phoneNumber = a.substring((a.indexOf("Tel: ")+ 5), a.length());
                    a = a.replace("Tel: ", "");
                }
                else {
                    phoneNumber = a.substring((a.indexOf("Tel No: ")+8), a.length());
                    a = a.replace("Tel No: ", "");
                }
                a = a.replace(phoneNumber, "");
                a = a.trim();
                postalCode = a.substring((a.indexOf("Singapore ")), (a.indexOf("Singapore ") + 16));
                a = a.replace(postalCode, "");
                a = a.trim();
                name = a.split("[0-9]")[0];
                a = a.replace(name, "");
                a = a.trim();
                address = a;
                clinicList.add(new Clinic(name, address, postalCode, phoneNumber, operating_hour));
            }
            i++;
        }
//        for(Clinic a: clinicList)
//        {
//            Log.d("Clinic_Record", "clinic name: " + a.getName() + ", address: " + a.getAddress() + ", postal code: " + a.getPostalCode()
//                    + ", Tel No: " + a.getPhone_number() + ", " + a.getOperating_hour());
//        }
    }

    /**
     * This method will extract the latitude and longitude
     */
    private void latLngChecker(){
        List<Address> list;
        int z = 0;
        for (Clinic a : clinicList) {
            try {
                list = (geocoder.getFromLocationName(clinicList.get(z).getPostalCode(), 1));
                clinicList.get(z).setLatitude(list.get(0).getLatitude());
                clinicList.get(z).setLongitude(list.get(0).getLongitude());
            } catch (IOException e) {
                e.printStackTrace();
            }
            z++;
        }
        for(Clinic a: clinicList)
        {
            Log.d("CRESULT", "clinic name: " + a.getName() + ", latitude: " + a.getLatitude() + ", longitude: " + a.getLongitude());
        }

    }
}
