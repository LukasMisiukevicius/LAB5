package com.example.lab5;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.widget.Spinner;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataLoader extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {
        String s = strings[0];
        try {
            DataManager dm = new DataManager();
            return dm.getRates(s);
        }
        catch (Exception e){
            return e.toString();
        }
    }


}
