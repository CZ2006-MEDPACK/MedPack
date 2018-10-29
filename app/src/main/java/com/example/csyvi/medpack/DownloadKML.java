package com.example.csyvi.medpack;

import android.content.Context;
import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DownloadKML {
    private Context mContext;
    private String fileTodownload = "https://data.gov.sg/dataset/31e92629-980d-4672-af33-cec147c18102/download";
    ArrayList<String> chasClinic = new ArrayList<>();

    public ArrayList<String> getChasClinic() {
        return chasClinic;
    }

    public DownloadKML(Context mContext) {
        this.mContext = mContext;

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
            for (Element e : doc.select("ExtendedData").select("SchemaData").select("SimpleData")){
                chasClinic.add(e.getElementsByAttributeValue("name", "HCI_NAME").text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
