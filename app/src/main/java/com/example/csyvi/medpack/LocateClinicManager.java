package com.example.csyvi.medpack;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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

import static android.content.Context.LOCATION_SERVICE;

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
    LocationManager locationManager;
    LocationListener locationListener;
    android.support.v4.app.FragmentManager fragmentManager;
    MapsActivity myFragment;
    ProgressDialog myProgress;

    //use either surroundingChas or clinicList to populate the surrounding general practitioner
    private ArrayList<Clinic> surroundingChas = new ArrayList<>();
    private ArrayList<Clinic> clinicList = new ArrayList<>();

    public LocateClinicManager(Context mContext, android.support.v4.app.FragmentManager fragmentManager, MapsActivity myFragment, ProgressDialog myProgress) {
        this.mContext = mContext;
        geocoder = new Geocoder(mContext);
        this.fragmentManager = fragmentManager;
        this.myFragment = myFragment;
        this.myProgress = myProgress;
    }


    public void compare() {
        for (Clinic c : clinicList) {
            String name = c.getName().toUpperCase();
            String postal = c.getPostalCode();
            postal = postal.replace("Singapore ", "");
            postal = postal.trim();
            for (Clinic chas : chasClinic) {
                String chasPostal = chas.getPostalCode();
                if (((chas.getName()).contains(name)) && chasPostal.equals(postal)) {
                    surroundingChas.add(c);
                }
            }
        }

        for (Clinic a : surroundingChas) {
            Log.d("chasClinic", "surroundingChas: " + a.getName() + ", " + a.getDistance());
        }
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
        locationAL.clear();
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
    }

    public void siteRetrieve() {
        for (String s : locationAL) {
            Log.d("storeDATA", s);
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
    }

    public void processData() {

        int i = 0;
        String name, postalCode, address, phoneNumber, operating_hour;
        for (String a : storage) {
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
            } else {
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
                clinicList.add(new Clinic(name, address, postalCode, phoneNumber, operating_hour));
            }
            i++;
        }
    }

    public void latLngChecker() {
        geocoder = new Geocoder(mContext);
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

    public void writeFile(String direction) {
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

        FileOutputStream fos = null;

        try {
            Log.d("storeDATA", "writing");
            fos = mContext.openFileOutput(direction + ".txt", mContext.MODE_PRIVATE);
            fos.write(data.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
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
                        Log.d("storeDATA", "text1: " + text);
                        text = text.replace("Clinic{", "");
                        Log.d("storeDATA", "text2: " + text);
                        text = text.replace("}", "");
                        Log.d("storeDATA", "text3: " + text);
                        String[] separated = text.split("~");
                        Log.d("storeDATA", "separated size: " + separated.length);
                        clinicList.add(new Clinic(separated[0], separated[1], separated[2], separated[3]
                                , separated[4], Double.valueOf(separated[5]), Double.valueOf(separated[6]), Double.valueOf(separated[7])));
                    }
                } else {
                    Log.d("storeDATA", "file date expired");
                    downloadCommand();
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
            downloadCommand();
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
            Log.d("downloaded", String.valueOf(mContext.getApplicationContext().getFilesDir()) + "/chas.kml");
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

        Log.d("storeDATA", expiredDate.toString());
        Log.d("storeDATA", new Date().toString());

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
                if (index < clinicList.size()) {
                    urlBuilder.append(clinicList.get(index).getLatitude() + "," + clinicList.get(index).getLongitude() + "|");
                } else {
                    break;
                }
                index++;
            }
            urlBuilder.deleteCharAt((urlBuilder.length() - 1));
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
                        if (index2 < clinicList.size()) {
                            JSONObject element = elements.getJSONObject(i);
//                    Log.d("ReturnResult", "element single record: " + element.toString());
                            JSONObject distanceRecord = element.getJSONObject("distance");
//                    Log.d("ReturnResult", "distance Record: " + distanceRecord.toString());
                            Double distanceText = distanceRecord.getDouble("value");
//                    Log.d("ReturnResult", "clinic index " + index2 + " distance: " + distanceText);
                            clinicList.get(index2).setDistance(distanceText);
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
            if (index >= clinicList.size())
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
//                    Log.d("chasClinic", "chasName: " + name + " | chasPostal: " + postal);
                chasClinic.add(new Clinic(name.toUpperCase(), postal));
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private class dataOperation extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(String... strings) {
            Log.d("chasClinic", "testingDoInBackground");
            readKML();
            calculatingDirection();
            for (String a : direction) {
                if (fileExist(a)) {
                    readFile(a);
                } else {
                    locatingName(a);
                    siteRetrieve();
                    processData();
                    latLngChecker();

                    Collections.sort(clinicList, new Comparator<Clinic>() {
                        @Override
                        public int compare(Clinic o1, Clinic o2) {
                            return (Double.toString(o1.getDistance())).compareTo(Double.toString(o2.getDistance()));
                        }
                    });

                    writeFile(a);
                }
            }
            distSearch();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            compare();
            Log.d("chasClinic", "testingOnPostExecute");

            //if user have chas then take 10 record from surroundingChas
            //else take 10 from clinicList

            MapsActivity myFragment = new MapsActivity();
            Bundle arguments = new Bundle();
            Log.d("chasClinic" , ""+surroundingChas.size());
            Log.d("chasClnic", ""+clinicList.size());
            arguments.putSerializable("ListClinic", clinicList);
            myFragment.setArguments(arguments);
//            Log.d("chasClinic", "test");
            fragmentManager.beginTransaction().replace(R.id.fragment_container, myFragment).commit();
//            Log.d("chasClinic", "test");

        }
    }


}
