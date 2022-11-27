package com.example.lab5;

import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataManager {
    String reqUrl = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";

    public InputStream apiRequest() throws IOException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL url = new URL(reqUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
    return conn.getInputStream();
    }

    public String getRates(String currencyCode) throws IOException {
        String rate = "Empty";
        InputStream stream = apiRequest();
        try {
            rate = DataParser.getRateFromECB(stream, currencyCode);
        }
        finally {
            if (stream != null) {
                stream.close();
            }
        }
        return rate;
    }
}



