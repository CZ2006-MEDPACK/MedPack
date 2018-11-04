package com.example.csyvi.medpack;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

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
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * The type Locate clinic manager.
 */
public class LocateClinicManager implements Serializable {

    private ArrayList<String> clinicName = new ArrayList<>();
    private Context mContext;
    private LatLng user_LatLng;
    private ArrayList<String> direction = new ArrayList<>();
    private ArrayList<String> locationAL = new ArrayList<>();
    private ArrayList<String> storage = new ArrayList<>();
    private Geocoder geocoder;
    private String fileTodownload = "https://data.gov.sg/dataset/31e92629-980d-4672-af33-cec147c18102/download";
    private ArrayList<Clinic> chasClinic = new ArrayList<>();
    private ArrayList<Clinic> readClinic = new ArrayList<>();
    LocationManager locationManager;
    LocationListener locationListener;
    android.support.v4.app.FragmentManager fragmentManager;
    LocateClinicActivity myFragment;
    ProgressDialog myProgress;
    String s_chasinfo, userId;
    FileOutputStream fos = null;

    //use either surroundingChas or clinicList to populate the surrounding general practitioner
    private ArrayList<Clinic> surroundingChas = new ArrayList<>();
    private ArrayList<Clinic> clinicList = new ArrayList<>();

    public LocateClinicManager(Context mContext, android.support.v4.app.FragmentManager fragmentManager, LocateClinicActivity myFragment, ProgressDialog myProgress) {
        this.mContext = mContext;
        geocoder = new Geocoder(mContext);
        this.fragmentManager = fragmentManager;
        this.myFragment = myFragment;
        this.myProgress = myProgress;
    }


    public void userLocation() {

        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                user_LatLng = new LatLng(location.getLatitude(), location.getLongitude());
                Log.d("chasClinic", user_LatLng.latitude + " | " + user_LatLng.longitude);
                locationManager.removeUpdates(locationListener);
                Log.d("chasClinic", "gone to asyncTask");
                new dataOperation().execute("");
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 120);
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    public void calculatingDirection() {
        int counter = 0;
        if (user_LatLng.longitude < 103.819839) {
            direction.add("West");
            Log.d("direction", "west");
        } else if (user_LatLng.longitude > 103.819839) {
            direction.add("East");
            Log.d("direction", "east");
        }
        if (user_LatLng.latitude > (1.3521 + 0.05)) {
            direction.add("North");
            Log.d("direction", "north");
        } else {
            direction.add("Central");
            Log.d("direction", "central");
        }
        for (String s : direction) {
            if (s.equals("North") || s.equals("East"))
                counter++;
        }
        if (counter == 2) {
            direction.add("Northeast");
            Log.d("direction", "north east");
        }
    }

    public void locatingName(String direction) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(mContext.getAssets().open(direction + ".txt")));

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
        Log.d("chasClinic", "getting location name");
    }

    public void siteRetrieve() {
        for (String s : locationAL) {
            String keyword = s;
            Document doc = null;
            try {
                int counter = 0;
                doc = Jsoup.connect("https://www.singhealth.com.sg/PatientCare/GP/Pages/" + keyword + ".aspx").get();
                String title = doc.title();
                Elements rows = doc.select("tbody > tr > td");
                keyword = checkKeyword(keyword);
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
        Log.d("chasClinic", "storage: " + storage.size());
    }

    public void processData() {
        int i = 0;
        double lng = 0.0;
        double lat = 0.0;
        List<Address> list;
        String name, postalCode, address, phoneNumber, operating_hour;
        Log.d("chasClinic", "processing the data");
        for (String a : storage) {
//            Log.d("chasClinic", "Enter loops");
            if (!a.equals(clinicName.get(i))) {
//                Log.d("chasClinic", "getting name1");
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
//                Log.d("chasClinic", "getting operating hour1");
                a = a.replace(phoneNumber, "");
                a = a.trim();
                operating_hour = a;
//                Log.d("chasClinic", "try to get lng and lat1");
                try {
//                    Log.d("chasClinic", "getting lng and lat1");
                    list = (geocoder.getFromLocationName(postalCode, 1));
                    lat = list.get(0).getLatitude();
//                    Log.d("chasClinic", "getting lat1:" + lat);
                    lng = list.get(0).getLongitude();
//                    Log.d("chasClinic", "getting lng1:" + lng);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                Log.d("chasClinic", "records1");
                clinicList.add(new Clinic(clinicName.get(i), address, postalCode, phoneNumber, operating_hour, lng, lat));
            } else {
//                Log.d("chasClinic", "getting operating hour2");
                operating_hour = a.substring(a.indexOf("Operating Hours: "), a.length());
                a = a.replace(operating_hour, "");
                a = a.trim();
                if (a.contains("Tel: ")) {
                    phoneNumber = a.substring((a.indexOf("Tel: ") + 5), a.length());
                    a = a.replace("Tel: ", "");
                } else {
                    phoneNumber = a.substring((a.indexOf("Tel No: ") + 8), a.length());
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
//                Log.d("chasClinic", "try to get lng and lat2");
                try {
//                    Log.d("chasClinic", "getting lng and lat2");
                    list = (geocoder.getFromLocationName(postalCode, 1));
//                    Log.d("chasClinic", "getting lat2:" + lat);
                    lng = list.get(0).getLongitude();
//                    Log.d("chasClinic", "getting lng2:" + lng);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                Log.d("chasClinic", "records2");
                clinicList.add(new Clinic(name, address, postalCode, phoneNumber, operating_hour, lng, lat));
            }
            i++;
        }
        storage.removeAll(storage);
        clinicName.removeAll(clinicName);
        Log.d("chasClinic", "processed all the data");
    }

    public void writeFile(String direction) {
        downloadCommand();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 10);
        Date EXPIRED = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(EXPIRED);
        StringBuilder data = new StringBuilder();
        data.append(strDate).append("\n");
        for (Clinic clinic : clinicList) {
            data.append(clinic.toString()).append("\n");
        }

        try {
            fos = mContext.openFileOutput(direction + ".txt", mContext.MODE_PRIVATE);
            fos.write(data.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void readFile(String direction) {
        FileInputStream fis = null;
        Log.d("storeDATA", "readingFile");
        File file = new File(mContext.getApplicationContext().getFilesDir(), direction + ".txt");
        Log.d("storeDATA", "The file exist: " + file.exists());
        if (file.exists()) {
            try {

                fis = mContext.openFileInput(direction + ".txt");
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                String text;
                String date = br.readLine();
                if (!isPackageExpired(date)) {
                    while ((text = br.readLine()) != null) {
                        text = text.replace("Clinic{", "");
                        text = text.replace("}", "");
                        String[] separated = text.split("~");
                        readClinic.add(new Clinic(separated[0], separated[1], separated[2], separated[3]
                                , separated[4], Double.valueOf(separated[5]), Double.valueOf(separated[6]), Double.valueOf(separated[7])));
                    }
                } else {
                    Log.d("storeDATA", "file date expired");
                    writeFile(direction);
                    readFile(direction);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else {
            Log.d("storeDATA", "file does not exist");
            writeFile(direction);
            readFile(direction);
        }

    }

    protected String downloadCommand() {
        String fileTodownload = "https://data.gov.sg/dataset/31e92629-980d-4672-af33-cec147c18102/download";
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
            output = new FileOutputStream(mContext.getApplicationContext().getFilesDir() + "/chas.kml");
            byte data[] = new byte[4096];
            long total = 0;
            double percent = 0.0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                percent = (double) (total * 100 / fileLength);
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

    public String checkKeyword(String keyword) {
        switch (keyword) {
            case "BeachRoad":
                    keyword = "Beach Road";
                break;
            case "BuonaVista":
                    keyword = "Buona Vista";
                break;
            case "BukitMerah":
                    keyword = "Bukit Merah";
                break;
            case "BukitTimah":
                    keyword = "Bukit Timah";
                break;
            case "CityHall":
                    keyword = "City Hall";
                break;
            case "DeportRoad":
                    keyword = "Deport Road";
                break;
            case "BedokReservoir":
                    keyword = "Bedok Reservoir";
                break;
            case "ChaiChee":
                keyword = "Chai Chee";
                break;
            case "JooChiat":
                keyword = "Joo Chiat";
                break;
            case "MarineParade":
                keyword = "Marine Parade";
                break;
            case "PasirRis":
                keyword = "Pasir Ris";
                break;
            case "PayaLebar":
                keyword = "Paya Lebar";
                break;
        }
        return keyword;
    }

    public boolean fileExist(String direction) {
        File file = new File(mContext.getApplicationContext().getFilesDir(), direction + ".txt");
        Log.d("storeDATA", "The file exist: " + file.exists());
        if (file.exists()) {
            return true;
        }
        return false;
    }

    private boolean isPackageExpired(String date) {
        boolean isExpired = false;
        Date expiredDate = stringToDate(date, "yyyy-MM-dd");
        if (new Date().after(expiredDate)) isExpired = true;

        return isExpired;
    }

    private Date stringToDate(String aDate, String aFormat) {

        if (aDate == null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;

    }

    public void distSearch() {
        StringBuilder urlBuilder = new StringBuilder();
        StringBuilder stringBuilder = new StringBuilder();
        boolean operating = true;
        int index = 0;
        int index2 = 0;
        while (operating) {
            urlBuilder.delete(0, urlBuilder.length());
            urlBuilder.append("https://maps.googleapis.com/maps/api/distancematrix/json?origins="
                    + user_LatLng.latitude + "," + user_LatLng.longitude +
                    "&destinations=");
            for (int a = 0; a < 25; a++) {
                if (index < readClinic.size()) {
                    urlBuilder.append(readClinic.get(index).getLatitude() + "," + readClinic.get(index).getLongitude() + "|");
                } else {
                    break;
                }
                index++;
            }
            urlBuilder.deleteCharAt((urlBuilder.length() - 1));
            urlBuilder.append("&key=AIzaSyCkU5Dt6se9ziYISEEGXse6nxRAQud5awk");
//            Log.d("ReturnResult", urlBuilder.toString());
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
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject = new JSONObject(stringBuilder.toString());
                    JSONArray rowList = jsonObject.getJSONArray("rows");
//                Log.d("ReturnResult", rowList.toString());
                    JSONObject row = rowList.getJSONObject(0);
//                Log.d("ReturnResult", "row: " +row.toString());
                    JSONArray elements = row.getJSONArray("elements");
//                Log.d("ReturnResult", "elements: " + elements.toString());
                    for (int i = 0; i < 25; i++) {
                        if (index2 < readClinic.size()) {
                            JSONObject element = elements.getJSONObject(i);
//                    Log.d("ReturnResult", "element single record: " + element.toString());
                            JSONObject distanceRecord = element.getJSONObject("distance");
//                    Log.d("ReturnResult", "distance Record: " + distanceRecord.toString());
                            Double distanceText = distanceRecord.getDouble("value");
//                    Log.d("ReturnResult", "clinic index " + index2 + " distance: " + distanceText);
                            readClinic.get(index2).setDistance(distanceText);
                        }
                        index2++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (reader != null) {
                    reader.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (index >= readClinic.size())
                operating = false;
        }
    }


    public void readKML() {
        String kmlData = null;
        try {
            File file = new File((mContext.getApplicationContext().getFilesDir() + "/chas.kml"));
            InputStream InputStream = new FileInputStream(file);
            int size = InputStream.available();
            byte[] buffer = new byte[size];
            InputStream.read(buffer);
            InputStream.close();
            kmlData = new String(buffer);
            Document doc = Jsoup.parse(kmlData, "", Parser.xmlParser());
            int i = 0;
            String name = "";
            String postal = "";
            Elements e = doc.select("ExtendedData").select("SchemaData");
            Elements chasName = e.select("SimpleData[name=\"HCI_NAME\"]");
            Elements chasPostal = e.select("SimpleData[name=\"POSTAL_CD\"]");

            for (Element a : chasName) {
                name = a.text();
                postal = chasPostal.get(i).text();
                chasClinic.add(new Clinic(name.toUpperCase(), postal));
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void compare() {
        int i = 1;
        for (Clinic c : readClinic) {
            String name = c.getName().toUpperCase();
            String postal = c.getPostalCode();
            postal = postal.replace("Singapore ", "");
            postal = postal.trim();
            for (Clinic chas : chasClinic) {
                String chasPostal = chas.getPostalCode();
                if (((chas.getName()).contains(name)) && chasPostal.equals(postal)) {
                    surroundingChas.add(c);
                }
                i++;
            }
        }
    }

    private class dataOperation extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            Log.d("chasClinic", "testingPreExecute");
            calculatingDirection();
        }


        @Override
        protected Void doInBackground(String... strings) {
            Log.d("chasClinic", "testingDoInBackground");

            for (String a : direction) {
                if (!fileExist(a)) {
                    Log.d("chasClinic", "locatingName");
                    locatingName(a);
                    Log.d("chasClinic", "siteRetrieve");
                    siteRetrieve();
                    Log.d("chasClinic", "processData");
                    processData();
                    Log.d("chasClinic", "writeFile");
                    writeFile(a);
                    Log.d("chasClinic", "clinicList size is: " + clinicList.size());
                    clinicList.removeAll(clinicList);
                    Log.d("chasClinic", "locationAL size was: " + locationAL.size());
                    locationAL.removeAll(locationAL);

                }
                if (fileExist(a)) {
                    Log.d("chasClinic", "reading file");
                    readFile(a);
                }
            }

            Log.d("chasClinic", "readKML");
            readKML();

            Log.d("chasClinic", "distSearch");
            distSearch();

            Log.d("chasClinic", "sorting");
            Collections.sort(readClinic, new Comparator<Clinic>() {
                @Override
                public int compare(Clinic o1, Clinic o2) {
                    return Double.compare(o1.getDistance(), o2.getDistance());
                }
            });

            s_chasinfo = CurrentPatient.getChasInfo();
            Log.d("chasClinic", "s_chasinfo: " + CurrentPatient.getChasInfo());
            Log.d("chasClinic", "s_chasinfo: " + s_chasinfo.equals("Yes"));
            if (s_chasinfo.equals("Yes")){
                compare();
                for (int i = 0; i < 10; i++) {
                    clinicList.add(surroundingChas.get(i));
                }
            }
            else{
                for (int i = 0; i < 10; i++) {
                    clinicList.add(readClinic.get(i));
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            Log.d("chasClinic", "testingOnPostExecute");
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent("com.blah.DISMISS_DIALOG"));
            LocateClinicActivity myFragment = new LocateClinicActivity();
            Bundle arguments = new Bundle();
            Log.d("chasClinic", "readClinic: " + readClinic.size());
            Log.d("chasClinic", "surroundingChas: " + surroundingChas.size());
            Log.d("chasClinic", "resultClinic: " + clinicList.size());
            arguments.putSerializable("ListClinic", clinicList);
            myFragment.setArguments(arguments);
            fragmentManager.beginTransaction().replace(R.id.fragment_container, myFragment).commit();
        }
    }


}
