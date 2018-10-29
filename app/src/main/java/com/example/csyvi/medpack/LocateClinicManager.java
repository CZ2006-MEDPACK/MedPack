package com.example.csyvi.medpack;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
    private Geocoder geocoder;
    private String fileTodownload = "https://data.gov.sg/dataset/31e92629-980d-4672-af33-cec147c18102/download";
    private ArrayList<Clinic> chasClinic = new ArrayList<>();


    //use either surroundingChas or clinicList to populate the surrounding general practitioner
    private ArrayList<Clinic> surroundingChas = new ArrayList<>();
    private ArrayList<Clinic> clinicList = new ArrayList<>();

    public ArrayList<Clinic> getClinicList() {
        return clinicList;
    }

    public LocateClinicManager(Context mContext) {
        this.mContext = mContext;
        geocoder = new Geocoder(mContext);

        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void compare(){
        for (Clinic c : clinicList){
            String name = c.getName().toUpperCase();
            String postal = c.getPostalCode();
            postal = postal.replace("Singapore ", "");
            postal = postal.trim();
            for (Clinic chas: chasClinic){
                String chasPostal = chas.getPostalCode();
                if (((chas.getName()).contains(name)) && chasPostal.equals(postal)) {
//                    Log.d("chasClinic", chas.getName() + " compare with " + name + "| " + (chas.getName().contains(name)) + " | " + chas.getPostalCode());
                    surroundingChas.add(c);
                }
            }
        }

        for (Clinic a: surroundingChas)
        {
            Log.d("chasClinic", "surroundingChas: " + a.getName() + ", " + a.getDistance());
        }
    }

    public void userLocation() {

        final LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                user_LatLng = new LatLng(location.getLatitude(), location.getLongitude());
//                downloadCommand();
                readKML();
                calculatingDirection();
                locatingName();
                siteRetrieve();
                latLngChecker();
                distSearch();
                compare();
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
        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);
    }

    public void calculatingDirection(){
        int counter = 0;
        if (user_LatLng.longitude < 103.819839){
            direction.add("West");
            Log.d("direction", "west");
        }
        else if (user_LatLng.longitude > 103.819839){
            direction.add("East");
            Log.d("direction", "east");
        }
        if (user_LatLng.latitude > (1.3521+0.05))
        {
            direction.add("North");
            Log.d("direction", "north");
        }
        else
        {
            direction.add("Central");
            Log.d("direction", "central");
        }
        for(String s: direction)
        {
            if (s.equals("North") || s.equals("East"))
                counter++;
        }
        if (counter == 2) {
            direction.add("Northeast");
            Log.d("direction", "north east");
        }
    }

    public void locatingName (){
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

    public void siteRetrieve() {

        ArrayList<String> clinicName = new ArrayList<>();
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
    }

    public void latLngChecker(){
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
    }

    public void distSearch(){
        StringBuilder urlBuilder = new StringBuilder();
        StringBuilder stringBuilder = new StringBuilder();
        urlBuilder.append("https://maps.googleapis.com/maps/api/distancematrix/json?origins="
                + user_LatLng.latitude + "," + user_LatLng.longitude +
                "&destinations=");
        for(Clinic a: clinicList)
        {
            urlBuilder.append(a.getLatitude() + "," + a.getLongitude() + "|");
        }
        urlBuilder.deleteCharAt((urlBuilder.length()-1));
        urlBuilder.append("&key=AIzaSyCkU5Dt6se9ziYISEEGXse6nxRAQud5awk");
        Log.d("ReturnResult", urlBuilder.toString());
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlBuilder.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

//            Log.d("ReturnResult", "stringBuilder: " + stringBuilder.toString());

            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject = new JSONObject(stringBuilder.toString());
                JSONArray rowList = jsonObject.getJSONArray("rows");
//                Log.d("ReturnResult", rowList.toString());
                JSONObject row = rowList.getJSONObject(0);
//                Log.d("ReturnResult", "row: " +row.toString());
                JSONArray elements = row.getJSONArray("elements");
//                Log.d("ReturnResult", "elements: " + elements.toString());
                int i = 0;
                for (Clinic a : clinicList) {
                    JSONObject element = elements.getJSONObject(i);
//                    Log.d("ReturnResult", "element single record: " + element.toString());
                    JSONObject distanceRecord = element.getJSONObject("distance");
//                    Log.d("ReturnResult", "distance Record: " + distanceRecord.toString());
                    Double distanceText = distanceRecord.getDouble("value");
//                    Log.d("ReturnResult", "clinic distance: " + distanceText);
                    clinicList.get(i).setDistance(distanceText);
                    i++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        for(Clinic a : clinicList)
//        {
//            Log.d("ReturnResult", "clinic name: " + a.getName() + ", clinic latitude and longitude: " + a.getLatitude()+"," + a.getLongitude()
//                    + " | distance from user: " + a.getDistance());
//
//        }
    }

    protected String downloadCommand() {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(fileTodownload);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int fileLength = connection.getContentLength();

            // download the file
            input = connection.getInputStream();
            output = new FileOutputStream(mContext.getFilesDir()+"/chas.kml");
            Log.d("downloaded", String.valueOf(mContext.getFilesDir())+"/chas.kml");
            byte data[] = new byte[4096];
            long total = 0;
            double percent = 0.0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                percent = (double) (total * 100 / fileLength);
                Log.d("downloaded", "Current percent is: " + percent + "%");
                output.write(data, 0, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException e) {
                Log.e("Error: ", e.getMessage());
            }

            if (connection != null)
                connection.disconnect();
        }
        return null;
    }

    public void readKML(){
        String kmlData = null;
        try{
            File file = new File((mContext.getFilesDir()+"/chas.kml"));
            InputStream InputStream = new FileInputStream(file);
            int size = InputStream.available();
            byte[] buffer = new byte[size];
            InputStream.read(buffer);
            InputStream.close();
            kmlData = new String(buffer);
            Document doc = Jsoup.parse(kmlData, "", Parser.xmlParser());
            int i =0;
            String name = "";
            String postal = "";
            Elements e =  doc.select("ExtendedData").select("SchemaData");
            Elements chasName = e.select("SimpleData[name=\"HCI_NAME\"]");
            Elements chasPostal = e.select("SimpleData[name=\"POSTAL_CD\"]");

            for (Element a:chasName){
                name = a.text();
                postal = chasPostal.get(i).text();
//                    Log.d("chasClinic", "chasName: " + name + " | chasPostal: " + postal);
                chasClinic.add(new Clinic(name.toUpperCase(),postal));
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
