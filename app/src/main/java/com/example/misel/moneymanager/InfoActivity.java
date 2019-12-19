package com.example.misel.moneymanager;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.InputStream;

public class InfoActivity extends AppCompatActivity {
    TextView readTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        readTxt = findViewById(R.id.txtRead);
        String result;
        try {
            Resources res = getResources();
            InputStream in_s = res.openRawResource(R.raw.about);

            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            result = new String(b);
            readTxt.setText(result);
        } catch (Exception e) {
            // e.printStackTrace();
            result = "Error: can't show file.";
        }



    }
}
