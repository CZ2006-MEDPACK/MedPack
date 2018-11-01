package com.example.csyvi.medpack;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ClinicIO {
    private ArrayList<String> locationAL = new ArrayList<>();
    private ArrayList<String> storage = new ArrayList<>();
    private ArrayList<String> clinicName = new ArrayList<>();
    private ArrayList<Clinic> clinicList = new ArrayList<>();
    private String direction;
    private Context mContext;
    private Geocoder geocoder;

    //find all the direction indicated private clinic and write into txt file
    //when search for clinic, need get user gps location then direction
    //check if the file exist and expiry
    //if exist and not expire, read from textfile
    //else search and write the text file again

    public ClinicIO(Context mContext) {
        this.mContext = mContext;
    }

    public void locatingName(String direction) {
        this.direction = direction;
        locationAL.clear();
        clinicList.clear();
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
        File file = new File(mContext.getApplicationContext().getFilesDir(),direction+".txt");
        Log.d("storeDATA", "The file exist: " + file.exists());
        if (file.exists()) {
            try {

                fis = mContext.openFileInput(direction + ".txt");
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String text;
                String date = br.readLine();
                if (!isPackageExpired(date)) {
                    while ((text = br.readLine()) != null) {
                        sb.append(text).append("\n");
                    }
                    Log.d("storeDATA", "sb: " + sb.toString());
//            Toast.makeText(mContext, sb.toString(), Toast.LENGTH_LONG).show();
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

    public boolean fileExist(String direction){
        File file = new File(mContext.getApplicationContext().getFilesDir(),direction+".txt");
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
}
