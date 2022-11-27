package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import kotlinx.coroutines.MainCoroutineDispatcher;

public class MainActivity extends AppCompatActivity {

    Button btnMain, buttonGetCurr;
    TextView tvResult, tvChange;
    Spinner spChoice;
    EditText etChange;
    String result = "0";
    String curr = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMain = findViewById(R.id.btnMain);
        tvResult = findViewById(R.id.tvResult);
        spChoice = findViewById(R.id.spChoice);
        etChange = findViewById(R.id.etInput);
        tvChange = findViewById(R.id.tvChange);
        tvChange.setText("0.00 " + curr);
        buttonGetCurr = findViewById(R.id.buttonGetCurr);

        buttonGetCurr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrenciesToSpinner();
            }
        });

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataLoader dl = new DataLoader();
                try {
                    etChange.setText("");
                    try{
                        curr = spChoice.getSelectedItem().toString();
                        result = dl.doInBackground(curr);
                        tvResult.setText("1 EUR = " + result + " " + curr);
                        tvChange.setText("0.00 " + curr);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(MainActivity.this,"No currency selected", Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    tvResult.setText(String.valueOf(e));
                }

            }
        });
        etChange.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = etChange.getText().toString();
                float calculation = 0;
                float amount = 0;
                try{

                    if(!text.isEmpty())
                    {
                        try{
                            amount = Float.parseFloat(text);
                            calculation = amount * Float.parseFloat(result);
                        }
                        catch (Exception e)
                        {}
                    }
                    String resultFinal = String.format("%.02f", calculation);
                    tvChange.setText(resultFinal + " " + curr);

                }
                catch (Exception e)
                {
                    Toast.makeText(MainActivity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                    tvChange.setText(String.valueOf(String.valueOf(e)));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
    public void getCurrenciesToSpinner()
    {
        DataManager dm = new DataManager();
        DataParser dp = new DataParser();
        try {
            InputStream is = dm.apiRequest();
            List<String> currencies = dp.getCurrencies(is);
            if(!currencies.isEmpty()){
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,currencies);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spChoice.setAdapter(adapter);
            }
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();

        }
    }
}